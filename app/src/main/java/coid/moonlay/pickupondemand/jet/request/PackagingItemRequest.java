package coid.moonlay.pickupondemand.jet.request;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.PackagingItem;
import coid.moonlay.pickupondemand.jet.model.QueryData;
import coid.moonlay.pickupondemand.jet.model.db.DBHelper;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class PackagingItemRequest extends BaseNetworkRequest<QueryData<PackagingItem>>
{
    public PackagingItemRequest()
    {
        super(null);
    }

    @Override
    protected Call<QueryData<PackagingItem>> getCall()
    {
        return RetrofitProvider.getResourcesService().getPackagingItemList(-1L);
    }

    @Override
    protected void onSuccessOnNetworkThread(Response<QueryData<PackagingItem>> response)
    {
        super.onSuccessOnNetworkThread(response);
        List<PackagingItem> packagingItemList = new ArrayList<>();
        packagingItemList.add(PackagingItem.getDefaultPackaging());
        for (PackagingItem packagingItem : response.body().getData())
        {
            packagingItemList.add(packagingItem);
        }
        DBQuery.truncate(PackagingItem.class);
        DBHelper.bulkSave(packagingItemList);
    }

    @Override
    protected void onTimeOutOnNetworkThread()
    {
        execute();
    }
}
