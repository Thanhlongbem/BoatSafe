package vnu.uet.boatsafe.data.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.Charset;

import vnu.uet.boatsafe.BuildConfig;
import vnu.uet.boatsafe.utils.AppConstants;
import vnu.uet.boatsafe.utils.CommonUtils;
import io.reactivex.schedulers.Schedulers;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class NetworkBuilder {

    public static final Charset UTF8 = Charset.forName("UTF-8");
    private static NetworkBuilder instance;
    private final AppApi suApi;

    private NetworkBuilder(Context context,String domain) {

        final Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl                (domain)
                                .addConverterFactory    (GsonConverterFactory.create(provideGson()))
                                .addCallAdapterFactory  (RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                                .client(provideOkhttpClientBasic(context))
                                .build();

        suApi                   = retrofit.create(AppApi.class);
    }

    public static NetworkBuilder build(Context context) {

        instance = new NetworkBuilder(context,AppApi.DOMAIN);

        return instance;
    }

    public static AppApi api(Context context) {
        return build(context).suApi;
    }

    public static Gson provideGson() {

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }


    private OkHttpClient provideOkhttpClientBasic(Context context)  {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        final Interceptor paramtersInterceptor = chain -> {
            final Request original = chain.request();
            final HttpUrl originalHttpUrl = original.url();
            HttpUrl url = originalHttpUrl.newBuilder()
                    .build();
            final Request request = original.newBuilder()
                    .addHeader("device_id", CommonUtils.getDeviceId(context))
                    .addHeader("version_name", BuildConfig.VERSION_NAME)
                    .addHeader("version_code",String.valueOf(BuildConfig.VERSION_CODE))
                    .addHeader("os", AppConstants.OS)
                    .url(url)
                    .build();
            return chain.proceed(request);
        };
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(paramtersInterceptor)
                .build();
    }

}
