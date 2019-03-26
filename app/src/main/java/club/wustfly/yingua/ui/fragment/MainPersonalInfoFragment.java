package club.wustfly.yingua.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.AboutUsActivity;
import club.wustfly.yingua.ui.CenterOfOrderActivity;
import club.wustfly.yingua.ui.HowToUseActivity;
import club.wustfly.yingua.ui.PersonalInfoActivity;
import club.wustfly.yingua.ui.base.BaseFragment;

public class MainPersonalInfoFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_info_fragment_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.logo, R.id.name, R.id.how_to_use_btn, R.id.order_center_btn, R.id.about_us_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.logo:
            case R.id.name:
                startActivity(new Intent(getContext(), PersonalInfoActivity.class));
                break;
            case R.id.order_center_btn:
                startActivity(new Intent(getContext(), CenterOfOrderActivity.class));
                break;
            case R.id.how_to_use_btn:
                startActivity(new Intent(getContext(), HowToUseActivity.class));
                break;
            case R.id.about_us_btn:
                startActivity(new Intent(getContext(), AboutUsActivity.class));
                break;
        }
    }


}
