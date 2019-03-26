package club.wustfly.yingua.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.logo_container)
    RelativeLayout logo_container;
    @BindView(R.id.logo_bg)
    ImageView logo_bg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_activity_layout);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle("关于我们");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

        logo_container.post(new Runnable() {
            @Override
            public void run() {
                int width = logo_container.getWidth();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.mipmap.about_us_logo_bg, options);
                int height = (int) (width * 1.0 / options.outWidth * options.outHeight);

                ViewGroup.LayoutParams lp = logo_bg.getLayoutParams();
                lp.width = width;
                lp.height = height;
                logo_bg.setLayoutParams(lp);
            }
        });

    }

    @OnClick({R.id.instructions_btn, R.id.service_agreement_btn, R.id.version_update_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.instructions_btn:
                break;
            case R.id.service_agreement_btn:
                startActivity(ServiceAgreementActivity.class);
                break;
            case R.id.version_update_btn:
                break;
        }
    }
}
