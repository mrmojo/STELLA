package com.stellago.stellago;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        Intent intent = getIntent();
        final Branch details = intent.getExtras().getParcelable("branchDetails");
        TextView message = (TextView)findViewById(R.id.notificationMessage);
        message.setText("Your reservation id is " + intent.getExtras().getString("reservationId"));

        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Reservation ID")
                        .setContentText("Your reservation id is " + intent.getExtras().getString("reservationId"))
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_ALL);
        Intent resultIntent = new Intent(this, ConfirmationPage.class);
        resultIntent.putExtra("branchDetails", details);
        resultIntent.putExtra("reservationId", intent.getExtras().getString("reservationId"));
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
