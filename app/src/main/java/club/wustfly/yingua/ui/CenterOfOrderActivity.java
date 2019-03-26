package club.wustfly.yingua.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;
import club.wustfly.yingua.ui.fragment.OrderAllFragment;
import club.wustfly.yingua.ui.fragment.OrderFinishedFragment;
import club.wustfly.yingua.ui.fragment.OrderPayedFragment;
import club.wustfly.yingua.ui.fragment.OrderWaitPayFragment;

public class CenterOfOrderActivity extends BaseActivity {

    @BindView(R.id.all_btn)
    TextView all_btn;
    @BindView(R.id.wait_pay_btn)
    TextView wait_pay_btn;
    @BindView(R.id.payed_btn)
    TextView payed_btn;
    @BindView(R.id.finish_btn)
    TextView finish_btn;

    Fragment[] fragments = {new OrderAllFragment(), new OrderWaitPayFragment(), new OrderPayedFragment(), new OrderFinishedFragment()};

    String[] tags = {"tab1", "tab2", "tab3", "tab4"};

    FragmentManager mFragmentMannager;

    int currentIndex = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.center_of_order_activity_layout);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("currentIndex", 0);
        }

        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentIndex", currentIndex);
    }

    private void init() {

        setTitle("订单中心");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

        mFragmentMannager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentMannager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            Fragment f = mFragmentMannager.findFragmentByTag(tags[i]);
            if (f == null) {
                ft.add(R.id.content, fragments[i], tags[i]);
            } else {
                fragments[i] = f;
            }
            ft.hide(fragments[i]);
        }
        ft.show(fragments[currentIndex]).commit();

    }

    @OnClick({R.id.all_btn, R.id.wait_pay_btn, R.id.payed_btn, R.id.finish_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.all_btn:
                all_btn.setTextColor(Color.parseColor("#FD7C13"));
                wait_pay_btn.setTextColor(Color.parseColor("#333333"));
                payed_btn.setTextColor(Color.parseColor("#333333"));
                finish_btn.setTextColor(Color.parseColor("#333333"));
                all_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.mipmap.center_of_order_tab_logo));
                wait_pay_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                payed_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                finish_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                showTab(0);
                break;
            case R.id.wait_pay_btn:
                all_btn.setTextColor(Color.parseColor("#333333"));
                wait_pay_btn.setTextColor(Color.parseColor("#FD7C13"));
                payed_btn.setTextColor(Color.parseColor("#333333"));
                finish_btn.setTextColor(Color.parseColor("#333333"));
                all_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                wait_pay_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.mipmap.center_of_order_tab_logo));
                payed_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                finish_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                showTab(1);
                break;
            case R.id.payed_btn:
                all_btn.setTextColor(Color.parseColor("#333333"));
                wait_pay_btn.setTextColor(Color.parseColor("#333333"));
                payed_btn.setTextColor(Color.parseColor("#FD7C13"));
                finish_btn.setTextColor(Color.parseColor("#333333"));
                all_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                wait_pay_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                payed_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.mipmap.center_of_order_tab_logo));
                finish_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                showTab(2);
                break;
            case R.id.finish_btn:
                all_btn.setTextColor(Color.parseColor("#333333"));
                wait_pay_btn.setTextColor(Color.parseColor("#333333"));
                payed_btn.setTextColor(Color.parseColor("#333333"));
                finish_btn.setTextColor(Color.parseColor("#FD7C13"));
                all_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                wait_pay_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                payed_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                finish_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.mipmap.center_of_order_tab_logo));

                showTab(3);
                break;
        }
    }

    private void showTab(int index) {
        currentIndex = index;
        FragmentTransaction ft = mFragmentMannager.beginTransaction();
        for (Fragment f : fragments) {
            ft.hide(f);
        }
        ft.show(fragments[index]).commit();
    }
}
