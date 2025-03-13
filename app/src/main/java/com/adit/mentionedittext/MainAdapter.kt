package com.adit.mentionedittext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.library.Mention
import com.adit.mentionedittext.databinding.ItemRecommendationBinding

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val items = mutableListOf<Mention>()
    private var onClickListener: AdapterClickListener? = null

    inner class ViewHolder(
        private val binding: ItemRecommendationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Mention) {
            with(binding) {
                tvName.text = item.getDisplayedText()
            }

            itemView.setOnClickListener {
                onClickListener?.onClick(item)
            }
        }
    }

    fun setData(items: List<Mention>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: AdapterClickListener) {
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item)
    }
}

fun interface AdapterClickListener {
    fun onClick(item: Mention)
}