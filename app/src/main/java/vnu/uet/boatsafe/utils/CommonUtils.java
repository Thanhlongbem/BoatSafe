

package vnu.uet.boatsafe.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.provider.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vnu.uet.boatsafe.R;


public final class CommonUtils {

    private static final String TAG = CommonUtils.class.getSimpleName();

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern;
        Matcher matcher;
        final String PHONE_PATTERN = "\\d{10,11}";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static String initMediaTime(int format, long time) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        if (format == 0) {
            long timeSecond = time / 1000;
            long seconds = timeSecond % 60;
            long minutes = timeSecond / 60 % 60;
            long hours = timeSecond / 3600;

            mFormatBuilder.setLength(0);
            if (hours > 0) {
                return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
            } else {
                return mFormatter.format("%02d:%02d", minutes, seconds).toString();
            }
        } else if (format == 1) {
            long seconds = time % 60;
            long minutes = time / 60 % 60;
            long hours = time / 3600;

            mFormatBuilder.setLength(0);
            if (hours > 0) {
                return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
            } else {
                return mFormatter.format("%02d:%02d", minutes, seconds).toString();
            }
        } else {
            return "";
        }
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String formatInteger(String str) {
        BigDecimal parsed = new BigDecimal(str);
        DecimalFormat formatter =
                new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(parsed);
    }

    public static String formatDecimal(String str) {
        if (str.equals(".")) {
            return ".";
        }
        BigDecimal parsed = new BigDecimal(str);
        // example pattern VND #,###.00
        DecimalFormat formatter = new DecimalFormat("#,###." + getDecimalPattern(str),
                new DecimalFormatSymbols(Locale.US));
        formatter.setRoundingMode(RoundingMode.DOWN);
        return formatter.format(parsed);
    }

    public static String getDecimalPattern(String str) {
        int decimalCount = str.length() - str.indexOf(".") - 1;
        StringBuilder decimalPattern = new StringBuilder();
        for (int i = 0; i < decimalCount && i < 3; i++) {
            decimalPattern.append("0");
        }
        return decimalPattern.toString();
    }

    public static String convertUnixToStringTime(long unixSeconds) {
        try {
            Date date = new java.util.Date(unixSeconds * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }

    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String getAddrLatLng(Context context, double lat, double lng) {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> addresses = null; // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
