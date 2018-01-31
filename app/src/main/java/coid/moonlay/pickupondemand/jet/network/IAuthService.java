package coid.moonlay.pickupondemand.jet.network;

import coid.moonlay.pickupondemand.jet.model.CourierProfile;
import coid.moonlay.pickupondemand.jet.model.Login;
import coid.moonlay.pickupondemand.jet.model.UserProfile;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IAuthService
{
    @FormUrlEncoded
    @POST("oauth/token?role=courier")
    Call<Login> login(@Field("username") String username,
                      @Field("password") String password,
                      @Field("client_id") String clientId,
                      @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("oauth/token")
    Call<Login> refresh(@Field("refresh_token") String refreshToken,
                      @Field("client_id") String clientId,
                      @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("api/account/externallogin")
    Call<Login> loginFacebook(@Field("accessToken") String accessToken, @Field("provider") String provider);

    @GET("me/profile")
    Call<UserProfile> getUserProfile();

    @GET("me/profile/courier")
    Call<CourierProfile> getCourierProfile();

    @FormUrlEncoded
    @POST("api/account/changepassword")
    Call<Void> changePassword(@Field("username") String username,
                              @Field("oldpassword") String oldPassword,
                              @Field("newpassword") String newPassword);
}
