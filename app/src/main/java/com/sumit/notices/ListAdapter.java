package com.sumit.notices;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;

public class ListAdapter extends ArrayAdapter<NoticeItem> {
    private ArrayList<NoticeItem> mList;
    private Context mContext;
    private int mResource;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<NoticeItem> objects) {
        super(context, resource, objects);
        mList = new ArrayList<>(objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(mResource, null);
        }

        NoticeItem notice = getItem(position);

        if (notice != null) {
            TextView tt1 =  v.findViewById(R.id.notice_title_text_view);
            TextView tt2 =  v.findViewById(R.id.notice_date_text_view);
            CardView cardView = v.findViewById(R.id.list_item_card);

            if (tt1 != null) {
                tt1.setText(notice.getTitle());
            }

            if (tt2 != null) {
                tt2.setText(notice.getDate());
            }

            if(cardView != null) {
                cardView.setOnClickListener(l -> {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(mContext, Uri.parse(notice.getLink()));
                });
            }
        }

        return v;
    }
}
