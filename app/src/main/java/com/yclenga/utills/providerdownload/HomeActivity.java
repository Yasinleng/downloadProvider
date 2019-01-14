package com.yclenga.utills.providerdownload;

import android.*;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yclenga.utills.providerdownload.download.DownloadManager;
import com.yclenga.utills.providerdownload.download.provider.DownloadChangeObserver;
import com.yclenga.utills.providerdownload.download.provider.DownloadService;
import com.yclenga.utills.providerdownload.download.provider.Downloads;

import java.util.List;

public class HomeActivity extends PermissionActivity {
    DownloadManager mDownloadManager;

    private DownloadChangeObserver mObserver;

    private static final String TAG = "HomeActivity";

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            Log.i(TAG, "handleMessage: 实时监听"+Thread.currentThread().getName());
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDownloadManager = new DownloadManager(getContentResolver(),
                getPackageName());

        mObserver=new DownloadChangeObserver(handler);


        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
    }

    public void requestPermission() {
        requestRuntimePermission(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted(List<String> grantPermissions) {
//                String url = "http://sw.bos.baidu.com/sw-search-sp/software/04c18d29f5aea/2345Explorer_8.8.3.16721.exe";
                String url = "http://bokumedia.amway.com.cn/hkbokuvideo/Drive_1_Unboxing_noReg_HD.mp4";
                Uri srcUri = Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(srcUri);
                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, "/");
                request.setDescription("Just for test");
                long downloadid=mDownloadManager.enqueue(request);

                Log.i(TAG, "onClick: "+downloadid);
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {

                for (String permission : deniedPermissions){
                    Toast.makeText(HomeActivity.this,"拒绝了"+permission+"权限",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(Downloads.ALL_DOWNLOADS_CONTENT_URI,true,mObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    private void find(){
        mDownloadManager.query(new DownloadManager.Query().setFilterById());
    }
}
