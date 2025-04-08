package com.buddyshopper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buddyshopper.R
import com.buddyshopper.adapters.ShoppingListsAdapter
import com.buddyshopper.data.MockDataProvider
import com.buddyshopper.databinding.FragmentShoppingListsBinding

class ShoppingListsFragment : Fragment() {
    private var _binding: FragmentShoppingListsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ShoppingListsAdapter(MockDataProvider.shoppingLists) { list ->
                val position = MockDataProvider.shoppingLists.indexOf(list)
                findNavController().navigate(
                    ShoppingListsFragmentDirections.actionListsToDetails(position)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 