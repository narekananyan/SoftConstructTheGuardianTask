package com.theguardian.ui.fragment.splashfragment

import android.os.Bundle
import com.theguardian.R
import com.theguardian.base.FragmentBaseMVVM
import com.theguardian.base.utils.hasNetwork
import com.theguardian.base.utils.setSoftConstructFonts
import com.theguardian.base.utils.viewBinding
import com.theguardian.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment : FragmentBaseMVVM<SplashFragmentViewModel, FragmentSplashBinding>() {

    override val viewModel: SplashFragmentViewModel by viewModel()
    override val binding: FragmentSplashBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context?.hasNetwork() == true) {
            viewModel.deleteAllArticles()
        }
    }

    override fun initView() {
        binding.softConstruct.setSoftConstructFonts()
        viewModel.goToHomePage()
    }

    override fun navigateUp() {
        navigateBackStack()
    }

    override fun observes() {
        with(viewModel) {
            observe(goToHome) {
                navigateFragment(R.id.action_splashFragment_to_homeFragment)
            }
        }
    }
}