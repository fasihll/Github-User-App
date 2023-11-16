package com.example.githubuserapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.local.datastore.SettingPreferences
import com.example.githubuserapp.data.local.datastore.dataStore
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.ui.viewModel.FollowViewModel
import com.example.githubuserapp.databinding.FragmentFollowBinding
import com.example.githubuserapp.ui.viewModel.SettingsViewModel
import com.example.githubuserapp.ui.viewModel.SettingsViewModelFactory


class FollowFragment : Fragment() {

    private var binding: FragmentFollowBinding? = null
    private val followViewModel by viewModels<FollowViewModel>()

    companion object{
        const val ARG_POSITION = "position"
        const val USERNAME = "username"
    }

    private var position = 0
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(USERNAME).toString()
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollow?.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(requireActivity(),layoutManager.orientation)
        binding?.rvFollow?.addItemDecoration(dividerItemDecoration)

        if (position == 1){
            followViewModel.showFollowers(username)
            followViewModel.listFollowers.observe(viewLifecycleOwner,{ listFollowers ->
               setFollowData(listFollowers)
           })
        }else{
            followViewModel.showFollowing(username)
            followViewModel.listFollowing.observe(viewLifecycleOwner,{ listFollowing ->
                setFollowData(listFollowing)
            })
        }

        followViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }

        showLoading(true)

        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )

        settingsViewModel.getThemeSetting().observe(requireActivity()){isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setFollowData(items: List<ItemsItem?>?) {
        val adapter = UsersAdapter()
        adapter.submitList(items)
        binding?.rvFollow?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding?.progressBar?.visibility = View.VISIBLE
        }else{
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}