package com.xyarim.users.ui.fragment.users


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xyarim.users.R
import com.xyarim.users.api.User
import com.xyarim.users.databinding.FragmentUsersBinding
import com.xyarim.users.utils.EventObserver
import com.xyarim.users.utils.setupRefreshLayout
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Display a list of [User]s.
 */
class UsersFragment : Fragment() {

    private val usersViewModel: UsersViewModel by viewModel()

    private lateinit var listAdapter: UsersAdapter

    private lateinit var usersFragmentDataBinding: FragmentUsersBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        usersFragmentDataBinding = FragmentUsersBinding.inflate(inflater, container, false)
                .apply { viewmodel = usersViewModel }
        // Set the lifecycle owner to the lifecycle of the view
        usersFragmentDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return usersFragmentDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        // Always reloading data for simplicity.
        usersViewModel.getUsers()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        setupNavigation()
        setupRefreshLayout(usersFragmentDataBinding.refreshLayout, usersFragmentDataBinding.usersList)
    }


    private fun setupNavigation() {
        usersViewModel.openUserDetailEvent.observe(this, EventObserver {
            navigateToUserDetails(it)
        })

        usersViewModel.createUserEvent.observe(this, EventObserver {
            navigateToAddNewUser()
        })
    }

    private fun navigateToUserDetails(user: User) {
        val action = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(
                user,
                resources.getString(R.string.title_edit_user)
        )
        findNavController().navigate(action)
    }

    private fun navigateToAddNewUser() {
        val action = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(
                null,
                resources.getString(R.string.title_create_user)
        )
        findNavController().navigate(action)
    }

    private fun setupListAdapter() {
        val viewModel = usersFragmentDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = UsersAdapter(viewModel)
            usersFragmentDataBinding.usersList.adapter = listAdapter
        } else {
            Log.w(this.javaClass.simpleName, "ViewModel not initialized when attempting to set up adapter.")
        }
    }
}
