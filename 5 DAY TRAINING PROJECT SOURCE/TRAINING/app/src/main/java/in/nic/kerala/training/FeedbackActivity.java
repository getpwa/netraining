package in.nic.kerala.training;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class FeedbackActivity extends AppCompatActivity {
    EditText edt_mobno, edt_appno, edt_feed;
    Button btn_veri, btn_feed;
    Utils util;
    private ProgressDialog pDialog = null;
    LinearLayout line;

    RequestQueue requestQueue;
    String url;
    Map<String, String> params;
    String feed;
    String apNO;
    String mobno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        btn_veri = (Button) findViewById(R.id.btnsub);
        edt_appno = (EditText) findViewById(R.id.input_app);
        edt_mobno = (EditText) findViewById(R.id.input_mob);
        edt_feed = (EditText) findViewById(R.id.input_feed);
        btn_feed = (Button) findViewById(R.id.btnfeed);
        util = new Utils(FeedbackActivity.this);


        btn_veri.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                hidekeyboard();
                if (edt_appno.getText().toString().equals(" ") || edt_mobno.getText().toString().equals(" ")) {
                    util.alertSingleButton("Please Enter the details", null);
                } else {

                    apNO = edt_appno.getText().toString().trim();
                    mobno = edt_mobno.getText().toString().trim();


                    if (Utils.isOnline(FeedbackActivity.this)) {


                        pDialog = new ProgressDialog(FeedbackActivity.this);
                        pDialog.setMessage("Loading...");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        verify();


                    } else {

                        Toast.makeText(getApplicationContext(),
                                "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }


        });


        btn_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidekeyboard();

                if (edt_feed.getText().toString().equals(" ")) {
                    util.alertSingleButton("Please Enter feedback", null);
                } else {
                    feed = edt_feed.getText().toString();
                    apNO = edt_appno.getText().toString().trim();


                    try {


                        if (Utils.isOnline(FeedbackActivity.this)) {


                            pDialog = new ProgressDialog(FeedbackActivity.this);
                            pDialog.setMessage("Loading...");
                            pDialog.setCancelable(false);
                            pDialog.show();

                            save();


                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void hidekeyboard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    private void save() {

        url = "http://103.251.43.122/pgmobapp/service/mobileapp/savePgFeedback";

        requestQueue = Volley.newRequestQueue(this);

        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    String Output = (response.trim() == null) ? "submitted" : response.trim();
                    if (Output.equals("Feedback submitted Successfully")) {
                        pDialog.dismiss();
                        util.alertButton(Output, true);

                    } else {
                        pDialog.dismiss();
                        util.alertButton(Output, true);

                    }
                } catch (Exception e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }) {
            @Override

            public Map<String, String> getParams() {
                try {
                    params = new HashMap<>();
                    params.put("apNo", URLEncoder
                            .encode(apNO, "UTF-8"));
                    params.put("Feedback", feed);

                } catch (Exception e) {

                }
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    private void verify() {

        url = "http://103.251.43.122/pgmobapp/service/mobileapp/VerifyPgFeedback";
        requestQueue = Volley.newRequestQueue(this);

        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    String output = (response.trim() == null) ? "Submit" : response.trim();
                    System.out.println("output" + output);
                    if (output.equals("Success")) {
                        pDialog.dismiss();
                        util.alertButton("Now You Can Enter Your Feedback", false);

                        btn_veri.setEnabled(false);

                        edt_appno.setEnabled(false);
                        edt_mobno.setEnabled(false);
                        btn_feed.setEnabled(true);
                        //  line.setVisibility(View.VISIBLE);


                    } else {
                        pDialog.dismiss();
                        util.alertButton(output, true);

                    }
                } catch (Exception e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                    util.alertButton("Connection Error",true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                pDialog.dismiss();
                util.alertButton("Connection Error",true);
                e.printStackTrace();
            }
        }) {
            @Override

            public Map<String, String> getParams() {
                try {
                    params = new HashMap<>();
                    params.put("apNo", URLEncoder.encode(apNO, "UTF-8"));
                    params.put("mbNo", URLEncoder.encode(mobno, "UTF-8"));

                } catch (Exception e) {

                }
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }


}


