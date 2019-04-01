package club.wustfly.inggua;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import club.wustfly.inggua.loc.LocationService;

public class YinGuaApplication extends Application {

    private static YinGuaApplication instance;

    public static LocationService locationService;

    public static YinGuaApplication getInstance() {
        return instance;
    }

    public static LocationService getLocationService() {
        return locationService;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        locationService = new LocationService(getApplicationContext());

        SDKInitializer.initialize(getApplicationContext());

    }


}
