package com.yeqifu.bus.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeqifu.bus.entity.GateWayBoard;
import com.yeqifu.bus.entity.GoodsBoard;
import com.yeqifu.bus.entity.iBeaconInfo;
import com.yeqifu.bus.mapper.iBeaconMapper;
import com.yeqifu.bus.service.IiBeaconService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class iBeaconServiceImpl extends ServiceImpl<iBeaconMapper, iBeaconInfo> implements IiBeaconService {

    @Autowired
    private iBeaconMapper iBeaconMapper;


    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (!new String(message.getPayload()).contains("alive")){
            processRssi(new String(message.getPayload()), topic);
        }
        System.out.println("接收消息主题:" + topic + "  接收消息Qos:" + message.getQos() + "接收消息内容:" + new String(message.getPayload()));
        Thread.sleep(1000);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }


    @Override
    public boolean save(iBeaconInfo entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<iBeaconInfo> entityList, int batchSize) {
        return super.saveBatch(entityList, batchSize);
    }

    @Override
    public boolean saveOrUpdate(iBeaconInfo entity) {
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<iBeaconInfo> entityList, int batchSize) {
        return super.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    public boolean update(iBeaconInfo entity, Wrapper<iBeaconInfo> updateWrapper) {
        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<iBeaconInfo> entityList, int batchSize) {
        return super.updateBatchById(entityList, batchSize);
    }

    @Override
    public iBeaconInfo getById(Serializable id) {
        return super.getById(id);
    }



    public void processRssi(String message, String topic){
        JSONArray parse = (JSONArray) JSON.parse(message);
        String gateWay = "";
        String gateWayMac = "";
        ArrayList<iBeaconInfo> list = new ArrayList<>();
        for (int i = 1; i < parse.size(); i++) {
            if (i == 0){
                JSONObject json =(JSONObject) parse.get(i);
                String type = json.getString("type");
                if (type == "GateWay"){
                    gateWay = type;
                    gateWayMac = json.getString(" mac");
                }
            }else{
                iBeaconInfo bluRssiEntity = new iBeaconInfo();
                JSONObject rssiEntity = (JSONObject) parse.get(i);
                String timestamp = rssiEntity.getString("timestamp");
                String mac = rssiEntity.getString("mac");
                String ibeaconUuid = rssiEntity.getString("ibeaconUuid");
                String ibeaconMajor = rssiEntity.getString("ibeaconMajor");
                String ibeaconMinor = rssiEntity.getString("ibeaconMinor");
                String rssi = rssiEntity.getString("rssi");

                bluRssiEntity.setRssi(rssi);
                bluRssiEntity.setGatewayMac("gateWayMac");
                bluRssiEntity.setIbeaconMajor(ibeaconMajor);
                bluRssiEntity.setIbeaconuuid(ibeaconUuid);
                bluRssiEntity.setIbeaconMinor(ibeaconMinor);
                bluRssiEntity.setTopic(topic);
                bluRssiEntity.setMac(mac);
                bluRssiEntity.setScandate(timestamp);
                Random a = new Random();

                bluRssiEntity.setIBeaconName("测试商品"+ a.nextInt(10));
                list.add(bluRssiEntity);
            }

        }
        iBeaconMapper.saveOrUpdateBatchBlueRssi(list);
    }

    @Override
    public List<iBeaconInfo> queryBlueRssiTop_10() {
        return iBeaconMapper.getRssiListTop_10();
    }

    /**
     *移盘表
     */

    @Override
    public List<GoodsBoard> queryGoodsBoard() {
        return iBeaconMapper.queryGoodsBoard();
    }

    @Override
    public List<GateWayBoard> queryGateWay() {
        return iBeaconMapper.queryGateWayBoard();
    }
}


