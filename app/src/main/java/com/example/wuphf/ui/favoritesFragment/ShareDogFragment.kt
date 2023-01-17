package com.example.wuphf.ui.favoritesFragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.wuphf.BuildConfig
import com.example.wuphf.databinding.FragmentShareDogBinding
import com.example.wuphf.ui.allDogsFragment.AllDogViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ShareDogFragment : Fragment() {

    private var _binding : FragmentShareDogBinding? = null
    private val binding get() = _binding!!

    private val favoritesViewModel : FavoritesViewModel by activityViewModels()
    private val allDogViewModel: AllDogViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareDogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRemoveButton()
        initShareButton()
        initImage()
    }

    private fun initRemoveButton() {
        binding.removeButton.setOnClickListener {
            allDogViewModel.remove(favoritesViewModel.extractedList[favoritesViewModel.selectedDog])
            activity?.onBackPressed()
        }
    }

    private fun initShareButton() {
        binding.shareButton.setOnClickListener {
            val bitmap = getBitmapFromView(binding.exportLayout)

            val filename = "whupf.png"
            saveImageExternal(bitmap!!, filename)
            val file = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)

            val uri = FileProvider.getUriForFile(
                Objects.requireNonNull(requireContext()),
                BuildConfig.APPLICATION_ID + ".provider", file)

            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/png"
            }
            startActivity(Intent.createChooser(intent, null))
        }
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveImageExternal(image: Bitmap, filename: String): Uri? {
        var uri: Uri? = null
        try {
            val file = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.close()
            uri = Uri.fromFile(file)
        } catch (e: IOException) {
            Log.d("Debug", "IOException while trying to write file for sharing: " + e.message)
        }
        return uri
    }

    private fun initImage() {
        val message = favoritesViewModel.extractedList[favoritesViewModel.selectedDog].message
        Glide.with(binding.dogImage.context)
            .load(message)
            .into(binding.dogImage)
    }
}