package com.hakan.marvel.ui.features.main.favorite_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.hakan.marvel.R
import com.hakan.marvel.app.util.init
import com.hakan.marvel.ui.features.main.SharedViewModel
import com.hakan.marvel.ui.features.main.characters.CharacterAdapter
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.characters_fragment_collapsing.*
import javax.inject.Inject

class BookmarkFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.characters_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initToolbar()
        viewModel.getLocalCharacters()
        viewModel.mutableLocalCharacterLiveData.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                txt_empty_state.visibility = View.VISIBLE
                adapter.clear()
            }else{
                txt_empty_state.visibility = View.GONE
                adapter.addAll(it)
            }
        })
    }

    private fun initAdapter() {
        adapter = CharacterAdapter(viewModel)
        recycler_character.init(adapter)
    }

    private fun initToolbar() {
        txt_pinned_title.text = "Bookmarks"
        btn_favorites.visibility  = View.GONE
        img_logo.setImageResource(R.drawable.ic_bookmark)
        appbarlayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    txt_pinned_title.visibility = View.VISIBLE
                    isShow = true
                } else if (isShow) {
                    txt_pinned_title.visibility = View.GONE
                    isShow = false
                }
            }
        })
    }

}
