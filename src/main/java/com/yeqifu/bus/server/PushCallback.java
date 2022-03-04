package com.yeqifu.bus.server;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。
 * 必须在回调类中实现三个方法：
 *  public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
 *  public void connectionLost(Throwable cause)在断开连接时调用。
 *  public void deliveryComplete(MqttDeliveryToken token))
 *  接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
 *  由 MqttClient.connect 激活此回调。
 */
@Component
public class PushCallback implements MqttCallback {


   // 1. 声明静态初始化
   private static PushCallback pushCallback;

   // 2.通过@postConstruct
    @PostConstruct
    public void init(){
        pushCallback = this;

    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("接收消息主题:" + topic + "  接收消息Qos:" + message.getQos() + "接收消息内容:" + new String(message.getPayload()));

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}
