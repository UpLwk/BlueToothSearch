package com.yeqifu.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeqifu.bus.entity.GateWayBoard;
import com.yeqifu.bus.entity.GoodsBoard;
import com.yeqifu.bus.entity.iBeaconInfo;
import com.yeqifu.bus.entity.iBeaconManage;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import java.util.ArrayList;
import java.util.List;

public interface IiBeaconService extends IService<iBeaconInfo>, MqttCallback {
    List<iBeaconInfo> queryBlueRssiTop_10();

    List<GoodsBoard> queryGoodsBoard();

    List<GateWayBoard> queryGateWay();

    ArrayList<iBeaconManage> findAll();

    ArrayList<String> queryIBeRSSI(String mac);
}
