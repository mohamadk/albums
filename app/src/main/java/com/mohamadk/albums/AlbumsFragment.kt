package com.mohamadk.albums

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class AlbumsFragment : Fragment(R.layout.fragment_albums) {

    companion object {
        fun instance(): AlbumsFragment {
            return AlbumsFragment()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}