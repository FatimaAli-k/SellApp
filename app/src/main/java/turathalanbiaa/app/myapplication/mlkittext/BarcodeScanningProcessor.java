package turathalanbiaa.app.myapplication.mlkittext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.IOException;
import java.util.List;

import turathalanbiaa.app.myapplication.SharedPrefrencesSession.SessionManager;
import turathalanbiaa.app.myapplication.mlkittext.utility.CameraImageGraphic;
import turathalanbiaa.app.myapplication.mlkittext.utility.FrameMetadata;
import turathalanbiaa.app.myapplication.mlkittext.utility.GraphicOverlay;

/**
 * Barcode Detector Demo.
 */
public class BarcodeScanningProcessor extends VisionProcessorBase<List<FirebaseVisionBarcode>> {


    private static final String TAG = "BarcodeScanProc";

    private final FirebaseVisionBarcodeDetector detector;
    public Context mContext;
    Integer scanFor;

    public BarcodeScanningProcessor(Context context, Integer scanFor) {
        // Note that if you know which format of barcode your app is dealing with, detection will be
        // faster to specify the supported barcode formats one by one, e.g.
        // new FirebaseVisionBarcodeDetectorOptions.Builder()
        //     .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
        //     .build();
        detector = FirebaseVision.getInstance().getVisionBarcodeDetector();
        this.mContext = context;
        this.scanFor=scanFor;
    }

    @Override
    public void stop() {
        try {
            detector.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception thrown while trying to close Barcode Detector: " + e);
        }
    }

    @Override
    protected Task<List<FirebaseVisionBarcode>> detectInImage(FirebaseVisionImage image) {
        return detector.detectInImage(image);
    }

    @Override
    protected void onSuccess(
            @Nullable Bitmap originalCameraImage,
            @NonNull List<FirebaseVisionBarcode> barcodes,
            @NonNull FrameMetadata frameMetadata,
            @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.clear();
        if (originalCameraImage != null) {
            CameraImageGraphic imageGraphic = new CameraImageGraphic(graphicOverlay, originalCameraImage);
            graphicOverlay.add(imageGraphic);
        }
        for (int i = 0; i < barcodes.size(); ++i) {
            FirebaseVisionBarcode barcode = barcodes.get(i);
//



 Log.d(TAG, "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwbarcode: " + barcode.getDisplayValue());

            if (!barcode.getRawValue().equals("") ) {
//                Intent intent = new Intent(mContext, testAct.class);
//
//                intent.putExtra("code", barcode.getDisplayValue());
//                mContext.startActivity(intent);

              SessionManager session=new SessionManager(mContext);
              session.createBarcode(barcode.getDisplayValue());
                if(scanFor==1) {
                    session.setScanfor("1");}
                //for item
                else{
                    session.setScanfor("2");}

                ((Activity)mContext).finish();

            }
            BarcodeGraphic barcodeGraphic = new BarcodeGraphic(graphicOverlay, barcode);
            graphicOverlay.add(barcodeGraphic);
        }
        graphicOverlay.postInvalidate();

    }


    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Barcode detection failed " + e);
    }
}
