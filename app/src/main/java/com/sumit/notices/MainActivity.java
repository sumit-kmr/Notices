package com.sumit.notices;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private LinearLayout progressBar;
    private ArrayList<NoticeItem> studentNotices = new ArrayList<>();
    private ArrayList<NoticeItem> examNotices = new ArrayList<>();
    private FetchNotice fetchNotice = new FetchNotice();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            bottomNavigationView = findViewById(R.id.bottom_nav_bar);
            progressBar = findViewById(R.id.progress_linear_layout);
            fetchNotice.execute();
        } catch(Exception e) {
            fetchNotice.execute();
        }
    }

    private void setupNav() {
        try {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, new StudentFragment(this, studentNotices), null)
                    .commit();

            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_student_notice:
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment_container_view, new StudentFragment(this, studentNotices), null)
                                .commit();
                        break;

                    case R.id.menu_exam_notice:
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment_container_view, new ExamFragment(this, examNotices), null)
                                .commit();
                        break;
                }
                return true;
            });
        } catch(Exception e) {
            fetchNotice.execute();
        }
    }

    class FetchNotice extends AsyncTask<Object, Object, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... objects) {
            try {
                URL studentNoticeUrl = new URL("https://www.soa.ac.in/iter-student-notice/?format=rss");
                URL examNoticeUrl = new URL("https://www.soa.ac.in/iter-exam-notice?format=rss");
                XmlPullParser xpp = XmlPullParserFactory
                        .newInstance()
                        .newPullParser();
                // fetching student notices
                xpp.setInput(studentNoticeUrl.openConnection().getInputStream(), "UTF_8");
                int eventType = xpp.getEventType();
                boolean insideItem = false;
                NoticeItem noticeItem = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                            noticeItem = new NoticeItem();
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                noticeItem.setTitle(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                noticeItem.setLink(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            if (insideItem) {
                                String fullDate = xpp.nextText();
                                String truncatedDate = fullDate.substring(4, 17);
                                noticeItem.setDate(truncatedDate);
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                        noticeItem.setType("student");
                        studentNotices.add(noticeItem);
                    }
                    eventType = xpp.next();
                }

                // fetching exam notices
                xpp.setInput(examNoticeUrl.openConnection().getInputStream(), "UTF_8");
                 eventType = xpp.getEventType();
                 insideItem = false;
                 noticeItem = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                            noticeItem = new NoticeItem();
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                noticeItem.setTitle(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                noticeItem.setLink(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            if (insideItem) {
                                String fullDate = xpp.nextText();
                                String truncatedDate = fullDate.substring(4, 17);
                                noticeItem.setDate(truncatedDate);
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                        noticeItem.setType("exam");
                        examNotices.add(noticeItem);
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
            return "Success";
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String message = (String) o;
            if(message.equalsIgnoreCase("Error")) {
                Toast.makeText(MainActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                return;
            }
            setupNav();
            progressBar.setVisibility(View.GONE);
        }
    }
}