package com.yeqifu.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeqifu.bus.entity.GateWayBoard;
import com.yeqifu.bus.entity.GoodsBoard;
import com.yeqifu.bus.entity.iBeaconInfo;
import com.yeqifu.bus.entity.iBeaconManage;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface iBeaconMapper extends BaseMapper<iBeaconInfo> {
    List<iBeaconInfo> getRssiListTop_10();

    public int saveOrUpdateBatchBlueRssi(@Param("list") List<iBeaconInfo> list);

    List<GoodsBoard> queryGoodsBoard();

    List<GateWayBoard> queryGateWayBoard();

    ArrayList<iBeaconManage> findAll();

    ArrayList<String> queryRssi();
}
