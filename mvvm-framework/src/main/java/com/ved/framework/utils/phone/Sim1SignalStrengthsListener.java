package com.ved.framework.utils.phone;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

import com.ved.framework.utils.ReflectUtil;

import java.lang.reflect.Method;

public class Sim1SignalStrengthsListener extends PhoneStateListener {

    public Sim1SignalStrengthsListener(int subId) {
        super();
        ReflectUtil.setFieldValue(this, "mSubId", subId);
    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        int level = getSignalStrengthsLevel(signalStrength);
    }

    private int getSignalStrengthsLevel(SignalStrength signalStrength) {
        int level = -1;
        try {
            Method levelMethod = SignalStrength.class.getDeclaredMethod("getLevel");
            level = (int) levelMethod.invoke(signalStrength);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return level;
    }

}
