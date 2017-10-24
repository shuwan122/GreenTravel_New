package com.example.zero.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.zero.adapter.RouteLineAdapter;
import com.example.zero.fragment.OverlayManager;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.BikingRouteOverlay;
import com.example.zero.util.DrivingRouteOverlay;
import com.example.zero.util.MassTransitRouteOverlay;
import com.example.zero.util.TransitRouteOverlay;
import com.example.zero.util.WalkingRouteOverlay;

import java.util.ArrayList;
import java.util.List;

public class AdvMapResultActivity extends AppCompatActivity implements BaiduMap.OnMapClickListener, OnGetRoutePlanResultListener {
    // 路线节点
    // 浏览路线节点相关
    Button mBtnPre = null; // 上一个节点
    Button mBtnNext = null; // 下一个节点
    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    RouteLine route = null;
    MassTransitRouteLine massroute = null;
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;
    private TextView popupText = null; // 泡泡view

    MapView mMapView = null;// 地图View
    BaiduMap mBaidumap = null;

    // 搜索相关
    int JUD = 0;
    String beginStation = null;
    String endStation = null;
    ArrayList<String> beginStationList = null;
    int beginNum = 0;

    // 搜索模块
    RoutePlanSearch mSearch = null;

    WalkingRouteResult nowResultwalk = null;
    BikingRouteResult nowResultbike = null;
    TransitRouteResult nowResultransit = null;
    DrivingRouteResult nowResultdrive = null;
    MassTransitRouteResult nowResultmass = null;

    int nowSearchType = -1; // 当前节点

    boolean hasShownDialogue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_map_result);
        Intent intent = getIntent();
        Bundle mBundle = intent.getExtras();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        CharSequence titleLable = "路线";
        setTitle(titleLable);
        // 初始化地图
        mMapView = (MapView) findViewById(R.id.route_result_map2);
        mBaidumap = mMapView.getMap();
        mBaidumap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        mBaidumap.setTrafficEnabled(true);

        mBtnPre = (Button) findViewById(R.id.route_result_pre2);
        mBtnNext = (Button) findViewById(R.id.route_result_next2);
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);

        // 地图点击事件处理
        mBaidumap.setOnMapClickListener(this);
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

        beginStation = mBundle.getString("beginStation");
        endStation = mBundle.getString("endStation");
    }

    /**
     * 发起路线规划搜索示例
     *
     * @param v
     */
    public void searchButtonProcess(View v) {
        // 重置浏览节点的路线数据
        route = null;
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);
        mBaidumap.clear();

        // 处理搜索按钮响应
        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", beginStation);
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", endStation);
        if (v.getId() == R.id.mass2) {
            PlanNode stMassNode = PlanNode.withCityNameAndPlaceName("北京", "天安门");
            PlanNode enMassNode = PlanNode.withCityNameAndPlaceName("上海", "东方明珠");

            mSearch.masstransitSearch(new MassTransitRoutePlanOption().from(stMassNode).to(enMassNode));
            nowSearchType = 0;
        } else if (v.getId() == R.id.drive2) {
            mSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode).to(enNode));
            nowSearchType = 1;
        } else if (v.getId() == R.id.transit2) {
            mSearch.transitSearch((new TransitRoutePlanOption())
                    .from(stNode).city("北京").to(enNode));
            nowSearchType = 2;
        } else if (v.getId() == R.id.walk2) {
            mSearch.walkingSearch((new WalkingRoutePlanOption())
                    .from(stNode).to(enNode));
            nowSearchType = 3;
        } else if (v.getId() == R.id.bike2) {
            mSearch.bikingSearch((new BikingRoutePlanOption())
                    .from(stNode).to(enNode));
            nowSearchType = 4;
        }

    }

    /**
     * 节点浏览示例
     *
     * @param v
     */
    public void nodeClick(View v) {
        LatLng nodeLocation = null;
        String nodeTitle = null;
        Object step = null;

        if (nowSearchType != 0 && nowSearchType != -1) {
            // 非跨城综合交通
            if (route == null || route.getAllStep() == null) {
                return;
            }
            if (nodeIndex == -1 && v.getId() == R.id.route_result_pre) {
                return;
            }
            // 设置节点索引
            if (v.getId() == R.id.route_result_next) {
                if (nodeIndex < route.getAllStep().size() - 1) {
                    nodeIndex++;
                } else {
                    return;
                }
            } else if (v.getId() == R.id.route_result_pre) {
                if (nodeIndex > 0) {
                    nodeIndex--;
                } else {
                    return;
                }
            }
            // 获取节结果信息
            step = route.getAllStep().get(nodeIndex);
            if (step instanceof DrivingRouteLine.DrivingStep) {
                nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
                nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
            } else if (step instanceof WalkingRouteLine.WalkingStep) {
                nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
                nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
            } else if (step instanceof TransitRouteLine.TransitStep) {
                nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
                nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
            } else if (step instanceof BikingRouteLine.BikingStep) {
                nodeLocation = ((BikingRouteLine.BikingStep) step).getEntrance().getLocation();
                nodeTitle = ((BikingRouteLine.BikingStep) step).getInstructions();
            }
        } else if (nowSearchType == 0) {
            // 跨城综合交通  综合跨城公交的结果判断方式不一样

            if (massroute == null || massroute.getNewSteps() == null) {
                return;
            }
            if (nodeIndex == -1 && v.getId() == R.id.route_result_pre) {
                return;
            }
            boolean isSamecity = nowResultmass.getOrigin().getCityId() == nowResultmass.getDestination().getCityId();
            int size = 0;
            if (isSamecity) {
                size = massroute.getNewSteps().size();
            } else {
                for (int i = 0; i < massroute.getNewSteps().size(); i++) {
                    size += massroute.getNewSteps().get(i).size();
                }
            }

            // 设置节点索引
            if (v.getId() == R.id.route_result_next) {
                if (nodeIndex < size - 1) {
                    nodeIndex++;
                } else {
                    return;
                }
            } else if (v.getId() == R.id.route_result_pre) {
                if (nodeIndex > 0) {
                    nodeIndex--;
                } else {
                    return;
                }
            }
            if (isSamecity) {
                // 同城
                step = massroute.getNewSteps().get(nodeIndex).get(0);
            } else {
                // 跨城
                int num = 0;
                for (int j = 0; j < massroute.getNewSteps().size(); j++) {
                    num += massroute.getNewSteps().get(j).size();
                    if (nodeIndex - num < 0) {
                        int k = massroute.getNewSteps().get(j).size() + nodeIndex - num;
                        step = massroute.getNewSteps().get(j).get(k);
                        break;
                    }
                }
            }
            nodeLocation = ((MassTransitRouteLine.TransitStep) step).getStartLocation();
            nodeTitle = ((MassTransitRouteLine.TransitStep) step).getInstructions();
        }

        if (nodeLocation == null || nodeTitle == null) {
            return;
        }

        // 移动节点至中心
        mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
        // show popup
        popupText = new TextView(AdvMapResultActivity.this);
        popupText.setBackgroundResource(R.drawable.popup);
        popupText.setTextColor(0xFF000000);
        popupText.setText(nodeTitle);
        mBaidumap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(AdvMapResultActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            result.getSuggestAddrInfo();
            AlertDialog.Builder builder = new AlertDialog.Builder(AdvMapResultActivity.this);
            builder.setTitle("提示");
            builder.setMessage("检索地址有歧义，请重新设置。");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            if (JUD == 0) {
                nodeIndex = -1;
                mBtnPre.setVisibility(View.VISIBLE);
                mBtnNext.setVisibility(View.VISIBLE);

                if (result.getRouteLines().size() > 1) {
                    nowResultwalk = result;
                    if (!hasShownDialogue) {
                        AdvMapResultActivity.MyTransitDlg myTransitDlg = new AdvMapResultActivity.MyTransitDlg(AdvMapResultActivity.this,
                                result.getRouteLines(),
                                RouteLineAdapter.Type.WALKING_ROUTE);
                        myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                hasShownDialogue = false;
                            }
                        });
                        myTransitDlg.setOnItemInDlgClickLinster(new AdvMapResultActivity.OnItemInDlgClickListener() {
                            public void onItemClick(int position) {
                                route = nowResultwalk.getRouteLines().get(position);
                                WalkingRouteOverlay overlay = new AdvMapResultActivity.MyWalkingRouteOverlay(mBaidumap);
                                mBaidumap.setOnMarkerClickListener(overlay);
                                routeOverlay = overlay;
                                overlay.setData(nowResultwalk.getRouteLines().get(position));
                                overlay.addToMap();
                                overlay.zoomToSpan();
                            }
                        });
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                } else if (result.getRouteLines().size() == 1) {
                    // 直接显示
                    route = result.getRouteLines().get(0);
                    WalkingRouteOverlay overlay = new AdvMapResultActivity.MyWalkingRouteOverlay(mBaidumap);
                    mBaidumap.setOnMarkerClickListener(overlay);
                    routeOverlay = overlay;
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }
            } else {
                route = result.getRouteLines().get(0);
                WalkingRouteOverlay overlay = new AdvMapResultActivity.MyWalkingRouteOverlay(mBaidumap);
                mBaidumap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(AdvMapResultActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            if (JUD == 0) {
                nodeIndex = -1;
                mBtnPre.setVisibility(View.VISIBLE);
                mBtnNext.setVisibility(View.VISIBLE);

                if (result.getRouteLines().size() > 1) {
                    nowResultransit = result;
                    if (!hasShownDialogue) {
                        AdvMapResultActivity.MyTransitDlg myTransitDlg = new AdvMapResultActivity.MyTransitDlg(AdvMapResultActivity.this,
                                result.getRouteLines(),
                                RouteLineAdapter.Type.TRANSIT_ROUTE);
                        myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                hasShownDialogue = false;
                            }
                        });
                        myTransitDlg.setOnItemInDlgClickLinster(new AdvMapResultActivity.OnItemInDlgClickListener() {
                            public void onItemClick(int position) {

                                route = nowResultransit.getRouteLines().get(position);
                                TransitRouteOverlay overlay = new AdvMapResultActivity.MyTransitRouteOverlay(mBaidumap);
                                mBaidumap.setOnMarkerClickListener(overlay);
                                routeOverlay = overlay;
                                overlay.setData(nowResultransit.getRouteLines().get(position));
                                overlay.addToMap();
                                overlay.zoomToSpan();
                            }

                        });
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                } else if (result.getRouteLines().size() == 1) {
                    // 直接显示
                    route = result.getRouteLines().get(0);
                    TransitRouteOverlay overlay = new AdvMapResultActivity.MyTransitRouteOverlay(mBaidumap);
                    mBaidumap.setOnMarkerClickListener(overlay);
                    routeOverlay = overlay;
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();

                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }
            } else {
                route = result.getRouteLines().get(0);
                TransitRouteOverlay overlay = new AdvMapResultActivity.MyTransitRouteOverlay(mBaidumap);
                mBaidumap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }
    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(AdvMapResultActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点模糊，获取建议列表
            result.getSuggestAddrInfo();
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nowResultmass = result;

            nodeIndex = -1;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);

            if (!hasShownDialogue) {
                // 列表选择
                AdvMapResultActivity.MyTransitDlg myTransitDlg = new AdvMapResultActivity.MyTransitDlg(AdvMapResultActivity.this,
                        result.getRouteLines(),
                        RouteLineAdapter.Type.MASS_TRANSIT_ROUTE);
                nowResultmass = result;
                myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        hasShownDialogue = false;
                    }
                });
                myTransitDlg.setOnItemInDlgClickLinster(new AdvMapResultActivity.OnItemInDlgClickListener() {
                    public void onItemClick(int position) {

                        AdvMapResultActivity.MyMassTransitRouteOverlay overlay = new AdvMapResultActivity.MyMassTransitRouteOverlay(mBaidumap);
                        mBaidumap.setOnMarkerClickListener(overlay);
                        routeOverlay = overlay;
                        massroute = nowResultmass.getRouteLines().get(position);
                        overlay.setData(nowResultmass.getRouteLines().get(position));

                        MassTransitRouteLine line = nowResultmass.getRouteLines().get(position);
                        overlay.setData(line);
                        if (nowResultmass.getOrigin().getCityId() == nowResultmass.getDestination().getCityId()) {
                            // 同城
                            overlay.setSameCity(true);
                        } else {
                            // 跨城
                            overlay.setSameCity(false);
                        }
                        mBaidumap.clear();
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }

                });
                myTransitDlg.show();
                hasShownDialogue = true;
            }
        }
    }


    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(AdvMapResultActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            if (JUD == 0) {
                if (result.getRouteLines().size() > 1) {
                    nowResultdrive = result;
                    if (!hasShownDialogue) {
                        AdvMapResultActivity.MyTransitDlg myTransitDlg = new AdvMapResultActivity.MyTransitDlg(AdvMapResultActivity.this,
                                result.getRouteLines(),
                                RouteLineAdapter.Type.DRIVING_ROUTE);
                        myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                hasShownDialogue = false;
                            }
                        });
                        myTransitDlg.setOnItemInDlgClickLinster(new AdvMapResultActivity.OnItemInDlgClickListener() {
                            public void onItemClick(int position) {
                                route = nowResultdrive.getRouteLines().get(position);
                                DrivingRouteOverlay overlay = new AdvMapResultActivity.MyDrivingRouteOverlay(mBaidumap);
                                mBaidumap.setOnMarkerClickListener(overlay);
                                routeOverlay = overlay;
                                overlay.setData(nowResultdrive.getRouteLines().get(position));
                                overlay.addToMap();
                                overlay.zoomToSpan();
                            }

                        });
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                } else if (result.getRouteLines().size() == 1) {
                    route = result.getRouteLines().get(0);
                    DrivingRouteOverlay overlay = new AdvMapResultActivity.MyDrivingRouteOverlay(mBaidumap);
                    routeOverlay = overlay;
                    mBaidumap.setOnMarkerClickListener(overlay);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }
            } else {
                route = result.getRouteLines().get(0);
                DrivingRouteOverlay overlay = new AdvMapResultActivity.MyDrivingRouteOverlay(mBaidumap);
                routeOverlay = overlay;
                mBaidumap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(AdvMapResultActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            AlertDialog.Builder builder = new AlertDialog.Builder(AdvMapResultActivity.this);
            builder.setTitle("提示");
            builder.setMessage("检索地址有歧义，请重新设置。");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            if (JUD == 0) {
                if (result.getRouteLines().size() > 1) {
                    nowResultbike = result;
                    if (!hasShownDialogue) {
                        AdvMapResultActivity.MyTransitDlg myTransitDlg = new AdvMapResultActivity.MyTransitDlg(AdvMapResultActivity.this,
                                result.getRouteLines(),
                                RouteLineAdapter.Type.DRIVING_ROUTE);
                        myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                hasShownDialogue = false;
                            }
                        });
                        myTransitDlg.setOnItemInDlgClickLinster(new AdvMapResultActivity.OnItemInDlgClickListener() {
                            public void onItemClick(int position) {
                                route = nowResultbike.getRouteLines().get(position);
                                BikingRouteOverlay overlay = new AdvMapResultActivity.MyBikingRouteOverlay(mBaidumap);
                                mBaidumap.setOnMarkerClickListener(overlay);
                                routeOverlay = overlay;
                                overlay.setData(nowResultbike.getRouteLines().get(position));
                                overlay.addToMap();
                                overlay.zoomToSpan();
                            }
                        });
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                } else if (result.getRouteLines().size() == 1) {
                    route = result.getRouteLines().get(0);
                    BikingRouteOverlay overlay = new AdvMapResultActivity.MyBikingRouteOverlay(mBaidumap);
                    routeOverlay = overlay;
                    mBaidumap.setOnMarkerClickListener(overlay);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }
            } else {
                route = result.getRouteLines().get(0);
                BikingRouteOverlay overlay = new AdvMapResultActivity.MyBikingRouteOverlay(mBaidumap);
                routeOverlay = overlay;
                mBaidumap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }
    }

    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }

    private class MyBikingRouteOverlay extends BikingRouteOverlay {

        public MyBikingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }

    private class MyMassTransitRouteOverlay extends MassTransitRouteOverlay {
        public MyMassTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }


    }

    @Override
    public void onMapClick(LatLng point) {
        mBaidumap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi poi) {
        return false;
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mSearch != null) {
            mSearch.destroy();
        }
        mMapView.onDestroy();
        super.onDestroy();
    }

    // 响应DLg中的List item 点击
    interface OnItemInDlgClickListener {
        public void onItemClick(int position);
    }

    // 供路线选择的Dialog
    class MyTransitDlg extends Dialog {

        private List<? extends RouteLine> mtransitRouteLines;
        private ListView transitRouteList;
        private RouteLineAdapter mTransitAdapter;

        AdvMapResultActivity.OnItemInDlgClickListener onItemInDlgClickListener;

        public MyTransitDlg(Context context, int theme) {
            super(context, theme);
        }

        public MyTransitDlg(Context context, List<? extends RouteLine> transitRouteLines, RouteLineAdapter.Type
                type) {
            this(context, 0);
            mtransitRouteLines = transitRouteLines;
            mTransitAdapter = new RouteLineAdapter(context, mtransitRouteLines, type);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        @Override
        public void setOnDismissListener(OnDismissListener listener) {
            super.setOnDismissListener(listener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transit_dialog);

            transitRouteList = (ListView) findViewById(R.id.transitList);
            transitRouteList.setAdapter(mTransitAdapter);

            transitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemInDlgClickListener.onItemClick(position);
                    dismiss();
                    hasShownDialogue = false;
                }
            });
        }

        public void setOnItemInDlgClickLinster(AdvMapResultActivity.OnItemInDlgClickListener itemListener) {
            onItemInDlgClickListener = itemListener;
        }

    }
}
