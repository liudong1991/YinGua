package club.wustfly.yingua.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import club.wustfly.yingua.R;
import club.wustfly.yingua.model.event.RequestFinishEvent;
import club.wustfly.yingua.ui.views.CustomProgressDialog;
import club.wustfly.yingua.utils.StatusBarUtil;

public class BaseActivity extends AppCompatActivity {

    private CustomProgressDialog dialog;

    /**
     * 获取状态栏的高度
     *
     * @param context
     */
    public static int getStatusBarHeight(Activity context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarDarkTheme(this, true);

        EventBus.getDefault().register(this);
    }

    protected void startActivity(Class clazz) {
        super.startActivity(new Intent(this, clazz));
    }

    protected void setTitle(String title) {
        ((TextView) findViewById(R.id.title)).setText(title);
    }

    protected void setBack() {
        View backBtn = findViewById(R.id.back_btn);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView left_btn = findViewById(R.id.left_btn);
        left_btn.setVisibility(View.GONE);
    }

    protected void setLeft(String content, final IOnSubtitle onSubtitle) {
        View backBtn = findViewById(R.id.back_btn);
        backBtn.setVisibility(View.GONE);
        TextView left_btn = findViewById(R.id.left_btn);
        left_btn.setVisibility(View.VISIBLE);
        left_btn.setText(content);
        left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubtitle.handle();
            }
        });
    }

    protected void setSubtitle(String subtitle, final IOnSubtitle onSubtitle) {
        TextView sub_txt = findViewById(R.id.subtitle);
        sub_txt.setVisibility(View.VISIBLE);
        sub_txt.setText(subtitle);
        sub_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubtitle.handle();
            }
        });
    }

    protected void setHeaderTopMargin() {
        View header = findViewById(R.id.header);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) header.getLayoutParams();
        lp.topMargin = getStatusBarHeight(this);
        header.setLayoutParams(lp);
    }

    protected void setHeaderTopPadding() {
        View header = findViewById(R.id.header);
        header.setPadding(0, getStatusBarHeight(this), 0, 0);
    }

    protected void setHeaderBackgroundColor(String colorStr) {
        findViewById(R.id.header).setBackgroundColor(Color.parseColor(colorStr));
    }

    protected interface IOnSubtitle {
        void handle();
    }

    public void dismissProgressDialog() {
        if (this.dialog != null) {
            if (this.dialog.isShowing())
                this.dialog.dismiss();
        }
    }

    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        if (this.dialog == null) {
            this.dialog = new CustomProgressDialog(this);
        }
        this.dialog.setTips("加载中...");
        if (!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestFinish(RequestFinishEvent ev) {
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
