package in.nic.kerala.training;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private EditText inputapp, inputmob;
    private TextInputLayout inputlayoutapp, inputlayoutmob;
    private Button btnok;
    TextView txt_nam, txt_num, txt_sta;
    Utils util;
    String appno, mobno;
    ProgressDialog pdialog;
    private TableLayout tblelayout_docList;
    int certid;
    String no;
    static String url1;
    private   ProgressDialog DownloadDialog;
    protected String fileName = "";
    RequestQueue requestQueue;
    String url;
    Map<String, String> params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputapp = (EditText) findViewById(R.id.input_app);
        inputmob = (EditText) findViewById(R.id.input_mob);

        btnok = (Button) findViewById(R.id.btnsub);
        txt_num = (TextView) findViewById(R.id.appnum);
        txt_nam = (TextView) findViewById(R.id.appname);
        txt_sta = (TextView) findViewById(R.id.appsta);
        View includedLayout = findViewById(R.id.head);
        tblelayout_docList = (TableLayout) findViewById(R.id.tablelayout_doc_list);
        tblelayout_docList.setVisibility(View.GONE);

        TextView txttitle = (TextView) includedLayout.findViewById(R.id.txttitile);
        txttitle.setText("Search");
        util = new Utils(SearchActivity.this);

    }

    public void onSearchClick(View view) {

        appno = inputapp.getText().toString();
        mobno = inputmob.getText().toString();
        tblelayout_docList.setVisibility(View.GONE);

        if (inputapp.getText().toString().equals("")) {
            util.alertButton("Please Enter Application number", false);

        } else if (inputmob.getText().toString().equals("")) {
            util.alertButton("Please Enter Mobile Number", false);
        } else {

            if (Utils.isOnline(SearchActivity.this)) {
                pdialog = new ProgressDialog(SearchActivity.this);

                pdialog = new ProgressDialog(SearchActivity.this);
                pdialog.setMessage("Loading...");
                pdialog.setCancelable(false);
                pdialog.show();


                LoadService();

            } else {
                pdialog.dismiss();

                Toast.makeText(getApplicationContext(),
                        "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void LoadService() {


        url = "http://103.251.43.122/pgmobapp/service/mobileapp/getstatus?mobileNo=" + mobno + "&applNo=" + appno;
        Log.i("URL",url);
        requestQueue = Volley.newRequestQueue(this);
        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    pdialog.dismiss();
                    JSONObject json = new JSONObject(response);

                    if (json.has("Status Response")) {
                        JSONObject object1 = json.getJSONObject("Status Response");
                        JSONArray object11 = object1.getJSONArray("Status");

                        if (!(object11.equals(null) || object11.equals(""))) {

                            no = (String) object11.getJSONObject(0).get("Application No");
                            String apname = (String) object11.getJSONObject(1).get("Applicant Name");
                            String stat = (String) object11.getJSONObject(2).get("Status");
                            certid=object11.getJSONObject(3).getInt("CertId");

                            System.out.println("number is" + no);


                            LinearLayout table = (LinearLayout) findViewById(R.id.details);
                            table.setVisibility(View.VISIBLE);
                            txt_nam.setText(":" + apname);
                            txt_sta.setText(":" + stat);
                            txt_num.setText(":" + no);
                            inputapp.setText("");
                            inputmob.setText("");

                            if(stat.equals("Redressed/Disposed")){
                                tblelayout_docList.setVisibility(View.VISIBLE);

                            }

                        }
                    } else {

                        JSONObject st = json.getJSONObject("Result");
                        String re = (String) st.get("error");
                        AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.this);
                        alert.setTitle("Message");
                        alert.setMessage(re);
                        alert.setPositiveButton("OK", null);
                        alert.show();
                    }
                }

                catch (Exception e) {

                    pdialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }


}