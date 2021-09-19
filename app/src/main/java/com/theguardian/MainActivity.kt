package com.theguardian

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.theguardian.base.FragmentBaseMVVM
import com.theguardian.base.utils.showSnack
import com.theguardian.base.utils.viewBinding
import com.theguardian.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentBaseMVVM.OnFragmentEventListener {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun progressVisibilityChange(isVisible: Boolean) {
        binding.lProgress.isVisible = isVisible
    }

    override fun showErrorSnackbar(text: String) {
        showSnack(binding.root, text, R.color.colorYellow, R.color.black)
    }
}