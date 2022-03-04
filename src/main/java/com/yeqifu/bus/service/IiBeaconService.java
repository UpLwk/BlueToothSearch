package com.yeqifu.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeqifu.bus.entity.iBeaconInfo;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import java.util.List;

public interface IiBeaconService extends IService<iBeaconInfo>, MqttCallback {
    List<iBeaconInfo> queryBlueRssiTop_10();
}
