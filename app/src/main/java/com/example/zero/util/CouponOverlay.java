package com.example.zero.util;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.example.zero.entity.Coupon;
import com.example.zero.fragment.OverlayManager;
import com.example.zero.greentravel_new.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近优惠券Overlay类
 */
public class CouponOverlay extends OverlayManager {

    private ArrayList<Coupon> CouponList = null;

    /**
     * 构造函数
     *
     * @param baiduMap 该NearbyOverlay 引用的 BaiduMap 对象
     */
    public CouponOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {

        if (CouponList == null) {
            return null;
        }

        List<OverlayOptions> overlayOptionses = new ArrayList<OverlayOptions>();
        // step node
        if (!CouponList.isEmpty()) {
            for (Coupon coupon : CouponList) {
                Bundle b = new Bundle();
                b.putString("name", coupon.getName());
                overlayOptionses.add((new MarkerOptions())
                        .position(new LatLng(coupon.getLat(), coupon.getLon()))
                        .anchor(0.5f, 0.5f).zIndex(10).extraInfo(b)
                        .icon(getIconForCoupon()));
            }
        }
        return overlayOptionses;
    }

    private BitmapDescriptor getIconForCoupon() {
        return BitmapDescriptorFactory.fromResource(R.drawable.sale_pop);
    }

    /**
     * 设置路线数据
     *
     * @param CouponList 路线数据
     */
    public void setData(ArrayList<Coupon> CouponList) {
        this.CouponList = CouponList;
    }

    /**
     * 覆写此方法以改变起默认点击行为
     *
     * @param i 被点击的step在
     *          {@link com.baidu.mapapi.search.route.TransitRouteLine#getAllStep()}
     *          中的索引
     * @return 是否处理了该点击事件
     */
    public boolean onRouteNodeClick(int i) {
        return false;
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
        for (Overlay mMarker : mOverlayList) {
            if (mMarker instanceof Marker && mMarker.equals(marker)) {
                if (marker.getExtraInfo() != null) {
                    onRouteNodeClick(marker.getExtraInfo().getInt("index"));
                }
            }
        }
        return true;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        // TODO Auto-generated method stub
        return false;
    }
}