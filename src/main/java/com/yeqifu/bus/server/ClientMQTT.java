package com.yeqifu.bus.server;

import com.yeqifu.bus.config.ApplicationContextProvider;
import com.yeqifu.bus.entity.iBeaconInfo;
import com.yeqifu.bus.service.IiBeaconService;
import com.yeqifu.bus.service.impl.iBeaconServiceImpl;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * 原因:Spring初始化顺序：
 * 类变量（static变量）
 * static初始化块
 * 类的成员变量
 * 普通初始化块
 * 构造函数
 * 被@Autowired注解的类
 * 被@PostConstruct注解的方法
 *
 * 如果一个成员变量里需要使用
 */

@Component
public class ClientMQTT {
    @Autowired
    public static IiBeaconService beaconService;

    private static ClientMQTT clientMQTT;

    @PostConstruct
    public void init(){
        clientMQTT = this;
        clientMQTT.beaconService = beaconService;
    }
    //MQTT安装的服务器地址和端口号（本机的ip）
    public static final String HOST = "tcp://192.168.0.105:1883";
    //定义一个主题
    public static final String TOPIC = "/gw/ac233fc07f66/status";
    //定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String clientid = "client-2";
    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "liwk";
    private String passWord = "95240200";


    public ArrayList<iBeaconInfo> searchBlueToothData(){
        ArrayList<iBeaconInfo> list = new ArrayList<>();
        ClientMQTT client = new ClientMQTT();
        client.start();
        return list;
    }

//    private ScheduledExecutorService scheduler;

    public void start() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            // 设置回调,client.setCallback就可以调用PushCallback类中的messageArrived()方法


            iBeaconServiceImpl blueRssiService = ApplicationContextProvider.getBean(iBeaconServiceImpl.class);

            client.setCallback(blueRssiService);
            MqttTopic topic = client.getTopic(TOPIC);
            int qos = 1;

            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(topic, "alive...".getBytes(), qos, true);
            client.connect(options);
            //订阅消息
            int[] Qos  = {qos};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1, Qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
