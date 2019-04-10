package club.wustfly.inggua.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class MainPageFragment extends BaseFragment {

    @BindView(R.id.banner)
    MZBannerView<BannerItem> banner;
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
    ViewPager viewPager;

    Integer[] imgs = new Integer[]{R.mipmap.banner1, R.mipmap.banner1, R.mipmap.banner1, R.mipmap.banner1, R.mipmap.banner1};

    List<BannerItem> list = new ArrayList<>();

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
        banner.setIndicatorRes(R.mipmap.banner_unselected_indicator, R.mipmap.banner_selected_indicator);

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
                ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
                lp.width = width + 20;
                lp.height = height;
                viewPager.setLayoutParams(lp);
            }
        });

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
        banner.setPages(bannerItemList, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        list.clear();
        list.addAll(bannerItemList);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveLocationResult(BDLocation location) {
        location_txt.setText(location.getLocationDescribe());
        showToast("位置已更新");
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.pause();
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

    public class BannerViewHolder implements MZViewHolder<BannerItem> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, BannerItem data) {

            Glide.with(MainPageFragment.this)
                    .load(Constants.BASE_URL + data.getImage())
                    .placeholder(R.mipmap.banner_model)
                    .into(mImageView);
        }
    }

    private PagerAdapter adapter = new PagerAdapter() {

        private final int mLooperCountFactor = 500;

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
    };
}
