package com.example.wuphf.ui.favoritesFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wuphf.databinding.FragmentFavoritesBinding
import com.example.wuphf.ui.MainActivity
import com.example.wuphf.ui.allDogsFragment.FavoriteListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoriteListener {

    private var _binding : FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel : FavoritesViewModel by activityViewModels()
    private val favouritesAdapter: FavoritesAdapter by lazy { FavoritesAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recycler.adapter = favouritesAdapter

        favoritesViewModel.getAllFavourites()
        observeUI()
    }

    fun observeUI(){
        favoritesViewModel.favList.observe(requireActivity()) {
            if(it.data != null && it.data.isNotEmpty()) {
                favouritesAdapter.submitList(it.data)
            }
        }
    }

    override fun onFavoriteItemClicked(index: Int) {
        favoritesViewModel.select(index)
        (requireActivity() as MainActivity).openFragment(ShareDogFragment(), "Opening Dog Info Fragment")
    }

    override fun onFavoriteItemLongClicked(index: Int) {

    }
}