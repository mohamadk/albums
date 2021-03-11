package com.mohamadk.albums

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.items.ModelAbstractItem
import com.mikepenz.fastadapter.ui.items.ProgressItem
import com.mohamadk.albums.adapter.AlbumsItemFactory
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.databinding.FragmentAlbumsBinding
import com.mohamadk.app.ViewModelFactory
import com.mohamadk.app.di.AlbumsApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class AlbumsFragment : Fragment(R.layout.fragment_albums) {

    companion object {
        fun instance(): AlbumsFragment {
            return AlbumsFragment()
        }
    }

    private val albumsItemFactory = AlbumsItemFactory()
    private val itemAdapter: ModelAdapter<AlbumsModelWrapper<*>, ModelAbstractItem<*, *>> =
        ModelAdapter(albumsItemFactory)
    private val footerAdapter = ItemAdapter<ProgressItem>()
    private val fastAdapter: FastAdapter<AbstractItem<out RecyclerView.ViewHolder>> =
        FastAdapter.with(listOf(itemAdapter, footerAdapter))

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this,viewModelFactory)[AlbumsFragmentViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AlbumsApp.inject(this)
    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenStarted {
            viewModel.viewStateFlow.collect{
                submitResult(it)
            }
        }
        _binding?.let {
            it.errorView.retry.setOnClickListener {
                viewModel.retry()
            }
        }

        viewModel.viewCreated()

        return binding.root
    }

    private fun submitResult(viewState: ViewState) {
        with(binding) {
            loading.isVisible = viewState.showLoading
            errorView.errorViewMainLayout.isVisible = viewState.showError
            viewState.items?.let { items ->
                itemAdapter.set(items)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.albumsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = fastAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}