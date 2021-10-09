package by.godevelopment.rsschool2021_android_task_5.ui.detail

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.godevelopment.rsschool2021_android_task_5.CatApp
import by.godevelopment.rsschool2021_android_task_5.R
import by.godevelopment.rsschool2021_android_task_5.databinding.DetailFragmentBinding
import by.godevelopment.rsschool2021_android_task_5.model.Cat
import by.godevelopment.rsschool2021_android_task_5.ui.utils.showSnackBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var layout: View

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
                downloadImage()
            } else {
                Log.i("Permission: ", "Denied")
                Snackbar.make(
                    binding.root,
                    "Permission: Denied",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }

    private val viewModel: DetailViewModel by viewModels {
        CatViewModelFactory((activity?.application as CatApp).catDownloadCase)
    }

    private val cat: Cat
        get() = requireArguments().getSerializable(BUNDLE_KEY) as Cat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        layout = binding.detailLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderToolbar()
        renderUI()
        setupClick()
    }

    private fun renderToolbar() {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.subtitle = "Pagination list"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    private fun renderUI() {
        binding.nameDetail.text = ("Cat: ${cat.id} = ${cat.width}x${cat.height}").toString()
        Glide.with(binding.root)
            .load(cat.url)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.drawable.image_not_loaded)
            .placeholder(R.drawable.image)
            .into(binding.imageDetail)
    }

    private fun setupClick() {
        binding.buttonSave.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    layout.showSnackBar(
                        it,
                        getString(R.string.permission_granted),
                        Snackbar.LENGTH_SHORT,
                        null
                    ) {}
                    downloadImage()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) -> {
                    layout.showSnackBar(
                        it,
                        getString(R.string.permission_required),
                        Snackbar.LENGTH_SHORT,
                        getString(R.string.ok)
                    ) {
                        requestPermissionLauncher.launch(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    }
                }
                else -> {
                    requestPermissionLauncher.launch(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }
            }
        }
    }

    private fun downloadImage() {
        val id = viewModel.downloadImage(cat)
        Snackbar.make(
            binding.root,
            "Download started. ID = $id",
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val BUNDLE_KEY = "CAT_KEY"
        fun newInstance(cat: Cat): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundleOf(BUNDLE_KEY to cat)
            return fragment
        }
    }
}
