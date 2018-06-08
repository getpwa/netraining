package in.nic.kerala.training;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MessageReceiver extends FirebaseMessagingService {
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 6579;

    public MessageReceiver() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        final String title = remoteMessage.getData().get("title");
        final String message = remoteMessage.getData().get("body");

        showNotifications(title, message);
    }

    private void showNotifications(String title, String msg) {
        Intent i = new Intent(this, DashboardActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE,
                i, PendingIntent.FLAG_UPDATE_CURRENT);




        NotificationManager notificationManager = (NotificationManager) this.getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("ID", "Sagara -Mobile App", importance);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(getApplicationContext(), notificationChannel.getId());
            builder.setContentIntent(pendingIntent);
        } else {

            builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setContentIntent(pendingIntent);
        }

        builder = builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
                .setContentTitle(title)
                .setTicker("Sagara -Mobile App")
                .setContentText(msg)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());



    }
}