package club.wustfly.inggua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.wustfly.inggua.R;
import club.wustfly.inggua.ui.base.BaseActivity;
import club.wustfly.inggua.utils.Cache;
import club.wustfly.inggua.utils.Util;

public class LauncherActivity extends BaseActivity {

    public static final String VERSION = "VERSION";

    @BindView(R.id.timer_counter)
    TextView timer_counter;

    private int delay = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_layout);
        ButterKnife.bind(this);

        wait_some_seconds();

    }

    private void wait_some_seconds() {
        timer_counter.setText(delay + "s");
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        delay = delay - 1;
                        timer_counter.setText(delay + "s");
                        if (delay == 0) {
                            goto_guide_or_main();
                            timer.cancel();
                        }
                    }
                });

            }
        }, 1000, 1000);
    }

    private void goto_guide_or_main() {
        String localVersionName = Util.getLocalVersionName(this);
        String version = Cache.getSharedValue(VERSION);
        if (version.equals(localVersionName)) {
            startActivity(LoginActivity.class);
        } else {
            startActivity(LoginActivity.class/*GuidePageActivity.class*/);
        }
        finish();
    }
}
