package com.ekakashi.greenhouse.features.timeline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.RoleOfUser;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.dao.RecentSearch;
import com.ekakashi.greenhouse.database.model_server_response.ChangeStageRecipeResponse;
import com.ekakashi.greenhouse.features.add_field_greenhouse.EKMainFieldActivity;
import com.ekakashi.greenhouse.features.recipe.add_field_greenhouse.EndlessRecyclerOnScrollListener;
import com.ekakashi.greenhouse.features.timeline.adapters.RecentSearchAdapter;
import com.ekakashi.greenhouse.features.timeline.adapters.TimelineAdapter;
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;
import com.ekakashi.greenhouse.features.timeline.timeline_detail.TimelineDetailActivity;
import com.ekakashi.greenhouse.features.timeline_filter.FilterActivity;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterMember;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterModel;
import com.ekakashi.greenhouse.features.timeline_post.EKTimelinePostActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class TimelineFragment extends Fragment implements TimelineInterface.View, View.OnClickListener,
        TimelineInterface.RecentSearchClick, TimelineInterface.TimelineAdapterCallback, SwipeRefreshLayout.OnRefreshListener, TextWatcher {
    private View view;

    private RelativeLayout layoutFilter;
    private ImageView imgFilter;
    private ImageView imgFilterClear;
    private ImageView imgSearchClose;
    private EditText edSearch;

    private RecyclerView rvTimeline;
    private RecyclerView rvRecentSearch;
    private EndlessRecyclerOnScrollListener mScrollListener = null;
    private Toolbar mToolbar;
    private LinkedHashMap<String, TimelineResponse.ListTimeline> listTimeline = new LinkedHashMap<>();
    private TimelineAdapter timelineAdapter;
    private RecentSearchAdapter searchAdapter;
    private SwipeRefreshLayout srLayout;

    private TimelineInterface.Presenter mPresenter;
    private LinearLayoutManager layoutManager;

    private EKMainFieldActivity mActivity;
    private ViewGroup vgFilter;
    private FilterModel mFilterModel;

    private int REQUEST_CODE_FILTER = 99;
    private int REQUEST_CODE_POST = 100;
    private int REQUEST_CODE_READ_MORE = 101;

    private int currentPage = 0, numberItemReturn;
    private int timelinePosition = 0;
    boolean isLoadFragmentFirstTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_timeline, container, false);
            addControls();
            addEvents();
            initPresenter();
            setUpToolbar();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        view.findViewById(R.id.viewForFocus).setFocusable(true);

        isLoadFragmentFirstTime = true;
        edSearch.addTextChangedListener(this);
        if(Prefs.getInstance(getActivity()).getReloadTimeline()){
            reloadTimeline();
            Prefs.getInstance(getActivity()).saveReloadTimeline(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isLoadFragmentFirstTime) {
            isLoadFragmentFirstTime = false;
        } else {
            String text = s.toString();
            imgSearchClose.setVisibility(text.isEmpty() ? View.INVISIBLE : View.VISIBLE);
            showRecentSearch(App.getDatabaseHelper(getApplicationContext()).getRecentSearch()
                    .getTextSearchByUserIdAndLikeKeySearch(Prefs.getInstance(getApplicationContext()).getUserId(), text.trim()));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onAttach(Context context) {
        mActivity = (EKMainFieldActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        mActivity = null;
        edSearch.removeTextChangedListener(this);
        super.onDetach();
    }

    private void setUpToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.title_timeline), null);
        if(RoleOfUser.TimeLine.PostonTimeLine(getContext()))
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_post_diary));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
            }

            @Override
            public void onToolbarRightListener() {
                if (mActivity != null) {
                    mActivity.startActivityForResult(new Intent(mActivity, EKTimelinePostActivity.class), REQUEST_CODE_POST);
                }
            }
        });
    }

    private void addControls() {
        mToolbar = view.findViewById(R.id.toolbar_main);
        rvTimeline = view.findViewById(R.id.rvTimeline);
        rvRecentSearch = view.findViewById(R.id.rvRecentSearch);
        srLayout = view.findViewById(R.id.srLayout);
        layoutFilter = view.findViewById(R.id.layoutFilter);

        imgFilter = view.findViewById(R.id.imgFilter);
        imgFilterClear = view.findViewById(R.id.imgFilterClear);
        imgSearchClose = view.findViewById(R.id.imgSearchClose);
        edSearch = view.findViewById(R.id.edSearch);
        layoutManager = new LinearLayoutManager(mActivity);
    }

    private void addEvents() {
        imgFilter.setOnClickListener(this);
        imgFilterClear.setOnClickListener(this);
        imgSearchClose.setOnClickListener(this);
        edSearch.setOnClickListener(this);
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        edSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) return;
                onClick(v);
            }
        });

        //---------------------------------------------------------------

        mScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMore();
            }
        };

        rvTimeline.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                rvRecentSearch.setVisibility(View.GONE);
            }
        });

        rvTimeline.addOnScrollListener(mScrollListener);

        srLayout.setColorSchemeColors(Utils.getColor(mActivity, R.color.bg_green_btn));
        srLayout.setOnRefreshListener(this);
    }

    private void performSearch() {
        if (mActivity == null) {
            return;
        }
        rvRecentSearch.setVisibility(View.GONE);

        String search = edSearch.getText().toString().trim();
        mFilterModel.setSearchText(search);
        if (!search.isEmpty()) {
            RecentSearch recentSearch = new RecentSearch(Prefs.getInstance(getActivity()).getUserId(), search);
            try {
                App.getDatabaseHelper(getApplicationContext()).getRecentSearch().createOrUpdate(recentSearch);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        onRefresh();
    }

    private void showRecentSearch(List<RecentSearch> searchList) {
        if (searchList == null || searchList.isEmpty() || mActivity == null) {
            rvRecentSearch.setVisibility(View.GONE);
            return;
        }
        rvRecentSearch.setVisibility(View.VISIBLE);
        if (searchAdapter == null) {
            searchAdapter = new RecentSearchAdapter(searchList, this, mActivity);

//            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            LinearLayoutManager layoutManagerRecentSearch = new LinearLayoutManager(mActivity);
            rvRecentSearch.setLayoutManager(layoutManagerRecentSearch);
            rvRecentSearch.setItemAnimator(new DefaultItemAnimator());

            rvRecentSearch.setAdapter(searchAdapter);
        } else {
            searchAdapter.updateSearchList(searchList);
        }
    }

    @Override
    public void onRecentSearchClick(String search) {
        if (mActivity == null) {
            return;
        }
        edSearch.setText(search);
        mFilterModel.setSearchText(search);
        rvRecentSearch.setVisibility(View.GONE);
        onRefresh();
    }

    private void displayTimeLineList() {
        if (timelineAdapter == null) {
            timelineAdapter = new TimelineAdapter(mActivity, this,
                    listTimeline, Prefs.getInstance(mActivity.getApplicationContext()).getUserId());

//            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvTimeline.setLayoutManager(layoutManager);

            rvTimeline.setAdapter(timelineAdapter);
            rvTimeline.setItemAnimator(new DefaultItemAnimator());

        } else {
            timelineAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            mFilterModel = new FilterModel();
            mPresenter = new TimelinePresenter(this);
            //init listView, pull to refresh and load more
            // displayTimeLineList();
            onRefresh();
        }
    }

    @Override
    public void TimelineResult(ArrayList<TimelineResponse.ListTimeline> list) {
        if (mActivity == null) {
            return;
        }
        if (list != null) {
            numberItemReturn = list.size();

            if (numberItemReturn != 0) {
                currentPage++;
                //PAGE = 1
                if (currentPage == 1) {
                    listTimeline.clear();
                    listTimeline.putAll(convertArrayToLinkedHashMap(list));
                    displayTimeLineList();
                } else {
                    LinkedHashMap<String, TimelineResponse.ListTimeline> map = convertArrayToLinkedHashMap(list);
                    for (String timelineId : map.keySet()) {
                        if (listTimeline.containsKey(timelineId)) {
                            listTimeline.remove(timelineId);
                        }
                        listTimeline.put(timelineId, map.get(timelineId));
                    }
                    timelineAdapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(mActivity, R.string.timeline_no_record, Toast.LENGTH_SHORT).show();
                listTimeline.clear();
                displayTimeLineList();
            }
        }
        mActivity.hideLoadingDialog();
        mScrollListener.setLoading(false);
    }

    @Override
    public void TimelineFail(String message) {
        if (mActivity == null) {
            return;
        }
        mActivity.hideLoadingDialog();
        mScrollListener.setLoading(false);
        if (message != null) {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void moveToNextStageSuccess(ChangeStageRecipeResponse.Data data) {
        TimelineResponse.ListTimeline timeline = (TimelineResponse.ListTimeline) listTimeline.values().toArray()[timelinePosition];
        TimelineResponse.Notification notification = timeline.getNotification();

        /*for (String timelineId : map.keySet()) {
                        if (listTimeline.containsKey(timelineId)) {
                            listTimeline.remove(timelineId);
                        }
                        listTimeline.put(timelineId, map.get(timelineId));
                    }*/
        for (String timelineId : listTimeline.keySet()) {
            TimelineResponse.Notification item = listTimeline.get(timelineId).getNotification();
            if (item != null) {
                if (item.getRecipeId() == notification.getRecipeId() && item.getCurrentGrowthStageId() == notification.getCurrentGrowthStageId()) {
                    item.setProcessToNextStage(Utils.MoveToNextStage.UNQUALIFIED_COMPLETED_STAGE);
                }
            }
        }

        timelineAdapter.notifyDataSetChanged();
        mActivity.hideLoadingDialog();
    }

    @Override
    public void moveToNextStageFail(String message) {
        if (mActivity == null) {
            return;
        }
        mActivity.hideLoadingDialog();
        if (message != null) {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFilterClear:
                mFilterModel = new FilterModel();
                vgFilter.removeAllViews();
                layoutFilter.setVisibility(View.GONE);
                //
                if (mActivity.isNetworkOffline()) {
                    listTimeline.clear();
                    displayTimeLineList();
                    return;
                }
                onRefresh();
                break;
            case R.id.imgFilter:
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, FilterActivity.class);
                    intent.putExtra(FilterActivity.FILTER_MODEL, mFilterModel);
                    mActivity.startActivityForResult(intent, REQUEST_CODE_FILTER);
                }
                break;
            case R.id.imgSearchClose:
                edSearch.getText().clear();
                mFilterModel.setSearchText("");
                imgSearchClose.setVisibility(View.INVISIBLE);
                rvRecentSearch.setVisibility(View.GONE);
                onRefresh();
                break;
            case R.id.edSearch:
                if (rvRecentSearch.getVisibility() == View.GONE) {
                    showRecentSearch(App.getDatabaseHelper(getApplicationContext()).getRecentSearch()
                            .getTextSearchByUserIdAndLikeKeySearch(Prefs.getInstance(getActivity())
                                    .getUserId(), edSearch.getText().toString().trim()));
                }
                break;
            default:
                break;
        }
    }

    /*
     * Apply Filter from FilterActivity
     */
    public void applyFilter(FilterModel filterModel) {
        if (filterModel == null) {
            mFilterModel = new FilterModel();
        } else {
            mFilterModel = filterModel;
        }
        if (vgFilter != null) {
            vgFilter.removeAllViews();
            layoutFilter.setVisibility(View.GONE);
        }

        ArrayList<FilterField> selectedPlaceList = mFilterModel.getPlaceList();
        if (selectedPlaceList != null && !selectedPlaceList.isEmpty()) {
            //TODO sort place list before showing
            for (FilterField place : selectedPlaceList) {
                addFilterApply(place.getPlaceName(), "place");
            }
        }

        ArrayList<FilterMember> selectedMemberList = mFilterModel.getMemberList();
        if (selectedMemberList != null && !selectedMemberList.isEmpty()) {
            //TODO sort member list before showing
            for (FilterMember member : selectedMemberList) {
                addFilterApply(member.getUserName(), "member");
            }
        }

        String fromDay = mFilterModel.getFromDay();
        if (fromDay != null) {
            addFilterApply(getString(R.string.filter_day_from) + ": " + fromDay, "fromDay");
        }
        String toDay = mFilterModel.getToDay();
        if (toDay != null) {
            addFilterApply(getString(R.string.filter_day_to) + ": " + toDay, "toDay");
        }

        ArrayList<Integer> filterTypeList = mFilterModel.getTypeList();
        for (int type : filterTypeList) {
            switch (type) {
                case Utils.Constant.TIMELINE_TYPE_NOTIFICATION:
                    addFilterApply(getString(R.string.timeline_notification), "notification");
                    break;
                case Utils.Constant.TIMELINE_TYPE_ADVICE:
                    addFilterApply(getString(R.string.timeline_advice), "advice");
                    break;
                case Utils.Constant.TIMELINE_TYPE_DIARY:
                    addFilterApply(getString(R.string.timeline_diary), "diary");
                    break;
                case Utils.Constant.TIMELINE_TYPE_REMOTE_CONTROL:
                    addFilterApply(getString(R.string.timeline_remote_control), "remote");
                    break;
                default:
                    break;
            }
        }
        onRefresh();

    }

    /*
    * Create Filter button when filer apply
    */
    private void addFilterApply(final String text, String type) {
        layoutFilter.setVisibility(View.VISIBLE);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") final View layout = inflater.inflate(R.layout.layout_filter, null);
        vgFilter = view.findViewById(R.id.layoutFilterApply);

        final TextView tvFilterApply = layout.findViewById(R.id.tvFilterApply);
        tvFilterApply.setText(text);
        final TextView tvType = layout.findViewById(R.id.type);
        tvType.setText(type);

        ImageView imgFilterItemClear = layout.findViewById(R.id.imgFilterItemClear);
        imgFilterItemClear.setVisibility(View.VISIBLE);
        imgFilterItemClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearFilter(tvType.getText().toString(), tvFilterApply.getText().toString());
                vgFilter.removeView(layout);
                if (vgFilter.getChildCount() == 0) {
                    layoutFilter.setVisibility(View.GONE);
                }
            }
        });

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(dp2px(10), 0, dp2px(0), dp2px(0));
        vgFilter.addView(layout, params);
    }

    /*
     * Clear filter button when user click button clear
     */
    private void onClearFilter(String type, String data) {
        switch (type) {
            case "member":
                ArrayList<FilterMember> list = mFilterModel.getMemberList();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUserName().equals(data)) {
                        list.remove(list.get(i));
                        i--;
                    }
                }
                mFilterModel.setMemberList(list);
                break;
            case "place":
                ArrayList<FilterField> placeList = mFilterModel.getPlaceList();
                for (int i = 0; i < placeList.size(); i++) {
                    if (placeList.get(i).getPlaceName().equals(data)) {
                        placeList.remove(placeList.get(i));
                        i--;
                    }
                }
                mFilterModel.setPlaceList(placeList);
                break;
            case "fromDay":
                mFilterModel.setFromDay(null);
                break;
            case "toDay":
                mFilterModel.setToDay(null);
                break;
            case "notification":
                onFilterTypeClear(Utils.Constant.TIMELINE_TYPE_NOTIFICATION);
                break;
            case "advice":
                onFilterTypeClear(Utils.Constant.TIMELINE_TYPE_ADVICE);
                break;
            case "diary":
                onFilterTypeClear(Utils.Constant.TIMELINE_TYPE_DIARY);
                break;
            case "remote":
                onFilterTypeClear(Utils.Constant.TIMELINE_TYPE_ADVICE);
                break;
            default:
                break;
        }
        onRefresh();
    }

    /*
     * Remove FilterType(Notification, Advice, Diary, Remote Control) and update list FilterType
     */
    private void onFilterTypeClear(int type) {
        ArrayList<Integer> list = mFilterModel.getTypeList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == type) {
                list.remove(i);
                i--;
            }
        }
        mFilterModel.setTypeList(list);
    }

    private int dp2px(int dp) {
        float scaleValue = getResources().getDisplayMetrics().density;
        return (int) (dp * scaleValue + 0.5f);
    }

    @Override
    public void tokenExpired() {
        if (mActivity == null) {
            return;
        }
        mActivity.hideLoadingDialog();
        Utils.tokenExpired(mActivity);
    }

    @Override
    public void onDiaryEdit(TimelineResponse.ListTimeline timelineObject) {
        Intent intent = new Intent(getActivity(), EKTimelinePostActivity.class);
        intent.putExtra("TimelineObject", timelineObject);
        intent.putExtra(Utils.Constant.TIMELINE_POST_EDIT, 1);
        mActivity.startActivityForResult(intent, REQUEST_CODE_POST);
    }

    @Override
    public void onReadMore(TimelineResponse.ListTimeline timeline) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Utils.Name.DIARY_ITEM, timeline);

        Intent intent = new Intent(mActivity, TimelineDetailActivity.class);
        intent.putExtra(Utils.Name.DIARY_DETAIL, bundle);
        mActivity.startActivityForResult(intent, REQUEST_CODE_READ_MORE);
    }

    @Override
    public void onMoveToNextStage(int position) {
        if (mActivity.isNetworkOffline()) {
            return;
        }
        timelinePosition = position;
        TimelineResponse.ListTimeline timeline = (TimelineResponse.ListTimeline) listTimeline.values().toArray()[position];
        TimelineResponse.Notification notification = timeline.getNotification();
        mActivity.showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.moveToNextStage(timeline.getEkfieldId(), notification.getRecipeId(), notification.getCurrentGrowthStageId());
    }

    private LinkedHashMap<String, TimelineResponse.ListTimeline> convertArrayToLinkedHashMap(ArrayList<TimelineResponse.ListTimeline> timelineList) {
        LinkedHashMap<String, TimelineResponse.ListTimeline> recipeLinkedHashMap = new LinkedHashMap<>();
        for (TimelineResponse.ListTimeline timeline : timelineList) {
            recipeLinkedHashMap.put(timeline.getTimelineId(), timeline);
        }
        return recipeLinkedHashMap;
    }

    @Override
    public void onRefresh() {
        currentPage = 0;
        mFilterModel.setStartPosition(currentPage + 1);
        // after refresh is done, remember to call the following code
        if (srLayout != null && srLayout.isRefreshing()) {
            srLayout.setRefreshing(false);  // This hides the spinner
        }
        reloadTimeline();
    }

    private void loadMore() {
        if (numberItemReturn == Utils.Name.TIMELINE_LOAD_MORE_RECORD) {
            mFilterModel.setStartPosition(currentPage + 1);
            reloadTimeline();
        }//numberItemReturn < recordPerPage. => No item to load more
    }

    public void reloadTimeline() {
        if (mActivity.isNetworkOffline()) {
            return;
        }
        mActivity.showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.getTimeline(mActivity, mFilterModel);
    }
}
