package turathalanbiaa.app.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import turathalanbiaa.app.myapplication.AlertDialogManager;
import turathalanbiaa.app.myapplication.SharedPrefrencesSession.SessionManager;
import turathalanbiaa.app.myapplication.blutooth.Main_Activity;
import turathalanbiaa.app.myapplication.volley.AppController;

public class LoginActivity extends Activity {

    // Email, password edittext
    EditText txtUsername, txtPassword;

    // json object response url
    //private String urlJsonObj = "https://jsonblob.com/api/48637412-34ba-11ea-ad35-07e513ecf69d";
    //real device
    private String urlJsonObj = "http://192.168.9.107:8000/api/user";
    //emu
//    private String urlJsonObj = "http://10.0.2.2:8000/api/user";

    private String jsonResponseName,jsonResponseId;
    private TextView txtResponse;
    private static String TAG = LoginActivity.class.getSimpleName();
    // Progress dialog
//    private ProgressDialog pDialog;

    // login button
    Button btnLogin;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
//        txtPassword = (EditText) findViewById(R.id.txtPassword);

        if(session.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), Main_Activity.class);
            startActivity(intent);
        }
//        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
               final String username = txtUsername.getText().toString();
//                String password = txtPassword.getText().toString();

                // Check if username, password is filled
                if(username.trim().length() > 0 ){

                    userLogin();

//                    session.createLoginSession("temp","1");
                        Intent i = new Intent(getApplicationContext(), Main_Activity.class);

                        startActivity(i);
                        finish();

                    }
                else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username ", false);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {



    }

    private void userLogin() {
        Map<String, String> params = new HashMap<>();
        params.put("secret_word", txtUsername.getText().toString());
//        showpDialog();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                urlJsonObj, new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("name");
                    String id=response.getString("id");
//

                    jsonResponseName = "";
                    jsonResponseName += name;
                    jsonResponseId="";
                    jsonResponseId+= id;
//                    jsonResponseName += "Email: " + email + "\n\n";
//                    jsonResponseName += "Home: " + home + "\n\n";
//                    jsonResponseName += "Mobile: " + mobile + "\n\n";


                    session.createLoginSession(jsonResponseName,jsonResponseId);

//                    Toast.makeText(getApplicationContext(),
//                            " "+ response.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
//                hidepDialog();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
//                hidepDialog();



            }

        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

//    private void showpDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hidepDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
}
