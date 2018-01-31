package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import java.util.List;

import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.Location;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;

public class AutoCompleteLocationRequest extends BaseNetworkRequest<List<Location>>
{
    private String mKeyword;

    public AutoCompleteLocationRequest(Context context, String keyword)
    {
        super(context);
        mKeyword = keyword;
    }

    @Override
    protected Call<List<Location>> getCall()
    {
        return RetrofitProvider.getResourcesService().getAutoCompleteLocation(mKeyword);
    }
}
