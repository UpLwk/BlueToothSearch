package com.yeqifu.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeqifu.bus.entity.iBeaconInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface iBeaconMapper extends BaseMapper<iBeaconInfo> {
    List<iBeaconInfo> getRssiListTop_10();

    public int saveOrUpdateBatchBlueRssi(@Param("list") List<iBeaconInfo> list);
}
