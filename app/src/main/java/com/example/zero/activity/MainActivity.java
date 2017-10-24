package com.example.zero.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.zero.fragment.FragmentController;

import com.example.zero.fragment.RouteFragmentDouble;
import com.example.zero.greentravel_new.R;
import com.example.zero.view.TitleLayout;
import com.example.zero.view.TitleRouteLayout;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, SensorEventListener {

    /**
     * 内容区域
     */
    private LinearLayout bottom_nav_content;
    /**
     * 底部导航栏
     */
    private BottomNavigationBar bottom_navigation_bar_container;

    private BottomNavigationItem personItem;
    private BottomNavigationItem adviceItem;
    private BottomNavigationItem routeItem;
    private BottomNavigationItem salesItem;
    private BadgeItem badgeItem;

    /**
     * 标题栏
     */
    private TitleLayout titleLayout;
    private TitleRouteLayout titleRouteLayout;

    /**
     * Fragment控制类
     */
    private FragmentController fragmentController;

    /**
     * 定位SDK的核心类
     */
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    /**
     * 定位按钮
     */
    private Button btnLocation;

    MapView mMapView;
    BaiduMap mBaiduMap;

    // UI相关
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    Button requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        initBottomNavBar();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        Log.d(TAG, "onCreate: success");
    }

    /**
     * 初始化视图
     */
    private void initView() {
        bottom_nav_content = (LinearLayout) findViewById(R.id.bottom_nav_content);
        bottom_navigation_bar_container = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar_container);

        titleLayout = (TitleLayout) findViewById(R.id.main_title);
        titleRouteLayout = (TitleRouteLayout) findViewById(R.id.route_title);

        btnLocation = (Button) findViewById(R.id.btn_map_main_location);

        Log.d(TAG, "Sensor: begin");
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        if (mSensorManager == null) {
            Log.d(TAG, "Sensor: sm is null");
            return;
        } else {
            Log.d(TAG, "Sensor: sm is ok");
        }
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    changeMode();
                }
            }
        });

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.map_main);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 设置返回结果包含手机的方向
        option.setLocationNotify(true); // 可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true); // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true); // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocClient.registerLocationListener(myListener);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();
    }

    private void changeMode() {
        switch (mCurrentMode) {
            case NORMAL:
                mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                Toast.makeText(MainActivity.this, "当前模式:跟随", Toast.LENGTH_SHORT).show();
                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.overlook(0);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                break;
            case COMPASS:
                mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                Toast.makeText(MainActivity.this, "当前模式:正常", Toast.LENGTH_SHORT).show();
                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
                MapStatus.Builder builder1 = new MapStatus.Builder();
                builder1.overlook(0);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
                break;
            case FOLLOWING:
                mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                Toast.makeText(MainActivity.this, "当前模式:罗盘", Toast.LENGTH_SHORT).show();
                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
                break;
            default:
                break;
        }
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomNavBar() {
        bottom_navigation_bar_container.setAutoHideEnabled(true);//自动隐藏

        bottom_navigation_bar_container.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_navigation_bar_container.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottom_navigation_bar_container.setBarBackgroundColor(R.color.white);//背景颜色
        bottom_navigation_bar_container.setInActiveColor(R.color.nav_gray);//未选中时的颜色
        bottom_navigation_bar_container.setActiveColor(R.color.colorPrimaryDark);//选中时的颜色

        badgeItem = new BadgeItem().setBackgroundColor(Color.RED).setText("99").setHideOnSelect(true);//角标

        routeItem = new BottomNavigationItem(R.drawable.route, "路线");
        adviceItem = new BottomNavigationItem(R.drawable.advice, "建议");
        salesItem = new BottomNavigationItem(R.drawable.sale, "促销");
        personItem = new BottomNavigationItem(R.drawable.person, "个人");
        personItem.setBadgeItem(badgeItem);

        bottom_navigation_bar_container.addItem(routeItem).addItem(adviceItem).addItem(salesItem).addItem(personItem);
        bottom_navigation_bar_container.initialise();
        bottom_navigation_bar_container.setTabSelectedListener(this);

        fragmentController = FragmentController.getInstance(this, R.id.bottom_nav_content);
        fragmentController.showFragment(0);
    }

    /**
     * 底部NaV监听
     *
     * @param position Fragment位置
     */
    @Override
    public void onTabSelected(int position) {
        fragmentController.hideFragments();//先隐藏所有frag
        switch (position) {
            case 0:
                fragmentController.showFragment(0);
                getSupportActionBar().setTitle("路线");
                titleLayout.setVisibility(View.GONE);
                titleRouteLayout.setVisibility(View.VISIBLE);
                mMapView.setVisibility(View.VISIBLE);
                btnLocation.setVisibility(View.VISIBLE);
                break;

            case 1:
                fragmentController.showFragment(1);
                getSupportActionBar().setTitle("建议");
                titleLayout.setVisibility(View.VISIBLE);
                titleRouteLayout.setVisibility(View.GONE);
                mMapView.setVisibility(View.GONE);
                btnLocation.setVisibility(View.GONE);
                break;

            case 2:
                fragmentController.showFragment(2);
                getSupportActionBar().setTitle("促销");
                titleLayout.setVisibility(View.VISIBLE);
                titleRouteLayout.setVisibility(View.GONE);
                mMapView.setVisibility(View.GONE);
                btnLocation.setVisibility(View.GONE);
                break;

            case 3:
                fragmentController.showFragment(3);
                getSupportActionBar().setTitle("个人");
                mMapView.setVisibility(View.GONE);
                btnLocation.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection)
                    .latitude(mCurrentLat)
                    .longitude(mCurrentLon)
                    .build();
            mBaiduMap.setMyLocationData(locData);

            MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfiguration(config);
        }
        Log.d(TAG, "Sensor: " + sensorEvent);
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(locData);
            MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfiguration(config);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            Toast.makeText(MainActivity.this, location.getLocationDescribe(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onReceiveLocation: " + location.getLocType());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mLocClient.isStarted()) {
            mLocClient.start();// 开启定位
        }
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "PERMISSION Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
