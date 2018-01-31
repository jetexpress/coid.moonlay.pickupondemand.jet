package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.base.BaseFragment;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.fragment.TaskFragment;
import coid.moonlay.pickupondemand.jet.model.Delivery;
import coid.moonlay.pickupondemand.jet.model.Query;
import coid.moonlay.pickupondemand.jet.model.QueryResult;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class WaybillOngoingRequest
{

}
//public class WaybillOngoingRequest extends BaseNetworkRequest<QueryResult<Delivery>>
//{
//    private TaskFragment mTaskFragment;
//    private Query mQuery;
//    private Long mPage = 1L;
//
//    public WaybillOngoingRequest(Context context)
//    {
//        super(context);
//    }
//
//    @Override
//    protected Call<QueryResult<Delivery>> getCall()
//    {
//        mPage = mQuery != null ? mQuery.getPage() + 1 : 1;
//        return RetrofitProvider.getAuthorizedResourcesService().getOngoingWaybills(ApiConfig.DEFAULT_PAGING_SIZE, mPage);
//    }
//
//    @Override
//    protected void onStartOnUIThread()
//    {
//        BaseFragment baseFragment = getBaseFragment();
//        if (baseFragment != null && baseFragment instanceof TaskFragment)
//            mTaskFragment = (TaskFragment) baseFragment;
//
//        if (mTaskFragment == null)
//            return;
//
//        if (!hasExecuted())
//            mTaskFragment.showTaskOngoingProgressBar();
//        else
//            mTaskFragment.showTaskOngoingPagingProgressBar();
//    }
//
//    @Override
//    protected void onSuccessOnUIThread(Response<QueryResult<Delivery>> response)
//    {
//        if (mTaskFragment == null)
//            return;
//        mQuery = response.body().getQuery();
////        mTaskFragment.updateDelivery(response.body().getResult(), mQuery.getPage() == 1);
//        mTaskFragment.hideProgressbar();
//        mTaskFragment.hideSwipeRefresh();
//        if (mTaskFragment.isTabTaskOngoing())
//            mTaskFragment.showTaskOngoing();
//    }
//
//    @Override
//    protected void onResponseFailedOnUIThread(Response<QueryResult<Delivery>> response)
//    {
//        if (mTaskFragment == null)
//            return;
//        mTaskFragment.hideProgressbar();
//        mTaskFragment.hideSwipeRefresh();
//    }
//
//    @Override
//    protected void onFailedOnUIThread(Exception ex)
//    {
//        if (mTaskFragment == null)
//            return;
//        mTaskFragment.hideProgressbar();
//        mTaskFragment.hideSwipeRefresh();
//    }
//
//    @Override
//    protected void onTimeOutOnUIThread()
//    {
//        if (mTaskFragment == null)
//            return;
//        mTaskFragment.hideProgressbar();
//        mTaskFragment.hideSwipeRefresh();
//    }
//
//    public Query getQuery()
//    {
//        return mQuery;
//    }
//
//    public Boolean hasExecuted()
//    {
//        return mQuery != null;
//    }
//
//    public Boolean isLastPage()
//    {
//        return mQuery != null && mQuery.getPage() >= mQuery.getTotalPage();
//    }
//}
