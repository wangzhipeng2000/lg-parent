package com.lg.other.controller;

import cn.hutool.core.date.DateUtil;
import com.lg.common.pojo.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/file/")
public class UploadController {

    @Value("${file.path}")
    private String filePath;

    @Value("${file.domain}")
    private String fileDomain;

    @PostMapping("upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile file){
        //返回的结果
        Map<String,Object> imgInfo = new HashMap<>();
        //文件名称
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //文件存储路径
        String path = DateUtil.format(new Date(),"yyyy/MM/dd");
        String fileName = UUID.randomUUID()+extName;
        String  savefilePath = filePath+path;
        //检查目录是否存在
        File saveFileDir = new File(savefilePath);
        if(!saveFileDir.exists()){
            saveFileDir.mkdirs();
        }
        //保存图片
        File saveFile = new File(filePath+path+"/",fileName);
        try {
            file.transferTo(saveFile);
            imgInfo.put("imgUrl",fileDomain+path+"/"+fileName);
            imgInfo.put("fileName",fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.success(imgInfo);
    }
}
