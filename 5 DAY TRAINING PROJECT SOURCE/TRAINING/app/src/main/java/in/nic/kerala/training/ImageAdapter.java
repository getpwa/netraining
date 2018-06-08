package in.nic.kerala.training;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


class ImageAdapter extends BaseAdapter {

    private Context context;
    private int [] imageId={R.drawable.search,R.drawable.feedback,R.drawable.fileupload,R.drawable.map,R.drawable.notification,R.drawable.sql,R.drawable.ic_menu_camera};
    private String[]mobileValues=new String[]{"Search","Feedback","File Upload","Map","Notification","SQL","Camera"};
    // Constructor
    public ImageAdapter(Context c){
        this.context = c;

    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return mobileValues[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

// get layout from mobile.xml
            gridView = inflater.inflate(R.layout.layout_custom, null);

// set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(mobileValues[position]);

// set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            imageView.setImageResource(imageId[position]);
            gridView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    if(position==0)
                    {
                        Intent in1 = new Intent(context, SearchActivity.class);
                        context.startActivity(in1);

                    }
                    else if(position==1){
                        Intent in2 = new Intent(context, FeedbackActivity.class);
                        context.startActivity(in2);

                    }
                    else if(position==2){
                        Intent in2 = new Intent(context, FileUploadActivity.class);
                        context.startActivity(in2);

                    }



                   else if(position==3){
                        Intent in2 = new Intent(context, MapsActivity.class);
                        context.startActivity(in2);

                    }
                    else if(position==4){
                        Intent in4 = new Intent(context, NotificationActivity.class);
                        context.startActivity(in4);

                    }
                    else if(position==5){
                        Intent in5 = new Intent(context, SqlActivity.class);
                        context.startActivity(in5);
                    }
                    else
                    {
                        Intent in6 = new Intent(context, camera.class);
                        context.startActivity(in6);
                    }

                }
            });

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

}


