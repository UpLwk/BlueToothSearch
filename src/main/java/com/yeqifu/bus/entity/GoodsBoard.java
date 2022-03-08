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
@TableName("bus_goods_statis")
@ToString
public class GoodsBoard implements Serializable {
    private static final long serialVersionUID=1L;

    //分区
    private int partitionone;
    //分区
    private int partitiontow;
    //分区
    private int partitionthree;
    //分区
    private int partitionfour;
    //货物总数
    private int waresum;
    // 日期
    private String date;

}
