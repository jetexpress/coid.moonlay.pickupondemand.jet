package coid.moonlay.pickupondemand.jet.request;

import java.util.List;

import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.Relation;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class RelationRequest extends BaseNetworkRequest<List<String>>
{
    public RelationRequest()
    {
        super(null);
    }

    @Override
    protected Call<List<String>> getCall()
    {
        return RetrofitProvider.getResourcesService().getRelations();
    }

    @Override
    protected void onSuccessOnNetworkThread(Response<List<String>> response)
    {
        super.onSuccessOnNetworkThread(response);
        DBQuery.truncate(Relation.class);
        Relation.bulkSave(response.body());
    }

    @Override
    protected void onTimeOutOnNetworkThread()
    {
        execute();
    }
}
