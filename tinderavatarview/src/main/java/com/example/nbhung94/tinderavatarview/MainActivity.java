package com.example.nbhung94.tinderavatarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.athbk.avatarview.TinderRecyclerView;
import com.example.nbhung94.tinderavatarview.Adapter.CustomAdapter;
import com.example.nbhung94.tinderavatarview.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Item> itemList = new ArrayList<>();
    private TinderRecyclerView recyclerView;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);
        initData();
        customAdapter = new CustomAdapter(this, itemList);
        recyclerView.initRecyclerView(this, customAdapter);
    }

    private void initData() {
        Item item = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 0);
        Item item1 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 1);
        Item item2 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 2);
        Item item3 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 3);
        Item item4 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 4);
        Item item5 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 5);
        Item item6 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 6);
        Item item7 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 7);
        Item item8 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 8);
        Item item9 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 9);
        Item item10 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 10);
        Item item11 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 11);
        Item item12 = new Item("https://cdn-images-1.medium.com/max/2000/1*LMtdnSjMi9HvMmasXY0kRQ.png", 12);
        itemList.add(item);
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item4);
        itemList.add(item5);
        itemList.add(item6);
        itemList.add(item7);
        itemList.add(item8);
        itemList.add(item9);
        itemList.add(item10);
        itemList.add(item11);
        itemList.add(item12);
    }
}
