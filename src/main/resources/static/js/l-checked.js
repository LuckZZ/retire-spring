$(function () {

    /**
     * 功能：全选
     * 执行者：table thead th input:checkbox
     */
    $('table thead th input:checkbox').change(function(){
        $(this).closest('table').find(':checkbox').prop('checked',$(this).is(':checked')?true:false);
    });

});

/**
 * 功能：返回选择框数组的值
 * 范围：table tbody td input:checkbox:checked
 * @returns {any[]}
 */
function checkedIds() {
    var checkedId = new Array();
    $("table tbody td input:checkbox:checked").each(function() {
        checkedId.push($(this).val());
    });
    return checkedId;
}

/**
 * 功能：返回选择框数组的父级元素tr
 * @returns {any[]}
 */
function checkedTrs() {
    var checkedTr = new Array();
    $("table tbody td input:checkbox:checked").each(function() {
        //当前元素查找父级元素
        checkedTr.push($(this).closest('tr'));
    });
    return checkedTr;
}