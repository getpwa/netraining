package in.nic.kerala.training;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by asg4.dev13 on 4/17/2017.
 */

public class Utils {
    static Context context;
    private static Activity activity;

    private static AlertDialog dialog;
    public Utils(Context con) {
        context = con;
        activity = (Activity) con;

    }



    public void alertButton(final String data, final boolean closeactivity) {
        activity.runOnUiThread(new Runnable() {
            public void run() {

                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_alert_cust);
                TextView text = (TextView) dialog.findViewById(R.id.txt_data);
                text.setText(data);


                Button dialogButton = (Button) dialog
                        .findViewById(R.id.btnok);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (closeactivity == true) {
                            dialog.dismiss();

                            activity.finish();
                        } else {
                            dialog.dismiss();

                        }
                    }
                });

                dialog.show();

            }
        });

    }
    public void alertSingleButton(final String data, final EditText edt) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (dialog == null) {

                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.layout_alert);
                    dialog.setTitle("Message....");

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.txt_data);
                    text.setText(data);


                    Button dialogButton = (Button) dialog
                            .findViewById(R.id.btnok);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (edt != null) {
                                dialog.dismiss();
                                edt.requestFocus();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    });

                    dialog.show();

                }
            }
        });
    }




    public static boolean isOnline(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }


}
