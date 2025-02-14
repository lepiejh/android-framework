package com.ved.framework.permission;

import android.content.pm.PackageManager;
import android.os.Build;

import com.tbruyelle.rxpermissions3.RxPermissions;

import java.util.Arrays;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import io.reactivex.rxjava3.functions.Consumer;

public class RxPermission {

    public static void requestPermission(final Object object, final IPermission iPermission, final String... permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (object instanceof FragmentActivity){
                if (hasAlwaysDeniedPermission((FragmentActivity) object, Arrays.asList(permission))){
                    iPermission.onGranted();
                    return;
                }
                final RxPermissions rxPermissions = new RxPermissions((FragmentActivity) object);
                rxPermissions.request(permission)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Throwable {
                                if (aBoolean) {
                                    iPermission.onGranted();
                                } else {
                                    rxPermissions.shouldShowRequestPermissionRationale((FragmentActivity) object,permission).subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean1) throws Throwable {
                                            iPermission.onDenied(!aBoolean1);
                                        }
                                    });
                                }
                            }
                        });
            }else if (object instanceof Fragment){
                if (hasAlwaysDeniedPermission(((Fragment)object).getActivity(), Arrays.asList(permission))){
                    iPermission.onGranted();
                    return;
                }
                final RxPermissions rxPermissions = new RxPermissions((Fragment)object);
                rxPermissions.request(permission)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Throwable {
                                if (aBoolean) {
                                    iPermission.onGranted();
                                } else {
                                    rxPermissions.shouldShowRequestPermissionRationale(((Fragment)object).getActivity(),permission).subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean1) throws Throwable {
                                            iPermission.onDenied(!aBoolean1);
                                        }
                                    });
                                }
                            }
                        });
            }
        }else {
            iPermission.onGranted();
        }
    }

    private static boolean hasAlwaysDeniedPermission(FragmentActivity activity, List<String> asList) {
        for (String permission : asList){
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }


}
