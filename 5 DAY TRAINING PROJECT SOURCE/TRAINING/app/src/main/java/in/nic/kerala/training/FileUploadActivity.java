package in.nic.kerala.training;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class FileUploadActivity extends AppCompatActivity {

    private int click = 0;
    Map<String, String> params;
    private Spinner attchtype1, attchtype2, attchtype3, attchtype4, attchtype5;

    private TableLayout tableinsert1, tableinsert2, tableinsert3, tableinsert4, tableinsert5;

    private TextView filePath, filePath1, filePath2, filePath3, filePath4, filePath5;
    private ProgressDialog pDialog = null;
    String encodedString;
    private LinearLayout linear;
    private static final int SELECT_FILE1 = 1;
    private static final int SELECT_FILE2 = 2;
    private static final int SELECT_FILE3 = 3;
    private static final int SELECT_FILE4 = 4;
    private static final int SELECT_FILE5 = 5;

    String url;

    RequestQueue requestQueue;

    private String selectedPath1 = "", selectedPath2 = "", selectedPath3 = "", selectedPath4 = "", selectedPath5 = "";
    private String selectedPath1s = "", selectedPath2s = "", selectedPath3s = "", selectedPath4s = "", selectedPath5s = "";

    Utils util;
    private TextInputLayout TextInputLayoutdocname1, TextInputLayoutdocname2, TextInputLayoutdocname3,
            TextInputLayoutdocname4, TextInputLayoutdocname5,
            TextInputLayoutattachremark1, TextInputLayoutattachremark2, TextInputLayoutattachremark3,
            TextInputLayoutattachremark4, TextInputLayoutattachremark5;

    private EditText applno, mblno;
    private String apNo, mbNo;
    private TextView applicationno, mobileno;
    private boolean flag;
    private String msg = "";
    private ProgressDialog progressDialog;
    private EditText slno1, slno2, slno3, slno4, slno5,
            attachremark1, attachremark2, attachremark3, attachremark4, attachremark5,
            docname1, docname2, docname3, docname4, docname5;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private String attchtypecode1 = "", attchtypecode2 = "", attchtypecode3 = "", attchtypecode4 = "", attchtypecode5 = "",
            attchtypecode1s = "", attchtypecode2s = "", attchtypecode3s = "", attchtypecode4s = "", attchtypecode5s = "";
    private String applNo, mobileNumber;
    private String slno1s, slno2s, slno3s, slno4s, slno5s,
            attachremark1s, attachremark2s, attachremark3s, attachremark4s, attachremark5s,
            docname1s, docname2s, docname3s, docname4s, docname5s;
    String attachrem, attachtypes, docnames, slnos;

    private String sprattchtype;
    private Button filesuploades;
    private Button addbutton, removebutton,
            browse1, browse2, browse3, browse4, browse5;
    ImageButton button_verify;

    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = FileUploadActivity.class.getSimpleName();
    private String selectedFilePath;
    private ProgressDialog dialog;
    private int serverResponseCode = 0;
    private File selectedFile;
    private HttpURLConnection connection;
    private DataOutputStream dataOutputStream;
    private String Filename = "";
    private String aplno, mobno;
    String Typename;

    List<String> Mylist = new ArrayList<String>();


    private TextInputLayout TextInputLayoutfbmblno, TextInputLayoutfbapplno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        TextInputLayoutfbapplno = (TextInputLayout) findViewById(R.id.TextInputLayoutfbapplno);
        util = new Utils(FileUploadActivity.this);
        TextInputLayoutfbmblno = (TextInputLayout) findViewById(R.id.TextInputLayoutfbmblno);



        slno1 = (EditText) findViewById(R.id.slno1);
        slno2 = (EditText) findViewById(R.id.slno2);
        slno3 = (EditText) findViewById(R.id.slno3);
        slno4 = (EditText) findViewById(R.id.slno4);
        slno5 = (EditText) findViewById(R.id.slno5);
        attachremark1 = (EditText) findViewById(R.id.attachremark1);
        attachremark2 = (EditText) findViewById(R.id.attachremark2);
        attachremark3 = (EditText) findViewById(R.id.attachremark3);
        attachremark4 = (EditText) findViewById(R.id.attachremark4);
        attachremark5 = (EditText) findViewById(R.id.attachremark5);
        docname1 = (EditText) findViewById(R.id.docname1);
        docname2 = (EditText) findViewById(R.id.docname2);
        docname3 = (EditText) findViewById(R.id.docname3);
        docname4 = (EditText) findViewById(R.id.docname4);
        docname5 = (EditText) findViewById(R.id.docname5);
        attchtype1 = (Spinner) findViewById(R.id.attchtype1);
        attchtype2 = (Spinner) findViewById(R.id.attchtype2);
        attchtype3 = (Spinner) findViewById(R.id.attchtype3);
        attchtype4 = (Spinner) findViewById(R.id.attchtype4);
        attchtype5 = (Spinner) findViewById(R.id.attchtype5);
        tableinsert1 = (TableLayout) findViewById(R.id.tableinsert1);
        tableinsert2 = (TableLayout) findViewById(R.id.tableinsert2);
        tableinsert3 = (TableLayout) findViewById(R.id.tableinsert3);
        tableinsert4 = (TableLayout) findViewById(R.id.tableinsert4);
        tableinsert5 = (TableLayout) findViewById(R.id.tableinsert5);
        button_verify = (ImageButton) findViewById(R.id.button_verify);
        linear = (LinearLayout) findViewById(R.id.linear);

        TextInputLayoutdocname1 = (TextInputLayout) findViewById(R.id.TextInputLayoutdocname1);
        TextInputLayoutdocname2 = (TextInputLayout) findViewById(R.id.TextInputLayoutdocname2);
        TextInputLayoutdocname3 = (TextInputLayout) findViewById(R.id.TextInputLayoutdocname3);
        TextInputLayoutdocname4 = (TextInputLayout) findViewById(R.id.TextInputLayoutdocname4);
        TextInputLayoutdocname5 = (TextInputLayout) findViewById(R.id.TextInputLayoutdocname5);
        TextInputLayoutattachremark1 = (TextInputLayout) findViewById(R.id.TextInputLayoutattachremark1);
        TextInputLayoutattachremark2 = (TextInputLayout) findViewById(R.id.TextInputLayoutattachremark2);
        TextInputLayoutattachremark3 = (TextInputLayout) findViewById(R.id.TextInputLayoutattachremark3);
        TextInputLayoutattachremark4 = (TextInputLayout) findViewById(R.id.TextInputLayoutattachremark4);
        TextInputLayoutattachremark5 = (TextInputLayout) findViewById(R.id.TextInputLayoutattachremark5);
        filePath1 = (TextView) findViewById(R.id.file_path1);
        filePath2 = (TextView) findViewById(R.id.file_path2);
        filePath3 = (TextView) findViewById(R.id.file_path3);
        filePath4 = (TextView) findViewById(R.id.file_path4);
        filePath5 = (TextView) findViewById(R.id.file_path5);
        applno = (EditText) findViewById(R.id.fbapplno);
        mblno = (EditText) findViewById(R.id.fbmblno);
        View buttonAdd = findViewById(R.id.addbutton);
        View browse1 = findViewById(R.id.browse1);
        View browse2 = findViewById(R.id.browse2);
        View browse3 = findViewById(R.id.browse3);
        View browse4 = findViewById(R.id.browse4);
        View browse5 = findViewById(R.id.browse5);
        View buttonRemove = findViewById(R.id.removebutton);
        filesuploades = (Button) findViewById(R.id.filesuploades);
        tableinsert1.setVisibility(View.VISIBLE);
        tableinsert2.setVisibility(View.GONE);
        tableinsert3.setVisibility(View.GONE);
        tableinsert4.setVisibility(View.GONE);
        tableinsert5.setVisibility(View.GONE);
        slno1.setText("1");
        slno1.setEnabled(false);
        slno2.setText("2");
        slno2.setEnabled(false);
        slno3.setText("3");
        slno3.setEnabled(false);
        slno4.setText("4");
        slno4.setEnabled(false);
        slno5.setText("5");
        slno5.setEnabled(false);


        attchtype1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(attchtype1.getWindowToken(), 0);
                return false;
            }
        });

        attchtype2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(attchtype2.getWindowToken(), 0);
                return false;
            }
        });

        attchtype3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(attchtype3.getWindowToken(), 0);
                return false;
            }
        });

        attchtype4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(attchtype4.getWindowToken(), 0);
                return false;
            }
        });

        attchtype5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(attchtype5.getWindowToken(), 0);
                return false;
            }
        });
        loadAttachment1();
        loadAttachment2();
        loadAttachment3();
        loadAttachment4();
        loadAttachment5();
        linear.setVisibility(View.INVISIBLE);

        browse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery(SELECT_FILE1);
            }
        });
        browse2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                openGallery(SELECT_FILE2);
            }
        });
        browse3.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                openGallery(SELECT_FILE3);
            }
        });
        browse4.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                openGallery(SELECT_FILE4);
            }
        });
        browse5.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                openGallery(SELECT_FILE5);
            }
        });

    }


    public void openGallery(int req_code) {
        if (ActivityCompat.checkSelfPermission(FileUploadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(FileUploadActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        }


        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file to upload "), req_code);


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ImageView mImageView=new ImageView();
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (requestCode == SELECT_FILE1) {
                selectedPath1 = FilePath.getPath(this, selectedImageUri);

                System.out.println("selectedPath1 " + selectedPath1);
                if (selectedPath1 != null) {
                    //String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    String checktpe = selectedPath1.substring(selectedPath1.lastIndexOf(".") + 1);
                    System.out.println("h " + checktpe);
                    if (attchtypecode1.equals("2")) {
                        if ((checktpe.equals("jpeg")) || (checktpe.equals("jpg"))) {
                            filePath1.setText(selectedPath1.substring(selectedPath1.lastIndexOf("/") + 1));
                        } else {
                            filePath1.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only jpeg/jpg Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            // Toast.makeText(getApplicationContext(), "Select only jpeg/jpg Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode1.equals("1")) {
                        if ((checktpe.equals("pdf"))) {
                            filePath1.setText(selectedPath1.substring(selectedPath1.lastIndexOf("/") + 1));
                        } else {
                            filePath1.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only pdf files");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //Toast.makeText(getApplicationContext(), "Select only pdf files" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode1.equals("3")) {
                        if ((checktpe.equals("mp4"))) {
                            filePath1.setText(selectedPath1.substring(selectedPath1.lastIndexOf("/") + 1));
                        } else {
                            filePath1.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only mp4 Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //Toast.makeText(getApplicationContext(), "Select only mp4 Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        filePath1.setText("");
                    }
                } else {

                    filePath1.setText("");
                }
            }
            if (requestCode == SELECT_FILE2) {
                selectedPath2 = FilePath.getPath(this, selectedImageUri);
                if (selectedPath2 != null) {
                    String checktpe = selectedPath2.substring(selectedPath2.lastIndexOf(".") + 1);
                    if (attchtypecode2.equals("2")) {
                        if ((checktpe.equals("jpeg")) || (checktpe.equals("jpg"))) {
                            filePath2.setText(selectedPath2.substring(selectedPath2.lastIndexOf("/") + 1));
                        } else {
                            filePath2.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only jpeg/jpg Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //Toast.makeText(getApplicationContext(), "Select only jpeg/jpg Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode2.equals("1")) {
                        if ((checktpe.equals("pdf"))) {
                            filePath2.setText(selectedPath2.substring(selectedPath2.lastIndexOf("/") + 1));
                        } else {
                            filePath2.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only pdf files");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            // Toast.makeText(getApplicationContext(), "Select only pdf files" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode2.equals("3")) {
                        if ((checktpe.equals("mp4"))) {
                            filePath2.setText(selectedPath2.substring(selectedPath2.lastIndexOf("/") + 1));
                        } else {
                            filePath2.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only mp4 Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //Toast.makeText(getApplicationContext(), "Select only mp4 Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        filePath2.setText("");
                    }
                } else {
                    filePath2.setText("");
                }
            }
            if (requestCode == SELECT_FILE3) {
                selectedPath3 = FilePath.getPath(this, selectedImageUri);
                if (selectedPath3 != null) {
                    String checktpe = selectedPath3.substring(selectedPath3.lastIndexOf(".") + 1);
                    if (attchtypecode3.equals("2")) {
                        if ((checktpe.equals("jpeg")) || (checktpe.equals("jpg"))) {
                            filePath3.setText(selectedPath3.substring(selectedPath3.lastIndexOf("/") + 1));
                        } else {
                            filePath3.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only jpeg/jpg Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            // Toast.makeText(getApplicationContext(), "Select only jpeg/jpg Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode3.equals("1")) {
                        if ((checktpe.equals("pdf"))) {
                            filePath3.setText(selectedPath3.substring(selectedPath3.lastIndexOf("/") + 1));
                        } else {
                            filePath3.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only pdf files");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //  Toast.makeText(getApplicationContext(), "Select only pdf files" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode3.equals("3")) {
                        if ((checktpe.equals("mp4"))) {
                            filePath3.setText(selectedPath3.substring(selectedPath3.lastIndexOf("/") + 1));
                        } else {
                            filePath3.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only mp4 Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            // Toast.makeText(getApplicationContext(), "Select only mp4 Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        filePath3.setText("");
                    }
                } else {
                    filePath3.setText("");
                }
            }
            if (requestCode == SELECT_FILE4) {
                selectedPath4 = FilePath.getPath(this, selectedImageUri);
                if (selectedPath4 != null) {
                    String checktpe = selectedPath4.substring(selectedPath4.lastIndexOf(".") + 1);
                    if (attchtypecode4.equals("2")) {
                        if ((checktpe.equals("jpeg")) || (checktpe.equals("jpg"))) {
                            filePath4.setText(selectedPath4.substring(selectedPath4.lastIndexOf("/") + 1));
                        } else {
                            filePath4.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only jpeg/jpg Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //Toast.makeText(getApplicationContext(), "Select only jpeg/jpg Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode4.equals("1")) {
                        if ((checktpe.equals("pdf"))) {
                            filePath4.setText(selectedPath4.substring(selectedPath4.lastIndexOf("/") + 1));
                        } else {
                            filePath4.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only pdf files");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //Toast.makeText(getApplicationContext(), "Select only pdf files" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode4.equals("3")) {
                        if ((checktpe.equals("mp4"))) {
                            filePath4.setText(selectedPath4.substring(selectedPath4.lastIndexOf("/") + 1));
                        } else {
                            filePath4.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only mp4 Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //Toast.makeText(getApplicationContext(), "Select only mp4 Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        filePath4.setText("");
                    }
                } else {
                    filePath4.setText("");
                }

            }
            if (requestCode == SELECT_FILE5) {
                selectedPath5 = FilePath.getPath(this, selectedImageUri);
                if (selectedPath5 != null) {
                    String checktpe = selectedPath5.substring(selectedPath5.lastIndexOf(".") + 1);
                    if (attchtypecode5.equals("2")) {
                        if ((checktpe.equals("jpeg")) || (checktpe.equals("jpg"))) {
                            filePath5.setText(selectedPath5.substring(selectedPath5.lastIndexOf("/") + 1));
                        } else {
                            filePath5.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only jpeg/jpg Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            // Toast.makeText(getApplicationContext(), "Select only jpeg/jpg Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode5.equals("1")) {
                        if ((checktpe.equals("pdf"))) {
                            filePath5.setText(selectedPath5.substring(selectedPath5.lastIndexOf("/") + 1));
                        } else {
                            filePath5.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only pdf files");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //Toast.makeText(getApplicationContext(), "Select only pdf files" ,Toast.LENGTH_SHORT).show();
                        }
                    } else if (attchtypecode5.equals("3")) {
                        if ((checktpe.equals("mp4"))) {
                            filePath5.setText(selectedPath5.substring(selectedPath5.lastIndexOf("/") + 1));
                        } else {
                            filePath5.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Select only mp4 Type");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                            //  Toast.makeText(getApplicationContext(), "Select only mp4 Type" ,Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        filePath5.setText("");
                    }
                } else {
                    filePath5.setText("");
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void FinalFileUpload(View view) {


        hidekeyboard();
        int rowNumCount = click + 1;

        aplno = applno.getText().toString().trim();
        mobno = mblno.getText().toString().trim();

        slno1s = "1";
        slno2s = "2";
        slno3s = "3";
        slno4s = "4";
        slno5s = "5";
        docname1s = docname1.getText().toString().trim();
        attachremark1s = attachremark1.getText().toString().trim();
        docname2s = docname2.getText().toString().trim();
        attachremark2s = attachremark2.getText().toString().trim();
        docname3s = docname3.getText().toString().trim();
        attachremark3s = attachremark3.getText().toString().trim();
        docname4s = docname4.getText().toString().trim();
        attachremark4s = attachremark4.getText().toString().trim();
        docname5s = docname5.getText().toString().trim();
        attachremark5s = attachremark5.getText().toString().trim();
        selectedPath1s = selectedPath1;
        selectedPath2s = selectedPath2;
        selectedPath3s = selectedPath3;
        selectedPath4s = selectedPath4;
        selectedPath5s = selectedPath5;
        attchtypecode1s = attchtypecode1;
        attchtypecode2s = attchtypecode2;
        attchtypecode3s = attchtypecode3;
        attchtypecode4s = attchtypecode4;
        attchtypecode5s = attchtypecode5;
        Pattern specialchar = Pattern.compile("[#;!£$%^&*}{@~`?<>.+_='|:\"\\[\\]\\\\]+");
        Pattern alphabets = Pattern.compile(".*[-#;!£$%^&*}{@~`?\n<>/+_(=),'|.:\"\\[\\]\\\\].*");
        Pattern digits = Pattern.compile(".*\\p{Digit}.*");
        boolean cancel = false;
        View focusView = null;


        if (!(TextUtils.isEmpty(attchtypecode1s))) {
            if (slno1s.length() > 2) {


                cancel = true;
            }
           /* if ((TextUtils.isEmpty(attchtypecode1s))) {
                cancel = true;

            }*/
            if (!(TextUtils.isEmpty(attchtypecode1s))) {
                if (!(Pattern.matches(".*\\p{Digit}.*", attchtypecode1s))) {
                    cancel = true;
                } else if (attchtypecode1s.length() > 10) {
                    cancel = true;
                }
            }
            if ((TextUtils.isEmpty(docname1s))) {
                TextInputLayoutdocname1.setError(getString(R.string.error_field_required));
                focusView = docname1;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(docname1s))) {
                if (Pattern.matches(".*[-#;!£$%^&*}{@~`?\n<>/+_(=),'|.:\"\\[\\]\\\\].*", docname1s)) {
                    if (!(alphabets.equals(docname1s))) {
                        focusView = docname1;
                        TextInputLayoutdocname1.setError(getString(R.string.error_field_required));
                        cancel = true;
                    } else if ((digits.equals(docname1s))) {
                        focusView = docname1;
                        cancel = true;
                        TextInputLayoutdocname1.setError(getString(R.string.error_field_required));
                    }
                }
            }

            if ((TextUtils.isEmpty(selectedPath1))) {
                /*Toast.makeText(getApplicationContext(),
                        "Select file to upload", Toast.LENGTH_SHORT).show();*/
                cancel = true;

            }
            if ((TextUtils.isEmpty(attachremark1s))) {
                TextInputLayoutattachremark1.setError(getString(R.string.error_field_required));
                focusView = attachremark1;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(attachremark1s))) {

                if (Pattern.matches("[#;!Â£$%^&*}{@~`?<>+_(=)'|:\"\\[\\]\\\\]+", attachremark1s)) {
                    TextInputLayoutattachremark1.setError(getString(R.string.error_field_required));
                    focusView = attachremark1;
                    cancel = true;
                } else if (Pattern.matches("[0-9]+", attachremark1s)) {
                    TextInputLayoutattachremark1.setError(getString(R.string.error_field_required));
                    focusView = attachremark1;
                    cancel = true;

                } else if (attachremark1.length() > 150) {
                    TextInputLayoutattachremark1.setError(getString(R.string.error_field_required));
                    focusView = attachremark1;
                    cancel = true;
                }
            }

        }

        if (!(TextUtils.isEmpty(attchtypecode2s))) {
            if (slno2s.length() > 2) {


                cancel = true;
            }
           /* if ((TextUtils.isEmpty(attchtypecode2s))) {
                cancel = true;

            }*/
            if (!(TextUtils.isEmpty(attchtypecode2s))) {
                if (!(Pattern.matches(".*\\p{Digit}.*", attchtypecode2s))) {
                    cancel = true;
                } else if (attchtypecode2s.length() > 10) {
                    cancel = true;
                }
            }
            if ((TextUtils.isEmpty(selectedPath2))) {
                /*Toast.makeText(getApplicationContext(),
                        "Select file to upload", Toast.LENGTH_SHORT).show();*/
                cancel = true;

            }

            if ((TextUtils.isEmpty(docname2s))) {
                TextInputLayoutdocname2.setError(getString(R.string.error_field_required));
                focusView = docname2;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(docname2s))) {
                if (Pattern.matches(".*[-#;!£$%^&*}{@~`?\n<>/+_(=),'|.:\"\\[\\]\\\\].*", docname2s)) {
                    if (!(alphabets.equals(docname2s))) {
                        TextInputLayoutdocname2.setError(getString(R.string.error_field_required));
                        focusView = docname2;
                        cancel = true;
                    } else if ((digits.equals(docname2s))) {
                        focusView = docname2;
                        TextInputLayoutdocname2.setError(getString(R.string.error_field_required));
                        cancel = true;

                    }
                }
            }
            if ((TextUtils.isEmpty(attachremark2s))) {
                TextInputLayoutattachremark2.setError(getString(R.string.error_field_required));
                focusView = attachremark2;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(attachremark2s))) {

                if (Pattern.matches("[#;!Â£$%^&*}{@~`?<>+_(=)'|:\"\\[\\]\\\\]+", attachremark2s)) {
                    TextInputLayoutattachremark2.setError(getString(R.string.error_field_required));
                    focusView = attachremark2;
                    cancel = true;
                } else if (Pattern.matches("[0-9]+", attachremark2s)) {
                    TextInputLayoutattachremark2.setError(getString(R.string.error_field_required));
                    focusView = attachremark2;
                    cancel = true;

                } else if (attachremark2.length() > 150) {
                    TextInputLayoutattachremark2.setError(getString(R.string.error_field_required));
                    focusView = attachremark2;
                    cancel = true;
                }
            }

        }

        if (!(TextUtils.isEmpty(attchtypecode3s))) {
            if (slno3s.length() > 2) {

                cancel = true;
            }
           /* if ((TextUtils.isEmpty(attchtypecode3s))) {
                cancel = true;

            }*/
            if (!(TextUtils.isEmpty(attchtypecode3s))) {
                if (!(Pattern.matches(".*\\p{Digit}.*", attchtypecode3s))) {
                    cancel = true;
                } else if (attchtypecode3s.length() > 10) {
                    cancel = true;
                }
            }
            if ((TextUtils.isEmpty(docname3s))) {
                TextInputLayoutdocname3.setError(getString(R.string.error_field_required));
                focusView = docname3;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(docname3s))) {
                if (Pattern.matches(".*[-#;!£$%^&*}{@~`?\n<>/+_(=),'|.:\"\\[\\]\\\\].*", docname3s)) {
                    if (!(alphabets.equals(docname3s))) {
                        focusView = docname3;
                        cancel = true;
                        TextInputLayoutdocname3.setError(getString(R.string.error_field_required));
                    } else if ((digits.equals(docname3s))) {
                        focusView = docname3;
                        cancel = true;
                        TextInputLayoutdocname3.setError(getString(R.string.error_field_required));

                    }
                }
            }

            if ((TextUtils.isEmpty(selectedPath3))) {
                /*Toast.makeText(getApplicationContext(),
                        "Select file to upload", Toast.LENGTH_SHORT).show();*/
                cancel = true;

            }
            if ((TextUtils.isEmpty(attachremark3s))) {
                TextInputLayoutattachremark3.setError(getString(R.string.error_field_required));
                focusView = attachremark3;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(attachremark3s))) {

                if (Pattern.matches("[#;!Â£$%^&*}{@~`?<>+_(=)'|:\"\\[\\]\\\\]+", attachremark3s)) {
                    TextInputLayoutattachremark3.setError(getString(R.string.error_field_required));
                    focusView = attachremark3;
                    cancel = true;
                } else if (Pattern.matches("[0-9]+", attachremark3s)) {
                    TextInputLayoutattachremark3.setError(getString(R.string.error_field_required));
                    focusView = attachremark3;
                    cancel = true;

                } else if (attachremark3.length() > 150) {
                    TextInputLayoutattachremark3.setError(getString(R.string.error_field_required));
                    focusView = attachremark3;
                    cancel = true;
                }
            }

        }
        if (!(TextUtils.isEmpty(attchtypecode4s))) {
            if (slno4s.length() > 2) {

                cancel = true;
            }
          /*  if ((TextUtils.isEmpty(attchtypecode4s))) {
                cancel = true;

            }*/
            if (!(TextUtils.isEmpty(attchtypecode4s))) {
                if (!(Pattern.matches(".*\\p{Digit}.*", attchtypecode4s))) {
                    cancel = true;
                } else if (attchtypecode4s.length() > 10) {
                    cancel = true;
                }
            }

            if ((TextUtils.isEmpty(selectedPath4))) {
               /* Toast.makeText(getApplicationContext(),
                        "Select file to upload", Toast.LENGTH_SHORT).show();*/
                cancel = true;

            }
            if ((TextUtils.isEmpty(docname4s))) {
                TextInputLayoutdocname4.setError(getString(R.string.error_field_required));
                focusView = docname4;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(docname4s))) {
                if (Pattern.matches(".*[-#;!£$%^&*}{@~`?\n<>/+_(=),'|.:\"\\[\\]\\\\].*", docname4s)) {
                    if (!(alphabets.equals(docname4s))) {
                        focusView = docname4;
                        cancel = true;
                        TextInputLayoutdocname4.setError(getString(R.string.error_field_required));
                    } else if ((digits.equals(docname4s))) {
                        focusView = docname4;
                        cancel = true;
                        TextInputLayoutdocname4.setError(getString(R.string.error_field_required));

                    }
                }
            }
            if ((TextUtils.isEmpty(attachremark4s))) {
                TextInputLayoutattachremark4.setError(getString(R.string.error_field_required));
                focusView = attachremark4;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(attachremark4s))) {

                if (Pattern.matches("[#;!Â£$%^&*}{@~`?<>+_(=)'|:\"\\[\\]\\\\]+", attachremark4s)) {
                    TextInputLayoutattachremark4.setError(getString(R.string.error_field_required));
                    focusView = attachremark4;
                    cancel = true;
                } else if (Pattern.matches("[0-9]+", attachremark4s)) {
                    TextInputLayoutattachremark4.setError(getString(R.string.error_field_required));
                    focusView = attachremark4;
                    cancel = true;

                } else if (attachremark4.length() > 150) {
                    TextInputLayoutattachremark4.setError(getString(R.string.error_field_required));
                    focusView = attachremark4;
                    cancel = true;
                }
            }

        }

        if (!(TextUtils.isEmpty(attchtypecode5s))) {

            if (slno5s.length() > 2) {

                cancel = true;
            }
           /* if ((TextUtils.isEmpty(attchtypecode5s))) {
                cancel = true;

            }*/
            if (!(TextUtils.isEmpty(attchtypecode5s))) {
                if (!(Pattern.matches(".*\\p{Digit}.*", attchtypecode5s))) {
                    cancel = true;
                } else if (attchtypecode5s.length() > 10) {
                    cancel = true;
                }
            }

            if ((TextUtils.isEmpty(selectedPath5))) {
             /*   Toast.makeText(getApplicationContext(),
                        "Select file to upload", Toast.LENGTH_SHORT).show();*/
                cancel = true;

            }
            if ((TextUtils.isEmpty(docname5s))) {
                TextInputLayoutdocname5.setError(getString(R.string.error_field_required));
                focusView = docname5;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(docname5s))) {
                if (Pattern.matches(".*[-#;!£$%^&*}{@~`?\n<>/+_(=),'|.:\"\\[\\]\\\\].*", docname5s)) {
                    if (!(alphabets.equals(docname5s))) {
                        focusView = docname5;
                        cancel = true;
                        TextInputLayoutdocname5.setError(getString(R.string.error_field_required));
                    } else if ((digits.equals(docname5s))) {
                        focusView = docname5;
                        cancel = true;
                        TextInputLayoutdocname5.setError(getString(R.string.error_field_required));
                    }
                }
            }
            if ((TextUtils.isEmpty(attachremark5s))) {
                TextInputLayoutattachremark5.setError(getString(R.string.error_field_required));
                focusView = attachremark5;
                cancel = true;

            }
            if (!(TextUtils.isEmpty(attachremark5s))) {

                if (Pattern.matches("[#;!Â£$%^&*}{@~`?<>+_(=)'|:\"\\[\\]\\\\]+", attachremark5s)) {
                    TextInputLayoutattachremark5.setError(getString(R.string.error_field_required));
                    focusView = attachremark5;
                    cancel = true;
                } else if (Pattern.matches("[0-9]+", attachremark5s)) {
                    TextInputLayoutattachremark5.setError(getString(R.string.error_field_required));
                    focusView = attachremark5;
                    cancel = true;

                } else if (attachremark5.length() > 150) {
                    TextInputLayoutattachremark5.setError(getString(R.string.error_field_required));
                    focusView = attachremark5;
                    cancel = true;
                }
            }

        }


        if (cancel == false) {
            if (!(selectedPath1s.equals("") || selectedPath1s.equals(null))) {

                uploadFile(selectedPath1s, docname1s, attachremark1s, slno1s, aplno, mobno, attchtypecode1s);
            }

            if (!(selectedPath2s.equals("") || selectedPath2s.equals(null))) {

                uploadFile(selectedPath2s, docname2s, attachremark2s, slno2s, aplno, mobno, attchtypecode2s);
            }

            if (!(selectedPath3s.equals("") || selectedPath3s.equals(null))) {

                uploadFile(selectedPath3s, docname3s, attachremark3s, slno3s, aplno, mobno, attchtypecode3s);
            }

            if (!(selectedPath4s.equals("") || selectedPath4s.equals(null))) {

                uploadFile(selectedPath4s, docname4s, attachremark4s, slno4s, aplno, mobno, attchtypecode4s);
            }

            if (!(selectedPath5s.equals("") || selectedPath5s.equals(null))) {

                uploadFile(selectedPath5s, docname5s, attachremark5s, slno5s, aplno, mobno, attchtypecode5s);
            }
        } else {
            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(FileUploadActivity.this);
            alert.setTitle("Message");
            alert.setMessage("Enter All Details");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    private void hidekeyboard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }


    public void uploadFile(String Filename, String docname, String attachremark, String slno, String aplno, String mobno, String attchtypecode) {
        boolean flag = false;
        InputStream inputStream;
        try {
            docnames = docname;
            attachrem = attachremark;
            slnos = slno;
            attachtypes = attchtypecode;
            inputStream = new FileInputStream(Filename);

            //You can get an inputStream using any IO API
            byte[] bytes;
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bytes = output.toByteArray();
            encodedString = Base64Utils.base64Encode(bytes);
            //Mylist.add(encodedString);
            int filesize = encodedString.length();
            if (attchtypecode.equals("1")) {
                int maxsize = 102400;
                if (filesize >= maxsize || filesize <= 0) {
                    flag = true;
                }
            }
            if (attchtypecode.equals("2")) {
                int maxsize = 204800;
                if (filesize >= maxsize || filesize <= 0) {
                    flag = true;
                }
            }
            if (attchtypecode.equals("3")) {
                int maxsize = 5242880;
                if (filesize >= maxsize || filesize <= 0) {
                    flag = true;
                }
            }
            if (flag == false) {
                int index = Filename.lastIndexOf("/");
                Typename = Filename.substring(index + 1);
                System.out.println("Typename " + Typename);


                if (Utils.isOnline(this)) {

                    pDialog = new ProgressDialog(FileUploadActivity.this);
                    pDialog.setMessage("Loading...");
                    pDialog.setCancelable(false);
                    pDialog.show();


                    upload();


                } else {

                    AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                    alert.setTitle("Message");
                    alert.setMessage("No Internet Connection!!!");
                    alert.setPositiveButton("OK", null);
                    alert.show();

                }

            } else {

                if (attchtypecode.equals("1")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                    alert.setTitle("Message");
                    alert.setMessage("Upload files upto 100KB");
                    alert.setPositiveButton("OK", null);
                    alert.show();

                }
                if (attchtypecode.equals("2")) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                    alert.setTitle("Message");
                    alert.setMessage("Upload image upto 200KB");
                    alert.setPositiveButton("OK", null);
                    alert.show();

                }
                if (attchtypecode.equals("3")) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(FileUploadActivity.this);
                    alert.setTitle("Message");
                    alert.setMessage("Upload video upto 5MB");
                    alert.setPositiveButton("OK", null);
                    alert.show();

                }

            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

    }

    private void upload() {
        url = "http://103.251.43.122/pgmobapp/service/mobileapp/fileupload";

            requestQueue = Volley.newRequestQueue(this);


        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    pDialog.dismiss();
                    String err = (response.trim() == null) ? "okk" : response.trim();
                    util.alertButton(err, true);
                } catch (Exception e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                pDialog.dismiss();
                util.alertButton("Connection Error", true);
            }
        }) {
            @Override

            public Map<String, String> getParams() {
                try {
                    params = new HashMap<>();
                    params.put("aplno", URLEncoder
                            .encode(aplno, "UTF-8"));
                    params.put("mobno", URLEncoder
                            .encode(mobno, "UTF-8"));
                    params.put("attachremark", URLEncoder
                            .encode(attachrem, "UTF-8"));

                    params.put("attchtypecode", URLEncoder
                            .encode(attachtypes, "UTF-8"));
                    params.put("docname", URLEncoder
                            .encode(docnames, "UTF-8"));
                    params.put("Typename", URLEncoder
                            .encode(Typename, "UTF-8"));
                    params.put("slno", URLEncoder
                            .encode(slnos, "UTF-8"));
                    params.put("encodedString", encodedString);


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


    String MobilePattern = "[0-9]{10}";

    public void onVerify(View view) {

        button_verify.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(button_verify.getWindowToken(), 0);
                return false;
            }
        });

        applNo = applno.getText().toString().trim();
        mobileNumber = mblno.getText().toString().trim();
        View focusView = null;
        flag = true;
        linear.setVisibility(View.INVISIBLE);


        if (TextUtils.isEmpty(applNo)) {
            flag = false;

            TextInputLayoutfbapplno.setError(getString(R.string.ErrorapplcationNo));

        } else {
            if (Pattern.matches(".*[-#;!£$%^&*}{@~`?\n<>/+_(=),'|.:\"\\[\\]\\\\].*", applNo)) {
                focusView = applno;
                flag = false;

                TextInputLayoutfbapplno.setError(getString(R.string.ErrorapplcationNo));

            } else if (!(Pattern.matches(".*\\p{Digit}.*", applNo))) {
                focusView = applno;
                flag = false;

                TextInputLayoutfbapplno.setError(getString(R.string.ErrorapplcationNo));

            } else if (applNo.length() > 6) {
                focusView = applno;
                flag = false;

                TextInputLayoutfbapplno.setError(getString(R.string.ErrorapplcationNo));

            } else {
                TextInputLayoutfbapplno.setError(null);
                // flag = true;
            }
        }


        if (TextUtils.isEmpty(mobileNumber)) {
            focusView = mblno;
            flag = false;

            TextInputLayoutfbmblno.setError(getString(R.string.ErrorMobileNumber));

        } else {
            if (Pattern.matches(".*[-#;!£$%^&*}{@~`?\n<>/+_(=),'|.:\"\\[\\]\\\\].*", mobileNumber)) {
                focusView = mblno;
                flag = false;

                TextInputLayoutfbmblno.setError(getString(R.string.ErrorMobileNumber));

            } else if (!(Pattern.matches(".*\\p{Digit}.*", mobileNumber))) {
                focusView = mblno;
                flag = false;

                TextInputLayoutfbmblno.setError(getString(R.string.ErrorMobileNumber));

            } else if (mobileNumber.length() > 11) {
                focusView = mblno;
                flag = false;

                TextInputLayoutfbmblno.setError(getString(R.string.ErrorMobileNumber));

            } else {
                TextInputLayoutfbmblno.setError(null);
                //flag = true;
            }
        }


        if (flag) {

            msg = "";

            if (Utils.isOnline(this)) {


                pDialog = new ProgressDialog(FileUploadActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.setCancelable(false);
                pDialog.show();
                LoadService();
            } else {

                Toast.makeText(getApplicationContext(),
                        "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            msg = "";

        }
    }


    private void LoadService() {

        url="http://103.251.43.122/pgmobapp/service/mobileapp/getstatus?mobileNo=" +
                mobileNumber +
                "&applNo=" +
                applNo;


            requestQueue = Volley.newRequestQueue(this);


        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    pDialog.dismiss();
                    JSONObject json = new JSONObject(response);

                    if (json.has("Status Response")) {
                        JSONObject object1 = json.getJSONObject("Status Response");
                        JSONArray object11 = object1.getJSONArray("Status");

                        if (!(object11.equals(null) || object11.equals(""))) {


                            String stat = (String) object11.getJSONObject(2).get("Status");

                            if (stat.equalsIgnoreCase("Processing")) {

                                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(FileUploadActivity.this);
                                alert.setTitle("Message");
                                alert.setMessage("Now You Can Upload Your Documents");
                                alert.setPositiveButton("OK", null);
                                alert.show();

                                linear.setVisibility(View.VISIBLE);
                            } else {
                                util.alertButton("Your application is under Processing.You can't upload Documents", true);
                            }

                        }
                    } else {

                        JSONObject st = json.getJSONObject("Result");
                        String re = (String) st.get("error");
                        util.alertButton(re, true);

                    }
                } catch (JSONException e) {
                    pDialog.dismiss();

                    util.alertSingleButton("Connection failed..Retry", null);

                }
            }

        }, new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                util.alertButton("Connection Error", true);

            }
        });


        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }


    public void addFileUpload(View view) {
        click++;
        if (click == 1) {
            tableinsert2.setVisibility(view.VISIBLE);
        }
        if (click == 2) {
            tableinsert3.setVisibility(view.VISIBLE);
        }
        if (click == 3) {
            tableinsert4.setVisibility(view.VISIBLE);
        }
        if (click == 4) {
            tableinsert5.setVisibility(view.VISIBLE);

        }
        if (click >= 5) {
            click = 0;
        }

    }

    public void removeFileUpload(View view) {

        if (click == 4) {
            tableinsert5.setVisibility(view.GONE);
            click--;
            slno5.setText("5");
            docname5.setText("");
            attachremark5.setText("");
            filePath5.setText("No File Selected");
            attchtype5.setSelection(0);

        } else if (click == 3) {
            tableinsert4.setVisibility(view.GONE);
            click--;
            slno4.setText("4");
            docname4.setText("");
            attachremark4.setText("");
            filePath4.setText("No File Selected");
            attchtype4.setSelection(0);
        } else if (click == 2) {
            tableinsert3.setVisibility(view.GONE);
            click--;
            slno3.setText("3");
            docname3.setText("");
            attachremark3.setText("");
            filePath3.setText("No File Selected");
            attchtype3.setSelection(0);

        } else if (click == 1) {
            tableinsert2.setVisibility(view.GONE);
            click--;
            slno2.setText("2");
            docname2.setText("");
            attachremark2.setText("");
            filePath2.setText("No File Selected");
            attchtype2.setSelection(0);
        }


    }

    public void clearFileUpload(View view) {
        slno5.setText("5");
        docname5.setText("");
        attachremark5.setText("");
        filePath5.setText("No File Selected");
        attchtype5.setSelection(0);

        slno4.setText("4");
        docname4.setText("");
        attachremark4.setText("");
        filePath4.setText("No File Selected");
        attchtype4.setSelection(0);

        slno3.setText("3");
        docname3.setText("");
        attachremark3.setText("");
        filePath3.setText("No File Selected");
        attchtype3.setSelection(0);

        slno2.setText("2");
        docname2.setText("");
        attachremark2.setText("");
        filePath2.setText("No File Selected");
        attchtype2.setSelection(0);

        slno1.setText("1");
        docname1.setText("");
        attachremark1.setText("");
        filePath1.setText("No File Selected");
        attchtype1.setSelection(0);
        tableinsert5.setVisibility(view.GONE);
        tableinsert4.setVisibility(view.GONE);
        tableinsert3.setVisibility(view.GONE);
        tableinsert2.setVisibility(view.GONE);


    }


    private void loadAttachment1() {
        ArrayAdapter<Attachment_enum> dataAdapter = new ArrayAdapter<Attachment_enum>(this,
                android.R.layout.simple_spinner_item, Attachment_enum.values());
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        attchtype1.setAdapter(dataAdapter);

        final int iCurrentSelection = -1;
        attchtype1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (iCurrentSelection != position) {

                    // System.out.println( ( (Attachment_enum) attchtype1.getSelectedItem() ).value());
                    attchtypecode1 = ((Attachment_enum) attchtype1.getSelectedItem()).value();
                    System.out.println(iCurrentSelection + " position" + attchtypecode1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void loadAttachment2() {
        ArrayAdapter<Attachment_enum> dataAdapter = new ArrayAdapter<Attachment_enum>(this,
                android.R.layout.simple_spinner_item, Attachment_enum.values());
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        attchtype2.setAdapter(dataAdapter);

        final int iCurrentSelection = -1;
        attchtype2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (iCurrentSelection != position) {

                    //  System.out.println( ( (Attachment_enum) attchtype2.getSelectedItem() ).value());
                    attchtypecode2 = ((Attachment_enum) attchtype2.getSelectedItem()).value();
                    System.out.println(iCurrentSelection + " position" + attchtypecode2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void loadAttachment3() {
        ArrayAdapter<Attachment_enum> dataAdapter = new ArrayAdapter<Attachment_enum>(this,
                android.R.layout.simple_spinner_item, Attachment_enum.values());
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        attchtype3.setAdapter(dataAdapter);

        final int iCurrentSelection = -1;
        attchtype3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (iCurrentSelection != position) {

                    //System.out.println( ( (Attachment_enum) attchtype3.getSelectedItem() ).value());
                    attchtypecode3 = ((Attachment_enum) attchtype3.getSelectedItem()).value();
                    System.out.println(iCurrentSelection + " position" + attchtypecode3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void loadAttachment4() {
        ArrayAdapter<Attachment_enum> dataAdapter = new ArrayAdapter<Attachment_enum>(this,
                android.R.layout.simple_spinner_item, Attachment_enum.values());
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        attchtype4.setAdapter(dataAdapter);

        final int iCurrentSelection = -1;
        attchtype4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (iCurrentSelection != position) {

                    //  System.out.println( ( (Attachment_enum) attchtype4.getSelectedItem() ).value());
                    attchtypecode4 = ((Attachment_enum) attchtype4.getSelectedItem()).value();
                    System.out.println(iCurrentSelection + " position" + attchtypecode4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void loadAttachment5() {
        ArrayAdapter<Attachment_enum> dataAdapter = new ArrayAdapter<Attachment_enum>(this,
                android.R.layout.simple_spinner_item, Attachment_enum.values());
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        attchtype5.setAdapter(dataAdapter);

        final int iCurrentSelection = -1;
        attchtype5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (iCurrentSelection != position) {

                    // System.out.println( ( (Attachment_enum) attchtype5.getSelectedItem() ).value());
                    attchtypecode5 = ((Attachment_enum) attchtype5.getSelectedItem()).value();
                    System.out.println(iCurrentSelection + " position" + attchtypecode5);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }


    public void RequestRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(FileUploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            //  Toast.makeText(reportJoin.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(FileUploadActivity.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    // Toast.makeText(reportJoin.this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    //  Toast.makeText(reportJoin.this,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

}
