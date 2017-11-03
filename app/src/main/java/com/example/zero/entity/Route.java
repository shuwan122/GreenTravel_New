package com.example.zero.entity;

import java.util.ArrayList;

/**
 * Created by ZERO on 2017/10/28.
 */

public class Route {
    /**
     *  中间站点
     */
    ArrayList<String> stationList;

    /**
     * 线路
     */
    ArrayList<String> routeLineList;

    /**
     * 描述
     */
    ArrayList<String> infoList;

    /**
     * 类型:fast,lessbusy,lesschange
     */
    RouteType type;

    public enum RouteType {
        FAST, // 最快速
        LESSBUSY, // 最少拥堵
        LESSCHANGE, // 最少换乘
        MULTI, // 多人
        ADVICE //建议
    }

    public Route(){

    }

    public Route(ArrayList<String> stationList, ArrayList<String> routeLineList, ArrayList<String> infoList, RouteType type) {
        this.stationList = stationList;
        this.routeLineList = routeLineList;
        this.infoList = infoList;
        this.type = type;
    }

    public ArrayList<String> getStationList() {
        return stationList;
    }

    public void setStationList(ArrayList<String> stationList) {
        this.stationList = stationList;
    }

    public ArrayList<String> getRouteLineList() {
        return routeLineList;
    }

    public void setRouteLineList(ArrayList<String> routeLineList) {
        this.routeLineList = routeLineList;
    }

    public ArrayList<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(ArrayList<String> infoList) {
        this.infoList = infoList;
    }

    public RouteType getType() {
        return type;
    }

    public void setType(RouteType type) {
        this.type = type;
    }
}
