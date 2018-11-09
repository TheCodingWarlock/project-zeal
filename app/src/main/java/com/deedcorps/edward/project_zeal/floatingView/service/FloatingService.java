package com.deedcorps.edward.project_zeal.floatingView.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.deedcorps.edward.project_zeal.R;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewListener;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;

public class FloatingService extends Service implements FloatingViewListener {
    private static final String TAG= FloatingService.class.getSimpleName();
    public static final String EXTRA_CUTOUT_SAFE_AREA = "cutout_safe_area";
    private static final int NOTIFICATION_ID= 9083150;

    /**
     * FLoating View Manager
     * */
    private FloatingViewManager floatingViewManager;

    /**
     *{@inheritDoc}
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (floatingViewManager != null) {
            return  START_STICKY;
        }

        final DisplayMetrics displayMetrics= new DisplayMetrics();
        final WindowManager windowManager= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        final LayoutInflater layoutInflater= LayoutInflater.from(this);
        final ImageView floatingView= (ImageView) layoutInflater.inflate(R.layout.widget_floating_head, null, false);

        floatingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, getString(R.string.floating_button_click_message));
            }
        });

        floatingViewManager = new FloatingViewManager(this, this);
        floatingViewManager.setFixedTrashIconImage(R.drawable.ic_trash_fixed);
        floatingViewManager.setActionTrashIconImage(R.drawable.ic_trash_action);
        floatingViewManager.setSafeInsetRect((Rect) intent.getParcelableExtra(EXTRA_CUTOUT_SAFE_AREA));

        final FloatingViewManager.Options options= new FloatingViewManager.Options();
        options.overMargin= (int) (16 * displayMetrics.density);
        floatingViewManager.addViewToWindow(floatingView, options);

        startForeground(NOTIFICATION_ID, createNotification(this));

        return START_REDELIVER_INTENT;
    }

    private Notification createNotification(Context context) {
        final NotificationCompat.Builder builder= new NotificationCompat.Builder()
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText("Demo: Active");
        builder.setContentText("Demo Running");
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MIN);
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE);

        return builder.build();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onFinishFloatingView() {
        stopSelf();
        Log.d(TAG, "Floatingview has been deleted");


    }

    @Override
    public void onTouchFinished(boolean isFinishing, int x, int y) {

    }
}
