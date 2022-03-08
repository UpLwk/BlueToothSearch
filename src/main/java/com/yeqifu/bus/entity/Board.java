package com.yeqifu.bus.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_goods")
@ToString
public class Board implements Serializable {
    //iBeacon设备
    int onlineiBeaconNum;
    int offlineiBeaconNum;
    //网关设备
    int onlinegateway;
    int offlinegateway;
    //网关监控
    ArrayList<GateWayBoard> gateWayBoards;

    // 在线用户
    int SignInUser;
    int offlineUser;

    //货物数量
    int partition;
    int goodsnum;
    // 货物出入库情况
    ArrayList<GoodsBoard> goodslist;

}
