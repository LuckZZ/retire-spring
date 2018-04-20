package com.example.controller;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.example.comm.aop.LoggerManage;
import com.example.domain.bean.UserSearchForm;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by : Zhangxuemeng
 */
@RequestMapping("/excel")
@Controller
public class ExcelController extends BaseController{

    @ResponseBody
    @RequestMapping("/exportUser")
    @LoggerManage(description = "导出用户表")
    public Response exportUser(ModelMap map, @ModelAttribute(value = "userSearchForm") UserSearchForm userSearchForm, HttpServletRequest request, HttpServletResponse response){

        String exportScope = request.getParameter("exportScope");

        String[] item = request.getParameterValues("item");

        if("all".equals(exportScope)){

        }else if("selected".equals(exportScope)){

        }

        List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();
        beanList .add(new ExcelExportEntity("Id", "id"));
        beanList .add(new ExcelExportEntity("学生姓名", "name"));
        beanList .add(new ExcelExportEntity("学生年龄", "age"));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < 80; i++){
            Map<String, Object> mapTT= new HashMap<>();
            mapTT.put("id","ID"+i);
            mapTT.put("name","小明"+i);
            mapTT.put("age","年龄"+i);
            list.add(mapTT);
        }

        ExportParams params = new ExportParams("用户信息","用户信息（2018.4.20）", ExcelType.XSSF);
        map.put(MapExcelConstants.MAP_LIST, list);
        map.put(MapExcelConstants.ENTITY_LIST, beanList);
        map.put(MapExcelConstants.PARAMS, params);
        map.put(MapExcelConstants.FILE_NAME, "aaaa");//文件名称
        PoiBaseView.render(map, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);

        return result(ExceptionMsg.SUCCESS);
    }
}
