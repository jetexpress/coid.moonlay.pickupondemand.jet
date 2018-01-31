package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import java.util.List;

import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.OperationStatus;
import coid.moonlay.pickupondemand.jet.model.QueryData;
import coid.moonlay.pickupondemand.jet.model.db.DBHelper;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class OperationStatusRequest extends BaseNetworkRequest<QueryData<OperationStatus>>
{
    public OperationStatusRequest()
    {
        super(null);
    }

    @Override
    protected Call<QueryData<OperationStatus>> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().getOperationStatusList(-1L);
    }

    @Override
    protected void onSuccessOnNetworkThread(Response<QueryData<OperationStatus>> response)
    {
        super.onSuccessOnNetworkThread(response);
        List<OperationStatus> operationStatusList = response.body().getData();
        DBQuery.truncate(OperationStatus.class);
        DBHelper.bulkSave(operationStatusList);
    }

    @Override
    protected void onTimeOutOnNetworkThread()
    {
        execute();
    }
}
