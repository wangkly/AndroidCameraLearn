package com.wangky.androidcameralearn;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.security.Permission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button camera1;

    private Button camera2;

    private  final int  PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera1 = findViewById(R.id.camera1);
        camera2 = findViewById(R.id.camera2);


        camera1.setOnClickListener(this);
        camera2.setOnClickListener(this);


        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO},PERMISSION_REQUEST_CODE);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.camera1:

                Intent intent = new Intent(MainActivity.this,Camera1Activity.class);

                startActivity(intent);

                break;

            case R.id.camera2:

                Intent intent2 = new Intent(MainActivity.this,Camera2Activity.class);

                startActivity(intent2);

                break;


            default:

                break;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_REQUEST_CODE){


        }


    }
}
