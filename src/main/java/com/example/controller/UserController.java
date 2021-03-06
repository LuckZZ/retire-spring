package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.comm.config.WebSecurityConfig;
import com.example.domain.bean.CommSearch;
import com.example.domain.bean.Login;
import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.*;
import com.example.domain.enums.Role;
import com.example.domain.enums.SearchType;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.*;
import com.example.utils.DataUtils;
import com.example.utils.FileUtils;
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
import java.util.List;
import java.util.UUID;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@RequestMapping("/user")
@Controller
@Access(roles = Role.admin)
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private NationService nationService;

    @Autowired
    private PoliticsService politicsService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DutyService dutyService;

    @Value("${file.pictures.url}")
    private String filePicturesUrl;

   @RequestMapping("/userList")
   @LoggerManage(description = "组员列表")
   @Access(roles = Role.grouper)
    public String userList(HttpSession session, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
       Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
       //       传递搜索类型
       model.addAttribute("searchType", SearchType.all);
       if (login.getRole() == Role.admin){
           Page<User> datas = userService.findAllUserCriteria(page,new UserSearchForm());
           model.addAttribute("datas",datas);
           assignModel(model);
           return "admin/user_list";
       }else if (login.getRole() == Role.grouper){
//        根据组id查找数据
           Page<User> datas = userService.findAllByGroupId(login.getGroup().getGroupId(),page);
           model.addAttribute("datas",datas);
           assignModel(model);
           return "grouper/user_list";
       }
       return "/noAccess";
    }

    @RequestMapping("/userList/superSearch")
    @LoggerManage(description = "组员高级搜索列表")
    public String superSearch(Model model, @ModelAttribute(value = "userSearchForm") UserSearchForm userSearchForm, @RequestParam(value = "page", defaultValue = "0") Integer page){
//       传递搜索类型
       model.addAttribute("searchType", SearchType.searchSuper);
        Page<User> datas = userService.findAllUserCriteria(page, userSearchForm);
        model.addAttribute("datas",datas);

        assignModel(model);

//        搜索表单的值，再传入页面
        model.addAttribute("userSearchForm",userSearchForm);

        return "admin/user_list";
    }

    /**
     *
     * @param model
     * @param type 1：根据工号查询；2：根据姓名查询
     * @param value
     * @return
     */
    @RequestMapping("/userList/{type}/{value}")
    @LoggerManage(description = "用户列表BySearch")
    @Access(roles = Role.grouper)
    public String userListByType(HttpSession session, Model model, @PathVariable Integer type, @PathVariable String value, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        //       传递搜索类型
        model.addAttribute("searchType", SearchType.search);
        if (login.getRole() == Role.admin){
            assignModel(model);
            model.addAttribute("userCommSearch", new CommSearch(type, value));
            if (type == 1 && value != null){
//        根据工号
                Page<User> datas = userService.findAllByJobNum(value,page);
                model.addAttribute("datas",datas);
                return "admin/user_list";
            }else if (type == 2 && value != null){
//        根据姓名
                Page<User> datas = userService.findAllByNameContaining(value,page);
                model.addAttribute("datas",datas);
                return "admin/user_list";
            }
        }else if (login.getRole() == Role.grouper){
            Integer groupId = login.getGroup().getGroupId();
            assignModel(model);
            model.addAttribute("userCommSearch", new CommSearch(type, value));
            if (type == 1 && value != null){
//        根据工号
                Page<User> datas = userService.findAllByGroupIdAndJobNum(groupId,value,page);
                model.addAttribute("datas",datas);
                return "grouper/user_list";
            }else if (type == 2 && value != null){
//        根据姓名
                Page<User> datas = userService.findAllByGroupIdAndNameContaining(groupId,value,page);
                model.addAttribute("datas",datas);
                return "grouper/user_list";
            }
        }
//        重定向
        return "redirect:/user/user_list";
    }

    /**
     *
     * @param jobNum
     * @return false：用户存在 true：用户不存
     */
    @ResponseBody
    @RequestMapping("/exist")
    @LoggerManage(description = "用户存在")
    public boolean existsUser(@RequestParam(value = "jobNum") String jobNum){
        boolean exist = userService.existsByJobNum(jobNum);
        logger.info("用户存在:"+exist);
        if (!exist){
            return true;
        }
        return false;
    }

    /**
     * 进入增加用户界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/addView")
    @LoggerManage(description = "增加用户界面")
    public String addView(Model model){

        model.addAttribute("user",new User());

        assignModel(model);

        return "admin/user_add";
    }


    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @LoggerManage(description = "用户保存")
    public Response save(@ModelAttribute(value = "user") User user){
        if(userService.existsByJobNum(user.getJobNum())){
            return  result(ExceptionMsg.JobNumUsed);
        }
        userService.save(user);
        return result(ExceptionMsg.UserAddSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    @LoggerManage(description = "修改用户")
    public Response update(@ModelAttribute(value = "user") User user){
        boolean b=  userService.updateExceptId(user);
        if (b){
            return result(ExceptionMsg.UserUpdSuccess);
        }
        return result(ExceptionMsg.UserUpdFailed);
    }

    @RequestMapping(value = "/updateView/{userId}")
    @LoggerManage(description = "修改用户界面")
    public String updateView(@PathVariable String userId, Model model){
        User user = userService.findOne(Integer.parseInt(userId));
        model.addAttribute("user",user);

        assignModel(model);

        return "admin/user_update";
    }

    @RequestMapping(value = "/datailView/{userId}")
    @LoggerManage(description = "用户详细信息界面")
    public String datailView(@PathVariable String userId, Model model){
        User user = userService.findOne(Integer.parseInt(userId));
        model.addAttribute("user",user);
        return "admin/user_datail";
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    @LoggerManage(description = "删除用户")
    public Response delete(HttpServletRequest request){

        String[] userIds = request.getParameterValues("id");
        Integer[] ids = DataUtils.turn(userIds);

        try {
            userService.delete(ids);
            return result(ExceptionMsg.UserDelSuccess);
        }catch (Exception e){
            return result(ExceptionMsg.UserDelFailed);
        }
    }

    /**
     * 实现文件上传
     * */
    @ResponseBody
    @RequestMapping(value="fileUpload",method = RequestMethod.POST)
    @LoggerManage(description = "图片上传")
    public Response fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request){

        String userId = request.getParameter("userId");
        String oldFileName = file.getOriginalFilename();

        if(file.isEmpty()){
            logger.warn("文件为空");
            return result(ExceptionMsg.FileUploadEmptyFailed);
        }

        String fileName= UUID.randomUUID().toString()+"."+FileUtils.getFileExtName(oldFileName);
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
        User user =  userService.findOne(Integer.parseInt(userId));
        FileUtils.deleteFile(filePicturesUrl+user.getImgUrl());
        //            修改数据库
        userService.updateImg(fileName, Integer.parseInt(userId));

        return result(ExceptionMsg.FileUploadSuccess);
    }

    @RequestMapping("/changeGroupHtml")
    @LoggerManage(description = "修改组员分组页面")
    public String changeGroupHtml(@RequestParam(value = "value") String value, Model model){
        User user = userService.findOne(Integer.parseInt(value));
//        传递user
        model.addAttribute("user",user);
        List<Group> groups = groupService.findAll();
//        传递groups
        model.addAttribute("groups",groups);
        return "admin/user_updgroup_load";
    }
    @ResponseBody
    @RequestMapping("/changeGroup")
    @LoggerManage(description = "修改组员分组")
    public Response changeGroupHtml(@RequestParam(value = "param1") String userId,@RequestParam(value = "param2") String groupId){
       try {
           userService.updateGroupByUseId(Integer.parseInt(groupId),Integer.parseInt(userId));
           return result(ExceptionMsg.UserUpdGroupSuccess);
       }catch (Exception e){
           e.printStackTrace();
           return result(ExceptionMsg.UserUpdGroupFailed);
       }
    }

    /**
     *
     * @param existType 1:设置在世 2：设置去世和更新去世时间
     * @param userId
     * @param passTime
     * @return
     */
    @ResponseBody
    @RequestMapping("/changeExist/{existType}")
    @LoggerManage(description = "修改组员状态")
    public Response changeExist(@PathVariable int existType, @RequestParam(value = "param1") String userId, @RequestParam(value = "param2", defaultValue = "") String passTime){
        try {
            if (existType == 1){
                userService.updateExistYes(Integer.parseInt(userId));
            }else if(existType == 2){
                userService.updateExistNO(Integer.parseInt(userId), passTime);
            }
            return result(ExceptionMsg.UserExistSuccess);
        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.UserExistFailed);
        }
    }

    private Model assignModel(Model model){
        //        所有的组
        List<Group> groups = groupService.findAll();

//        所有的民族
        List<Nation> nations = nationService.findAll();

        //        所有的政治面貌
        List<Politics> politicss = politicsService.findAll();

        //        所有的部门
        List<Department> departments = departmentService.findAll();

        //        所有的职务
        List<Duty> duties = dutyService.findAll();

//        用于用户高级搜索
        UserSearchForm userSearchForm = new UserSearchForm();

        model.addAttribute("groups",groups);

        model.addAttribute("nations",nations);

        model.addAttribute("politicss",politicss);

        model.addAttribute("departments",departments);

        model.addAttribute("duties",duties);

        model.addAttribute("userSearchForm",userSearchForm);

        model.addAttribute("userCommSearch", new CommSearch(1, ""));

        return model;
    }

}
