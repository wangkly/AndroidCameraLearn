package com.wangky.androidcameralearn;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraSurfaceView extends SurfaceView  implements SurfaceHolder.Callback {


    private SurfaceHolder mHolder;

    private Camera mCamera;

    private int mCurrentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;


    public CameraSurfaceView(Context context,Camera camera,int currentCameraId) {
        super(context);
        mCamera = camera;
        mCurrentCameraId = currentCameraId;


       Camera.Parameters parameters = mCamera.getParameters();
       int count = Camera.getNumberOfCameras();


        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(1, info);



       if(parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_AUTO)){

           parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
       }

       mCamera.setParameters(parameters);

        mHolder = getHolder();
        mHolder.addCallback(this);
    }




    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if(mHolder == null){

            return;
        }

        try {
            mCamera.stopPreview();

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }







    public void refreshAfterToggleCamera(Camera camera,int currentCameraId){



        mCurrentCameraId = currentCameraId;
        mCamera.release();
        mCamera = camera;

        try {

        mCamera.stopPreview();
        mCamera.setPreviewDisplay(mHolder);
        mCamera.startPreview();
        }catch (Exception e){

            e.printStackTrace();
        }
    }


}
