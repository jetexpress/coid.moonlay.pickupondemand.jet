package coid.moonlay.pickupondemand.jet.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.adapter.TaskAdapter;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.custom.ConfirmationDialog;
import coid.moonlay.pickupondemand.jet.custom.ISwipeRevealLayoutOnClickListener;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.model.Task;
import coid.moonlay.pickupondemand.jet.request.PickupCancelRequest;
import coid.moonlay.pickupondemand.jet.request.TaskHistoryRequest;
import coid.moonlay.pickupondemand.jet.request.TaskOngoingRequest;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends BaseMainFragment implements PickupDetailFragment.IPickupDetailListener
{
    private SwipeRefreshLayout swipe_refresh_layout_task_ongoing, swipe_refresh_layout_task_history;
    private ProgressBar progress_bar_task_ongoing, progress_bar_task_history, progress_bar_paging_task_ongoing, progress_bar_paging_task_history;
    private ListView list_view_task_ongoing, list_view_task_history;
    private CardView card_view_search_ongoing_container, card_view_search_history_container;
    private LinearLayout ll_task_ongoing_tab_container, ll_task_history_tab_container;
    private RelativeLayout rl_task_ongoing_content_container, rl_task_history_content_container;
    private EditText et_filter_ongoing, et_filter_history;
    private TextView tv_tab_task_ongoing, tv_tab_task_history, tv_task_ongoing_rto, tv_task_history_rto;
    private Button btn_task_ongoing_retry, btn_task_history_retry;
    private View tab_task_ongoing_indicator, tab_task_history_indicator;

    private Boolean mIsTabTaskOngoing;
    private Boolean mIsOnTabClicked;

    private TaskOngoingRequest mTaskOngoingRequest;
    private TaskHistoryRequest mTaskHistoryRequest;

    private TaskAdapter mTaskOngoingAdapter;
    private TaskAdapter mTaskHistoryAdapter;

    private Handler mOngoingHandler;
    private String mOngoingFilterKeyword;
    private Handler mHistoryHandler;
    private String mHistoryFilterKeyword;
    private Long mFilterDelay = 2000L;

    public TaskFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mIsTabTaskOngoing = true;
        mIsOnTabClicked = false;
        mTaskOngoingAdapter = new TaskAdapter(mContext, new ArrayList<Task>(), false);
        mTaskHistoryAdapter = new TaskAdapter(mContext, new ArrayList<Task>(), true);
        mOngoingFilterKeyword = "";
        mHistoryFilterKeyword = "";
        mTaskOngoingRequest = new TaskOngoingRequest(mContext, mOngoingFilterKeyword);
        mTaskHistoryRequest = new TaskHistoryRequest(mContext, mHistoryFilterKeyword);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setEvent();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTitle(Utility.Message.get(R.string.task_list));
        setNotificationMenuEnabled(true);

        if (mIsTabTaskOngoing)
            onTabTaskOngoing();
        else
            onTabTaskHistory();
    }

    @Override
    public void onDestroy()
    {
        if (mTaskOngoingRequest != null)
        {
            mTaskOngoingRequest.clear();
            mTaskOngoingRequest = null;
        }
        if (mTaskHistoryRequest != null)
        {
            mTaskHistoryRequest.clear();
            mTaskHistoryRequest = null;
        }
        super.onDestroy();
    }

    @Override
    public void onStatusChanged(Task task)
    {
        mTaskOngoingAdapter.notifyDataSetChanged();
    }

    private void setView()
    {
        progress_bar_task_ongoing = (ProgressBar) findViewById(R.id.progress_bar_task_ongoing);
        progress_bar_task_history = (ProgressBar) findViewById(R.id.progress_bar_task_history);
        tv_task_ongoing_rto = (TextView) findViewById(R.id.tv_task_ongoing_rto);
        tv_task_history_rto = (TextView) findViewById(R.id.tv_task_history_rto);
        btn_task_ongoing_retry = (Button) findViewById(R.id.btn_task_ongoing_retry);
        btn_task_history_retry = (Button) findViewById(R.id.btn_task_history_retry);
        progress_bar_paging_task_ongoing = (ProgressBar) findViewById(R.id.progress_bar_paging_task_ongoing);
        progress_bar_paging_task_history = (ProgressBar) findViewById(R.id.progress_bar_paging_task_history);
        swipe_refresh_layout_task_ongoing = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_task_ongoing);
        swipe_refresh_layout_task_history = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_task_history);
        list_view_task_ongoing = (ListView) findViewById(R.id.list_view_task_ongoing);
        list_view_task_history = (ListView) findViewById(R.id.list_view_task_history);
        ll_task_ongoing_tab_container = (LinearLayout) findViewById(R.id.ll_task_ongoing_tab_container);
        ll_task_history_tab_container = (LinearLayout) findViewById(R.id.ll_task_history_tab_container);
        rl_task_ongoing_content_container = (RelativeLayout) findViewById(R.id.rl_task_ongoing_content_container);
        rl_task_history_content_container = (RelativeLayout) findViewById(R.id.rl_task_history_content_container);
        tv_tab_task_ongoing = (TextView) findViewById(R.id.tv_tab_task_ongoing);
        tv_tab_task_history = (TextView) findViewById(R.id.tv_tab_task_history);
        tab_task_ongoing_indicator = findViewById(R.id.tab_task_ongoing_indicator);
        tab_task_history_indicator = findViewById(R.id.tab_task_history_indicator);
        card_view_search_ongoing_container = (CardView) findViewById(R.id.card_view_search_ongoing_container);
        card_view_search_history_container = (CardView) findViewById(R.id.card_view_search_history_container);
        et_filter_ongoing = (EditText) findViewById(R.id.et_filter_ongoing);
        et_filter_history = (EditText) findViewById(R.id.et_filter_history);

        list_view_task_ongoing.setAdapter(mTaskOngoingAdapter);
        list_view_task_history.setAdapter(mTaskHistoryAdapter);
    }

    private void setEvent()
    {
        mTaskOngoingAdapter.setSwipeRevealLayoutOnClickListener(new ISwipeRevealLayoutOnClickListener()
        {
            @Override
            public void onMainLayoutClicked(Integer position)
            {
                Task task = mTaskOngoingAdapter.getItem(position);
                if (task != null)
                {
                    if (task.isPickup())
                    {
                        PickupDetailFragment pickupDetailFragment = PickupDetailFragment.newInstance(task);
                        pickupDetailFragment.setTargetFragment(mFragment, 0);
                        getNavigator().showFragment(pickupDetailFragment);
                    }
                    else if(task.isDRS())
                        getNavigator().showFragment(DeliveryDetailFragment.newInstance(task));
                    else if(task.isPRSOutlet()){
                        getNavigator().showFragment(PrsOutletDetailFragment.newInstance(task));
                    }else{
                        getNavigator().showFragment(PrsJetidDetailFragment.newInstance(task));
                    }

                }
            }
            @Override
            public void onSecondaryLayoutClicked(Integer position)
            {
                Task task = mTaskOngoingAdapter.getItem(position);
                if (task != null)
                {
                    if (task.isTripStarted())
                        cancelTrip(task);
                    else
                        rejectTask(task);
                }
            }
        });
        mTaskHistoryAdapter.setSwipeRevealLayoutOnClickListener(new ISwipeRevealLayoutOnClickListener()
        {
            @Override
            public void onMainLayoutClicked(Integer position)
            {
                Task task = mTaskHistoryAdapter.getItem(position);
                if (task != null)
                {
                    if (task.isPickup())
                        getNavigator().showFragment(PickupHistoryDetailFragment.newInstance(task));
                    else if (task.isDRS())
                        getNavigator().showFragment(DeliveryHistoryDetailFragment.newInstance(task));
                    else if(task.isPRSOutlet()){
                        getNavigator().showFragment(PrsHistoryOutletDetailFragment.newInstance(task));
                    }else{
                        getNavigator().showFragment(PrsHistoryJetidDetailFragment.newInstance(task));
                    }
                }
            }
            @Override
            public void onSecondaryLayoutClicked(Integer position){ showToast(Utility.Message.get(R.string.dev_not_implemented));}
        });
        et_filter_ongoing.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (mOngoingHandler != null)
                    mOngoingHandler.removeCallbacks(null);
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                mOngoingFilterKeyword = s.toString();
                if (mTaskOngoingRequest != null && mTaskOngoingRequest.isExecuting())
                    mTaskOngoingRequest.cancel();

                filterOngoingTask();
            }
        });
        et_filter_history.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (mHistoryHandler != null)
                    mHistoryHandler.removeCallbacks(null);
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                mHistoryFilterKeyword = s.toString();
                if (mTaskHistoryRequest != null && mTaskHistoryRequest.isExecuting())
                    mTaskHistoryRequest.cancel();

                filterHistoryTask();
            }
        });
        swipe_refresh_layout_task_ongoing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                mTaskOngoingRequest = new TaskOngoingRequest(mContext, mOngoingFilterKeyword);
                mTaskOngoingRequest.executeAsync();
            }
        });
        swipe_refresh_layout_task_history.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                mTaskHistoryRequest = new TaskHistoryRequest(mContext, mHistoryFilterKeyword);
                mTaskHistoryRequest.executeAsync();
            }
        });
        btn_task_ongoing_retry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mTaskOngoingRequest.executeAsync();
            }
        });
        btn_task_history_retry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mTaskHistoryRequest.executeAsync();
            }
        });
        ll_task_ongoing_tab_container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!mIsTabTaskOngoing)
                {
                    mIsOnTabClicked = true;
                    onTabTaskOngoing();
                }
            }
        });
        ll_task_history_tab_container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mIsTabTaskOngoing)
                {
                    mIsOnTabClicked = true;
                    onTabTaskHistory();
                }
            }
        });
        list_view_task_ongoing.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){}
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (firstVisibleItem > 0 &&
                        visibleItemCount > 0 &&
                        totalItemCount > 0 &&
                        firstVisibleItem + visibleItemCount >= totalItemCount &&
                        mTaskOngoingRequest.isIdle() &&
                        !mTaskOngoingRequest.isLastPage())
                {
                    mTaskOngoingRequest.executeAsync();
                }
            }
        });
        list_view_task_history.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){}
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (firstVisibleItem > 0 &&
                        visibleItemCount > 0 &&
                        totalItemCount > 0 &&
                        firstVisibleItem + visibleItemCount >= totalItemCount &&
                        mTaskHistoryRequest.isIdle() &&
                        !mTaskHistoryRequest.isLastPage())
                {
                    mTaskHistoryRequest.executeAsync();
                }
            }
        });
    }

    private void onTabTaskOngoing()
    {
        showTaskOngoing();
        if (!mTaskOngoingRequest.hasExecuted())
            mTaskOngoingRequest.executeAsync();
    }

    private void onTabTaskHistory()
    {
        showTaskHistory();
        if (!mTaskHistoryRequest.hasExecuted())
            mTaskHistoryRequest.executeAsync();
    }

    private void filterOngoingTask()
    {
        if (mOngoingHandler == null)
            mOngoingHandler = new Handler(Looper.getMainLooper());

        mOngoingHandler.postDelayed(filterOngoingTaskRunnable, mFilterDelay);
    }

    private Runnable filterOngoingTaskRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            mTaskOngoingRequest = new TaskOngoingRequest(mContext, mOngoingFilterKeyword);
            mTaskOngoingRequest.executeAsync();
        }
    };

    private void filterHistoryTask()
    {
        if (mHistoryHandler == null)
            mHistoryHandler = new Handler(Looper.getMainLooper());

        mHistoryHandler.postDelayed(filterHistoryRunnable, mFilterDelay);
    }

    private Runnable filterHistoryRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            mTaskHistoryRequest = new TaskHistoryRequest(mContext, mHistoryFilterKeyword);
            mTaskHistoryRequest.executeAsync();
        }
    };

    private void cancelTrip(final Task task)
    {
        String title = Utility.Message.get(R.string.pickup_cancel_trip_title);
        String message = Utility.Message.get(R.string.pickup_cancel_trip_confirmation);
        ConfirmationDialog cancelDialog = new ConfirmationDialog(mContext, title, message)
        {
            @Override
            public void onOKClicked()
            {
                PickupCancelRequest pickupCancelRequest = new PickupCancelRequest(mContext, task.getCode())
                {
                    @Override
                    protected void onSuccessOnUIThread(Response<Pickup> response)
                    {
                        task.setStatus(response.body().getStatus());
                        mTaskOngoingAdapter.notifyDataSetChanged();
                        super.onSuccessOnUIThread(response);
                        showToast(Utility.Message.get(R.string.success));
                    }
                };
                pickupCancelRequest.executeAsync();
            }
        };
        cancelDialog.show();
    }

    private void rejectTask(final Task task)
    {
        String title = Utility.Message.get(R.string.pickup_reject_pickup_title);
        String message = Utility.Message.get(R.string.pickup_reject_pickup_confirmation);
        ConfirmationDialog dialog = new ConfirmationDialog(mContext, title, message)
        {
            @Override
            public void onOKClicked()
            {
                PickupCancelRequest pickupCancelRequest = new PickupCancelRequest(mContext, task.getCode())
                {
                    @Override
                    protected void onSuccessOnUIThread(Response<Pickup> response)
                    {
                        mTaskOngoingAdapter.remove(task);
                        mTaskHistoryAdapter.notifyDataSetChanged();

                        Long count = mTaskOngoingRequest.getQuery() != null ? mTaskOngoingRequest.getQuery().getCount() : 0;
                        String tabTaskTitle = count > 0 ? Utility.Message.get(R.string.task) + " (" + String.valueOf(mTaskOngoingRequest.getQuery().getCount()) + ")" : Utility.Message.get(R.string.task);
                        tv_tab_task_ongoing.setText(tabTaskTitle);

                        super.onSuccessOnUIThread(response);
                        showToast(Utility.Message.get(R.string.success));
                    }
                };
                pickupCancelRequest.executeAsync();
            }
        };
        dialog.setCancelable(true);
        dialog.show();
    }

    public void showTaskOngoing()
    {
        rl_task_ongoing_content_container.setVisibility(View.VISIBLE);
        rl_task_history_content_container.setVisibility(View.GONE);
        swipe_refresh_layout_task_ongoing.setVisibility(View.VISIBLE);
        swipe_refresh_layout_task_history.setVisibility(View.GONE);
        card_view_search_ongoing_container.setVisibility(View.VISIBLE);
        card_view_search_history_container.setVisibility(View.GONE);

        progress_bar_task_ongoing.setVisibility(View.GONE);
        progress_bar_paging_task_ongoing.setVisibility(View.GONE);
        btn_task_ongoing_retry.setVisibility(View.GONE);
        swipe_refresh_layout_task_ongoing.setRefreshing(false);

        if (mIsOnTabClicked)
        {
            Utility.Animation.slideOutToRight(list_view_task_history);
            Utility.Animation.slideInFromLeft(list_view_task_ongoing);
            Utility.Animation.slideOutToLeft(tab_task_history_indicator);
            Utility.Animation.slideInFromRight(tab_task_ongoing_indicator);
        }
        else
        {
            list_view_task_ongoing.setVisibility(View.VISIBLE);
            list_view_task_history.setVisibility(View.GONE);
            tab_task_ongoing_indicator.setVisibility(View.VISIBLE);
            tab_task_history_indicator.setVisibility(View.GONE);
        }
        mIsOnTabClicked = false;
        mIsTabTaskOngoing = true;
        Long count = mTaskOngoingRequest.getQuery() != null ? mTaskOngoingRequest.getQuery().getCount() : 0;
        String tabTaskTitle = count > 0 ? Utility.Message.get(R.string.task) + " (" + String.valueOf(mTaskOngoingRequest.getQuery().getCount()) + ")" : Utility.Message.get(R.string.task);
        tv_tab_task_ongoing.setText(tabTaskTitle);
        tv_tab_task_ongoing.setTextColor(Color.WHITE);
        tv_tab_task_history.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextDisable));
    }

    public void showTaskHistory()
    {
        rl_task_ongoing_content_container.setVisibility(View.GONE);
        rl_task_history_content_container.setVisibility(View.VISIBLE);
        swipe_refresh_layout_task_ongoing.setVisibility(View.GONE);
        swipe_refresh_layout_task_history.setVisibility(View.VISIBLE);
        card_view_search_ongoing_container.setVisibility(View.GONE);
        card_view_search_history_container.setVisibility(View.VISIBLE);

        progress_bar_task_history.setVisibility(View.GONE);
        progress_bar_paging_task_history.setVisibility(View.GONE);
        btn_task_history_retry.setVisibility(View.GONE);
        swipe_refresh_layout_task_history.setRefreshing(false);

        if (mIsOnTabClicked)
        {
            Utility.Animation.slideOutToLeft(list_view_task_ongoing);
            Utility.Animation.slideInFromRight(list_view_task_history);
            Utility.Animation.slideOutToRight(tab_task_ongoing_indicator);
            Utility.Animation.slideInFromLeft(tab_task_history_indicator);
        }
        else
        {
            list_view_task_ongoing.setVisibility(View.GONE);
            list_view_task_history.setVisibility(View.VISIBLE);
            tab_task_ongoing_indicator.setVisibility(View.GONE);
            tab_task_history_indicator.setVisibility(View.VISIBLE);
        }
        mIsOnTabClicked = false;
        mIsTabTaskOngoing = false;
        Long count = mTaskOngoingRequest.getQuery() != null ? mTaskOngoingRequest.getQuery().getCount() : 0;
        String tabTaskTitle = count > 0 ? Utility.Message.get(R.string.task) + " (" + String.valueOf(mTaskOngoingRequest.getQuery().getCount()) + ")" : Utility.Message.get(R.string.task);
        tv_tab_task_ongoing.setText(tabTaskTitle);
        tv_tab_task_ongoing.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextDisable));
        tv_tab_task_history.setTextColor(Color.WHITE);
    }

    public void showTaskOngoingProgressBar()
    {
        progress_bar_task_ongoing.setVisibility(View.VISIBLE);
        btn_task_ongoing_retry.setVisibility(View.GONE);
        tv_task_ongoing_rto.setVisibility(View.GONE);
        swipe_refresh_layout_task_ongoing.setVisibility(View.GONE);
    }

    public void showTaskOngoingRetry()
    {
        progress_bar_task_ongoing.setVisibility(View.GONE);
        btn_task_ongoing_retry.setVisibility(View.VISIBLE);
        tv_task_ongoing_rto.setVisibility(View.VISIBLE);
        swipe_refresh_layout_task_ongoing.setVisibility(View.GONE);    }

    public void showTaskHistoryProgressBar()
    {
        progress_bar_task_history.setVisibility(View.VISIBLE);
        btn_task_history_retry.setVisibility(View.GONE);
        tv_task_history_rto.setVisibility(View.GONE);
        swipe_refresh_layout_task_history.setVisibility(View.GONE);
    }

    public void showTaskHistoryRetry()
    {
        progress_bar_task_history.setVisibility(View.GONE);
        btn_task_history_retry.setVisibility(View.VISIBLE);
        tv_task_history_rto.setVisibility(View.VISIBLE);
        swipe_refresh_layout_task_history.setVisibility(View.GONE);
    }

    public void showTaskOngoingPagingProgressBar()
    {
        progress_bar_paging_task_ongoing.setVisibility(View.VISIBLE);
    }

    public void showTaskHistoryPagingProgressBar()
    {
        progress_bar_paging_task_history.setVisibility(View.VISIBLE);
    }

    public void updateTaskOngoing(List<Task> taskList, Boolean isReplace)
    {
        mTaskOngoingAdapter.updateFilteredList(mOngoingFilterKeyword, taskList, isReplace);
    }

    public void updateTaskHistory(List<Task> taskList, Boolean isReplace)
    {
        mTaskHistoryAdapter.updateFilteredList(mHistoryFilterKeyword, taskList, isReplace);
    }

    public Boolean isTabTaskOngoing()
    {
        return mIsTabTaskOngoing;
    }
}
