package com.yeqifu.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeqifu.bus.entity.*;
import com.yeqifu.bus.service.ICustomerService;
import com.yeqifu.bus.service.IGoodsService;
import com.yeqifu.bus.service.ISalesbackService;
import com.yeqifu.bus.service.IiBeaconService;
import com.yeqifu.bus.vo.SalesbackVo;
import com.yeqifu.bus.vo.iBeaconboardVo;
import com.yeqifu.sys.common.DataGridView;
import com.yeqifu.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author luoyi-
 * @since 2019-12-23
 */
@RestController
@RequestMapping("/salesback")
public class SalesbackController {

    @Autowired
    private ISalesbackService salesbackService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IiBeaconService iBeaconService;
    /**
     * 添加退货信息
     * @param id    进货单ID
     * @param number    退货数量
     * @param remark    备注
     * @return
     */
    @RequestMapping("addSalesback")
    public ResultObj addSalesback(Integer id,Integer number,String remark){
        try {
            salesbackService.addSalesback(id,number,remark);
            return ResultObj.BACKINPORT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.BACKINPORT_ERROR;
        }
    }

    /**
     * 查询商品销售退货
     * @param salesbackVo
     * @return
     */
    @RequestMapping("loadAlliBeacon")
    public DataGridView loadAllSalesback(SalesbackVo salesbackVo){
        IPage<iBeaconboardVo> page = new Page<iBeaconboardVo>(salesbackVo.getPage(),salesbackVo.getLimit());
        QueryWrapper<iBeaconManage> queryWrapper = new QueryWrapper<iBeaconManage>();
        //对客户进行查询
        queryWrapper.eq(salesbackVo.getCustomerid()!=null&&salesbackVo.getCustomerid()!=0,"customerid",salesbackVo.getCustomerid());
        //对商品进行查询
        queryWrapper.eq(salesbackVo.getGoodsid()!=null&&salesbackVo.getGoodsid()!=0,"goodsid",salesbackVo.getGoodsid());
        //对时间进行查询要求大于开始时间小于结束时间
        queryWrapper.ge(salesbackVo.getStartTime()!=null,"salesbacktime",salesbackVo.getStartTime());
        queryWrapper.le(salesbackVo.getEndTime()!=null,"salesbacktime",salesbackVo.getEndTime());
        //通过商品退货时间对商品进行排序
        //queryWrapper.orderByDesc("salesbacktime");
        ArrayList<iBeaconManage>  list=iBeaconService.findAll();
        ArrayList<iBeaconboardVo> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            iBeaconManage iBeaconManage = list.get(i);
            HashMap<String, ArrayList<String>> map = new HashMap<>();

            int count = 0;
            iBeaconboardVo iBeaconboardVo = new iBeaconboardVo();
            iBeaconboardVo.setIBeaconManage(iBeaconManage);
            for (int k=0; k < 6; k++){
                ArrayList<String> rssiList = new ArrayList<>();
                for (int j=0; j <15; j++ ){
                    Random random = new Random();
                    int num = (30+random.nextInt(10)*8);
                    if (count >3 && num >70){
                        rssiList.add(String.valueOf(-56+random.nextInt(10)));
                    }else{
                        if (num >70){
                            count++;
                        } else {
                            rssiList.add(String.valueOf(-num));
                        }
                    }


                }
                count = 0;
                map.put("minew0"+(k+1), rssiList);
                iBeaconboardVo.setIBeaconrssiBord(map);
            }

            res.add(iBeaconboardVo);
        }
        page.setRecords(res);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 删除商品销售退回信息
     * @param id
     * @return
     */
    @RequestMapping("deleteSalesback")
    public ResultObj deleteSalesback(Integer id){
        try {
            salesbackService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    @RequestMapping("loadAlliBeacontest")
    public DataGridView loadAlliBeacon(SalesbackVo salesbackVo){
        IPage<iBeaconInfo> page = new Page<iBeaconInfo>(salesbackVo.getPage(),salesbackVo.getLimit());
        QueryWrapper<iBeaconInfo> queryWrapper = new QueryWrapper<iBeaconInfo>();
         //对商品进行查询
        queryWrapper.eq(salesbackVo.getGoodsid()!=null&&salesbackVo.getGoodsid()!=0,"goodsid",salesbackVo.getGoodsid());
        //对时间进行查询要求大于开始时间小于结束时间
        queryWrapper.ge(salesbackVo.getStartTime()!=null,"salesbacktime",salesbackVo.getStartTime());
        queryWrapper.le(salesbackVo.getEndTime()!=null,"salesbacktime",salesbackVo.getEndTime());
        //通过商品退货时间对商品进行排序
        queryWrapper.orderByDesc("salesbacktime");
        /**
        salesbackService.page(page, queryWrapper);
        List<iBeaconInfo> records = page.getRecords();
        for (Salesback salesback : records) {
            System.out.println("============================");
            Customer customer = customerService.getById(salesback.getCustomerid());
            if (customer!=null){
                //设置客户姓名
                salesback.setCustomername(customer.getCustomername());
            }
            Goods goods = goodsService.getById(salesback.getGoodsid());
            if (goods!=null){
                //设置商品名称
                salesback.setGoodsname(goods.getGoodsname());
                //设置商品规格
                salesback.setSize(goods.getSize());
            }
        }
         **/
        return new DataGridView(page.getTotal(),page.getRecords());
    }


}

