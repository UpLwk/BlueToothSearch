package com.yeqifu.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeqifu.bus.entity.*;
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

import java.util.ArrayList;
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

    @RequestMapping("findGoodsNum")
    public DataGridView findGoods(iBeaconInfo ibeacon){
        IPage<Board> page = new Page<Board>(1,10);
        QueryWrapper<iBeaconInfo> queryWrapper = new QueryWrapper<iBeaconInfo>();
        Board board = new Board();
        //添加货物
        ArrayList<GoodsBoard> goodsBoards = (ArrayList<GoodsBoard>) iBeaconService.queryGoodsBoard();
        board.setGoodslist(goodsBoards);


        //添加网关移盘表信息
        List<GateWayBoard> gateWayBoards = iBeaconService.queryGateWay();
        board.setGateWayBoards((ArrayList<GateWayBoard>) gateWayBoards);

        board.setOnlineiBeaconNum(10);
        board.setOfflineiBeaconNum(0);

        board.setSignInUser(7);
        board.setOfflineUser(2);

        board.setOnlinegateway(6);
        board.setOfflinegateway(0);
        ArrayList list = new ArrayList();
        list.add(board);
        page.setRecords(list);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
}
