package com.deedcorps.edward.project_zeal.floatingView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import com.abangfadli.shotwatch.ScreenshotData;
import com.abangfadli.shotwatch.ShotWatch;
import com.deedcorps.edward.project_zeal.R;
import com.deedcorps.edward.project_zeal.floatingView.service.FloatingService;
import com.deedcorps.edward.project_zeal.mlservice.TextDetectorActivity;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class FloatingViewControlFragment extends Fragment {
    private static final String TAG = FloatingViewControlFragment.class.getSimpleName();
    private static final int CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE = 100;
    private static final int CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE = 101;

    private static final String SCREENSHOTS_DIR_NAME = "Screenshots";
    private static final String SCREENSHOT_FILE_NAME_TEMPLATE = "Screenshot_%s.png";
    private static final String SCREENSHOT_SHARE_SUBJECT_TEMPLATE = "Screenshot (%s)";
    private File mScreenshotDir;
    private String mImageFileName;
    private String mImageFilePath;
    private long mImageTime;
    public static Uri screenShotUri;

    private ShotWatch shotWatch;


    public FloatingViewControlFragment() {
        // Required empty public constructor
    }

    public static FloatingViewControlFragment newInstance() {
        final FloatingViewControlFragment fragment = new FloatingViewControlFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_floating_view_control, container, false);
        showFloatingView(getActivity(), true, false);
        //return Bitmap
//        Bitmap p= readFromFile();
        final ImageView imageView = rootView.findViewById(R.id.imgBitmap);
//        imageView.setImageBitmap(p);

        shotWatch = new ShotWatch(getActivity().getContentResolver(), new ShotWatch.Listener() {
            @Override
            public void onScreenShotTaken(ScreenshotData screenshotData) {
                //Path to image
//                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    Log.v(TAG, "Permission is granted");
//                    Uri uri = Uri.parse(screenshotData.getPath());
//                    imageView.setImageURI(uri);
////                    return true;
//                }else {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
//                }
                Uri screenShotUri = Uri.fromFile(new File(screenshotData.getPath()));
                screenshotData.getPath();
                imageView.setImageURI(screenShotUri);
                Intent textDetectorIntent = new Intent(getActivity(), TextDetectorActivity.class);
                textDetectorIntent.setData(screenShotUri);
                startActivity(textDetectorIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        shotWatch.register();
    }

    @Override
    public void onStart() {
        super.onStart();
        shotWatch.register();
    }

    @Override
    public void onStop() {
        super.onStop();
        shotWatch.register();
    }

    @Override
    public void onPause() {
        super.onPause();
        shotWatch.unregister();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE) {
            showFloatingView(getActivity(), false, false);
        } else if (requestCode == CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE) {
            showFloatingView(getActivity(), false, true);
        }
    }

    @SuppressLint("NewApi")
    private void showFloatingView(Context context, boolean isShowOverlayPermission, boolean isCustomFloatingView) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startFloatingViewService(getActivity(), isCustomFloatingView);
            return;
        }

        if (Settings.canDrawOverlays(context)) {
            startFloatingViewService(getActivity(), isCustomFloatingView);
            return;
        }

        if (isShowOverlayPermission) {
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            startActivityForResult(intent, CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }

    private static void startFloatingViewService(Activity activity, boolean isCustomFloatingView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (activity.getWindow().getAttributes().layoutInDisplayCutoutMode == WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER) {
                throw new RuntimeException(activity.getString(R.string.prompt_window_layout_not_set_to_never));
            }

            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                throw new RuntimeException(activity.getString(R.string.warning_do_not_set_to_landscape));
            }
        }

        final Class<? extends Service> service;
        final String key;

        service = FloatingService.class;
        key = FloatingService.EXTRA_CUTOUT_SAFE_AREA;
        final Intent intent = new Intent(activity, service);
        intent.putExtra(key, FloatingViewManager.findCutoutSafeArea(activity));
        ContextCompat.startForegroundService(activity, intent);


    }


    private Bitmap readFromFile() {
        mImageTime = System.currentTimeMillis();
        String imageDate = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(mImageTime));
        mImageFileName = String.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate);

        mScreenshotDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), SCREENSHOTS_DIR_NAME);
        mImageFilePath = new File(mScreenshotDir, mImageFileName).getAbsolutePath();
        Bitmap bitmap = null;
        if (!mImageFilePath.equals(null)) {
            Log.i(TAG, mImageFileName);
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(mImageFilePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            mScreenshotDir = new File(Environment.DIRECTORY_PICTURES, SCREENSHOTS_DIR_NAME);
            mImageFilePath = new File(mScreenshotDir, mImageFileName).getAbsolutePath();

            Log.i(TAG, mImageFileName);
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(mImageFilePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }


        return bitmap;


    }

    public Uri getScreenShotUri() {
        return screenShotUri;
    }


}
