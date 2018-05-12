package com.example.controller;

import com.example.comm.Constant;
import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.comm.config.WebSecurityConfig;
import com.example.domain.bean.CommSearch;
import com.example.domain.bean.Login;
import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Role;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.AdminService;
import com.example.utils.DataUtils;
import com.example.utils.FileUtils;
import com.example.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/admin")
@Controller
@Access(roles = Role.admin)
public class AdminController extends BaseController{
    @Autowired
    private AdminService adminService;

    @Value("${file.pictures.url}")
    private String filePicturesUrl;

    @RequestMapping("/adminList")
    @LoggerManage(description = "管理员列表")
    public String adminList(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("userCommSearch", new CommSearch(1, ""));
        Page<Admin> datas = adminService.findAllNoCriteria(page);
        model.addAttribute("datas",datas);
        return "admin/admin_list";
    }

    /**
     *
     * @param model
     * @param type 1：根据工号查询；2：根据姓名查询
     * @param value
     * @return
     */
    @RequestMapping("/adminList/{type}/{value}")
    @LoggerManage(description = "管理员列表BySearch")
    public String adminListByType(Model model, @PathVariable Integer type, @PathVariable String value, @RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("userCommSearch", new CommSearch(type, value));
        if (type == 1 && value != null){
//        根据工号
            Page<Admin> datas = adminService.findAllByJobNum(value,page);
            model.addAttribute("datas",datas);
            return "admin/admin_list";
        }else if (type == 2 && value != null){
//        根据姓名
            Page<Admin> datas = adminService.findAllByName(value,page);
            model.addAttribute("datas",datas);
            return "admin/admin_list";
        }
//        重定向
        return "redirect:/admin/admin_list";
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
    public Response save(@ModelAttribute(value = "admin") Admin admin){
        if(adminService.existsByJobNum(admin.getJobNum())){
            return  result(ExceptionMsg.JobNumUsed);
        }
        adminService.save(admin);
        return result(ExceptionMsg.AdminAddSuccess);
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

    @RequestMapping(value = "/pwdUpdateView")
    @LoggerManage(description = "修改管理员密码界面")
    public String pwdUpdateView(Model model){
        model.addAttribute("admin",new Admin());
        return "admin/pwd_update";
    }

    @ResponseBody
    @RequestMapping(value = "/pwdUpdate")
    @LoggerManage(description = "修改管理员密码")
    public Response pwdUpdate(HttpServletRequest request){
        String oldPassword = request.getParameter("oldPassword");
        String password = request.getParameter("password");
        //     取出session
        HttpSession session = request.getSession();
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Integer adminId = login.getId();
        Admin admin = adminService.findOne(adminId);
        if (!admin.getPassword().equals(getPasswordMD5(oldPassword))){
            return result(ExceptionMsg.LoginPasswordFailed);
        }
        adminService.updatePassword(getPasswordMD5(password), adminId);
        return result(ExceptionMsg.pwdUpdateSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    @LoggerManage(description = "删除管理员")
    public Response delete(HttpServletRequest request){

        String[] adminIds = request.getParameterValues("id");
        Integer[] ids = DataUtils.turn(adminIds);

       if (!adminService.canDelete(ids)){
            return result(ExceptionMsg.DisableAdminDelete);
       }
       try {
            adminService.delete(ids);
           return result(ExceptionMsg.AdminDelSuccess);
       }catch (Exception e){
           return result(ExceptionMsg.AdminDelFailed);
       }
    }

    @ResponseBody
    @RequestMapping("/changeCanLogin")
    @LoggerManage(description = "切换管理员登陆权限")
    public Response changeCanLogin(@RequestParam(value = "id") String paramId){
        Integer id = Integer.parseInt(paramId);
        boolean b = adminService.notCanLogin(id);
        if (b){
            return result(ExceptionMsg.ChangeCanLoginSuccess);
        }
        return result(ExceptionMsg.ChangeCanLoginFailed);
    }

    @RequestMapping(value = "/updateView/{adminId}")
    @LoggerManage(description = "修改管理员界面")
    public String updateView(@PathVariable String adminId, Model model){
        Admin admin = adminService.findOne(Integer.parseInt(adminId));
        model.addAttribute("admin",admin);
        return "admin/admin_update";
    }

    @RequestMapping(value = "/datailView/{adminId}")
    @LoggerManage(description = "管理员详细信息界面")
    public String datailView(@PathVariable String adminId, Model model){
        Admin admin = adminService.findOne(Integer.parseInt(adminId));
        model.addAttribute("admin",admin);
        return "admin/admin_datail";
    }

    /**
     * typeId=1：修改姓名、权限
     * typeId=2:修改密码
     * @param typeId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update/{typeId}")
    @LoggerManage(description = "修改管理员")
    public Response update(@PathVariable Integer typeId, HttpServletRequest request){
        String adminId = request.getParameter("adminId");
        if (typeId == 1){
            logger.info("修改用户名及登陆状态");
            String name = request.getParameter("name");
            String canLoginStr = request.getParameter("canLogin");
            //        设置canLogin的值
            CanLogin canLogin = CanLogin.no;
            if(canLoginStr == null){
                canLogin=CanLogin.no;
            }else {
                canLogin=CanLogin.yes;
            }
            adminService.updateNameAndCanLogin(name, canLogin, Integer.parseInt(adminId));
            return result(ExceptionMsg.AdminUpdSuccess);
        }else if(typeId == 2){
//            修改密码
            logger.info("修改密码");
            String password = request.getParameter("password");
            adminService.updatePassword(getPasswordMD5(password),Integer.parseInt(adminId));
            return result(ExceptionMsg.ResetPwdSuccess);
        }
        return result(ExceptionMsg.FAILED);
    }

    /**
     * 实现文件上传
     * */
    @ResponseBody
    @RequestMapping(value="fileUpload",method = RequestMethod.POST)
    @LoggerManage(description = "图片上传")
    public Response fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request){

        String adminId = request.getParameter("adminId");
        String oldFileName = file.getOriginalFilename();

        if(file.isEmpty()){
            logger.warn("文件为空");
            return result(ExceptionMsg.FileUploadEmptyFailed);
        }

        String fileName= UUID.randomUUID().toString()+"."+ FileUtils.getFileExtName(oldFileName);
//        String savePath = filePicturesUrl +fileName;

        try {
//            上传图片
            FileUtils.uploadFile(file, filePicturesUrl, fileName);
            logger.info("上传图片成功,图片名："+fileName);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("上传图片出现异常："+e);
            return result(ExceptionMsg.FileUploadFailed);
        }
        //        删除以前的图片
        Admin admin =  adminService.findOne(Integer.parseInt(adminId));
        FileUtils.deleteFile(filePicturesUrl+admin.getImgUrl());
        //            修改数据库
        adminService.updateImg(fileName, Integer.parseInt(adminId));

        HttpSession session = request.getSession();
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
//        如果相等，修改session图片
        if (login.getId() == Integer.parseInt(adminId)){
            login.setImgUrl(fileName);
            session.setAttribute(WebSecurityConfig.SESSION_KEY, login);
        }

        return result(ExceptionMsg.FileUploadSuccess);
    }

    /**
     * 密码加密后，字符串
     * @param password
     * @return
     */
    private String getPasswordMD5(String password){
        String str = MD5Util.encrypt(password+ Constant.PASSWORD_SALT);
        return str;
    }

}
