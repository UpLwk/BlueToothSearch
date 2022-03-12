package com.yeqifu.bus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_ibe_manage")
@ToString
public class iBeaconManage implements Serializable {
    private static final long serialVersionUID=1L;
    private int id;

    private String iBeaconName;

    private String major;

    private String minor;

    private String mac;

    private String topic;

    private String binddate;

    //电量
    private String batter;

    private int isalive;
}
