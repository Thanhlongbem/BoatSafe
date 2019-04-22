

package vnu.uet.boatsafe.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import vnu.uet.boatsafe.R;


public final class AppUtils {

    private AppUtils() {
    }



    public static boolean isOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static void requestPermission(Activity activity, String[] permission,PermissionCallBack permissionCallBack) {
        Dexter.withActivity(activity)
                .withPermissions(permission)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        permissionCallBack.onPermissionsChecked(report);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        permissionCallBack.onPermissionRationaleShouldBeShown(permissions,token);
                    }
                }).check();
    }

    public static boolean isPermisstionLocationGrant(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void replaceFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId, boolean onBackstack, @Nullable String tag) {


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.setCustomAnimations(R.anim.attack_fragment,R.anim.detack_fragment,R.anim.backstack_in,R.anim.backstack_out);
        transaction.replace(frameId, fragment, tag);
        if (onBackstack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();


    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, boolean onBackstack, @Nullable String tag) {

        Fragment currentFr = fragmentManager.findFragmentById(frameId);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.hide(currentFr);
        if (onBackstack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public static void popToFragment(@NonNull FragmentManager fragmentManager,String TAG){
        fragmentManager.popBackStack(TAG,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void popToFragmentIndex(@NonNull FragmentManager fragmentManager,int index){
        fragmentManager.popBackStack(index,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    public static void clearBackStacks(@NonNull FragmentManager fm) {

        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

    }

    public interface PermissionCallBack{
        void onPermissionsChecked(MultiplePermissionsReport report);
        void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token);
    }




}
