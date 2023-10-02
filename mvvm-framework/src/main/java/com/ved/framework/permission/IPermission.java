package com.ved.framework.permission;

public interface IPermission {
    /**
     * 用户已经同意所有权限
     */
    void onGranted();

    /**
     * @param denied  false : 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
     *                true :  用户拒绝了该权限，并且选中『不再询问』  需要跳转设置页面打开权限
     */
    void onDenied(boolean denied);
}
