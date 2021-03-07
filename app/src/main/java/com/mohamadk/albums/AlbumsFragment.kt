package com.mohamadk.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)

        return binding.root
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