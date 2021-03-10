package ru.netology.motivationlist.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.motivationlist.R
import ru.netology.motivationlist.databinding.CardListBinding
import ru.netology.motivationlist.dto.Motivation
import java.net.URI

interface OnInteractionListener {
    fun onLikeUp(motivation: Motivation) {}
    fun onLikeDown(motivation: Motivation) {}
    fun onSortNameAutor(motivation: Motivation) {}
    fun onRemove(motivation: Motivation) {}
    fun onShare(motivation: Motivation) {}
    //fun onUrlContent(motivation: Motivation) {}
}

class MotivationAdapter(
        private val onInteractionListener: OnInteractionListener
) : ListAdapter<Motivation, MotivationViewHolder>(MotivationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotivationViewHolder {
        val binding = CardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MotivationViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: MotivationViewHolder, position: Int) {
        val motivation = getItem(position)
        holder.bind(motivation)
    }
}

class MotivationViewHolder(
        private val binding: CardListBinding,
        private val onInteractionListener: OnInteractionListener
): RecyclerView.ViewHolder(binding.root){
    fun bind(motivation: Motivation) {
        binding.apply {
            author.text = motivation.author
            published.text = motivation.published
            content.text = motivation.content
            urlContent.text = motivation.urlImage
            imageLike.text = motivation.countLike.toString()
            imageShare.text = motivation.countShare.toString()
            val url:String = motivation.urlImage
            image.setImageURI(null)
            image.setImageURI(Uri.parse(url))

            if (urlContent.text == "") urlContent.visibility = View.GONE
            else {
                urlContent.visibility = View.VISIBLE
            }

            imageLike.setOnClickListener {
                onInteractionListener.onLikeUp(motivation)
            }
            imageDisLike.setOnClickListener{
                onInteractionListener.onLikeDown(motivation)
            }
            imageShare.setOnClickListener {
                onInteractionListener.onShare(motivation)
            }
//            urlContent.setOnClickListener() {
//                onInteractionListener.onUrlContent(motivation)
//            }
            author.setOnClickListener() {
                onInteractionListener.onSortNameAutor(motivation)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options)           // пункты меню
                    setOnMenuItemClickListener { item ->  // обработчик клика пункта меню
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(motivation)
                                true
                            }
//                            R.id.edit -> {
//                                onInteractionListener.onEdit(post)
//                                true
//                            }
                            else -> false
                        }
                    }
                }.show()  //показ меню
            }

        }
    }
}

class MotivationDiffCallback : DiffUtil.ItemCallback<Motivation>() {
    override fun areItemsTheSame(oldItem: Motivation, newItem: Motivation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Motivation, newItem: Motivation): Boolean {
        return oldItem == newItem
    }
}