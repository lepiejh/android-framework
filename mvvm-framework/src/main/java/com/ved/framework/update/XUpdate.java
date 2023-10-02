package com.ved.framework.update;

import android.view.View;
import android.widget.TextView;

import com.ved.framework.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import constant.UiType;
import listener.OnBtnClickListener;
import listener.OnInitUiListener;
import listener.UpdateDownloadListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

public class XUpdate {

    private XUpdate() {
    }

    private final static class Inner {
        private final static XUpdate instance = new XUpdate();
    }

    public static XUpdate newInstance() {
        return Inner.instance;
    }

    /**
     * 版本更新
     * @param apkUrl            apk下载地址
     * @param updateTitle       更新页面的标题
     * @param updateContent     更新页面的内容部分
     * @param isForce           是否强制更新
     * @param customLayoutId    自定义更新UI（不需要设置为-1）
     *                          需要注意页面布局必须包含一下id:
     *                          tv_update_title,tv_update_content,btn_update_sure,btn_update_cancel
     * @param notifyImgRes      更新通知栏图标  （不需要设置为-1）
     * @param cancelClick       更新页面取消按钮的点击事件(不需要设置为null)
     * @param submitClick       更新页面确定按钮的点击事件(不需要设置为null)
     * @param updateListener    更新UI的特殊处理(不需要设置为null)
     */
    public void update(String apkUrl, final String updateTitle, final String updateContent, final boolean isForce, int customLayoutId, int notifyImgRes,boolean alwaysShowDownLoadDialog,
                       OnBtnClickListener cancelClick, OnBtnClickListener submitClick, final OnUpdateListener updateListener, final UpdateDownloadListener downloadListener) {
        UpdateConfig updateConfig = new UpdateConfig();
        updateConfig.setCheckWifi(true);
        updateConfig.setNeedCheckMd5(true);
        updateConfig.setForce(isForce);
        updateConfig.setAlwaysShowDownLoadDialog(alwaysShowDownLoadDialog);
        if (notifyImgRes != -1) updateConfig.setNotifyImgRes(notifyImgRes);

        UpdateAppUtils updateAppUtils = UpdateAppUtils.getInstance().apkUrl(apkUrl).updateTitle(updateTitle).
                updateContent(updateContent).updateConfig(updateConfig);

        UiConfig uiConfig = new UiConfig();
        if (customLayoutId == -1) {
            uiConfig.setUiType(UiType.PLENTIFUL);
        } else {
            uiConfig.setUiType(UiType.CUSTOM);
            uiConfig.setCustomLayoutId(customLayoutId);
            updateAppUtils.setOnInitUiListener(new OnInitUiListener() {
                @Override
                public void onInitUpdateUi(@Nullable View view, @NotNull UpdateConfig updateConfig1, @NotNull UiConfig uiConfig1) {
                    if (view != null) {
                        TextView title = view.findViewById(R.id.tv_update_title);
                        TextView tvUpdateContent = view.findViewById(R.id.tv_update_content);
                        title.setText(updateTitle);
                        tvUpdateContent.setText(updateContent);
                        view.findViewById(R.id.btn_update_cancel).setVisibility(isForce ? View.GONE : View.VISIBLE);
                        if (updateListener != null)
                            updateListener.onInitUpdateUi(view, updateConfig1, uiConfig1);
                    }
                }
            });
        }
        updateAppUtils.uiConfig(uiConfig);
        if (cancelClick != null) updateAppUtils.setCancelBtnClickListener(cancelClick);
        if (submitClick != null) updateAppUtils.setUpdateBtnClickListener(submitClick);
        if (downloadListener != null) {
            updateAppUtils.setUpdateDownloadListener(downloadListener);
        }
        updateAppUtils.update();
    }
}
