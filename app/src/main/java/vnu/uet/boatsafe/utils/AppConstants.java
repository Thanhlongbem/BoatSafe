

package vnu.uet.boatsafe.utils;

import java.util.ArrayList;
import java.util.List;

import vnu.uet.boatsafe.models.History;

public class AppConstants {



    private AppConstants() {
        // This utility class is not publicly instantiable
    }

    public static final String OS = "0";


    public static final String[] LOCATION_PERMISSION = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static final List<String> LIST_FILE_LOGGER = new ArrayList<String>() {
        {
            add(new String("accRaw.txt"));
            add(new String("accKFilter.txt"));
            add(new String("speedUpdate.txt"));
            add(new String("v_gps.txt"));
            add(new String("v_acc.txt"));
            add(new String("accFilter.txt"));
            add(new String("locationUpdate.txt"));
            add(new String("orientation.txt"));
        }
    };




}
