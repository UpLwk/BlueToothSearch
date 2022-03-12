package com.yeqifu.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 9216 kB; (`providerid`) REFER `warehouse/bus_provider`(`id`)
 * </p>
 *
 * @author luoyi-
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_goods")
@ToString
public class IBeaconGoods implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //货物名称
    private String goodsname;
    // iBeacon信标mac地址
    private String iBeacon_mac;
    //描述
    private String description;
    //价格
    private Double price;
    //数量
    private Integer number;
    //货余量
    private Integer dangernum;
    //图片
    private String goodsimg;

    @TableField(exist = false)
    private String providername;


}
