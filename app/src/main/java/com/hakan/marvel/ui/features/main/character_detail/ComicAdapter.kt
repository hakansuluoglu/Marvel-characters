package com.hakan.marvel.ui.features.main.character_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hakan.marvel.R
import com.hakan.marvel.app.util.CircleTransform
import com.hakan.marvel.data.model.comic.Comic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item.view.*


class ComicAdapter : RecyclerView.Adapter<ComicAdapter.ViewHolder>() {
    private val items = mutableListOf<Comic>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comic = items[position]
        holder.txtName.text = comic.title
        Picasso.get()
            .load("${comic.thumbnail.path}/standard_medium.${comic.thumbnail.extension}")
            .transform(CircleTransform())
            .into(holder.imgThumbnail)

    }

    override fun getItemCount(): Int = items.size
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgThumbnail = itemView.img_thumbnail
        val txtName = itemView.txt_name
    }

    fun addAll(comics: List<Comic>) {
        items.clear()
        items.addAll(comics)
        notifyItemInserted(items.lastIndex)
    }
}
