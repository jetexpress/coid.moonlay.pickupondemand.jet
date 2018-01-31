package coid.moonlay.pickupondemand.jet.request;

import java.util.List;

import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.Product;
import coid.moonlay.pickupondemand.jet.model.QueryData;
import coid.moonlay.pickupondemand.jet.model.db.DBHelper;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class ProductRequest extends BaseNetworkRequest<QueryData<Product>>
{
    public ProductRequest()
    {
        super(null);
    }

    @Override
    protected Call<QueryData<Product>> getCall()
    {
        return RetrofitProvider.getResourcesService().getProductList(-1L);
    }

    @Override
    protected void onSuccessOnNetworkThread(Response<QueryData<Product>> response)
    {
        super.onSuccessOnNetworkThread(response);
        List<Product> productList = response.body().getData();
        DBQuery.truncate(Product.class);
        DBHelper.bulkSave(productList);
    }

    @Override
    protected void onTimeOutOnNetworkThread()
    {
        execute();
    }
}
