var gGroups;

$(function () {
    loadGroup();
    toastrOpt();
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
    for(var i=0;i<gGroups.length;i++) {
        var groupId=gGroups[i].groupId;
        var groupName=gGroups[i].groupName;
        var url = "/group/"+groupId;
        var id= groupId;
        var group= "<li class=\"sidebar-nav-link\">";
        group = group+"<a href=\""+url+"\" id=\""+id+"\" th:class=\"(${activeId} eq '"+id+"')?'active':''\">";
        group = group + "<span class=\"am-icon-angle-right sidebar-nav-link-logo\"></span>"+groupName;
        group = group+"</a></li>";
        $("#newGroup").after(group)
    }
}

/**
 * ajax提交form
 * @param paramForm 传递表单
 * @param paramUrl
 */
function myFormAjax(paramForm,paramUrl) {
    $.ajax({
        url: paramUrl,
        type: "POST",
        data: paramForm.serialize(),
        dataType: "json",
        error: function ()
        {
            console.log("myFormAjax error function!");
            toastr.error('出现错误！', '错误');
        },
        success: function (data)
        {
            console.log("myFormAjax success function!");

            if(data.rspCode == '000000') {
                toastr.success('操作成功！', '操作成功');
            }
            else {
                toastr.error('操作失败！', '操作失败');
            }
        }
    });
}

/**
 *
 * @param paramUrl
 * @param paramId 传递id
 */
function ajaxAndReload(paramUrl,paramId) {
    $.ajax({
        url: paramUrl,
        type: "POST",
        data:{
            id:paramId
        },
        error: function ()
        {
            console.log("ajaxAndReload error function!");
            toastr.error('出现错误！', '错误');
        },
        success: function (data)
        {
            if(data.rspCode == '000000') {
                //toastr.success('操作成功！', '操作成功');
                window.location.reload();
            }
            else {
                toastr.error('操作失败！', '操作失败');
            }
        }
    });
}

//ssss