package coid.moonlay.pickupondemand.jet.request;

import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.Config;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class ConfigRequest extends BaseNetworkRequest<Config>
{
    public ConfigRequest()
    {
        super(null);
    }

    @Override
    protected Call<Config> getCall()
    {
        return RetrofitProvider.getResourcesService().getConfig();
    }

    @Override
    protected void onSuccessOnNetworkThread(Response<Config> response)
    {
        DBQuery.truncate(Config.class);
        Config config = response.body();
        config.save();
    }

    @Override
    protected void onTimeOutOnNetworkThread()
    {
        execute();
    }
}
