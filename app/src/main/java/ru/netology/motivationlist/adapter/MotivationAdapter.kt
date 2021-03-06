package ru.netology.motivationlist.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.motivationlist.R
import ru.netology.motivationlist.databinding.CardListBinding
import ru.netology.motivationlist.dto.Motivation

interface OnInteractionListener {
    fun onLikeUp(motivation: Motivation) {}
    fun onLikeDown(motivation: Motivation) {}
    fun onFilterNameAuthor(motivation: Motivation) {}
    fun onRemove(motivation: Motivation) {}
    fun onShare(motivation: Motivation) {}
}

class MotivationAdapter(
        private val onInteractionListener: OnInteractionListener
) : PagedListAdapter<Motivation, MotivationViewHolder>(MotivationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotivationViewHolder {
        val binding = CardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MotivationViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: MotivationViewHolder, position: Int) {
        val motivation: Motivation? = getItem(position)
        if (motivation == null) {
            holder.clear()
        } else {
            holder.bind(motivation)
        }
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
            urlContent.text = motivation.urlContent
            imageLike.text = motivation.countLike.toString()
            imageShare.text = motivation.countShare.toString()

            val url:String = motivation.urlImage

            if (url == "") image.visibility = View.GONE
            else {
                image.visibility = View.VISIBLE
            }

            Glide
                .with(image)
                .load(Uri.parse(url))
                .centerCrop()
                .skipMemoryCache(true)
                .into(image)

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

            author.setOnClickListener() {
                onInteractionListener.onFilterNameAuthor(motivation)
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
                            else -> false
                        }
                    }
                }.show()  //показ меню
            }
        }
    }
    fun clear(){
        binding.apply {
            author.text = null
            published.text = null
            content.text = null
            urlContent.text = null
            imageLike.text = null
            imageShare.text = null
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