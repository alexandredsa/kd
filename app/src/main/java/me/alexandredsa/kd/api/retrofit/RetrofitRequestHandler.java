package me.alexandredsa.kd.api.retrofit;

import android.content.Context;

import me.alexandredsa.kd.R;
import me.alexandredsa.kd.api.LocationAPIService;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by alexa on 23/02/2016.
 */
public class RetrofitRequestHandler {

    private static final String TAG = "RetrofitRequestHandler";
    private static Retrofit retrofit;
    private static LocationAPIService locationAPIService;

    public static LocationAPIService getAPIService(Context mContext) {
        if (retrofit == null)
            initRetrofit(mContext);

        if (locationAPIService == null)
            locationAPIService = retrofit.create(LocationAPIService.class);

        return locationAPIService;
    }

    private static void initRetrofit(Context mContext) {
        retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
