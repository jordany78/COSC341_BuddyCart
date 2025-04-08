package com.buddyshopper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.buddyshopper.adapters.ShoppingItemsAdapter
import com.buddyshopper.data.MockDataProvider
import com.buddyshopper.databinding.FragmentListDetailsBinding
import com.buddyshopper.models.ShoppingItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ListDetailsFragment : Fragment() {
    private var _binding: FragmentListDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ListDetailsFragmentArgs by navArgs()
    private var selectedItemsCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shoppingList = MockDataProvider.shoppingLists[args.listPosition]
        
        binding.listNameTitle.text = shoppingList.listName
        binding.storeText.text = shoppingList.store
        updateSelectionCounter(0, shoppingList.totalItems)

        binding.itemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ShoppingItemsAdapter { item, isSelected ->
                item.isSelected = isSelected
                selectedItemsCount = if (isSelected) selectedItemsCount + 1 else selectedItemsCount - 1
                updateSelectionCounter(selectedItemsCount, shoppingList.totalItems)
                updateConfirmButton(selectedItemsCount > 0)
            }
            (adapter as ShoppingItemsAdapter).submitList(shoppingList.items)
        }

        binding.confirmButton.apply {
            isEnabled = false
            setOnClickListener {
                val selectedItems = shoppingList.items.filter { it.isSelected }
                
                if (selectedItems.isEmpty()) {
                    Snackbar.make(binding.root, "Please select at least one item", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                showConfirmationDialog(selectedItems)
            }
        }

        binding.backButton.setOnClickListener {
            if (selectedItemsCount > 0) {
                showBackConfirmationDialog()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private fun updateSelectionCounter(selected: Int, total: Int) {
        binding.selectionCounter.text = "I can fulfill $selected of $total items on this list."
    }

    private fun updateConfirmButton(enabled: Boolean) {
        binding.confirmButton.isEnabled = enabled
    }

    private fun showConfirmationDialog(selectedItems: List<ShoppingItem>) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirm Selection")
            .setMessage("You have selected:\n${selectedItems.map { "${it.name} (${it.quantity})" }.joinToString("\n")}")
            .setPositiveButton("Confirm") { _, _ ->
                Snackbar.make(binding.root, "Thank you! Your fulfillment has been recorded.", Snackbar.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showBackConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Discard Changes?")
            .setMessage("You have selected $selectedItemsCount items. Are you sure you want to go back?")
            .setPositiveButton("Yes, go back") { _, _ ->
                findNavController().popBackStack()
            }
            .setNegativeButton("No, stay", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 