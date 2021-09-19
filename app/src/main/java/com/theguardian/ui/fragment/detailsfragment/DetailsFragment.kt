package com.theguardian.ui.fragment.detailsfragment

import android.annotation.SuppressLint
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.theguardian.R
import com.theguardian.base.FragmentBaseMVVM
import com.theguardian.base.utils.viewBinding
import com.theguardian.databinding.FragmentDetailsBinding
import com.theguardian.entity.localmodels.ArticleUI
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment : FragmentBaseMVVM<DetailsFragmentViewModel, FragmentDetailsBinding>() {

    override val viewModel: DetailsFragmentViewModel by viewModel()
    override val binding: FragmentDetailsBinding by viewBinding()

    private val args: DetailsFragmentArgs by navArgs()

    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initView() {
        val article = args.article

        with(binding) {
            context?.let {
                Glide.with(it)
                    .load(article?.fields?.thumbnail)
                    .placeholder(context?.getDrawable(R.drawable.softconstruct_logo))
                    .into(detailImage)
            }

            detailTitle.text = article?.webTitle
            detailBody.text = article?.fields?.body?.let {
                    HtmlCompat.fromHtml(
                        it,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
        }
    }

    override fun navigateUp() {
        navigateBackStack()
    }

    override fun initViewClickListeners() {
        binding.backBtn.setOnClickListener {
            navigateUp()
        }
    }
}