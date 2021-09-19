package com.theguardian.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.theguardian.base.statemodel.ErrorStateModel
import com.theguardian.base.statemodel.ErrorStateModel.*
import com.theguardian.base.viewmodel.BaseViewModel
import com.theguardian.base.utils.observe

abstract class FragmentBaseMVVM<ViewModel : BaseViewModel, ViewBind : ViewBinding> : Fragment() {
    protected abstract val viewModel: ViewModel
    protected abstract val binding: ViewBind

    private lateinit var navController: NavController
    private val navOptions = NavOptions.Builder()
        .setLaunchSingleTop(false)
        .build()

    private var callback: OnFragmentEventListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callback = activity as OnFragmentEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString() + " must implement SelectedListener"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this) {
            navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        observes()
        initView()
        initViewClickListeners()
        observeToBaseChanges()
    }

    protected fun <T> observe(liveData: LiveData<T>, action: (T) -> Unit) = view?.run {
        if (!this@FragmentBaseMVVM.isAdded) return@run
        liveData.observe(viewLifecycleOwner, Observer { action(it ?: return@Observer) })
    }

    private fun observeToBaseChanges() {
        with(viewLifecycleOwner) {
            observe(viewModel.loading) { isVisible -> callback?.progressVisibilityChange(isVisible) }
            observe(viewModel.error) { err -> onErrorReceived(err) }
        }
    }

    private fun onErrorReceived(errorStateModel: ErrorStateModel) {
        when (errorStateModel) {
            is SnackbarErrorStateModel -> callback?.showErrorSnackbar(errorStateModel.message)
            is DialogErrorStateModel -> callback?.showErrorSnackbar(errorStateModel.message.toString())
        }
    }

    protected abstract fun initView()
    protected open fun initViewClickListeners() {}
    protected open fun observes() {}

    protected open fun navigateUp() {
        navigateBackStack()
    }

    protected fun navigateBackStack() {
        navController.popBackStack()
    }

    protected fun navigateFragment(
        destinationId: Int,
        bundle: Bundle? = null
    ) {
        navController.navigate(
            destinationId,
            bundle,
            navOptions,
        )
    }

    protected fun navigateFragment(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    interface OnFragmentEventListener {
        fun progressVisibilityChange(isVisible: Boolean)

        fun showErrorSnackbar(text: String)

    }
}