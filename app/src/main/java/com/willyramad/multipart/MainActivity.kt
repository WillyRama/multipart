package com.willyramad.multipart

import android.content.ContentResolver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.willyramad.multipart.databinding.ActivityMainBinding
import com.willyramad.multipart.vm.ViewModelUser
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {

    private var imageMultiPart: MultipartBody.Part? = null
    private var imageUri: Uri? = Uri.EMPTY
    private var imageFile: File? = null
    lateinit var viewModelUser : ViewModelUser

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelUser = ViewModelProvider(this).get(ViewModelUser::class.java)

        binding.addImage.setOnClickListener {
            openGallery()
        }
        binding.postUser.setOnClickListener {
            postDataUser()
        }
    }
    fun postDataUser(){
        val userName = binding.username.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val password = binding.password.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val kpassword =  binding.konfirmpass.text.toString()
        viewModelUser.addUserRegis.observe(this,{
            if (it !=null){
                Toast.makeText(this, "Register berhasil", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelUser.postUser(userName,password,imageMultiPart!!)
    }
    fun openGallery() {
        getContent.launch("image/*")
    }
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = this!!.contentResolver
                val type = contentResolver.getType(it)
                imageUri = it

                val fileNameimg = "${System.currentTimeMillis()}.png"
                binding.addImage.setImageURI(it)
                Toast.makeText(this, "$imageUri", Toast.LENGTH_SHORT).show()

                val tempFile = File.createTempFile("and1-", fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use    { result ->
                    inputstream?.copyTo(result)
                }
                val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                imageMultiPart = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
            }
        }
}