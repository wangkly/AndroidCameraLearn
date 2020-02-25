package com.wangky.androidcameralearn;

import android.app.Activity;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class Camera1Activity extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout frameLayout;

    private Button takePicture;

    private Button mToggle;

    private Camera mCamera;

    private CameraSurfaceView cameraSurfaceView;

    private int currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera1);
        setTitle("camera1");

        mCamera = getCameraInstance();

        setCameraDisplayOrientation(this,currentCameraId,mCamera);

        frameLayout = findViewById(R.id.camera_frame_layout);
        cameraSurfaceView= new CameraSurfaceView(this,mCamera,currentCameraId);

        frameLayout.addView(cameraSurfaceView);

        takePicture = findViewById(R.id.takePicture);
        mToggle = findViewById(R.id.toggle);
        takePicture.setOnClickListener(this);
        mToggle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.takePicture:

                mCamera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {

                    }
                }, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {

                    }
                }, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {

                    }
                });

                break;


            case R.id.toggle:

                mCamera.release();

                if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){

                    currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                }else{

                    currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                }
                mCamera = Camera.open(currentCameraId);
                setCameraDisplayOrientation(this,currentCameraId,mCamera);

                cameraSurfaceView.refreshAfterToggleCamera(mCamera,currentCameraId);

                break;

             default:


               break;
        }

    }


    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.release();
    }


    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
}
