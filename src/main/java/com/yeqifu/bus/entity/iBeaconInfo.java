package com.yeqifu.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bluerssi_form")
public class iBeaconInfo implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private String gatewayMac;

    private String topic;

    private String scandate;

    private String mac;

    private String rssi;

    private String ibeaconuuid;

    private String ibeaconMajor;

    private String ibeaconMinor;

    private String iBeaconName;

    //电量
    private String dangernum;
}
