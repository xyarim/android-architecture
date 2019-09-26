package com.xyarim.users.ui.fragment.users


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xyarim.users.databinding.FragmentUsersBinding
import com.xyarim.users.utils.setupRefreshLayout
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 *
 */
class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by inject()

    private lateinit var listAdapter: UsersAdapter

    private lateinit var usersFragmentDataBinding: FragmentUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersFragmentDataBinding = FragmentUsersBinding.inflate(inflater, container, false)
            .apply { viewmodel = viewModel }
        return usersFragmentDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        usersFragmentDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupRefreshLayout(usersFragmentDataBinding.refreshLayout, usersFragmentDataBinding.usersList)
        viewModel.getUsers()
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
