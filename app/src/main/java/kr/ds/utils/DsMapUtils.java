package kr.ds.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2015-02-10.
 */
public class DsMapUtils {

    private static DsMapUtils mapUtils = null;

    public DsMapUtils(){

    }
    public static DsMapUtils getInstance(){
        if(mapUtils == null){
            synchronized (DsMapUtils.class){
                if(mapUtils == null){
                    mapUtils = new DsMapUtils();
                }
            }
        }
        return mapUtils;
    }


    public static boolean isGps(Context context) {
        LocationManager gpsLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (gpsLocationManager != null) {
            boolean isGPS = gpsLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetwork = gpsLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPS || isNetwork) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static HashMap getAddress(Context context, Location location){
        if(location == null) {
            return null;
        }
        HashMap<String, String> mHashMap = new HashMap<String,String>();
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        Geocoder gc = new Geocoder(context, Locale.KOREAN);
        try {
            List<Address> addresses = gc.getFromLocation(lat, lon, 1);
            StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    mHashMap.put("locality",address.getLocality());
                    mHashMap.put("thoroghfare",address.getThoroughfare());
                    mHashMap.put("featurename",address.getFeatureName());
                    mHashMap.put("lat",String.valueOf(lat));
                    mHashMap.put("lon",String.valueOf(lon));
                }
        }catch (Exception e){
            return null;
        }
        return mHashMap;
    }


}
