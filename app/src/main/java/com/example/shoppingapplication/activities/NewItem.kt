package com.example.shoppingapplication.activities

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.shoppingapplication.BuildConfig
import com.example.shoppingapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.image_picker.*
import kotlinx.android.synthetic.main.image_picker.view.*
import kotlinx.android.synthetic.main.image_picker.view.galleryBottomSheet
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NewItem : AppCompatActivity() {
    private lateinit var edit_name:EditText
    private lateinit var edit_quantity:EditText

    private lateinit var photoURL:String

    private val REQUEST_TAKE_PHOTO = 1
    private val REQUEST_GALLERY_PHOTO = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)

        edit_name = findViewById(R.id.editName)
        edit_quantity=findViewById(R.id.editQuantity)

        val button =findViewById<Button>(R.id.button_save)
        val addImage =findViewById<Button>(R.id.addImage)
        button.setOnClickListener{
            val replyIntent = Intent()
            if(TextUtils.isEmpty(edit_name.text)){
                setResult(Activity.RESULT_CANCELED,replyIntent)
            }
            else{
                val layout=findViewById<View>(R.id.addNewItem)
                val name=  edit_name.text.toString()
                val quantity=edit_quantity.text.toString()
                replyIntent.putExtra("name",name)
                replyIntent.putExtra("quantity",quantity)
                replyIntent.putExtra("url",photoURL)
                setResult(Activity.RESULT_OK,replyIntent)
            }
            finish()
        }

        addImage.setOnClickListener {
            customImagePicker()
        }


        val textView: TextView = findViewById(R.id.editDate)
        textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.text = sdf.format(cal.time)

        }

        textView.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }



    }





    private fun customImagePicker() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            bottomSheetDialog()

        } else {
            checkPermissionForExternalStorage()
        }
    }
    private fun bottomSheetDialog(){
        val dialog=BottomSheetDialog(this)
        val bottomSheet =layoutInflater.inflate(R.layout.image_picker,null)
         dialog.setContentView(bottomSheet)
        dialog.galleryBottomSheet.setOnClickListener {

        }
        dialog.CameraBottomSheet.setOnClickListener {
            Log.i("Camera","Inside Listener")
            takeCameraPhoto()
        }
        dialog.show()



    }

    private fun checkPermissionForExternalStorage(requestCode: Int = 1) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
        }
    }

    private fun takeCameraPhoto(){
        val takePictureIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(takePictureIntent.resolveActivity(packageManager)!=null){
            var imageFile : File? =null
            try {
                imageFile =createImageFile()
            }
            catch (ex:IOException){
                ex.printStackTrace()
            }
            if(imageFile!=null){
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                    BuildConfig.APPLICATION_ID + ".provider", imageFile))
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile():File{
        val timestamp=SimpleDateFormat("yyyyMMddmmss",Locale.US).format(Date())
        val imageFileName ="IMG_$timestamp"
        val storage =getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image =File.createTempFile(imageFileName,".jpg",storage)

        photoURL=image.absolutePath
        Log.i("Camera",photoURL)
        return image
    }
}
