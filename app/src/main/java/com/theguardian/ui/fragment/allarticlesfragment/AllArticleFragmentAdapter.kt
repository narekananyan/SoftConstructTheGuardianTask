package com.theguardian.ui.fragment.allarticlesfragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.theguardian.R
import com.theguardian.base.adapter.BaseAdapter
import com.theguardian.base.adapter.BaseViewHolder
import com.theguardian.databinding.ArticlesItemBinding
import com.theguardian.entity.localmodels.ArticleUI

class AllArticleFragmentAdapter :
    BaseAdapter<ArticlesItemBinding, ArticleUI, AllArticleFragmentAdapter.AllArticleViewHolder>(
        ArticlesDiffCallback()
    ) {

    private var selectFavoriteCallBack: (item: ArticleUI) -> Unit = {}
    private var deleteFavoriteCallBack: (item: ArticleUI) -> Unit = {}
    private var onItemClickCallBack: (item: ArticleUI) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllArticleViewHolder {
        return AllArticleViewHolder(
            ArticlesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class AllArticleViewHolder(private val binding: ArticlesItemBinding) :
        BaseViewHolder<ArticleUI, ArticlesItemBinding>(binding) {

        @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
        override fun bind(item: ArticleUI) {
            with(binding) {

                Glide.with(articleImage.context)
                    .load(item.fields?.thumbnail)
                    .placeholder(articleImage.context.getDrawable(R.drawable.softconstruct_logo))
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                    .into(binding.articleImage)

                articleTitle.text = item.webTitle
                articleType.text = "#" + item.type

                if (item.isFavorite) {
                    favoriteArticle.setImageDrawable(ContextCompat.getDrawable(binding.root.context,R.drawable.ic_baseline_favorite_24))
                } else {
                    favoriteArticle.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_baseline_favorite_no_selected24
                    ))
                }

                favoriteArticle.setOnClickListener {
                    if (item.isFavorite) {
                        deleteFavoriteCallBack.invoke(item)
                    } else {
                        selectFavoriteCallBack.invoke(item)
                    }
                }
            }
        }

        override fun onItemClick(item: ArticleUI) {
            onItemClickCallBack.invoke(item)
        }
    }

    fun addSelectFavoriteCallBack(callback: (item: ArticleUI) -> Unit) {
        selectFavoriteCallBack = callback
    }

    fun deleteFavoriteCallBack(callback: (item: ArticleUI) -> Unit) {
        deleteFavoriteCallBack = callback
    }

    fun addOnItemClickCallBack(callback: (item: ArticleUI) -> Unit) {
        onItemClickCallBack = callback
    }
}

class ArticlesDiffCallback : DiffUtil.ItemCallback<ArticleUI>() {
    override fun areItemsTheSame(oldItem: ArticleUI, newItem: ArticleUI): Boolean =
        oldItem.id == newItem.id


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ArticleUI, newItem: ArticleUI): Boolean =
        oldItem == newItem
}