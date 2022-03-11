package com.yeqifu.sys.controller;

import com.yeqifu.sys.common.DataGridView;
import com.yeqifu.sys.entity.ImageDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("map")
public class MapController{

    /**
     * 加载地图
     * @param
     * @return
     */
    @RequestMapping("loadmap")
    public DataGridView loadAllNotice(String srcImgPath, String targetImgPath, List<ImageDTO> list){

           return null;
    }


    @RequestMapping("processImg")
    public void loadAllNotice(){


    }


}
