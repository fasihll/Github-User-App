package com.example.githubuserapp.presentation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.local.datastore.SettingPreferences
import com.example.githubuserapp.data.local.datastore.dataStore
import com.example.githubuserapp.databinding.FragmentFollowBinding
import com.example.githubuserapp.domain.model.ItemsItem
import com.example.githubuserapp.presentation.viewModel.FollowViewModel
import com.example.githubuserapp.presentation.viewModel.MainViewModel
import com.example.githubuserapp.presentation.viewModel.SettingsViewModel
import com.example.githubuserapp.presentation.viewModel.ViewModelFactory
import com.example.githubuserapp.utils.Result


class FollowFragment : Fragment() {

    private var binding: FragmentFollowBinding? = null
    private val viewModel by viewModels<FollowViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    private val viewModel2 by viewModels<SettingsViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }

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
            viewModel.showFollowers(username).observe(viewLifecycleOwner,{ result ->
                if (result != null){
                    when(result){
                        is Result.Loading -> {
                            Log.d("RES","Loading...")
                        }
                        is Result.Success -> {
                            setFollowData(result.data)
                        }
                        is Result.Error -> {
                            Log.d("RES","Error!!")
                        }
                    }
                }else{
                    Log.d("RES","null!!")
                }
           })
        }else{
            viewModel.showFollowing(username).observe(viewLifecycleOwner,{ result ->
                if (result != null){
                    when(result){
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            setFollowData(result.data)
                        }
                        is Result.Error -> {
                            showLoading(true)
                        }
                    }
                }else{
                    Toast.makeText(requireContext(),"Data Gaga Di Muat", Toast.LENGTH_SHORT).show()
                }
            })
        }

        viewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }



        viewModel2.getThemeSetting.observe(viewLifecycleOwner){ result ->

            if (result){
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