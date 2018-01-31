package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import org.json.JSONObject;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class ChangePasswordRequest extends BaseNetworkRequest<Void>
{
    private String mUsername;
    private String mOldPassword;
    private String mNewPassword;

    public ChangePasswordRequest(Context context, String username, String oldPassword, String newPassword)
    {
        super(context);
        mUsername = username;
        mOldPassword = oldPassword;
        mNewPassword = newPassword;
    }

    @Override
    protected Call<Void> getCall()
    {
        return RetrofitProvider.getAuthorizedAuthService().changePassword(mUsername, mOldPassword, mNewPassword);
    }

    @Override
    protected void onSuccessOnUIThread(Response<Void> response)
    {
        super.onSuccessOnUIThread(response);
        showToast(Utility.Message.get(R.string.success));
        getBaseActivity().getNavigator().back();
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<Void> response)
    {
        try
        {
            String failedMessage = response.errorBody().string();
            JSONObject jsonObject = new JSONObject(failedMessage);
            String errors = jsonObject.getString("errors");
            jsonObject = new JSONObject(errors);
            failedMessage = jsonObject.getString("key_");
            showToast(failedMessage);
        }
        catch (Exception ex)
        {
            showToast(Utility.Message.getResponseMessage(R.string.change_password_failed, response));
        }
        super.onResponseFailedOnUIThread(response);
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.change_password_failed, ex));
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }
}
