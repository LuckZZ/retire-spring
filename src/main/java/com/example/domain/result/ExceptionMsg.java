package com.example.domain.result;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public enum ExceptionMsg {
    SUCCESS("操作成功",true),
    FAILED("操作失败",false),
    ParamError("参数错误！",false),
    LoginNameOrPassWordError("用户名或者密码错误！",false),

    JobNumNotUsed("工号不存在",true),
    JobNumUsed("工号已存在",false),

    AdminAddSuccess("新增管理员成功",true),
    AdminAddFailed("新增管理员失败",false),

    AdminDelSuccess("删除管理员成功",true),
    AdminDelFailed("删除管理员失败",false),

    ChangeCanLoginSuccess("切换登陆权限成功",true),
    ChangeCanLoginFailed("切换登陆权限失败",false),

    ResetPwdSuccess("重置密码成功",true),
    ResetPwdFailed("重置密码失败",false),

    AdminUpdSuccess("修改管理员成功",true),
    AdminUpdFailed("修改管理员失败",false),

    AbleAdminDelete("管理员删除成功",true),
    DisableAdminDelete("用户有登录权限，不能删除",false),

    UserAddSuccess("新增用户成功",true),
    UserAddFailed("新增用户失败",false),

    UserDelSuccess("删除用户成功",true),
    UserDelFailed("删除用户失败",false),

    UserUpdGroupSuccess("用户修改分组成功",true),
    UserUpdGroupFailed("用户需要分组失败",false),

    UserUpdSuccess("修改用户成功",true),
    UserUpdFailed("修改用户失败",false),

    GrouperRemoveSuccess("移除组长成功",true),
    GrouperRemoveFailed("移除组长失败失败",false),

    UserTypeSuccess("修改用户类型成功",true),
    UserTypeFailed("修改用户类型失败",false),

    UserExistSuccess("修改用户存在状态成功",true),
    UserExistFailed("修改用户存在状态失败",false),

    GroupUsed("组名已存在",false),

    GroupAddSuccess("新增组成功",true),
    GroupAddFailed("新增组失败失败",false),

    GroupDelSuccess("删除组成功",true),
    GroupDelFailed("删除组失败",false),

    GroupUpdSuccess("修改组名成功",true),
    GroupUpdFailed("修改组名失败",false),

    GroupRemUserSuccess("移除组员成功",true),
    GroupRemUserFailed("移除组员失败",false),

    ActivityAddSuccess("新增活动成功",true),
    ActivityAddFailed("新增活动失败",false),

    ActivityUsed("活动名称已存在",false),

    ActivityPublishSuccess("发布活动成功",true),
    ActivityPublishFailed("发布活动失败",false),

    ActivityUpdSuccess("更新活动成功",true),
    ActivityUpdFailed("更新活动失败",false),

    ActivityDelSuccess("删除活动成功",true),
    ActivityDelFailed("删除活动失败",false),

    ActivityStatusSuccess("切换活动状态成功",true),
    ActivityStatusFailed("切换活动状态失败",false),

    ActivityOpenDelFailed("开启状态的活动，不能删除",false),

    JoinSuccess("活动报名成功",true),
    JoinFailed("活动报名失败",false),

    JoinDraftSuccess("活动报名保存成功",true),
    JoinDraftFailed("活动报名保存失败",false),

    JoinDelSuccess("活动报名删除成功",true),
    JoinDelFailed("活动报名删除失败",false),

    JoinForCloseFailed("活动已关闭不能报名",false),
    JoinDelForCloseFailed("活动已关闭不能删除",false),

    JoinAlreadyFailed("此用户已报名",false),

    AgeAddFailed("新增年龄范围失败",false),
    AgeAddSuccess("新增年龄范围成功",true),

    AgeDelFailed("删除年龄范围失败",false),
    AgeDelSuccess("删除年龄范围成功",true),

    LoginJobNumNotUerFailed("登陆账号不存在",false),
    LoginPasswordFailed("密码错误",false),
    LoginCantFailed("该用户无登陆权限",false),
    LoginSuccess("登陆成功",true),

    RoleNoAccess("此用户无权限",false),

    pwdUpdateSuccess("密码修改成功",true),

    FileUploadEmptyFailed("图片上传为空",false),
    FileUploadFailed("图片上传失败",false),
    FileUploadSuccess("图片上传成功",true),

    EmailUpdFailed("邮箱修改失败",false),
    EmailUpdSuccess("邮箱修改成功",true),

    EmailSendFailed("发送邮件失败",false),
    EmailSendForVerifyFailed("此用户已验证邮箱，请刷新页面",false),
    EmailSendSuccess("发送邮件成功，请查收邮件",true),

    JobNumRetPwdFailed("工号不存在",false),
    JobNumRetPwdSuccess("工号存在",true),

    EmailRetPwdFailed("邮箱与工号不一致，或邮箱未验证",false),
    EmailRetPwdSuccess("邮箱与工号一致",true);

    private String message;
    private boolean codeBool;

    ExceptionMsg(String message, boolean codeBool) {
        this.message = message;
        this.codeBool = codeBool;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCodeBool() {
        return codeBool;
    }


}
