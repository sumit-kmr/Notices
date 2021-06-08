package com.sumit.notices;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class StudentFragment extends Fragment {
    private Context context;
    private ArrayList<NoticeItem> studentNotices;
    private ListView listView;

    public StudentFragment(Context ctx, ArrayList<NoticeItem> notices) {
        context = ctx;
        studentNotices = new ArrayList<NoticeItem>(notices);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View root = inflater.inflate(R.layout.fragment_student, container, false);
       listView = root.findViewById(R.id.student_notice_list_view);
       ListAdapter adapter = new ListAdapter(context, R.layout.list_item, studentNotices);
       listView.setAdapter(adapter);
       return root;
    }
}