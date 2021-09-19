package com.theguardian.ui.fragment.favoritearticlesfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theguardian.ui.fragment.allarticlesfragment.AllArticleFragmentAdapter
import com.theguardian.ui.fragment.homefragment.HomeFragment
import com.theguardian.base.utils.viewBinding
import com.theguardian.databinding.FragmentFavoriteArticlesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteArticlesFragment : Fragment() {

    val viewModel: FavoriteArticlesFragmentViewModel by viewModel()
    private val binding: FragmentFavoriteArticlesBinding by viewBinding()

    private val adapter: AllArticleFragmentAdapter by lazy {
        AllArticleFragmentAdapter().apply {
            deleteFavoriteCallBack {
                viewModel.deleteFavoriteArticle(it)
            }
            addOnItemClickCallBack {
                HomeFragment.goToDetail.invoke(it)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteArticlesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteArticles.adapter = adapter
        observes()
    }

    private fun observes() {
        with(viewModel) {
            favoriteArticleLiveData.observe(viewLifecycleOwner) {
                adapter.submitList(it.toMutableList())
            }
        }
    }

}