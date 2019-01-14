package com.yclenga.utills.providerdownload;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by yclenga on 2017/8/2.
 * 邮箱:  yclenga@isoftstone.com
 */

public class PermissionActivity extends AppCompatActivity {

    private PermissionListener mListener;


    public void requestRuntimePermission(String[] permissions,PermissionListener listener){

        mListener=listener;

        //当前没有获得权限的集合
        List<String> currentPermissions=new ArrayList<>();
        List<String> grantPermissions=new ArrayList<>();
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                currentPermissions.add(permission);
            }else {
                grantPermissions.add(permission);
            }
        }

        if (!currentPermissions.isEmpty()){
            ActivityCompat.requestPermissions(this,currentPermissions.toArray(new String[currentPermissions.size()]),1);
        }else {
            mListener.onGranted(grantPermissions);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    List<String> deniedPermission=new ArrayList<>();
                    List<String> grantPermissions=new ArrayList<>();
                    for (int i=0 ; i < grantResults.length ; i++ ){
                        int grantResult=grantResults[i];
                        String permission=permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED){
                            deniedPermission.add(permission);
                        }else {
                            grantPermissions.add(permission);
                        }
                    }

                    if (!grantPermissions.isEmpty()){
                        mListener.onGranted(grantPermissions);
                    }

                    if (!deniedPermission.isEmpty()){
                        mListener.onDenied(deniedPermission);
                    }

                }
                break;
            default:
                break;
        }
    }

    public interface PermissionListener {

        /**
         *
         * @param grantPermissions 授权时，允许的权限
         */
        void onGranted(List<String> grantPermissions);


        /**
         *
         * @param deniedPermissions  授权时，拒绝的权限
         */
        void onDenied(List<String> deniedPermissions);
    }
}
