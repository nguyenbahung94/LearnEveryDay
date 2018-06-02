package com.example.nbhung94.customrecyclerview.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.nbhung94.customrecyclerview.R;
import com.example.nbhung94.customrecyclerview.recyclerview.helper.OnItemCLickListener;
import com.example.nbhung94.customrecyclerview.recyclerview.helper.OnStartDragListener;
import com.example.nbhung94.customrecyclerview.recyclerview.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 11/15/2017.
 */

public class ActivityRecycleView extends AppCompatActivity implements OnStartDragListener, OnItemCLickListener {
    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    private recycleViewAdapter mRecycleViewAdapter;
    private ItemTouchHelper mItemTouchHelper;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        mRecyclerView = findViewById(R.id.mRecycleView);
        mList.add("hello1");
        mList.add("hello2");
        mList.add("hello3");
        mList.add("hello4");
        mList.add("hello5");
        mList.add("hello6");
        mList.add("hello7");
        mList.add("hello8");
        mList.add("hello9");
        mList.add("hello10");
        mRecycleViewAdapter = new recycleViewAdapter(mList, ActivityRecycleView.this, this);
        mRecycleViewAdapter.setiItemClick(this);
        //   RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //    mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecycleViewAdapter);

        //set up gird view
        final int spanCount = 2;
        final GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(layoutManager);


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mRecycleViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "postiiton =" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this, "postiiton =" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoteListChanged(List<String> strings) {
        mList.clear();
        mList.addAll(strings);
    }


}
