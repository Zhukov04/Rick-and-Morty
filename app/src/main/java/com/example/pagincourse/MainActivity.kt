package com.example.pagincourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pagincourse.adapter.RickMortyPagedAdapter
import com.example.pagincourse.databinding.ActivityMainBinding
import com.example.pagincourse.viewmodel.RickMortyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: RickMortyPagedAdapter
    private val viewModel: RickMortyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRv()
        loadingData()

    }

    private fun loadingData() {

        lifecycleScope.launch {
            viewModel.ListData.collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupRv() {
        mAdapter = RickMortyPagedAdapter()
        binding.recyclerView.apply {

            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )

            adapter = mAdapter
            setHasFixedSize(true)
        }
    }
}