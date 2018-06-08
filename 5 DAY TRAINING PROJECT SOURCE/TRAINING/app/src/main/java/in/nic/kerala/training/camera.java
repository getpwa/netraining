package in.nic.kerala.training;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class camera extends AppCompatActivity {
    private ImageView ivImage;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    Drawable drawable;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private String userChoosenTask;
    String ImagePath;
    Uri URI;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ivImage = (ImageView) findViewById(R.id.imageView2);

        ivImage.setImageResource(R.drawable.image);
    }






    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }


    public void selectImage(View view) {


        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(camera.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(camera.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        Bitmap thumbnail =null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                thumbnail = this.CompressResizeImage(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivImage.setImageBitmap(thumbnail);
        ivImage.setTag(thumbnail);


    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ivImage.setImageBitmap(thumbnail);

        ivImage.setTag(thumbnail);


    }

    public static byte[] ConvertDrawableToByteArray(Drawable drawableResource) {

        Bitmap imageBitmap = ((BitmapDrawable) drawableResource).getBitmap();
        ByteArrayOutputStream imageByteStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, imageByteStream);
        byte[] imageByteData = imageByteStream.toByteArray();
        return imageByteData;
    }

    public Bitmap CompressResizeImage(Bitmap bm)
    {
        int bmWidth = bm.getWidth();
        int bmHeight = bm.getHeight();
        int ivWidth = ivImage.getWidth();
        int ivHeight = ivImage.getHeight();


        int new_height = (int) Math.floor((double) bmHeight *( (double) ivWidth / (double) bmWidth));
        Bitmap newbitMap = Bitmap.createScaledBitmap(bm, ivWidth, new_height, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        newbitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        Bitmap bm1 = BitmapFactory.decodeByteArray(b, 0, b.length);

        return bm1;
    }


    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(8);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }


    //BUTTON CLICK
    public void saveImage(View view) {

        if (ActivityCompat.checkSelfPermission(camera.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(camera.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        }

        Bitmap bmImg = ((BitmapDrawable)ivImage.getDrawable()).getBitmap();


        File filename;
        try {
            String path1 = android.os.Environment.getExternalStorageDirectory()
                    .toString();
            Log.i("in save()", "after mkdir");
            String dirctioary = random();
            File file = new File(path1 + "/" + dirctioary);


            if (!file.exists())
                file.mkdirs();
            filename = new File(file.getAbsolutePath() + "/" + "tests"
                    + ".jpg");
            Log.i("in save()", "after file");
            FileOutputStream out = new FileOutputStream(filename);
            Log.i("in save()", "after outputstream");
            bmImg.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.i("in save()", "after outputstream closed");
            //File parent = filename.getParentFile();
            ContentValues image = getImageContent(filename);
            Uri result = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
            Toast.makeText(getApplicationContext(),
                    "File is Saved in  " + filename, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(camera.this, "Image Saved Successfully", Toast.LENGTH_LONG).show();

    }

    public ContentValues getImageContent(File parent) {
        ContentValues image = new ContentValues();
        image.put(MediaStore.Images.Media.TITLE, "TRAINING");
        image.put(MediaStore.Images.Media.DISPLAY_NAME, "tests");
        image.put(MediaStore.Images.Media.DESCRIPTION, "App Image");
        image.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        image.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        image.put(MediaStore.Images.Media.ORIENTATION, 0);
        image.put(MediaStore.Images.ImageColumns.BUCKET_ID, parent.toString()
                .toLowerCase().hashCode());
        image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName()
                .toLowerCase());
        image.put(MediaStore.Images.Media.SIZE, parent.length());
        image.put(MediaStore.Images.Media.DATA, parent.getAbsolutePath());
        return image;
    }
    public void RequestRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(camera.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

              Toast.makeText(camera.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(camera.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

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
