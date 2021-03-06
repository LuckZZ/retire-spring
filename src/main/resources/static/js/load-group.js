$(function () {
    loadGroup();
});

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
            if (data.codeBool){
                gGroups=data.data;
                initGroups(gGroups);
            }else {
                // toastr.error(data.message, '操作失败');
            }
        }
    });
}

/**
 * 生成html代码
 * @param gGroups
 */
function initGroups(gGroups) {

    var dynaGroupId = $('#dynaGroupId').data('id');

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