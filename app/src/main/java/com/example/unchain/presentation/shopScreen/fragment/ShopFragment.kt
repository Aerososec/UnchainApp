package com.example.unchain.presentation.shopScreen.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Insert
import com.example.unchain.R
import com.example.unchain.UnchainApp
import com.example.unchain.databinding.FragmentShopBinding
import com.example.unchain.di.ViewModelFactory
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.presentation.shopScreen.recyclerView.ShopAdapter
import com.example.unchain.presentation.shopScreen.viewModel.ShopViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class ShopFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: ShopViewModel
    private lateinit var binding: FragmentShopBinding
    private lateinit var shopAdapter: ShopAdapter

    private var addictionId = UNKNOWN_ADDICTION_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as UnchainApp).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            factory
        )[ShopViewModel::class.java]
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.personalities.collect {
                    shopAdapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val userProgress = viewModel.getUserProgress(addictionId)
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                userProgress.collect {
                    binding.tvCurrency.text = it?.currency.toString()
                }
            }

        }

        shopAdapter.itemClick = { personality ->
            viewLifecycleOwner.lifecycleScope.launch {
                selectPersonality(personality)
            }
        }
    }

    private suspend fun selectPersonality(personality: Personality){
        val awp = AddictionWithPersonality(addictionId, personality.id)
        viewModel.selectPersonality(awp)
    }

    private fun setUpRecyclerView(){
        shopAdapter = ShopAdapter()

        binding.rvPersonalities.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = shopAdapter
        }
    }


    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(ADDICTION_ID_KEY)) return

        addictionId = args.getInt(ADDICTION_ID_KEY)
    }

    companion object {
        const val ADDICTION_ID_KEY = "ADDICTION_ID_KEY"
        private const val UNKNOWN_ADDICTION_ID = -1

        fun newInstance(addictionId: Int): ShopFragment {
            return ShopFragment().apply {
                arguments = Bundle().apply {
                    putInt(ADDICTION_ID_KEY, addictionId)
                }
            }
        }
    }
}