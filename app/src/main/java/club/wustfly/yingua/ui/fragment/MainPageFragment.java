package club.wustfly.yingua.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.MainActivity;
import club.wustfly.yingua.R;
import club.wustfly.yingua.common.Constants;
import club.wustfly.yingua.model.bean.BannerItem;
import club.wustfly.yingua.model.event.ReLocateEvent;
import club.wustfly.yingua.model.resp.GetBannerImgRespDto;
import club.wustfly.yingua.net.RequestWrapper;
import club.wustfly.yingua.ui.PrintDocumentActivity;
import club.wustfly.yingua.ui.base.BaseFragment;

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

    //Integer[] imgs = new Integer[]{R.mipmap.banner1, R.mipmap.banner1, R.mipmap.banner1, R.mipmap.banner1, R.mipmap.banner1};

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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveLocationResult(BDLocation location) {
        location_txt.setText(location.getAddrStr());
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
                    .placeholder(R.mipmap.banner1)
                    .into(mImageView);


        }
    }
}
