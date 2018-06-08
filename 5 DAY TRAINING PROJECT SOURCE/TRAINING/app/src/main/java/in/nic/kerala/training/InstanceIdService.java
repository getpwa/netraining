        package in.nic.kerala.training;


        import android.util.Log;

        import com.google.firebase.iid.FirebaseInstanceId;
        import com.google.firebase.iid.FirebaseInstanceIdService;

         public class InstanceIdService extends FirebaseInstanceIdService {
            public InstanceIdService() {
                super();
            }

            @Override
            public void onTokenRefresh() {
                super.onTokenRefresh();

                String token = FirebaseInstanceId.getInstance().getToken();

                 Log.d("Firebase", "token "+ token);

                if(token!=null) {
                    if(!token.trim().isEmpty()) {
                        //store locally if u want
                           }
                }


                //sends this token to the server
                sendToServer(token);


            }

            private void sendToServer(String token) {

               //here you can write
                //logic to send this token
                //to your server
            }
        }