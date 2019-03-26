package club.wustfly.yingua.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.weigan.loopview.LoopView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;

public class SelectSendTimeDialog extends Dialog {

    @BindView(R.id.loopView_left)
    LoopView loopView_left;
    @BindView(R.id.loopView_right)
    LoopView loopView_right;

    String[] data_left = {"今天（周二）", "明天（周三）"};
    String[] data_right = {"7:00~8:00", "11:30~13:00", "16:30~18:00", "21:00~22:30"};

    Context mContext;

    public SelectSendTimeDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_send_time_dialog_layout);

        ButterKnife.bind(this);

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = defaultDisplay.getWidth() * 4 / 5;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);

        init();
    }

    private void init() {
        loopView_left.setItems(Arrays.asList(data_left));
        loopView_right.setItems(Arrays.asList(data_right));
        loopView_left.setNotLoop();
        loopView_right.setNotLoop();
    }

    @OnClick({R.id.cancel_btn, R.id.confirm_btn})
    public void handleClick(View view) {

        switch (view.getId()) {
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.confirm_btn:

                break;
        }

    }
}
