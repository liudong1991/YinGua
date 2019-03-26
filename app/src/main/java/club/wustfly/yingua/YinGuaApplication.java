package club.wustfly.yingua;

import android.app.Application;

public class YinGuaApplication extends Application {

    private static YinGuaApplication instance;

    public static YinGuaApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

    }
}
