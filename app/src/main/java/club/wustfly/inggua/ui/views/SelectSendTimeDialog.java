package club.wustfly.inggua.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;

public class SelectSendTimeDialog extends Dialog {

    @BindView(R.id.loopView_left)
    LoopView loopView_left;
    @BindView(R.id.loopView_right)
    LoopView loopView_right;

    String[] data_left = {"今天（周二）", "明天（周三）"};
    String[] data_right = {"7:00~8:00", "11:30~13:00", "16:30~18:00", "21:00~22:30"};

    Context mContext;

    public SelectSendTimeDialog(@NonNull Context context, OnTimeSelector mOnTimeSelector) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mOnTimeSelector = mOnTimeSelector;
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


    int left_index = 0;
    int right_index = 0;

    List<String> list_left = new ArrayList<>();
    List<String> list_right1 = new ArrayList<>();
    List<String> list_right2 = new ArrayList<>();

    String today;
    String tomorrow;

    private void init() {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("E");
            String dat1 = "今天(" + sdf.format(calendar.getTime()) + ")";
            today = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            String dat2 = "明天(" + sdf.format(calendar.getTime()) + ")";
            tomorrow = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

            data_left = new String[]{dat1, dat2};

            calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            String d = sdf.format(now);

            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date time_07_00 = sdf.parse(d + " 07:00");
            Date time_11_30 = sdf.parse(d + " 11:30");
            Date time_16_30 = sdf.parse(d + " 16:30");
            Date time_21_00 = sdf.parse(d + " 21:00");
            Date time_20_45 = sdf.parse(d + " 20:45");
            Date time_23_30 = sdf.parse(d + " 23:30");

            list_left.clear();
            list_right1.clear();
            list_right2.clear();

            if (now.before(time_20_45)) {
                list_left.add(dat1);
                list_left.add(dat2);

                if (now.before(time_07_00)) {
                    list_right1.addAll(Arrays.asList(data_right));
                } else if (now.before(time_11_30)) {
                    list_right1.add("11:30~13:00");
                    list_right1.add("16:30~18:00");
                    list_right1.add("21:00~22:30");
                } else if (now.before(time_16_30)) {
                    list_right1.add("16:30~18:00");
                    list_right1.add("21:00~22:30");
                } else {
                    list_right1.add("21:00~22:30");
                }

                list_right2.addAll(Arrays.asList(data_right));
            } else if (now.before(time_23_30)) {
                list_left.add(dat2);
                list_right1.addAll(Arrays.asList(data_right));
            } else {
                list_left.add(dat2);
                list_right1.add("11:30~13:00");
            }

            loopView_left.setItems(list_left);
            loopView_right.setItems(list_right1);

            loopView_left.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    left_index = index;
                    right_index = 0;
                    if (index == 0) {
                        loopView_right.setItems(list_right1);
                    } else if (index == 1) {
                        loopView_right.setItems(list_right2);
                    }
                    loopView_right.setCurrentPosition(0);
                }
            });

            loopView_right.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    right_index = index;
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

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
                String timeStr = "";
                String showTimeStr = "";
                if (left_index == 0) {
                    if (list_left.size() > 1) {
                        timeStr = today + " " + list_right1.get(right_index);
                        showTimeStr = data_left[0] + " " + list_right1.get(right_index);
                    } else {
                        timeStr = tomorrow + " " + list_right1.get(right_index);
                        showTimeStr = data_left[1] + " " + list_right1.get(right_index);
                    }
                } else if (left_index == 1) {
                    timeStr = tomorrow + " " + list_right2.get(right_index);
                    showTimeStr = data_left[1] + " " + list_right2.get(right_index);
                }
                mOnTimeSelector.selectTime(showTimeStr, timeStr);
                dismiss();
                break;
        }

    }

    private OnTimeSelector mOnTimeSelector;

    public interface OnTimeSelector {
        void selectTime(String showTimeStr, String timeStr);
    }
}
