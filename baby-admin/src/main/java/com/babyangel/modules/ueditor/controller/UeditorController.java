package com.babyangel.modules.ueditor.controller;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;
import com.babyangel.common.exception.RRException;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;
import com.babyangel.common.validator.ValidatorUtils;
import com.babyangel.modules.hospital.entity.MaterialEntity;
import com.babyangel.modules.hospital.service.MaterialService;
import com.babyangel.modules.ueditor.entity.UeditorEntity;
import com.babyangel.modules.ueditor.entity.UeditorResultEntity;
import com.babyangel.modules.oss.cloud.OSSFactory;
import com.babyangel.modules.oss.entity.SysOssEntity;
import com.babyangel.modules.oss.service.SysOssService;
import com.babyangel.modules.ueditor.entity.UeditorResultListEntity;
import com.babyangel.modules.ueditor.entity.UrlEntity;
import com.babyangel.modules.ueditor.service.UeditorService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("ueditor/ueditor")
public class UeditorController {

    @Autowired
    private UeditorService ueditorService;

    @Autowired
    private MaterialService materialService;

    /**
     * 获取ueditor配置信息
     */
    @RequestMapping("/config")
    public String config(String action, HttpServletRequest request, HttpServletResponse response) throws Exception{
        if ("config".equals(action)) {
            //读取配置文件
            return "redirect:/statics/plugins/ueditor/jsp/config.json";
        } else if ("listfile".equals(action)||"listimage".equals(action)) {
            //转发文件列表文件请求
            return "forward:/ueditor/ueditor/imgList";
        } else{
            //转发文件上传请求
            return "forward:/ueditor/ueditor/imgUpload";
        }
    }

    /**
     * 上传图片文件
     * @ResponseBody,必须有，否则报错
     * 注意，@RequestParam("upfile")要与config.json中的保持一致
     */
    @RequestMapping("/imgUpload")
    @ResponseBody
    public UeditorResultEntity upload(@RequestParam("upfile") MultipartFile file) throws Exception {
        UeditorResultEntity image = new UeditorResultEntity();
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        Map<String,String> map = new HashMap<String,String >();
        try {
            String fileName = file.getOriginalFilename();

            //1、上传文件到云盘
            String suffix = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
            String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

            //2、设置Ueditor返回值
            image.setUrl(url);
            image.setState("SUCCESS");
            image.setOriginal(fileName);
            image.setTitle(fileName);

            //3、保存文件信息到服务器数据库
            UeditorEntity materialEntity = new UeditorEntity();
            materialEntity.setType(file.getContentType());
            materialEntity.setUrl(url);
            materialEntity.setCreateTime(new Date());
            ueditorService.insert(materialEntity);

        } catch (Exception e) {
            e.printStackTrace();
            image.setState("FAIL");
        }
        return image;
    }

    /**
     * 上传图片文件
     * @ResponseBody,必须有，否则报错
     * 注意，@RequestParam("upfile")要与config.json中的保持一致
     */
    @RequestMapping("/upload")
    @ResponseBody
    public UeditorResultEntity uploadMaterial(@RequestParam("upfile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        UeditorResultEntity image = new UeditorResultEntity();
        uploadFile(file, image);
        return image;
    }

    /**
     * 上传图片文件
     * @ResponseBody,必须有，否则报错
     * 注意，@RequestParam("upfile")要与config.json中的保持一致
     */
    @RequestMapping("/uploadOne")
    @ResponseBody
    public UeditorResultEntity uploadOneMaterial(@RequestParam("file") MultipartFile file)  {
        UeditorResultEntity image = new UeditorResultEntity();
        if (file.isEmpty()) {
            image.setState("FAIL");
            return image;
        }

        uploadFile(file, image);
        return image;
    }



    /**
     * 获取图片文件列表
     * @ResponseBody,必须有，否则报错
     * 注意，@RequestParam("upfile")要与config.json中的保持一致
     */
    @RequestMapping("/imgList")
    @ResponseBody
    public UeditorResultListEntity selectAllImg() throws Exception {
        UeditorResultListEntity list = new UeditorResultListEntity();

        try{
            //3、保存文件信息到服务器数据库
            Wrapper<UeditorEntity> wrapper = new EntityWrapper<>();
            List<UeditorEntity> ueditorEntityList = ueditorService.selectList(wrapper);
            list.setState("SUCCESS");
            List<UrlEntity> urlEntities = new ArrayList<>();
            for (UeditorEntity oss : ueditorEntityList){
                urlEntities.add(new UrlEntity(oss.getUrl()));
            }
            list.setList(urlEntities);
            list.setStart(20);
            if(null!=ueditorEntityList) {
                list.setTotal(ueditorEntityList.size());
            }
        } catch (Exception e) {
            list.setState("FAIL");
            return list;
        }
        return list;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ueditor:ueditor:list")
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ueditorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ueditor:ueditor:info")
    @ResponseBody
    public R info(@PathVariable("id") Long id){
        UeditorEntity ueditor = ueditorService.selectById(id);

        return R.ok().put("ueditor", ueditor);
    }

    /**
     * 保存
     */
    @Log(title = "ueditor管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("ueditor:ueditor:save")
    @ResponseBody
    public R save(@RequestBody UeditorEntity ueditor){
        ueditorService.insert(ueditor);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "ueditor管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("ueditor:ueditor:update")
    @ResponseBody
    public R update(@RequestBody UeditorEntity ueditor){
        ValidatorUtils.validateEntity(ueditor);
        ueditorService.updateAllColumnById(ueditor);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "ueditor管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("ueditor:ueditor:delete")
    @ResponseBody
    public R delete(@RequestBody Long[] ids){
        ueditorService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    private void uploadFile(@RequestParam("file") MultipartFile file, UeditorResultEntity image) {
        try {
            String fileName = file.getOriginalFilename();

            //1、上传文件到云盘
            String suffix = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
            String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

            //2、设置Ueditor返回值
            image.setUrl(url);
            image.setState("SUCCESS");
            image.setOriginal(fileName);
            image.setTitle(fileName);

            //3、保存文件信息到服务器数据库
            MaterialEntity materialEntity = new MaterialEntity();
            materialEntity.setType(file.getContentType());
            materialEntity.setUrl(url);
            materialEntity.setCreateTime(new Date());
            materialService.insert(materialEntity);

        } catch (Exception e) {
            e.printStackTrace();
            image.setState("FAIL");
        }
    }
}
