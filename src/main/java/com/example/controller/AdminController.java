package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Gender;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.AdminService;
import com.example.utils.DateUtils;
import com.example.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController extends BaseController{
    @Autowired
    private AdminService adminService;

    @RequestMapping("/adminList")
    @LoggerManage(description = "管理员列表")
    public String adminList(Model model){
        List<Admin> admins = adminService.findAll();
        model.addAttribute("admins",admins);
        return "admin/admin_list";
    }

    /**
     *
     * @param jobNum
     * @return false：用户存在 true：用户不存
     */
    @ResponseBody
    @RequestMapping("/exist")
    @LoggerManage(description = "管理员存在")
    public boolean existsAdmin(@RequestParam(value = "jobNum") String jobNum){
        boolean exist = adminService.existsByJobNum(jobNum);
        logger.info("用户存在:"+exist);
        if (!exist){
            return true;
        }
        return false;
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @LoggerManage(description = "管理员保存")
    public Response save(@ModelAttribute(value = "admin") Admin admin, Model model){
        if(admin.getCanLogin() == null){
            admin.setCanLogin(CanLogin.no);
        }

        System.out.println(admin.getImgUrl());

//        设置创建时间
        admin.setCreateTime(DateUtils.getDateSequence());
//        logger.info(admin.toString());

        if(!adminService.existsByJobNum(admin.getJobNum())){
            adminService.save(admin);
            return result(ExceptionMsg.SUCCESS);
        }
        return  result(ExceptionMsg.FAILED);
    }

    /**
     * 进入增加管理员界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/addView")
    @LoggerManage(description = "增加管理员界面")
    public String addView(Model model){
        model.addAttribute("admin",new Admin());
        return "admin/admin_add";
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    @LoggerManage(description = "删除管理员")
    public Response delete(@RequestParam(value = "adminId") String adminId){
       logger.info("adminId:"+adminId);
       try {
           Integer id = Integer.parseInt(adminId);
           adminService.delete(id);
           return result(ExceptionMsg.SUCCESS);
       }catch (Exception e){
           return result(ExceptionMsg.FAILED);
       }
    }


    /**
     * 实现文件上传
     * */
    @ResponseBody
    @RequestMapping(value="fileUpload",method = RequestMethod.POST)
    @LoggerManage(description = "图片上传")
    public Response fileUpload(@RequestParam(value = "file") MultipartFile file,@RequestParam(value = "jobNum") String jobNum){
        String fileName = file.getOriginalFilename();
        System.out.println("fileName:"+fileName);
        System.out.println("jobNum:"+jobNum);
        ImgUtils imgUtils = new ImgUtils(jobNum,file);
        imgUtils.save();
/*        if(file.isEmpty()){
            return result(ExceptionMsg.FAILED);
        }
        String fileName = file.getOriginalFilename();
        String path = System.getProperty("user.dir") + "/uploadFile" ;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return result(ExceptionMsg.SUCCESS);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return result(ExceptionMsg.FAILED);
        } catch (IOException e) {
            e.printStackTrace();
            return result(ExceptionMsg.FAILED);
        }*/
        return result(ExceptionMsg.FAILED);
    }
}
