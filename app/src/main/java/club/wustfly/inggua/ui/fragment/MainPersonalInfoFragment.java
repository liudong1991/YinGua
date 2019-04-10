package club.wustfly.inggua.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;
import club.wustfly.inggua.cache.Session;
import club.wustfly.inggua.common.Constants;
import club.wustfly.inggua.ui.AboutUsActivity;
import club.wustfly.inggua.ui.CenterOfOrderActivity;
import club.wustfly.inggua.ui.HowToUseActivity;
import club.wustfly.inggua.ui.PersonalInfoActivity;
import club.wustfly.inggua.ui.base.BaseFragment;

public class MainPersonalInfoFragment extends BaseFragment {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.name)
    TextView name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_info_fragment_layout, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
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

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getContext())
                .load(Constants.BASE_URL + Session.getSession().getUser().getHeadimg())
                .placeholder(R.mipmap.inggua_logo_big)
                .into(logo);
        name.setText(Session.getSession().getUser().getUsername());
    }
}
