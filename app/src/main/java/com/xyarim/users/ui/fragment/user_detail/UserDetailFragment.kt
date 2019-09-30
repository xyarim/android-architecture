package com.xyarim.users.ui.fragment.user_detail


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.razir.progressbutton.DrawableButton.Companion.GRAVITY_CENTER
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.xyarim.users.R
import com.xyarim.users.databinding.FragmentUserDetailBinding
import com.xyarim.users.utils.EventObserver
import com.xyarim.users.utils.showKeyboard
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 *
 */
class UserDetailFragment : Fragment() {

    private lateinit var fragmentUserDetailBinding: FragmentUserDetailBinding

    private val viewModel: UserDetailViewModel by inject()

    private val args: UserDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        fragmentUserDetailBinding = FragmentUserDetailBinding.inflate(inflater, container, false)
                .apply {
                    viewmodel = viewModel
                }
        fragmentUserDetailBinding.lifecycleOwner = this.viewLifecycleOwner
        return fragmentUserDetailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
        setupProgressIndicator()
        args.user?.let {
            viewModel.setupUser(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentUserDetailBinding.saveButton.attachTextChangeAnimator()
        fragmentUserDetailBinding.textUserName.showKeyboard()
    }

    fun setupProgressIndicator() {
        viewModel.dataLoading.observe(this, Observer {
            if (it) {
                fragmentUserDetailBinding.saveButton.showProgress {
                    buttonTextRes = R.string.button_save
                    progressColor = Color.WHITE
                    gravity = GRAVITY_CENTER
                }
            } else fragmentUserDetailBinding.saveButton.hideProgress(R.string.button_save)
        })

    }

    private fun setupNavigation() {
        viewModel.userSavedEvent.observe(this, EventObserver {
            findNavController().navigateUp()
        })
    }

}
