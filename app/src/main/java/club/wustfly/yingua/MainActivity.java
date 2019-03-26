package club.wustfly.yingua;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.ui.base.BaseActivity;
import club.wustfly.yingua.ui.fragment.MainPageFragment;
import club.wustfly.yingua.ui.fragment.MainPersonalInfoFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_page_btn)
    TextView main_page_btn;
    @BindView(R.id.personal_info_btn)
    TextView personal_info_btn;

    Fragment[] fragments = {new MainPageFragment(), new MainPersonalInfoFragment()};

    String[] tags = {"tab1", "tab2"};

    FragmentManager mFragmentMannager;

    int currentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    @OnClick({R.id.main_page_btn, R.id.personal_info_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.main_page_btn:
                main_page_btn.setTextColor(Color.parseColor("#FD7C13"));
                personal_info_btn.setTextColor(Color.parseColor("#999999"));
                main_page_btn.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.mipmap.main_page_selected), null, null);
                personal_info_btn.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.mipmap.personal_info_unselected), null, null);

                showTab(0);
                break;
            case R.id.personal_info_btn:
                main_page_btn.setTextColor(Color.parseColor("#999999"));
                personal_info_btn.setTextColor(Color.parseColor("#FD7C13"));
                main_page_btn.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.mipmap.main_page_unselected), null, null);
                personal_info_btn.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.mipmap.personal_info_selected), null, null);

                showTab(1);
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
