package com.theguardian.ui.fragment.allarticlesfragment

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theguardian.base.utils.gone
import com.theguardian.base.utils.viewBinding
import com.theguardian.base.utils.visible
import com.theguardian.broadcast.NetworkChangeReceiver
import com.theguardian.databinding.FragmentAllArticlesBinding
import com.theguardian.ui.fragment.homefragment.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllArticlesFragment : Fragment() {

    val viewModel: AllArticlesFragmentViewModel by viewModel()
    private val binding: FragmentAllArticlesBinding by viewBinding()

    private var networkReceiver: NetworkChangeReceiver = NetworkChangeReceiver()
    private var isLoading = false

    private val adapter: AllArticleFragmentAdapter by lazy {
        AllArticleFragmentAdapter().apply {
            addSelectFavoriteCallBack {
                viewModel.saveFavorite(it)
            }
            deleteFavoriteCallBack {
                viewModel.deleteFavoriteArticle(it)
            }
            addOnItemClickCallBack {
                HomeFragment.goToDetail.invoke(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        val filter = IntentFilter()
        filter.addAction(CONNECTIVITY_ACTION)
        activity?.registerReceiver(networkReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(networkReceiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkReceiver.apply {
            addIsOnLineCallBack { isConnected ->
                if (isConnected) {
                    getArticles()
                } else {

                }
            }
        }

        with(binding) {
            allArticles.adapter = adapter
            observer()
            viewModel.getArticles()
            allArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    getArticles()
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllArticlesFragment()
        val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }

    private fun observer() {
        with(viewModel) {
            getArticlesError.observe(viewLifecycleOwner) {
                isLoading = false
                binding.progressBar.gone()
            }
            allArticleLiveData.observe(viewLifecycleOwner) {
                binding.progressBar.gone()
                isLoading = false
                adapter.submitList(it.toMutableList())
            }
        }
    }

    private fun getArticles() {
        val recyclerView = binding.allArticles
        val visibleItemCount: Int = recyclerView.layoutManager?.childCount ?: 0
        val totalItemCount: Int = recyclerView.layoutManager?.itemCount ?: 0
        val firstVisibleItemPosition: Int =
            (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                ?: 0

        if (visibleItemCount == 0 && !isLoading) {
            viewModel.getArticles()
            isLoading = true
        } else if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 3
            && firstVisibleItemPosition >= 0
            && totalItemCount >= 20 && !isLoading
        ) {
            viewModel.getArticles()
            isLoading = true
            binding.progressBar.visible()
        }
    }

}