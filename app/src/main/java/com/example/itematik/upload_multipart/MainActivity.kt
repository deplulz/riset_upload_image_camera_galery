package com.example.itematik.upload_multipart

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.ByteArrayOutputStream
import android.net.Uri.fromParts
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import android.provider.MediaStore.Images.Media.getBitmap
import android.support.v7.app.AlertDialog
import android.widget.AdapterView
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.listeners.IPickResult


class MainActivity : AppCompatActivity() {

    lateinit var btn: Button
    lateinit var imageView: ImageView
    private val GALLERY = 1
    private val upload_URL = "http://192.168.100.144/multipart_api/uploadfile.php"
    private var rQueue: RequestQueue? = null
    private val arraylist: ArrayList<HashMap<String, String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestMultiplePermissions()

        btn = findViewById(R.id.btn)
        imageView = findViewById(R.id.iv)

        btn.setOnClickListener {
//            val galleryIntent =
//                Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takePicture, 0)
//            startActivityForResult(galleryIntent, 0)
//            PickImageDialog.build(PickSetup()).show(this)

            showPictureDialog()
        }
    }

    fun showPictureDialog() {
        var pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action");
        var pictureDialogItems = arrayOf(
            "Gallery",
            "Camera"
        )
        pictureDialog.setItems(pictureDialogItems) { dialog, which ->
            when (which) {
                0 -> {
                    choosePhotoFromGallary()
                }
                1 -> {
                    takePhotoFromCamera()
                }
            }
        }
        pictureDialog.show();
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        upload_image().result_intent(
            this,
            imageView,
            upload_URL,
            rQueue,
            requestCode,
            resultCode,
            data,
            GALLERY,
            applicationContext
        )
    }

    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    private fun requestMultiplePermissions() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
//                        Toast.makeText(applicationContext, "All permissions are granted by user!", Toast.LENGTH_SHORT)
//                            .show()
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { Toast.makeText(applicationContext, "Some Error! ", Toast.LENGTH_SHORT).show() }
            .onSameThread()
            .check()
    }
}
