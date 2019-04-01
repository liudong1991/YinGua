package club.wustfly.inggua.ui;

import club.wustfly.inggua.ui.base.BaseActivity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ImageView;
//
//import club.wustfly.yingua.R;
//import club.wustfly.yingua.utils.Cache;
//import club.wustfly.yingua.utils.Util;
//import cn.bingoogolapple.bgabanner.BGABanner;
//import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class GuidePageActivity extends BaseActivity {

    /*private BGABanner mBackgroundBanner;
    private BGABanner mForegroundBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        setListener();
        processLogic();
    }

    private void initView() {
        setContentView(R.layout.guide_page_layout);
        mBackgroundBanner = findViewById(R.id.banner_guide_background);
        mForegroundBanner = findViewById(R.id.banner_guide_foreground);
    }

    private void setListener() {
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                Cache.putSharedValue(LauncherActivity.VERSION, Util.getLocalVersionName(GuidePageActivity.this));
                startActivity(new Intent(GuidePageActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void processLogic() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.mipmap.uoko_guide_background_1,
                R.mipmap.uoko_guide_background_2,
                R.mipmap.uoko_guide_background_3);

        mForegroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.mipmap.uoko_guide_foreground_1,
                R.mipmap.uoko_guide_foreground_2,
                R.mipmap.uoko_guide_foreground_3);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }*/


}
