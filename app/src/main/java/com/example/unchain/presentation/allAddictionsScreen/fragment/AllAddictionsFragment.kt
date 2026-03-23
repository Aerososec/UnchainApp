package com.example.unchain.presentation.allAddictionsScreen.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unchain.R
import com.example.unchain.UnchainApp
import com.example.unchain.databinding.FragmentAllAddictionsBinding
import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.presentation.allAddictionsScreen.recyclerView.AllAddictionsAdapter
import com.example.unchain.presentation.allAddictionsScreen.viewModel.AllAddictionUiState
import com.example.unchain.presentation.allAddictionsScreen.viewModel.AllAddictionsViewModel
import com.example.unchain.presentation.userProgressScreen.fragment.UserProgressFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllAddictionsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var addictionViewModel: AllAddictionsViewModel
    private lateinit var allAddictionsAdapter: AllAddictionsAdapter
    private lateinit var binding: FragmentAllAddictionsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as UnchainApp).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addictionViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[AllAddictionsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllAddictionsBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                addictionViewModel.state.collect{
                    loadAddiction(it)
                }
            }
        }
    }

    private suspend fun loadAddiction(state: AllAddictionUiState){
        when{
            state.isLoading -> {

            }
            state.addictionList.isNotEmpty() -> {
                allAddictionsAdapter.submitList(state.addictionList)
            }
            state.error != null -> {

            }
        }
    }


    private fun setUpRecyclerView() {
        allAddictionsAdapter = AllAddictionsAdapter()

        allAddictionsAdapter.startButtonClick = { addiction ->
            openUserProgress(addiction)
        }

        allAddictionsAdapter.progressAddictionCardClick = { addiction ->
            openUserProgress(addiction)
        }

        binding.addictionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allAddictionsAdapter
        }
    }

    private fun openUserProgress(addiction: AddictionWithProgress) {
        val fragment = UserProgressFragment.newInstanceUserProgress(addiction.id, addiction.name)
        openUserProgressFragment(fragment)
    }

    private fun openUserProgressFragment(fragment: UserProgressFragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

}