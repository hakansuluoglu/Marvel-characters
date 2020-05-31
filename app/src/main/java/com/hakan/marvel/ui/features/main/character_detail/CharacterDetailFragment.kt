package com.hakan.marvel.ui.features.main.character_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.hakan.marvel.R
import com.hakan.marvel.app.util.init
import com.hakan.marvel.data.model.character.Character
import com.hakan.marvel.data.model.comic.Comic
import com.hakan.marvel.domain.Resource
import com.hakan.marvel.ui.features.main.SharedViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import io.paperdb.Paper
import kotlinx.android.synthetic.main.character_detail_fragment.*
import timber.log.Timber


class CharacterDetailFragment : Fragment()  {

    private val viewModel: SharedViewModel by activityViewModels()

    private val observer = Observer<Resource<List<Comic>>> { handleResponse(it) }

    lateinit var adapter: ComicAdapter

    lateinit var character : Character
    var isLocal : Boolean = false
    lateinit var favorites : ArrayList<Character>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.character_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)
        favorites  = Paper.book().read("favorite", java.util.ArrayList<Character>())

        initAdapter()
        viewModel.mutableSelectedCharacter.observe(viewLifecycleOwner, Observer {
            val localCharacter = favorites.find { local  -> local.id == it.id }
            if(localCharacter != null){
                character = localCharacter
                if(localCharacter.savedComics != null  &&  localCharacter.savedComics?.isNotEmpty()!!) adapter.addAll(localCharacter.savedComics!!)
                isLocal = true
                btn_bookmark.setImageResource(R.drawable.ic_bookmark)
            }else{
                character = it
                viewModel.getComics(character.id)
                btn_bookmark.setImageResource(R.drawable.ic_bookmark_border)
            }

            viewModel.mutableComicsLiveData.observe(viewLifecycleOwner, observer)
            Picasso.get()
                .load("${it.thumbnail.path}/landscape_incredible.${it.thumbnail.extension}")
                .fit()
                .centerCrop()
                .into(img_detail_thumbnail)

            txt_detail_name.text = it.name
            if(it.description != ""){
                txt_character_description.visibility = View.VISIBLE
                txt_description.visibility = View.VISIBLE
                txt_character_description.text = it.description
            }
        })

        btn_bookmark.setOnClickListener {
            if(isLocal){
                isLocal = false
                favorites.remove(character)
                Paper.book().write("favorite" , favorites)
                viewModel.getLocalCharacters()
                val toast = Toast.makeText(requireContext(),"Character has been removed to your favorites",Toast.LENGTH_SHORT)
                toast.show()
                btn_bookmark.setImageResource(R.drawable.ic_bookmark_border)

            }else{
                isLocal = true
                favorites.add(character)
                Paper.book().write("favorite" , favorites)
                viewModel.getLocalCharacters()
                val toast = Toast.makeText(requireContext(),"Character has been saved to your favorites",Toast.LENGTH_SHORT)
                toast.show()
                btn_bookmark.setImageResource(R.drawable.ic_bookmark)
            }
        }
    }


    private fun initAdapter() {
        adapter = ComicAdapter()
        recycler_comic.init(adapter)
    }

    private fun handleResponse(it: Resource<List<Comic>>) {
        when (it.status) {
            Resource.LOADING -> {
                Timber.d("Loading...")
            }
            Resource.SUCCESS -> {
                if(it.data?.size!! > 0){
                    character.savedComics = it.data!!
                    txt_comic.visibility = View.VISIBLE
                    adapter.addAll(it.data!!)
                }
            }
            Resource.ERROR -> {
                Timber.d("Error: ${it.message}")
            }
        }
    }

     override fun onDestroyView() {
        super.onDestroyView()
         viewModel.mutableSelectedCharacter.value = null
    }



}
