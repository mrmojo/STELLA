package com.stellago.stellago;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        Intent intent = getIntent();
        final Branch details = intent.getExtras().getParcelable("branchDetails");
        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        Intent resultIntent = new Intent(getApplicationContext(), ConfirmationPage.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        // Sets an ID for the notification
        final int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        final NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Button checkDirections = (Button) findViewById(R.id.CheckDirections);
        checkDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toDirectionsPage = new Intent(getApplicationContext(), FindBranchPage.class);
                toDirectionsPage.putExtra("latitude",details.getBranchLatitude());
                toDirectionsPage.putExtra("longitude",details.getBranchLongitude());
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                startActivity(toDirectionsPage);
            }
        });
    }
}
