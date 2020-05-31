package com.hakan.marvel.ui.features.main.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.hakan.marvel.R
import com.hakan.marvel.app.util.init
import com.hakan.marvel.data.model.character.Character
import com.hakan.marvel.domain.Resource
import com.hakan.marvel.ui.features.main.SharedViewModel
import com.hakan.marvel.ui.features.main.character_detail.CharacterDetailFragment
import com.hakan.marvel.ui.features.main.favorite_characters.BookmarkFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.characters_fragment.*
import kotlinx.android.synthetic.main.characters_fragment_collapsing.*
import timber.log.Timber

class CharactersFragment : Fragment()  {

    private val viewModel: SharedViewModel by activityViewModels()
    private val characterObserver = Observer<Resource<List<Character>>> { handleResponse(it) }
    private val selectedObserver = Observer<Character>{ handleSelected(it) }

    lateinit var adapter: CharacterAdapter
    var page : Int  = 0

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
        viewModel.mutableCharactersLiveData.observe(viewLifecycleOwner,characterObserver)
        viewModel.mutableSelectedCharacter.observe(viewLifecycleOwner, selectedObserver)
        viewModel.getCharacters(page)
        addRecyclerScrollListener()
        addFavoritesOnClickListener()
    }

    private fun initAdapter() {
        adapter = CharacterAdapter(viewModel)
        recycler_character.init(adapter)
    }

    private fun addRecyclerScrollListener() {
        recycler_character.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager =  (recyclerView.layoutManager) as LinearLayoutManager
                val lastVisibleItemPosition = manager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition == adapter.itemCount - 1) {
                    page += 1
                    viewModel.getCharacters(page)
                }
            }
        })
    }

    private fun addFavoritesOnClickListener() {
        btn_favorites.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                val fragment = BookmarkFragment()
                setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                addToBackStack(fragment.tag)
                add(R.id.main_fragment_container, fragment, fragment.tag)
            }
        }
    }

    private fun handleResponse(it: Resource<List<Character>>) {
        when (it.status) {
            Resource.LOADING -> {
                showProgressView()
                Timber.d("Loading...")
            }
            Resource.SUCCESS -> {
                if(!recycler_character.isVisible) recycler_character.visibility = VISIBLE
                hideProgressView()
                it.data?.map { character ->
                    adapter.add(character)
                }
            }
            Resource.ERROR -> {
                hideProgressView()
                Timber.d("Error: ${it.message}")
            }
        }
    }

    private fun handleSelected(it: Character?) {
        if(it != null){
            requireActivity().supportFragmentManager.commit {
                val fragment = CharacterDetailFragment()
                setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                addToBackStack(fragment.tag)
                add(R.id.main_fragment_container, fragment, fragment.tag)
            }
        }
    }

    private fun showProgressView() {
        progress_bar_characters.visibility = View.VISIBLE
    }

    private fun hideProgressView() {
        progress_bar_characters.visibility = View.INVISIBLE
    }

    private fun initToolbar() {
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
