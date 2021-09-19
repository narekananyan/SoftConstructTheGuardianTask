package com.theguardian.ui.fragment.homefragment

import com.google.android.material.tabs.TabLayoutMediator
import com.theguardian.base.FragmentBaseMVVM
import com.theguardian.base.utils.setSoftConstructFonts
import com.theguardian.base.utils.viewBinding
import com.theguardian.databinding.FragmentHomeBinding
import com.theguardian.entity.localmodels.ArticleUI
import com.theguardian.ui.fragment.adapter.FragmentAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : FragmentBaseMVVM<HomeFragmentViewModel, FragmentHomeBinding>() {

    override val viewModel: HomeFragmentViewModel by viewModel()
    override val binding: FragmentHomeBinding by viewBinding()
    private var fragmentAdapter: FragmentAdapter? = null

    companion object {
        var goToDetail = { _: ArticleUI -> }
        const val ALL_ARTICLES = "All Articles"
        const val FAVORITE_ARTICLES = "Favorite Articles"
    }

    override fun initView() {

        goToDetail = {
            navigateFragment(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
        }
        fragmentAdapter = FragmentAdapter(childFragmentManager, lifecycle)

        with(binding) {
            softConstructHeader.setSoftConstructFonts()
            viewPager.adapter = fragmentAdapter
            val tabTitlesList = mutableListOf<String>().apply {
                add(ALL_ARTICLES)
                add(FAVORITE_ARTICLES)
            }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitlesList[position]
            }.attach()
        }
    }

    override fun navigateUp() {
        navigateBackStack()
    }
}