package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.*;
import com.example.domain.enums.Exist;
import com.example.domain.enums.Gender;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.*;
import com.example.utils.DataUtils;
import com.example.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/user")
@Controller
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

   @RequestMapping("/userList")
   @LoggerManage(description = "组员列表")
    public String userList(Model model){
       List<User> users = userService.findAll();
       model.addAttribute("users",users);
       return "admin/user_list";
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

//        所有的组
        List<Group> groups = groupService.findAll();

//        所有的民族
        List<Nation> nations = nationService.findAll();

        //        所有的政治面貌
        List<Politics> politicss = politicsService.findAll();

        //        所有的政治面貌
        List<Department> departments = departmentService.findAll();

        //        所有的政治面貌
        List<Duty> duties = dutyService.findAll();

        model.addAttribute("user",new User());

        model.addAttribute("groups",groups);

        model.addAttribute("nations",nations);

        model.addAttribute("politicss",politicss);

        model.addAttribute("departments",departments);

        model.addAttribute("duties",duties);

        return "admin/user_add";
    }


    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @LoggerManage(description = "用户保存")
    public Response save(@ModelAttribute(value = "user") User user){
        if(userService.existsByJobNum(user.getJobNum())){
            return  result(ExceptionMsg.FAILED);
        }
        user.setExist(Exist.yes);
//            保存
        logger.info(user.toString());
        userService.save(user);
        return result(ExceptionMsg.SUCCESS);
    }

    @RequestMapping(value = "/datailView/{userId}")
    @LoggerManage(description = "用户详细信息界面")
    public String datailView(@PathVariable String userId, Model model){
        User user = userService.findOne(Integer.parseInt(userId));
//        电话String切割
        String tel = user.getTel();
        List<String> tels = DataUtils.turnToList(tel);

        model.addAttribute("user",user);
        model.addAttribute("tels",tels);
        return "admin/user_datail";
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    @LoggerManage(description = "删除用户")
    public Response delete(HttpServletRequest request){

        String[] userIds = request.getParameterValues("userId");
        Integer[] ids = DataUtils.turn(userIds);

        try {
            userService.delete(ids);
            return result(ExceptionMsg.SUCCESS);
        }catch (Exception e){
            return result(ExceptionMsg.FAILED);
        }
    }
}
