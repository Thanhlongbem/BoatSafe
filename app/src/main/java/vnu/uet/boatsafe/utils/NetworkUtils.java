

package vnu.uet.boatsafe.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import vnu.uet.boatsafe.data.network.NetworkBuilder;
import vnu.uet.boatsafe.ui.base.GlideApp;



public final class NetworkUtils {

    private NetworkUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static Object parseObjectFromJson(String json, Type type){
        try {
            return NetworkBuilder.provideGson().fromJson(json, type);
        } catch (Exception e) {
            return null;
        }

    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .placeholder(android.R.color.white)
                .error(android.R.color.white)
                .into(imageView);
    }

    public static Bitmap loadBitmap(Context context,String url) throws ExecutionException, InterruptedException {
        return Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get();
    }
}
