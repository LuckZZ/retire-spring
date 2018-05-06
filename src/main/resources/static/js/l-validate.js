$(function () {
    $.validator.setDefaults({
        errorElement: "small",
        rules:{
            jobNum:{
                required: true,
                digits:true,
                rangelength:[6,6]
            },
            name:{
                required: true,
                rangelength:[2,10]
            },
            tel1:{
                digits:true,
                rangelength:[7,11]
            },
            tel2:{
                digits:true,
                rangelength:[7,11]
            },
            tel3:{
                digits:true,
                rangelength:[7,11]
            },
            password:{
                required: true,
                rangelength:[6,10]
            },
            passwordCfm:{
                required: true,
                rangelength:[6,10],
                equalTo:"#password"
            },
            birth:{
                required:true
            },
            workTime:{
                required:true,
                compareDate:"#birth"
            },
            retireTime:{
                required:true,
                compareDate:"#workTime"
            },
            groupName:{
                required:true
            },
            //活动名称
            activityName:{
                required:true
            },
            //自定义活动列
            inputDef:{
                required:true
            },
            minAge:{
                required:true,
                range:[0,120]
            },
            maxAge:{
                required:true,
                range:[0,120],
                compareVal:"#minAge"
            }
        },
        messages:{
            jobNum:{
                rangelength:"工号必须6位",
                remote:"此工号已存在，重新输入"
            },
            name:{
                rangelength:"姓名2-10位"
            },
            password:{
                rangelength:"密码6-10位"
            },
            passwordCfm:{
                rangelength:"密码6-10位",
                equalTo:"两次密码输入不一致"
            },
            tel1:{
                rangelength: "电话7-11位"
            },
            tel2:{
                rangelength: "电话7-11位"
            },
            tel3:{
                rangelength: "电话7-11位"
            },
            workTime:{
                compareDate:"工作时间必须大于出生时间"
            },
            retireTime:{
                compareDate:"退休时间必须大于工作时间"
            },
            groupName:{
                remote:"此组名已存在"
            },
            activityName:{
                remote:"此活动名已存在"
            },
            maxAge:{
                compareVal:"最大年龄必须大于最小年龄"
            }
        },
        errorPlacement: function(error, element) {
            $( element ).closest( "div" ).append( error );
        }
    });

    //日期比较，date1<date2返回true
    $.validator.methods.compareDate = function(value, element, param) {
        var startDate = $(param).val();
        var date1 = new Date(Date.parse(startDate.replace("-", "/")));
        var date2 = new Date(Date.parse(value.replace("-", "/")));
        return date1 < date2;
    };

    //数值比较，v1<v2返回true
    $.validator.methods.compareVal = function(value, element, param) {
        var v1 = $(param).val();
        var v2 = value;
        return v1 < v2;
    };
});