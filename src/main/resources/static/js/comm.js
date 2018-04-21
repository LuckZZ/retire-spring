var gGroups;

$(function () {
    loadGroup();
    toastrOpt();

    //如果不支持trim函数
    if (!String.prototype.trim){
        /*---------------------------------------
         * 清除字符串两端空格，包含换行符、制表符
         *---------------------------------------*/
        String.prototype.trim = function () {
            return this.replace(/(^[\s\n\t]+|[\s\n\t]+$)/g, "");
        }
    }

})

/**
 * toastrOpt提示框
 */
function toastrOpt() {
    toastr.options = {
        'closeButton': true,
        'positionClass': 'toast-top-center',
        'timeOut': '5000',
    };
}

/**
 *
 * 无数据交互，走此处
 * 为风格统一，url以"/"开头
 * @param url */
/*function directUrl(url) {
    window.location.href="/direct"+url;
}*/


/**
 * 加载组
 */
function loadGroup() {
    $.ajax({
        async: false,
        type: 'POST',
        dataType: 'json',
        url: '/group/groups',
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest);
            console.log(textStatus);
            console.log(errorThrown);
        },
        success: function(data){
            gGroups=data.data;
            initGroups(gGroups);
        }
    });
}

/**
 * 生成html代码
 * @param gGroups
 */
function initGroups(gGroups) {

    var dynaGroupId = $('#dynaGroupId').data('id');
    // alert("vvvv:"+dynaGroupId);

    for(var i=0;i<gGroups.length;i++) {
        var groupId=gGroups[i].groupId;
        var groupName=gGroups[i].groupName;
        var url = "/group/"+groupId;
        var id= groupId;
        var group= "<li class=\"sidebar-nav-link\">";

        if (dynaGroupId == id){
            group = group+"<a href=\""+url+"\" id=\""+id+"\" class='active'>";
        }else {
            group = group+"<a href=\""+url+"\" id=\""+id+"\" class=''>";
        }
        group = group + "<span class=\"am-icon-angle-right sidebar-nav-link-logo\"></span>"+groupName;
        group = group+"</a></li>";
        $("#dynaGroupId .sidebar-nav-link:last").after(group)

    }
}

/**
 * ajax提交form
 * @param paramForm 传递表单
 * @param paramUrl
 */
function formAjaxNoReload(paramForm,paramUrl) {
    $.ajax({
        url: paramUrl,
        type: "POST",
        data: paramForm.serialize(),
        dataType: "json",
        error: function ()
        {
            console.log("formAjaxNoReload error function!");
            toastr.error('出现错误！', '错误');
        },
        success: function (data)
        {
            if (data.codeBool){
                toastr.success(data.message, '操作成功');
            }else {
                toastr.error(data.message, '操作失败');
            }
        }
    });
}

/**
 *
 * @param paramUrl
 * @param paramId 传递id
 */
function idAjaxReload(paramUrl,paramId) {
    $.ajaxSettings.traditional=true;
    $.ajax({
        url: paramUrl,
        type: "POST",
        data:{
            id:paramId
        },
        error: function ()
        {
            console.log("idAjaxReload error function!");
            toastr.error('出现错误！', '错误');
        },
        success: function (data)
        {
            if (data.codeBool){
                location.reload();
            }else {
                toastr.error(data.message, '操作失败');
            }
        }
    });
}

function twoIdAjaxReload(paramUrl,paramVal1,paramVal2) {
    $.ajax({
        url: paramUrl,
        type: "POST",
        data:{
            param1:paramVal1,
            param2:paramVal2
        },
        error: function ()
        {
            console.log("idAjaxReload error function!");
            toastr.error('出现错误！', '错误');
        },
        success: function (data)
        {
            if (data.codeBool){
                location.reload();
            }else {
                toastr.error(data.message, '操作失败');
            }
        }
    });
}

