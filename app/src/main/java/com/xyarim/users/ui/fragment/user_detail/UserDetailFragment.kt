package com.xyarim.users.ui.fragment.user_detail


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.xyarim.users.R
import com.xyarim.users.databinding.FragmentUserDetailBinding
import com.xyarim.users.utils.EventObserver
import com.xyarim.users.utils.setupSnackbar
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 *
 */
class UserDetailFragment : Fragment() {

    private lateinit var fragmentUserDetailBinding: FragmentUserDetailBinding

    private val viewModel: UserDetailViewModel by inject()

    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentUserDetailBinding = FragmentUserDetailBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = viewModel
            }
        fragmentUserDetailBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)

        return fragmentUserDetailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
        setupSnackbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.user?.let {
            viewModel.setupUser(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_detail_fragment_menu, menu)
    }

    private fun setupNavigation() {
        viewModel.userSavedEvent.observe(this, EventObserver {
            findNavController().navigateUp()
        })
    }
    private fun setupSnackbar(){
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                viewModel.saveUser()
                true
            }
            else -> false
        }
    }


}
