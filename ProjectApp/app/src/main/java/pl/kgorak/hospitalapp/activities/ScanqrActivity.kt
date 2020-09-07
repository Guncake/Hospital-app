package pl.kgorak.hospitalapp.activities

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_scan_q_r.*
import pl.kgorak.hospitalapp.data.DataObj
import pl.kgorak.hospitalapp.R
import java.lang.Exception

class ScanqrActivity : AppCompatActivity() {

    private val requestCameraPermission = 1001

    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_q_r)
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            askForCameraPermission()
        }else {
            setupControls()
        }
    }

    private fun setupControls (){
        detector = BarcodeDetector.Builder(this).build()
        cameraSource = CameraSource.Builder(this, detector)
            .setAutoFocusEnabled(true)
            .build()
        surfaceView_qr.holder.addCallback(surfaceCallBack)
        detector.setProcessor(processor)
    }

    private fun askForCameraPermission(){
        ActivityCompat.requestPermissions(this@ScanqrActivity, arrayOf(android.Manifest.permission.CAMERA), requestCameraPermission)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == requestCameraPermission && grantResults.isNotEmpty()){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            }else {
                Toast.makeText(applicationContext, "permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val surfaceCallBack = object: SurfaceHolder.Callback {
        override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
            try{
                cameraSource.start(surfaceHolder)
            }catch(exception: Exception){
                Toast.makeText(applicationContext, "something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            cameraSource.stop()
        }
    }

    private val processor = object : Detector.Processor<Barcode>{

        override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
            if(detections != null && detections.detectedItems.isNotEmpty()){
                val qrCodes: SparseArray<Barcode> = detections.detectedItems
                val code = qrCodes.valueAt(0)
                qr_text.text = code.displayValue
                DataObj.setQRcode(code.displayValue.toString())
                finish()

            }else {
                qr_text.text = ""

            }
        }
        override fun release() {
        }
    }
}
