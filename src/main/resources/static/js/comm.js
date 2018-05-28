var gGroups;

$(function () {
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

    //    测试cookie
    // $.cookie("cookie-msg", data.message);
    // alert("cookie-msg:"+$.cookie("cookie-msg"));
    // alert("true:"+($.cookie("cookie-msg")!=""));
    // $.cookie("cookie-msg", "");
    // alert("cookie-msg:"+$.cookie("cookie-msg"));
    // alert("true:"+($.cookie("cookie-msg")==""));
    // alert("false:"+($.cookie("cookie-msg")!=""));
    if (($.cookie("cookie-msg")!="") && (typeof($.cookie("cookie-msg"))!="undefined")){
        toastr.success($.cookie("cookie-msg"), '操作成功');
        $.cookie("cookie-msg", "");
    }

});

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
                // toastr.success(data.message, '操作成功');
                // paramForm[0].reset();
                //刷新页面前，把提示信息存入cookie
                $.cookie("cookie-msg", data.message);
                //刷新页面
                location.reload();
            }else {
                toastr.error(data.message, '操作失败');
            }
        }
    });
}

/**
 * ajax提交form
 * @param paramForm 传递表单
 * @param paramUrl
 */
function formAjaxReload(paramForm,paramUrl) {
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
                //刷新页面前，把提示信息存入cookie
                $.cookie("cookie-msg", data.message);
                //刷新页面
                location.reload();
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
    if (paramId.length == 0){
        toastr.error('请选择数据!', '操作提示');
        return;
    }
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
                //刷新页面前，把提示信息存入cookie
                $.cookie("cookie-msg", data.message);
                //刷新页面
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
                //刷新页面前，把提示信息存入cookie
                $.cookie("cookie-msg", data.message);
                //刷新页面
                location.reload();
            }else {
                toastr.error(data.message, '操作失败');
            }
        }
    });
}

