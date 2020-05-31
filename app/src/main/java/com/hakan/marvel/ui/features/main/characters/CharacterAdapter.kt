package com.hakan.marvel.ui.features.main.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hakan.marvel.R
import com.hakan.marvel.app.util.CircleTransform
import com.hakan.marvel.data.model.character.Character
import com.hakan.marvel.ui.features.main.SharedViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item.view.*


class CharacterAdapter (private val sharedViewModel: SharedViewModel) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private val items = mutableListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = items[position]
        holder.txtName.text = character.name
        Picasso.get()
            .load("${character.thumbnail.path}/standard_medium.${character.thumbnail.extension}")
            .transform(CircleTransform())
            .into(holder.imgThumbnail)

        holder.item_character.setOnClickListener{
            if(sharedViewModel.mutableSelectedCharacter.value == null){
                sharedViewModel.mutableSelectedCharacter.value = character
            }
        }
    }

    override fun getItemCount(): Int = items.size
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgThumbnail = itemView.img_thumbnail
        val txtName = itemView.txt_name
        val item_character = itemView.item_character
    }

    fun add(character: Character) {
        items.add(character)
        notifyItemInserted(items.lastIndex)
    }

    fun addAll(character: List<Character>) {
        items.clear()
        notifyDataSetChanged()
        items.addAll(character)
        notifyItemRangeInserted(0, items.size)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }


}
