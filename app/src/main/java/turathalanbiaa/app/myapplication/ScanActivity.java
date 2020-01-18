package turathalanbiaa.app.myapplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import turathalanbiaa.app.myapplication.SharedPrefrencesSession.SessionManager;
import turathalanbiaa.app.myapplication.Model.SellMenuItem;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    //1 for menu, 2 for item
    Integer scanFor;
    String sellMenuId;


    BarcodeReader barcodeReader;
    ArrayList<SellMenuItem> menuItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        setContentView(R.layout.scan);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
        scanFor=getIntent().getIntExtra("ScanFor",0);



    }
    @Override
    public void onScanned(Barcode barcode) {
//        // single barcode scanned
        barcodeReader.playBeep();


        SessionManager session = new SessionManager(getApplicationContext());

        session.createBarcode(barcode.displayValue);
        finish();


    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {
        // multiple barcodes scanned
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        // barcode scanned from bitmap image
    }

    @Override
    public void onScanError(String s) {
        // scan error
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPermissionDenied() {
        // camera permission denied
    }
}
