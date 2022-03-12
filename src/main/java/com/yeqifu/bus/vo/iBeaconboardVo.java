package com.yeqifu.bus.vo;


import com.yeqifu.bus.entity.iBeaconManage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = false)
public class iBeaconboardVo {
    private com.yeqifu.bus.entity.iBeaconManage iBeaconManage;
    private HashMap<String, ArrayList<String>> iBeaconrssiBord;
}
