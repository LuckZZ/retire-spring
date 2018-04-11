/**
 * 打开单个删除模态框
 * @param $oneDel
 * @param myurl
 * @param paramId
 */
function oneDelModal($oneDel,paramUrl,paramId) {
    $oneDel.modal({
        onConfirm: function(options) {
            $.ajax({
                async: false,
                type: 'POST',
                dataType: 'json',
                data:{
                    id:paramId
                },
                url: paramUrl,
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    toastr.error('出现错误！', '错误');
                },
                success: function(data){
                    if (data.codeBool){
                        location.reload();
                    }else {
                        toastr.error(data.message, '操作失败');
                    }
                }
            });

        }
    });
    //移除暂存的实例
    removeModalData($oneDel);
}

/**
 * 打开批量删除模态框
 * @param $mulDel
 * @param myurl
 * @param paramId
 */
function mulDelModal($mulDel,paramUrl,paramId) {
    $mulDel.modal({
        onConfirm: function(options) {
            $.ajaxSettings.traditional=true;
            $.ajax({
                async: false,
                type: 'POST',
                dataType: 'json',
                data:{
                    id:paramId
                },
                url: paramUrl,
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    toastr.error('出现错误！', '错误');
                },
                success: function(data){
                    if (data.codeBool){
                        location.reload();
                    }else {
                        toastr.error(data.message, '操作失败');
                    }
                }
            });

        }
    });
    //移除暂存的实例
    removeModalData($mulDel);

}
