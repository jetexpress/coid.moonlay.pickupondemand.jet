package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.base.BaseFragment;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.fragment.TaskFragment;
import coid.moonlay.pickupondemand.jet.model.Query;
import coid.moonlay.pickupondemand.jet.model.QueryResult;
import coid.moonlay.pickupondemand.jet.model.Task;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class TaskOngoingRequest extends BaseNetworkRequest<QueryResult<Task>>
{
    private TaskFragment mTaskFragment;
    private Query mQuery;
    private Long mPage = 1L;

    public TaskOngoingRequest(Context context)
    {
        super(context);
    }

    @Override
    protected Call<QueryResult<Task>> getCall()
    {
        mPage = mQuery != null ? mQuery.getPage() + 1 : 1;
        return RetrofitProvider.getAuthorizedResourcesService().getOngoingTaskList(ApiConfig.DEFAULT_PAGING_SIZE, mPage);
    }

    @Override
    protected void onStartOnUIThread()
    {
        BaseFragment baseFragment = getBaseFragment();
        if (baseFragment != null && baseFragment instanceof TaskFragment)
            mTaskFragment = (TaskFragment) baseFragment;

        if (mTaskFragment == null)
            return;

        if (!hasExecuted())
            mTaskFragment.showTaskOngoingProgressBar();
        else
            mTaskFragment.showTaskOngoingPagingProgressBar();
    }

    @Override
    protected void onSuccessOnUIThread(Response<QueryResult<Task>> response)
    {
        if (mTaskFragment == null)
            return;
        mQuery = response.body().getQuery();
        mTaskFragment.updateTaskOngoing(response.body().getResult(), mQuery.getPage() <= 1);
        if (mTaskFragment.isTabTaskOngoing())
            mTaskFragment.showTaskOngoing();
    }

    @Override
    protected void hideLoadingDialog()
    {
        if (mTaskFragment == null)
            return;
        if (!hasExecuted())
            mTaskFragment.showTaskOngoingRetry();
    }

    public Query getQuery()
    {
        return mQuery;
    }

    public Boolean hasExecuted()
    {
        return mQuery != null;
    }

    public Boolean isLastPage()
    {
        return mQuery != null && mQuery.getPage() >= mQuery.getTotalPage();
    }
}
