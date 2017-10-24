package com.example.zero.fragment;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.zero.activity.RouteResultActivity;
import com.example.zero.adapter.RouteSearchAdapter;
import com.example.zero.bean.RouteSearchBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.view.SearchPopView;
import com.example.zero.view.SearchView;
import com.example.zero.view.SimpleSearchView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class RouteMultiFragment extends Fragment implements SearchPopView.SearchPopViewListener, SensorEventListener {
    /**
     * 搜索view
     */
    private SearchPopView endSearchView;
    /**
     * 搜索结果列表view
     */
    private ListView lvResults;
    /**
     * 起点搜索view
     */
    private ArrayList<SearchPopView> searchViewList;
    /**
     * 起点添加按钮
     */
    private ArrayList<Button> btnaddList;
    /**
     * 搜索按钮
     */
    private Button btnSearch;
    /**
     * 抽屉按钮
     */
    private Button btnDrawer;
    /**
     * 抽屉
     */
    private SlidingDrawer slidingDrawer;
    /**
     * 定位按钮
     */
    private Button btnLocation;
    /**
     * 地图
     */
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    /**
     * 定位
     */
    private BMapManager mBMapManager;
    private SensorManager mSensorManager;
    private BitmapDescriptor mCurrentMarker;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float mCurrentAccracy;
    private MyLocationConfiguration.LocationMode mCurrentMode;

    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;
    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;
    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter0;
    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter1;
    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter2;
    /**
     * 搜索结果列表adapter
     */
    private RouteSearchAdapter resultAdapter;

    /**
     * 数据库数据，总数据
     */
    private List<RouteSearchBean> dbData;
    /**
     * 热搜版数据
     */
    private List<String> hintData;
    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;
    /**
     * 搜索结果的数据
     */
    private List<RouteSearchBean> resultData;
    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;
    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    private static final String TAG = "RouteMultiFragment";

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        RouteMultiFragment.hintSize = hintSize;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 定位初始化
        mLocClient = new LocationClient(getActivity().getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 设置返回结果包含手机的方向
        option.setLocationNotify(true); // 可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true); // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true); // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocClient.setLocOption(option);
        mLocClient.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_route_multi, container, false);
        //使用地图sdk前需先初始化BMapManager，这个必须在setContentView()先初始化
        mBMapManager = new BMapManager();
        mMapView = (MapView) v.findViewById(R.id.map_multi);
        mMapView.removeViewAt(1);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initViews();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        endSearchView = (SearchPopView) getView().findViewById(R.id.route_search_multi);
        lvResults = (ListView) getView().findViewById(R.id.route_lv_search_multi_results);
        btnSearch = (Button) getView().findViewById(R.id.route_btn_multi_search);

        int size = 4;
        searchViewList = new ArrayList<SearchPopView>(size);
        searchViewList.add((SearchPopView) getView().findViewById(R.id.route_search_multi0));
        searchViewList.add((SearchPopView) getView().findViewById(R.id.route_search_multi1));
        searchViewList.add((SearchPopView) getView().findViewById(R.id.route_search_multi2));

        btnaddList = new ArrayList<Button>(size);
        btnaddList.add((Button) getView().findViewById(R.id.route_btn_add_multi0));
        btnaddList.add((Button) getView().findViewById(R.id.route_btn_add_multi1));

        btnLocation = (Button) getView().findViewById(R.id.btn_map_multi_location);

        endSearchView.setHintText("请输入终点：");
        searchViewList.get(0).setHintText("情输入起点：");
        searchViewList.get(1).setHintText("情输入起点：");
        searchViewList.get(2).setHintText("情输入起点：");

        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mCurrentMode) {
                    case NORMAL:
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        Toast.makeText(getActivity(), "当前模式:跟随", Toast.LENGTH_SHORT).show();
                        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.overlook(0);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        break;
                    case COMPASS:
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        Toast.makeText(getActivity(), "当前模式:正常", Toast.LENGTH_SHORT).show();
                        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                        MapStatus.Builder builder1 = new MapStatus.Builder();
                        builder1.overlook(0);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
                        break;
                    case FOLLOWING:
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        Toast.makeText(getActivity(), "当前模式:罗盘", Toast.LENGTH_SHORT).show();
                        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/9/11 具体搜索
//                Toast.makeText(getActivity(), "开始搜索", Toast.LENGTH_SHORT).show();String beginStation = searchView.getText();
                String endStation = endSearchView.getText();
                ArrayList<String> beginStationList = new ArrayList<String>();
                beginStationList.add(searchViewList.get(0).getText());
                beginStationList.add(searchViewList.get(1).getText());
                beginStationList.add(searchViewList.get(2).getText());
                int beginNum = 0;
                for (String str : beginStationList) {
                    if (!str.equals("")) {
                        beginNum++;
                    }
                }

                Bundle mBundle = new Bundle();
                mBundle.putString("origin", "Multi");
                mBundle.putString("endStation", endStation);
                mBundle.putStringArrayList("beginStationList", beginStationList);
                mBundle.putInt("beginNum", beginNum);
                if ((!endStation.equals("")) & (beginNum != 0)) {
                    Intent intent = new Intent(getActivity(), RouteResultActivity.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "搜索框消息不完善，请填充完整后在开始搜索！", Toast.LENGTH_LONG).show();
                }
            }
        });

        //设置监听
        endSearchView.setSearchPopViewListener(this);
        searchViewList.get(0).setSearchPopViewListener(this);
        searchViewList.get(1).setSearchPopViewListener(this);
        searchViewList.get(2).setSearchPopViewListener(this);
        //设置adapter
        endSearchView.setTipsHintAdapter(hintAdapter);
        endSearchView.setAutoCompleteAdapter(autoCompleteAdapter);
        searchViewList.get(0).setTipsHintAdapter(hintAdapter);
        searchViewList.get(1).setTipsHintAdapter(hintAdapter);
        searchViewList.get(2).setTipsHintAdapter(hintAdapter);
        searchViewList.get(0).setAutoCompleteAdapter(autoCompleteAdapter0);
        searchViewList.get(1).setAutoCompleteAdapter(autoCompleteAdapter1);
        searchViewList.get(2).setAutoCompleteAdapter(autoCompleteAdapter2);

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
            }
        });

        btnaddList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchViewList.get(1).setVisibility(View.VISIBLE);
                btnaddList.get(1).setVisibility(View.VISIBLE);
            }
        });
        btnaddList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchViewList.get(2).setVisibility(View.VISIBLE);
            }
        });

        btnDrawer = (Button) getView().findViewById(R.id.drawer_multi_handle);
        slidingDrawer = (SlidingDrawer) getView().findViewById(R.id.route_multi_drawer);

        //完全打开
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                btnDrawer.setBackgroundResource(R.drawable.drawer_down);
                btnLocation.setVisibility(View.GONE);
            }
        });

        //完全关闭
        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                btnDrawer.setBackgroundResource(R.drawable.drawer_up);
                btnLocation.setVisibility(View.VISIBLE);
            }
        });

        slidingDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {
            //开始滚动时的操作
            @Override
            public void onScrollStarted() {
                btnDrawer.setBackgroundResource(R.drawable.drawer_loading);
                btnLocation.setVisibility(View.GONE);
            }

            //结束滚动时的操作
            @Override
            public void onScrollEnded() {
                if (slidingDrawer.isOpened()) {
                    btnDrawer.setBackgroundResource(R.drawable.drawer_down);
                } else {
                    btnDrawer.setBackgroundResource(R.drawable.drawer_up);
                }
            }

        });
    }

    /**
     * 获取db数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<>(size);
        dbData.add(new RouteSearchBean(R.drawable.title_icon, "北京南站地铁站",
                "周围简介\n热门吃、喝、玩、乐", 99 + ""));
        dbData.add(new RouteSearchBean(R.drawable.title_icon, "北京邮电大学西门",
                "周围简介\n热门吃、喝、玩、乐", 99 + ""));
        dbData.add(new RouteSearchBean(R.drawable.title_icon, "北京大学未名湖",
                "周围简介\n热门吃、喝、玩、乐", 99 + ""));
        for (int i = 0; i < size; i++) {
            dbData.add(new RouteSearchBean(R.drawable.title_icon, "站点" + (i + 1),
                    "周围简介\n热门吃、喝、玩、乐", i * 20 + 2 + ""));
        }
    }

    /**
     * 获取热搜版data和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
//        hintData.add("热门搜索站点");
        hintData.add("北京南站地铁站");
        hintData.add("北京邮电大学西门");
        hintData.add("北京大学未名湖");
        for (int i = 1; i <= hintSize; i++) {
            hintData.add("站点" + i * 10);
        }

        hintAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text获取autodata
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }

        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }

        if (autoCompleteAdapter0 == null) {
            autoCompleteAdapter0 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter0.notifyDataSetChanged();
        }

        if (autoCompleteAdapter1 == null) {
            autoCompleteAdapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter1.notifyDataSetChanged();
        }

        if (autoCompleteAdapter2 == null) {
            autoCompleteAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter2.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().equals(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }

        if (resultAdapter == null) {
            resultAdapter = new RouteSearchAdapter(getActivity(), resultData, R.layout.route_search_item_list);
        }
    }

    private void cvSearchBtn() {
        if ((lvResults.getVisibility() == View.VISIBLE) && ((!searchViewList.get(0).getText().equals("") |
                (!searchViewList.get(1).getText().equals("")) | (!searchViewList.get(2).getText().equals(""))))) {
            btnSearch.setVisibility(View.VISIBLE);
        } else {
            btnSearch.setVisibility(View.GONE);
        }
    }

    /**
     * 当搜索框文本改变时触发的回调 ,更新自动补全数据
     *
     * @param text 搜索栏文本
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
        cvSearchBtn();
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text 搜索栏文本
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);

        //第一次获取结果 还未配置适配器
        if (endSearchView.isFocus()) {
            if (lvResults.getAdapter() == null) {
                //获取搜索数据 设置适配器
                resultAdapter.getItem(0).setComments("起点");
                lvResults.setAdapter(resultAdapter);
            } else {
                //更新搜索数据
                resultAdapter.getItem(0).setComments("起点");
                resultAdapter.notifyDataSetChanged();
            }
        }
        cvSearchBtn();

        Toast.makeText(getActivity(), "完成搜索", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点击返回键触发回调
     */
    @Override
    public void onBack() {
        if (endSearchView.hasFocus()) {
            autoCompleteAdapter.notifyDataSetChanged();
            resultAdapter.notifyDataSetChanged();
            lvResults.setVisibility(View.GONE);
        }
        if (searchViewList.get(0).hasFocus()) {
            autoCompleteAdapter0.notifyDataSetChanged();
        }
        if (searchViewList.get(1).hasFocus()) {
            autoCompleteAdapter1.notifyDataSetChanged();
        }
        if (searchViewList.get(2).hasFocus()) {
            autoCompleteAdapter2.notifyDataSetChanged();
        }
        hintAdapter.notifyDataSetChanged();
        cvSearchBtn();
    }

    /**
     * 自动补全提示框出现触发回调
     */
    @Override
    public void isFocus() {
        if (endSearchView.hasFocus()) {
            autoCompleteAdapter.notifyDataSetChanged();
            resultAdapter.notifyDataSetChanged();
            lvResults.setVisibility(View.GONE);
        }
        if (searchViewList.get(0).hasFocus()) {
            autoCompleteAdapter0.notifyDataSetChanged();
        }
        if (searchViewList.get(1).hasFocus()) {
            autoCompleteAdapter1.notifyDataSetChanged();
        }
        if (searchViewList.get(2).hasFocus()) {
            autoCompleteAdapter2.notifyDataSetChanged();
        }
        hintAdapter.notifyDataSetChanged();
        cvSearchBtn();
    }

    /**
     * 热门提示数据在数据库中是否存在
     *
     * @param text 热门提示
     * @return true存在，false不存在
     */
    @Override
    public boolean onHintClick(String text) {
        boolean JUD = false;
        for (int i = 0; i < dbData.size(); i++) {
            if (dbData.get(i).getTitle().equals(text.trim())) {
                JUD = true;
            }
        }
        hintAdapter.notifyDataSetChanged();
        cvSearchBtn();
        return JUD;
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
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * 定位SDK监听函数
     */
    private class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            Toast.makeText(getActivity(), location.getLocationDescribe(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onReceiveLocation: " + location.getLocType());
        }
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        mMapView.setVisibility(View.GONE);
        super.onPause();
    }


    @Override
    public void onResume() {
        mMapView.onResume();
        mMapView.setVisibility(View.VISIBLE);
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
    //核心方法，避免因Fragment跳转导致地图崩溃
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // if this view is visible to user, start to request user location
            startRequestLocation();
        } else {
            // if this view is not visible to user, stop to request user location
            stopRequestLocation();
        }
    }

    private void stopRequestLocation() {
        if (mLocClient != null) {
            mLocClient.unRegisterLocationListener(myListener);
            mLocClient.stop();
        }
    }

    long startTime;
    long costTime;

    private void startRequestLocation() {
        // this nullpoint check is necessary
        if (mLocClient != null) {
            mLocClient.registerLocationListener(myListener);
            mLocClient.start();
            mLocClient.requestLocation();
            startTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);

    }
}
