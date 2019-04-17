package club.wustfly.inggua.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;
import club.wustfly.inggua.common.Constants;
import club.wustfly.inggua.model.bean.BannerItem;
import club.wustfly.inggua.model.event.ReLocateEvent;
import club.wustfly.inggua.model.resp.GetBannerImgRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.PrintDocumentActivity;
import club.wustfly.inggua.ui.base.BaseFragment;
import club.wustfly.inggua.ui.views.LzViewPager;

public class MainPageFragment extends BaseFragment {

    @BindView(R.id.location_module)
    RelativeLayout location_module;
    @BindView(R.id.location_bg)
    ImageView location_bg;
    @BindView(R.id.location_txt)
    TextView location_txt;
    @BindView(R.id.service_status_txt)
    TextView service_status_txt;
    @BindView(R.id.re_locate_btn)
    TextView re_locate_btn;
    @BindView(R.id.viewpager)
    LzViewPager viewPager;
    @BindView(R.id.mIndicatorContainer)
    LinearLayout mIndicatorContainer;

    private int mDelayedTime = 3000;// Banner 切换时间间隔

    Handler mHandler = new Handler();

    private ViewPagerScroller mViewPagerScroller;

    //Integer[] imgs = new Integer[]{R.mipmap.banner1, R.mipmap.banner1, R.mipmap.banner1, R.mipmap.banner1, R.mipmap.banner1};

    List<BannerItem> list = new ArrayList<>();

    List<ImageView> mIndicators = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_page_fragment_layout, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHeaderBackgroundColor("#FFFFFF");
        setTitle("首页");
        setHeaderTopPadding();
    }

    private void init() {
        location_module.post(new Runnable() {
            @Override
            public void run() {
                int width = location_module.getWidth();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.mipmap.main_page_location_bg, options);
                int height = (int) (width * 1.0 / options.outWidth * options.outHeight);

                ViewGroup.LayoutParams lp = location_bg.getLayoutParams();
                lp.width = width;
                lp.height = height;
                location_bg.setLayoutParams(lp);
            }
        });

        getBannerImg();

        viewPager.post(new Runnable() {
            @Override
            public void run() {
                int width = viewPager.getWidth();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.mipmap.banner_model, options);
                int height = (int) (width * 1.0 / options.outWidth * options.outHeight);
                //Log.i("wust-lz", "wust-ls===>viewpager width:" + width);
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) viewPager.getLayoutParams();
                lp.width = width + 20;
                lp.height = height;
                lp.leftMargin = 0;
                lp.rightMargin = 0;
                viewPager.setLayoutParams(lp);
            }
        });

        initViewPagerScroll();
        viewPager.setListener(new LzViewPager.onLzEventListener() {
            @Override
            public void pause() {
                MainPageFragment.this.pause();
            }

            @Override
            public void start() {
                MainPageFragment.this.start();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != mCurrentItem) mCurrentItem = position;
                int realSelectPosition = mCurrentItem % mIndicators.size();
                for (int i = 0; i < mIndicators.size(); i++) {
                    if (i == realSelectPosition) {
                        mIndicators.get(i).setImageResource(R.mipmap.banner_selected_indicator);
                    } else {
                        mIndicators.get(i).setImageResource(R.mipmap.banner_unselected_indicator);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
        viewPager.setPageTransformer(false, new ScaleTransformer());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    private void getBannerImg() {
        showProgressDialog();
        RequestWrapper.getBannerImg();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveGetBannerImgResult(GetBannerImgRespDto bannerImgRespDto) {
        List<BannerItem> bannerItemList = bannerImgRespDto.getBanner();
        pause();
        list.clear();
        list.addAll(bannerItemList);
        initIndicator();
        adapter.notifyDataSetChanged();
        mCurrentItem = getStartSelectItem();
        viewPager.setCurrentItem(mCurrentItem);
        start();
    }

    private void initIndicator() {
        mIndicatorContainer.removeAllViews();
        mIndicators.clear();
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setPadding(6, 0, 6, 0);

            if (i == (mCurrentItem % list.size())) {
                imageView.setImageResource(R.mipmap.banner_selected_indicator);
            } else {
                imageView.setImageResource(R.mipmap.banner_unselected_indicator);
            }

            mIndicators.add(imageView);
            mIndicatorContainer.addView(imageView);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveLocationResult(BDLocation location) {
        location_txt.setText(location.getLocationDescribe());
        showToast("位置已更新");
    }

    @Override
    public void onResume() {
        super.onResume();
        //banner.start();
        start();
    }

    @Override
    public void onPause() {
        super.onPause();
        //banner.pause();
        pause();
    }

    @OnClick({R.id.label_title1_container, R.id.re_locate_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.label_title1_container:
                startActivity(new Intent(getContext(), PrintDocumentActivity.class));
                break;
            case R.id.re_locate_btn:
                // re_locate_btn.setEnabled(false);
                EventBus.getDefault().post(new ReLocateEvent());
                break;
        }

    }

    private PagerAdapter adapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return getRealCount() * mLooperCountFactor;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int realPosition = position % getRealCount();
            ImageView iv = new ImageView(getContext());
            container.addView(iv);
            BannerItem data = list.get(realPosition);
            Glide.with(MainPageFragment.this)
                    .load(Constants.BASE_URL + data.getImage())
                    .placeholder(/*R.mipmap.banner_model*/R.mipmap.banner1)
                    .into(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private void setCurrentItem(int position) {
            try {
                viewPager.setCurrentItem(position, false);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void finishUpdate(@NonNull ViewGroup container) {
            int position = viewPager.getCurrentItem();
            if (position == getCount() - 1) {
                position = 0;
                setCurrentItem(position);
            }
        }
    };

    private final int mLooperCountFactor = 50000;

    private int getStartSelectItem() {
        if (getRealCount() == 0) {
            return 0;
        }
        // 我们设置当前选中的位置为Integer.MAX_VALUE / 2,这样开始就能往左滑动
        // 但是要保证这个值与getRealPosition 的 余数为0，因为要从第一页开始显示
        int currentItem = getRealCount() * mLooperCountFactor / 2;
        if (currentItem % getRealCount() == 0) {
            return currentItem;
        }
        // 直到找到从0开始的位置
        while (currentItem % getRealCount() != 0) {
            currentItem++;
        }
        return currentItem;
    }

    private int getRealCount() {
        return list == null ? 0 : list.size();
    }

    public class ScaleTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.80f;
        private static final float MIN_ALPHA = 0.5f;

        @Override
        public void transformPage(View page, float position) {
            if (position < -1 || position > 1) {
                page.setAlpha(MIN_ALPHA);
                //page.setScaleX(MIN_SCALE);
                page.setScaleY(MIN_SCALE);
            } else if (position <= 1) { // [-1,1]
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                if (position < 0) {
                    float scaleX = 1 + 0.2f * position;
                    //Log.d("google_lenve_fb", "transformPage: scaleX:" + scaleX);
                    //page.setScaleX(scaleX);
                    page.setScaleY(scaleX);
                } else {
                    float scaleX = 1 - 0.2f * position;
                    //page.setScaleX(scaleX);
                    page.setScaleY(scaleX);
                }
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            }
        }
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mViewPagerScroller = new ViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, mViewPagerScroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static class ViewPagerScroller extends Scroller {
        private int mDuration = 800;// ViewPager默认的最大Duration 为600,我们默认稍微大一点。值越大越慢。
        private boolean mIsUseDefaultDuration = false;

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mIsUseDefaultDuration ? duration : mDuration);
        }

        public void setUseDefaultDuration(boolean useDefaultDuration) {
            mIsUseDefaultDuration = useDefaultDuration;
        }

        public boolean isUseDefaultDuration() {
            return mIsUseDefaultDuration;
        }

        public void setDuration(int duration) {
            mDuration = duration;
        }


        public int getScrollDuration() {
            return mDuration;
        }
    }

    int mCurrentItem;

    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            mCurrentItem = viewPager.getCurrentItem();
            mCurrentItem++;
            if (mCurrentItem == adapter.getCount() - 1) {
                mCurrentItem = 0;
                viewPager.setCurrentItem(mCurrentItem, false);
                mHandler.postDelayed(this, mDelayedTime);
            } else {
                viewPager.setCurrentItem(mCurrentItem);
                mHandler.postDelayed(this, mDelayedTime);
            }
        }
    };

    public void start() {
        pause();
        mHandler.postDelayed(mLoopRunnable, mDelayedTime);
    }

    public void pause() {
        mHandler.removeCallbacks(mLoopRunnable);
    }
}
