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
@TableName("gateway_board")
@ToString
public class GateWayBoard  implements Serializable {
    private static final long serialVersionUID=1L;

    private int gateWayG1;

    private int gateWayG2;

    private int gateWayG3;

    private int gateWayG4;

    private int gateWayG5;

    private int gateWayG6;

    // 日期
    private String date;
}
