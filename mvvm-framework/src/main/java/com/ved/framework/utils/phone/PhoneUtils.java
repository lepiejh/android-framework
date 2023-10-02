package com.ved.framework.utils.phone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import com.ved.framework.utils.SDCardUtils;
import com.ved.framework.utils.ToastUtils;
import com.ved.framework.utils.Utils;

import java.lang.reflect.Method;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class PhoneUtils {
    public static void callPhone(String phoneNumber) {
        call(Intent.ACTION_CALL, "tel:", phoneNumber);
    }

    public static void sendSMS(String phoneNumber) {
        call(Intent.ACTION_SENDTO, "smsto:", phoneNumber);
    }

    private static void call(String actionCall, String s, String phoneNumber) {
        if (hasSimCard(Utils.getContext())) {
            Intent intent_call = new Intent(actionCall, Uri.parse(s + phoneNumber));
            intent_call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getContext().startActivity(intent_call);
        } else {
            ToastUtils.showLong("机内无卡，请确认后再操作！");
        }
    }

    public static boolean isMultiSim(Context context) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
            if (telecomManager != null) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
                List<PhoneAccountHandle> phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();
                result = phoneAccountHandleList.size() >= 2;
            }
        }
        return result;
    }

    public static void call(int id, String telNum,boolean isAuto){
        if (isAuto && isMultiSim(Utils.getContext())){
            callPhone(id,telNum);
        }else {
            callPhone(telNum);
        }
    }

    /**
     *
     * @param id   0即为卡1 ，1即为卡二
     * @param telNum
     */
    public static void callPhone(int id, String telNum) {
        try {
            TelecomManager telecomManager = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                telecomManager = (TelecomManager) Utils.getContext().getSystemService(Context.TELECOM_SERVICE);
            }
            if (telecomManager != null) {
                if (ActivityCompat.checkSelfPermission(Utils.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                List<PhoneAccountHandle> phoneAccountHandleList = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + telNum));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    intent.putExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandleList.get(id));
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getContext().startActivity(intent);
            }else {
                callPhone(telNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callPhone(telNum);
        }
    }

    /**
     * 判断是否包含SIM卡
     *
     * @return 状态
     */
    public static boolean hasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        result = simState == TelephonyManager.SIM_STATE_READY; // 没有SIM卡
        return result;
    }

    public static boolean testingSIM1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return testingSIM(0);
        } else {
            return SDCardUtils.isSDCardEnable();
        }
    }

    public static boolean testingSIM2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return testingSIM(1);
        } else {
            return SDCardUtils.isSDCardEnable();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private static boolean testingSIM(int cardId) {
        TelephonyManager mTelephonyManager = (TelephonyManager) Utils.getContext().getSystemService(Context.TELEPHONY_SERVICE);

        SubscriptionManager mSubscriptionManager = SubscriptionManager.from(Utils.getContext());
        if (ActivityCompat.checkSelfPermission(Utils.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        SubscriptionInfo sub0 = mSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(cardId);
        if (null != sub0) {
            //如果不为空 说明sim卡1存在
    /*
        sub0.getSubscriptionId() 获取sim卡1的 subId
    */
            Sim1SignalStrengthsListener mSim1SignalStrengthsListener = new Sim1SignalStrengthsListener(sub0.getSubscriptionId());
            //开始监听
            if (mTelephonyManager != null) {
                mTelephonyManager.listen(mSim1SignalStrengthsListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            }
        }

        boolean isSimCardExist = false;
        try {
            Method method = TelephonyManager.class.getMethod("getSimState", int.class);
            int simState = (Integer) method.invoke(mTelephonyManager, new Object[]{0});
            if (TelephonyManager.SIM_STATE_READY == simState) {
                isSimCardExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSimCardExist;
    }
}
