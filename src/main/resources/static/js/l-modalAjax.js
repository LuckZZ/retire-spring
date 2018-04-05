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
                    if(data.rspCode == '000000') {
                        //toastr.success('删除成功！', '操作成功');
                        location.reload();
                    }else if(data.rspCode == '000202'){
                        toastr.error(data.rspMsg, '操作失败');
                    }
                    else {
                        toastr.error('删除失败！', '操作失败');
                    }
                }
            });

        }
    });
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
                    if(data.rspCode == '000000') {
                        //toastr.success('删除成功！', '操作成功');
                        location.reload();
                    }else if(data.rspCode == '000202'){
                        toastr.error(data.rspMsg, '操作失败');
                    }
                    else {
                        toastr.error('删除失败！', '操作失败');
                    }
                }
            });

        }
    });

}
