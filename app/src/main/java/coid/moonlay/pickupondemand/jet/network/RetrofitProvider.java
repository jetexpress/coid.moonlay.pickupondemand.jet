package coid.moonlay.pickupondemand.jet.network;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.model.Login;
import okhttp3.Interceptor;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider
{
    public static IAuthService getAuthService()
    {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException
            {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("x-jet-appid", "android-courier");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiConfig.AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                .create()
                ))
                .client(new okhttp3.OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                        .build()
                )
                .build()
                .create(IAuthService.class);
    }

    public static IAuthService getAuthorizedAuthService()
    {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException
            {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Authorization", Login.getHeaderAuthorization())
                        .addHeader("x-jet-appid", "android-courier");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiConfig.AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                .create()
                ))
                .client(new okhttp3.OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                        .build()
                )
                .build()
                .create(IAuthService.class);
    }

    public static IResourceService getResourcesService()
    {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException
            {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("x-jet-appid", "android-courier");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiConfig.RESOURCE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                .create()
                ))
                .client(new okhttp3.OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                        .build()
                )
                .build()
                .create(IResourceService.class);
    }

    public static IResourceService getAuthorizedResourcesService()
    {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException
            {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Authorization", Login.getHeaderAuthorization())
                        .addHeader("x-jet-appid", "android-courier");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiConfig.RESOURCE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                .create()
                ))
                .client(new okhttp3.OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                        .build()
                )
                .build()
                .create(IResourceService.class);
    }

    public static IMapService getMapService()
    {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiConfig.GOOGLE_MAP__URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                .create()
                ))
                .client(new okhttp3.OkHttpClient.Builder()
                        .connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS).build()
                )
                .build()
                .create(IMapService.class);
    }
}
