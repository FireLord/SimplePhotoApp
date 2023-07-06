package com.firelord.growighassignment.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.FragmentUploadBinding

class UploadFragment : Fragment() {

    private lateinit var uploadBinding: FragmentUploadBinding
    private val PICK_IMAGE_REQUEST_CODE = 100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        uploadBinding = FragmentUploadBinding.inflate(inflater)
        return uploadBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uploadBinding.btSelectImage.setOnClickListener {
            openDocumentPicker()
        }

        uploadBinding.ivButtonBack.setOnClickListener {
            uploadBinding.root.findNavController().navigate(R.id.action_uploadFragment_to_welcomeFragment)
        }

        uploadBinding.ivButtonCross.setOnClickListener {
            Glide.with(this)
                .load(R.drawable.no_image)
                .into(uploadBinding.ivResultImage)
        }
    }

    private fun openDocumentPicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { selectedImageUri ->
                //uploadBinding.ivResultImage.setImageURI(selectedImageUri)
                Glide.with(this)
                    .load(selectedImageUri)
                    .into(uploadBinding.ivResultImage)
            }
        }
    }
}