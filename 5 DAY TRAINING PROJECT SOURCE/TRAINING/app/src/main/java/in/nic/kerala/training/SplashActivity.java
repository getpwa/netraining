package in.nic.kerala.training;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent in = new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(in);


                finish();
            }
        }, 2*1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}




