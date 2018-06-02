package com.ekakashi.greenhouse.features.system_news;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.recipe.add_field_greenhouse.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SystemNewsActivity extends BaseActivity implements SystemNewsInterface.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvSystemNews;
    private SystemNewsInterface.Presenter mPresenter;
    private LinkedHashMap<Integer, SystemNews.Data> systemNewsList = new LinkedHashMap<>();
    private EndlessRecyclerOnScrollListener mScrollListener = null;
    public static final String SYSTEM_NEWS = "SystemNews";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private SystemNewsAdapter adapter;

    private int currentPage = 1, recordPerPage = 100, numberItemReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_news);
        addToolbar();
        initPresenter();
        addControls();
        addEvents();
    }

    private void addControls() {
        rvSystemNews = findViewById(R.id.rvSystemNews);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);

        layoutManager = new LinearLayoutManager(this);
        adapter = new SystemNewsAdapter(this, systemNewsList);
    }

    private void addEvents() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light);

        mScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                // TODO LOAD MORE
                if (numberItemReturn == recordPerPage) {
                    if (isNetworkOffline()) {
                        return;
                    }
                    showLoadingDialog(getString(R.string.message_please_wait));
                    mPresenter.getNotification(currentPage + 1, recordPerPage);
                }//numberItemReturn < recordPerPage. => No item to load more
            }
        };

        rvSystemNews.addOnScrollListener(mScrollListener);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.system_news), "");
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {

            }
        });
    }

    private void setUpRecyclerView(LinkedHashMap<Integer, SystemNews.Data> list) {
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSystemNews.setLayoutManager(layoutManager);
        adapter.setNews(list);

        rvSystemNews.setAdapter(adapter);
        rvSystemNews.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initPresenter() {
        mPresenter = new SystemNewsPresenter( this);
        mPresenter.getNotification(currentPage, recordPerPage);
    }

    @Override
    public void onNotificationSuccess(ArrayList<SystemNews.Data> list) {
        hideLoadingDialog();
        mScrollListener.setLoading(false);

        if (list != null && !list.isEmpty()) {
            numberItemReturn = list.size();
            if (numberItemReturn != 0) {
                //PAGE = 1
                if (currentPage == 1) {
                    systemNewsList = convertArrayToLinkedHashMap(list);
                    setUpRecyclerView(systemNewsList);
                }
                //PAGE 2
                else {
                    LinkedHashMap<Integer, SystemNews.Data> map = convertArrayToLinkedHashMap(list);
                    for (Integer newsID : map.keySet()) {
                        if (systemNewsList.containsKey(newsID)) {
                            systemNewsList.remove(newsID);
                        }
                        systemNewsList.put(newsID, map.get(newsID));
                    }
                    adapter.notifyDataSetChanged();
                }
                currentPage++;
            }
            mPresenter.updateSystemNews();
        }

    }

    private LinkedHashMap<Integer, SystemNews.Data> convertArrayToLinkedHashMap(ArrayList<SystemNews.Data> recipeList) {
        LinkedHashMap<Integer, SystemNews.Data> recipeLinkedHashMap = new LinkedHashMap<>();
        for (SystemNews.Data systemNews : recipeList) {
            recipeLinkedHashMap.put(systemNews.getId(), systemNews);
        }
        return recipeLinkedHashMap;
    }

    @Override
    public void onNotificationFail(String message) {
        mScrollListener.setLoading(false);
        hideLoadingDialog();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateSuccess() {
        Prefs.getInstance(this).saveBadgeSetting(0);
    }

    @Override
    public void onUpdateFail() {

    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.getNotification(currentPage, recordPerPage);

        // after refresh is done, remember to call the following code
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);  // This hides the spinner
        }
    }

}
