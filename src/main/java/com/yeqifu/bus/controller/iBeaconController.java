package com.yeqifu.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeqifu.bus.entity.Goods;
import com.yeqifu.bus.entity.Provider;
import com.yeqifu.bus.entity.iBeaconInfo;
import com.yeqifu.bus.server.ClientMQTT;
import com.yeqifu.bus.service.IGoodsService;
import com.yeqifu.bus.service.IProviderService;
import com.yeqifu.bus.service.IiBeaconService;
import com.yeqifu.bus.vo.GoodsVo;
import com.yeqifu.sys.common.DataGridView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/ibeacon")
public class iBeaconController {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IiBeaconService iBeaconService;
    /**
     * 获取最新搜寻的rssi信号
     * @param
     * @return
     */
    @RequestMapping("loadRandomGoods")
    public DataGridView loadRandomGoods(iBeaconInfo ibeacon){
        IPage<iBeaconInfo> page = new Page<iBeaconInfo>(1,10);
        QueryWrapper<iBeaconInfo> queryWrapper = new QueryWrapper<iBeaconInfo>();


        List<iBeaconInfo> iBeaconInfos = iBeaconService.queryBlueRssiTop_10();
        page.setRecords(iBeaconInfos);
        page.setSize(iBeaconInfos.size());
        List<iBeaconInfo> records = page.getRecords();
        Random a = new Random();

        for (iBeaconInfo cur : records) {
            int i = a.nextInt(10) + 80;
            cur.setDangernum(""+i);
        }

        return new DataGridView(page.getTotal(),page.getRecords());
    }


    @RequestMapping("startScan")
    public DataGridView scan(GoodsVo goodsVo){
        IPage<Goods> page = new Page<Goods>(goodsVo.getPage(),goodsVo.getLimit());

        ClientMQTT clientMQTT = new ClientMQTT();
        clientMQTT.searchBlueToothData();
        return new DataGridView(page.getTotal(),page.getRecords());
    }
}
