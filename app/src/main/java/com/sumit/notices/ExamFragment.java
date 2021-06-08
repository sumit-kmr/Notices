package com.sumit.notices;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ExamFragment extends Fragment {
    Context context;
    ArrayList<NoticeItem> examNotices;
    ListView listView;

    public ExamFragment(Context ctx, ArrayList<NoticeItem> notices) {
        context = ctx;
        examNotices = notices;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exam, container, false);
        listView = root.findViewById(R.id.exam_notice_list_view);
        ListAdapter adapter = new ListAdapter(context, R.layout.list_item, examNotices);
        listView.setAdapter(adapter);
        return root;
    }
}