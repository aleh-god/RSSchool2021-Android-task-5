package by.godevelopment.rsschool2021_android_task_5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.rsschool2021_android_task_5.R
import by.godevelopment.rsschool2021_android_task_5.model.Cat
import by.godevelopment.rsschool2021_android_task_5.databinding.ItemCatsListBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CatsAdapter(
    private val onItemClick: ((cat: Cat) -> Unit)? = null
) : PagingDataAdapter<Cat, CatViewHolder>(CAT_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            ItemCatsListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bindTo(repoItem)
        }
    }

    companion object {
        private val CAT_COMPARATOR = object : DiffUtil.ItemCallback<Cat>() {
            override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean =
                oldItem == newItem
        }
    }
}

class CatViewHolder(
    @NonNull
    private val binding: ItemCatsListBinding,
    private val onItemClick: ((cat: Cat) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(cat: Cat) {
        itemView.setOnClickListener {
            onItemClick?.invoke(cat)
        }

        binding.name.text = ("Cat: ${cat.id} = ${cat.width}x${cat.height}").toString()
        Glide.with(binding.root)
            .load(cat.url)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.drawable.image_not_loaded)
            .placeholder(R.drawable.image)
            .into(binding.image)
    }
}
