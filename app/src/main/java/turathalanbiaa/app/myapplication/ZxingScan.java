package turathalanbiaa.app.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import turathalanbiaa.app.myapplication.SharedPrefrencesSession.SessionManager;


public class ZxingScan extends Activity implements ZXingScannerView.ResultHandler {

    Integer scanFor;
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

        scanFor=getIntent().getIntExtra("ScanFor",0);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here


        SessionManager session = new SessionManager(getApplicationContext());



        session.createBarcode(rawResult.getText());
        //if scanning fo menu id
        if(scanFor==1) {
            session.setScanfor("1");}
        //for item
        else
            session.setScanfor("2");


        finish();

        // If you would like to resume scanning, call this method below:
//        mScannerView.resumeCameraPreview(this);
    }
}




