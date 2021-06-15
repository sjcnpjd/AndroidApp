package com.cookandroid.food

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cookandroid.food.databinding.ActivityPostBinding
import com.cookandroid.food.vm.MyViewModel
import com.github.nkzawa.emitter.Emitter

import com.github.nkzawa.socketio.client.IO

import com.github.nkzawa.socketio.client.Socket


import org.json.JSONObject
import java.io.*


class PostActivity : AppCompatActivity() {

    lateinit var mSocket: Socket;
    var message = ""


    private lateinit var binding: ActivityPostBinding

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }

    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val PERMISSIONS_REQUEST = 100

    // Request Code
    private val BUTTON1 = 100
    private var photoUri = ""
    private var output: StringBuilder = StringBuilder()
    var tag: String = ""
    var name: String = ""
    var name2: String = ""
    var name4: String = ""

    private var imageData: ByteArray? = null
    private val postURL: String = "http://18.117.244.158:5000/img"
    val image = System.currentTimeMillis()
    val imagename = image.toString()
    val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        val num: String? = intent.getStringExtra("num")

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSocket = IO.socket("http://18.117.244.158:5000")
        mSocket.connect()
        Log.i("Socket", "Connect")




        if (intent.hasExtra("name")) {
            name = intent.getStringExtra("name")!!
            name2 = name

        } else {

        }

        if (intent.hasExtra("name3")) {
            name4 = intent.getStringExtra("name3")!!

        } else {

        }


        checkPermissions(PERMISSIONS, PERMISSIONS_REQUEST)


        binding.gallerybtn.setOnClickListener {
            launchGallery()
        }

        binding.button2.setOnClickListener {
            uploadImage()

            binding.uploadtag.text = "이미지 분석중"


        }

        mSocket.on("result", setMessage)


        binding.btn.setOnClickListener {

            sendcall()
            binding.uploadtag.text =  message

        }

        binding.camerabtn.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, BUTTON1)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

            }

        }

        binding.locationbtn.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            intent.putExtra("name2", name2)
            startActivity(intent)

        }


    }


    private fun launchGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    fun sendcall() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://18.117.244.158/mainfeed"
        var editText = findViewById<View>(R.id.uploadet) as EditText
        var ratingValue = findViewById<View>(R.id.ratingBar) as RatingBar
        val text = editText.text.toString()
        var value = ratingValue.rating


        var mStringRequest =
            object : StringRequest(Method.POST, url, Response.Listener { response ->
                Toast.makeText(applicationContext, "Logged In Successfully", Toast.LENGTH_SHORT)
                    .show()



            }, Response.ErrorListener { error ->
                Log.i("This is the error", "Error :" + error.toString())
                Toast.makeText(applicationContext,
                    "Please make sure you enter correct password and username",
                    Toast.LENGTH_SHORT).show()
            }) {
                override fun getHeaders(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()


                    params["restaurant"] = "id"

                    return params
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val params = HashMap<String, String>()
                    val params2 = HashMap<String, Int>()
                    val json = JSONObject()

                    val calArray = message.split("_")
                    val foodname = calArray[0]
                    val cal = calArray[1].substring(0,calArray[1].length-3)


                    json.put("username", name4)
                    json.put("text", "$text\n#$foodname")
                    json.put("rating", value.toString())
                    json.put("calorie", cal)
                    json.put("img_path", "image$imagename")

                    val num: String? = intent.getStringExtra("num")
                    Log.d("num1", "2번째수$num")

                    params["id"] = num.toString()

                    val restaurant = JSONObject(params as Map<*, *>)
                    json.put("restaurant", restaurant)

                    return json.toString().toByteArray()


                }


            }
        queue.add(mStringRequest!!)
    }


    private fun uploadImage() {

        val request = object : VolleyFileUploadRequest(
            Method.POST,
            postURL,

            { response ->
                val success: ByteArray? = response.data



                Log.d("kj", "response is: $success")

            },
            { error ->
                // Handle error
                Log.d("kj", "ERROR: %s".format(error.toString()))

            }) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                var params = HashMap<String, FileDataPart>()


                params["imageFile"] = FileDataPart("image$imagename", imageData!!, "jpeg")
                Log.d("kj", "$params[\"imageFile\"]")
                return params
            }


        }





        Volley.newRequestQueue(this).add(request)


    }




            @Throws(IOException::class)
            private fun createImageData(uri: Uri) {
                val inputStream = contentResolver.openInputStream(uri)
                inputStream?.buffered()?.use {
                    imageData = it.readBytes()
                }
            }

            //
            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
                    val uri = data?.data


                    if (uri != null) {
                        binding.uploadiv.setImageURI(uri)
                        createImageData(uri)
                    }


                } else {

                    when (requestCode) {
                        BUTTON1 -> {
                            val imageBitmap = data?.extras?.get("data") as Bitmap


                            binding.uploadiv.setImageBitmap(imageBitmap)
                            val uri = getImageUri(this, imageBitmap)
                            createImageData(uri!!)


                        }
                    }


                }

                super.onActivityResult(requestCode, resultCode, data)



                fun receiveData(name: String) {
                    this@PostActivity.name = name
                }


            }

            fun getImageUri(inContext: Context?, inImage: Bitmap?): Uri? {
                val bytes = ByteArrayOutputStream()
                if (inImage != null) {
                    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                }
                val path = MediaStore.Images.Media.insertImage(inContext?.getContentResolver(),
                    inImage,
                    "image$imagename",
                    null)
                return Uri.parse(path)
            }



            private fun saveBitmapAsJPGFile(bitmap: Bitmap) {
                val path = File(filesDir, "image")
                if (!path.exists()) {
                    path.mkdirs()
                }
                val file = File(path, "image$imagename")
                var imageFile: OutputStream? = null
                try {
                    file.createNewFile()
                    imageFile = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageFile)
                    imageFile.close()
                    Toast.makeText(this, file.absolutePath, Toast.LENGTH_LONG).show()
                    photoUri = file.absolutePath
                } catch (e: Exception) {
                    null
                }
            }


            private fun checkPermissions(
                permissions: Array<String>,
                permissionsRequest: Int,
            ): Boolean {
                val permissionList: MutableList<String> = mutableListOf()
                for (permission in permissions) {
                    val result = ContextCompat.checkSelfPermission(this, permission)
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(permission)
                    }
                }
                if (permissionList.isNotEmpty()) {
                    ActivityCompat.requestPermissions(this,
                        permissionList.toTypedArray(),
                        PERMISSIONS_REQUEST)
                    return false
                }
                return true
            }

            override fun onRequestPermissionsResult(
                requestCode: Int,
                permissions: Array<out String>,
                grantResults: IntArray,
            ) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "권한 승인 부탁드립니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }


    var setMessage = Emitter.Listener { args ->
        val obj = JSONObject(args[0].toString())

        Log.d("chat", obj.toString())
        try {
           message = obj.getString("foodname")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}




