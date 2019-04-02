package club.wustfly.inggua.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//import com.wildma.pictureselector.ImageUtils;
//import com.wildma.pictureselector.PictureSelector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;
import club.wustfly.inggua.cache.Session;
import club.wustfly.inggua.common.Constants;
import club.wustfly.inggua.ui.base.BaseActivity;

import static com.baidu.mapapi.BMapManager.getContext;

public class PersonalInfoActivity extends BaseActivity {

    @BindView(R.id.profile_img)
    ImageView mIvImage;
    @BindView(R.id.username_txt)
    TextView username_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_activity_layout);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle("个人信息");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();
    }

    @OnClick({R.id.modify_profile, R.id.modify_nickname})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.modify_profile:
//                PictureSelector
//                        .create(this, PictureSelector.SELECT_REQUEST_CODE)
//                        .selectPicture(true, 200, 200, 1, 1);
                break;
            case R.id.modify_nickname:
                startActivity(ModifyNicknameActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
//        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
//            if (data != null) {
//                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
//                mIvImage.setImageBitmap(ImageUtils.getBitmap(picturePath));
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(getContext())
                .load(Constants.BASE_URL + Session.getSession().getUser().getHeadimg())
                .placeholder(R.mipmap.profile_img)
                .into(mIvImage);
        username_txt.setText(Session.getSession().getUser().getUsername());
    }
}
