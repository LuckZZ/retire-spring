package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.*;
import com.example.domain.enums.Exist;
import com.example.domain.enums.Gender;
import com.example.domain.enums.Rank;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.*;
import com.example.utils.DataUtils;
import com.example.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
//        默认组员
        user.setRank(Rank.user);
//            保存
        logger.info(user.getExist());
        userService.save(user);
        return result(ExceptionMsg.SUCCESS);
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    @LoggerManage(description = "修改用户")
    public Response update(@ModelAttribute(value = "user") User user){
        boolean b=  userService.updateExceptId(user);
        if (b){
            return result(ExceptionMsg.SUCCESS);
        }
        return result(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/updateView/{userId}")
    @LoggerManage(description = "修改用户界面")
    public String updateView(@PathVariable String userId, Model model){
        User user = userService.findOne(Integer.parseInt(userId));
        model.addAttribute("user",user);

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

        model.addAttribute("groups",groups);

        model.addAttribute("nations",nations);

        model.addAttribute("politicss",politicss);

        model.addAttribute("departments",departments);

        model.addAttribute("duties",duties);

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
    public Response fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request){

        String adminId = request.getParameter("userId");
        String jobNum = request.getParameter("jobNum");
        String imgUrl = request.getParameter("imgUrl");
        String fileName = file.getOriginalFilename();

        System.out.println("userId:"+adminId);
        System.out.println("jobNum:"+jobNum);
        System.out.println("imgUrl:"+imgUrl);
        System.out.println("fileName:"+fileName);

 /*       String filePath = request.getSession().getServletContext().getRealPath("/");
        System.out.println(filePath);*/

        if(file.isEmpty()){
            return result(ExceptionMsg.FAILED);
        }

        return result(ExceptionMsg.SUCCESS);

/*        ImgUtils imgUtils = new ImgUtils(jobNum,file);
        try {
            imgUtils.save();
            return result(ExceptionMsg.SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return result(ExceptionMsg.FAILED);
        }*/
    }
}
