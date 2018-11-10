package com.deedcorps.edward.project_zeal.floatingView;

import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.deedcorps.edward.project_zeal.floatingView.service.ScreenshotService;

public class MainActivity extends AppCompatActivity {

    static final String EXTRA_RESULT_CODE = "resultCode";
    static final String EXTRA_RESULT_INTENT = "resultIntent";
    private static final int REQUEST_SCREENSHOT = 59706;
    private MediaProjectionManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        mgr = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

        startActivityForResult(mgr.createScreenCaptureIntent(),
                REQUEST_SCREENSHOT);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            final String channelId = getString(R.string.default_floatingview_channel_id);
//            final String channelName = getString(R.string.default_floatingview_channel_name);
//            final NotificationChannel defaultChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN);
//            final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            if (manager != null) {
//                manager.createNotificationChannel(defaultChannel);
//            }
//        }
//
//        if (savedInstanceState == null) {
//            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(R.id.container, FloatingViewControlFragment.newInstance());
//            ft.commit();
//        }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCREENSHOT) {
            if (resultCode == RESULT_OK) {
                Intent i =
                        new Intent(this, ScreenshotService.class)
                                .putExtra(EXTRA_RESULT_CODE, resultCode)
                                .putExtra(EXTRA_RESULT_INTENT, data);
                startService(i);
            }
        }

        finish();
    }


}