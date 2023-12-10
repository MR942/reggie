package com.mxl.reggie.controller;

import com.mxl.reggie.common.R;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;




/**
 * 进行文件上传下载的处理
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        // file是一个临时文件
        log.info(file.toString());
        // 原始文件名
        String originalFilename = file.getOriginalFilename();

        // 使用UUID来对文件从新命名，防止文件名重复
        String s = UUID.randomUUID().toString();
        // 解决文件名后缀问题
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 将随机和后缀拼接
        String name = s + substring;

        File dir = new File(basePath);
        if(!dir.exists()){
            // 目录不存在，需要创建
            dir.mkdirs();
        }
        // 将临时文件转存到指定位置
        file.transferTo(new File(basePath+name));
        return R.success(name);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        // 输入流，读取文件内容
        FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
        ServletOutputStream outputStream = response.getOutputStream();
        // 输出流，将文件写回浏览器，再浏览器展示

        response.setContentType("image/jpeg");

        int len = 0;
        byte[] bytes = new byte[1024];
        while((len = fileInputStream.read(bytes)) != -1)    {
            outputStream.write(bytes,0,len);
            outputStream.flush();
        }

        // 关闭资源
        outputStream.close();
        fileInputStream.close();

    }





}
