package com.yeqifu.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeqifu.sys.common.DataGridView;
import com.yeqifu.sys.entity.ImageDTO;
import com.yeqifu.sys.entity.Notice;
import com.yeqifu.sys.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("map")
public class MapController {

    /**
     * 加载地图
     * @param noticeVo
     * @return
     */
    @RequestMapping("loadmap")
    public DataGridView loadAllNotice(String srcImgPath, String targetImgPath, List<ImageDTO> list){

        return null;
       // return new DataGridView(page.getTotal(),page.getRecords());
    }
}
