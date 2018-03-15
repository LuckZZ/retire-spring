var gGroups;

$(function () {
    loadGroup();
})

/**
 * 加载组
 */
function loadGroup() {
    $.ajax({
        async: false,
        type: 'POST',
        dataType: 'json',
        url: '/group/groupList',
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest);
            console.log(textStatus);
            console.log(errorThrown);
        },
        success: function(data){
            gGroups=data.data;
            initFollows(gGroups);
        }
    });
}

/**
 * 生成html代码
 * @param gGroups
 */
function initFollows(gGroups) {
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