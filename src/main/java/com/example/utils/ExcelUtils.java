package com.example.utils;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Create by : Zhangxuemeng
 */
public class ExcelUtils {

    public static void export(ModelMap map, String fileName, String title, List<ExcelExportEntity> beanList, List<Map<String, Object>> list, HttpServletRequest request, HttpServletResponse response){
        ExportParams params = new ExportParams(title,title, ExcelType.XSSF);
        map.put(MapExcelConstants.MAP_LIST, list);
        map.put(MapExcelConstants.ENTITY_LIST, beanList);
        map.put(MapExcelConstants.PARAMS, params);
        map.put(MapExcelConstants.FILE_NAME, fileName);//文件名称
        PoiBaseView.render(map, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);
    }
}
