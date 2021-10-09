package by.godevelopment.rsschool2021_android_task_5.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.godevelopment.rsschool2021_android_task_5.R
import by.godevelopment.rsschool2021_android_task_5.adapter.CatsAdapter
import by.godevelopment.rsschool2021_android_task_5.databinding.MainFragmentBinding
import by.godevelopment.rsschool2021_android_task_5.model.Cat
import by.godevelopment.rsschool2021_android_task_5.ui.detail.DetailFragment
import kotlinx.coroutines.flow.collectLatest

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding
    get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var catsAdapter: CatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        renderToolbar()
        renderUI()
    }

    private fun renderToolbar() {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.subtitle = "Pagination list"
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
        }
    }

    private fun initRecyclerView() {
        catsAdapter = CatsAdapter { cat -> myActionClick(cat) }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2) // LinearLayoutManager(context)
            adapter = catsAdapter
        }
    }

    private fun renderUI() {
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest { PagingData ->
                catsAdapter.submitData(PagingData)
                // TODO = "back save state"
                // viewModel.saveLastPagingData = PagingData
            }
        }
    }

    private fun myActionClick(cat: Cat) {
        val fragment = DetailFragment.newInstance(cat)
        parentFragmentManager.commit {
            setCustomAnimations(
                R.anim.card_flip_right_in,
                R.anim.card_flip_right_out,
                R.anim.card_flip_left_in,
                R.anim.card_flip_left_out
            )
            replace(R.id.container, fragment)
            addToBackStack(null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
