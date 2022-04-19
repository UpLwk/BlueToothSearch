package com.yeqifu.bus.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeqifu.bus.entity.GateWayBoard;
import com.yeqifu.bus.entity.GoodsBoard;
import com.yeqifu.bus.entity.iBeaconInfo;
import com.yeqifu.bus.entity.iBeaconManage;
import com.yeqifu.bus.mapper.iBeaconMapper;
import com.yeqifu.bus.service.IiBeaconService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class iBeaconServiceImpl extends ServiceImpl<iBeaconMapper, iBeaconInfo> implements IiBeaconService {

    static ArrayList<String> rssiSeq1 = new ArrayList<>();  //蓝牙网关的序号 1 2 3
    static ArrayList<String> rssiSeq2 = new ArrayList<>();
    static ArrayList<String> rssiSeq3 = new ArrayList<>();
    static ArrayList<String> rssiSeq4 = new ArrayList<>();
    int count = 0;
    @Autowired
    private iBeaconMapper iBeaconMapper;


    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (!new String(message.getPayload()).contains("alive")){
            Singlelocation(new String(message.getPayload()));
        }
        System.out.println("接收消息主题:" + topic + "  接收消息Qos:" + message.getQos() + "接收消息内容:" + new String(message.getPayload()));
        Thread.sleep(100);
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
        //iBeaconMapper.saveOrUpdateBatchBlueRssi(list);
    }
    public void Singlelocation(String message) {
        JSONArray parse = (JSONArray) JSON.parse(message);
        String gateWay = "";
        String gateWayMac = "";
        ArrayList<iBeaconInfo> list = new ArrayList<>();
        if (count < 30){
            for (int i = 0; i < parse.size(); i++) {
                if (i == 0) {
                    JSONObject json = (JSONObject) parse.get(i);
                    String type = json.getString("type");
                    if (type.equals("Gateway")) {
                        gateWay = type;
                        gateWayMac = json.getString("mac");
                    }
                } else {
                    iBeaconInfo bluRssiEntity = new iBeaconInfo();
                    JSONObject rssiEntity = (JSONObject) parse.get(i);
                    String timestamp = rssiEntity.getString("timestamp");
                    String mac = rssiEntity.getString("mac");
                    String ibeaconUuid = rssiEntity.getString("ibeaconUuid");
                    String ibeaconMajor = rssiEntity.getString("ibeaconMajor");
                    String ibeaconMinor = rssiEntity.getString("ibeaconMinor");
                    String rssi = rssiEntity.getString("rssi");
                    if (mac.equals("CB9D6740F4EB")) {
                        if (gateWayMac.equals("ac233fc0883f".toUpperCase())) {
                            rssiSeq1.add(rssi);
                        }
                        if (gateWayMac.equals("ac233fc0801e".toUpperCase())) {
                            rssiSeq2.add(rssi);
                        }
                        if (gateWayMac.equals("ac233fc08886".toUpperCase())) {
                            rssiSeq3.add(rssi);
                        }
                        if (gateWayMac.equals("ac233fc07f4e".toUpperCase())) {
                            rssiSeq4.add(rssi);
                        }
                    }
                }
                //iBeaconMapper.saveOrUpdateBatchBlueRssi(list);
            }
            count++;
        }else {
            //定位算法
            double x3 = 0, y3 = 0;
            double x4 = 0, y4 =5;
            double x2 = 3.5, y2 = 0;
            double x1 = 3.5, y1 = 4.2;
            //double ave1 = Math.pow( 10,(59-average(rssiSeq1))/20);
            double ave1 =1/ Math.pow( 10,(-25+average(rssiSeq1))/30);
            double ave2=1/ Math.pow( 10,(-25+average(rssiSeq2))/30);
            double ave3 =1/Math.pow( 10,(-25+average(rssiSeq3))/30);
            double ave4 =1/Math.pow( 10,(-25+average(rssiSeq4))/30);
            //System.out.println("ave1: "+ 1/ave1 +"  , ave2:" + 1/ave2 +" , ave3 : "+1/ave3 + ", ave4:" + 1/ave4 );
            double sum = ave1+ ave2+ ave3 +ave4;
            double realx = (ave1/sum)*x1 + (ave2/sum)*x2 + (ave3/sum)*x3 +(ave4/sum)*x4;
            double realy = (ave1/sum)*y1 + (ave2/sum)*y2 + (ave3/sum)*y3 +(ave4/sum)*y4;
            //System.out.println("货物位置坐标为： "+realx+"," + realy);
            count = 0;
            rssiSeq1.clear();
            rssiSeq2.clear();
            rssiSeq3.clear();
            rssiSeq4.clear();
        }



    }
    @Override
    public List<iBeaconInfo> queryBlueRssiTop_10() {
        return iBeaconMapper.getRssiListTop_10();
    }
    public double average(ArrayList<String> list) {
        double res = 0;
        for (int i = 0; i < list.size(); i++) {
            res += Integer.valueOf(list.get(i));
        }
        //System.out.println(list + "   平均值" + res/list.size());
        return Math.abs(res/list.size());
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

    @Override
    public ArrayList<iBeaconManage> findAll() {
        return iBeaconMapper.findAll();
    }

    @Override
    public ArrayList<String> queryIBeRSSI(String mac) {

        return iBeaconMapper.queryRssi();
    }
}


