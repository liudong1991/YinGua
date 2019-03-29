package club.wustfly.yingua;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.loc.LocationService;
import club.wustfly.yingua.model.event.ReLocateEvent;
import club.wustfly.yingua.ui.base.BaseActivity;
import club.wustfly.yingua.ui.fragment.MainPageFragment;
import club.wustfly.yingua.ui.fragment.MainPersonalInfoFragment;

public class MainActivity extends BaseActivity {

    private static final int BAIDU_READ_PHONE_STATE = 100;

    @BindView(R.id.main_page_btn)
    TextView main_page_btn;
    @BindView(R.id.personal_info_btn)
    TextView personal_info_btn;

    Fragment[] fragments = {new MainPageFragment(), new MainPersonalInfoFragment()};

    String[] tags = {"tab1", "tab2"};

    FragmentManager mFragmentMannager;

    int currentIndex = 0;

    private LocationService locationService;

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

        locationService = YinGuaApplication.getLocationService();

        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locate();
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

    @Override
    protected void onStop() {
        locationService.unregisterListener(mListener);
        locationService.stop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService.registerListener(mListener);
    }

    private void locate() {
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts();
        } else {
            locationService.start();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLocate(ReLocateEvent event) {
        if (locationService.isStart())
            locationService.stop();
        locate();
    }

    public void showContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED/*
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED*/) {
            Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION/*, Manifest.permission.READ_PHONE_STATE*/}, BAIDU_READ_PHONE_STATE);
        } else {
            locationService.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    locationService.start();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.i("wcl", "定位结果：" + sb.toString());
                EventBus.getDefault().post(location);
                locationService.stop();
            }
        }

    };

}
