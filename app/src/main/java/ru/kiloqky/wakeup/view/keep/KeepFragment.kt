package ru.kiloqky.wakeup.view.keep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentKeepBinding
import ru.kiloqky.wakeup.rest.room.model.Keep
import ru.kiloqky.wakeup.view.keep.adapters.RecyclerKeepsAdapter
import ru.kiloqky.wakeup.viewmodels.KeepViewModel


class KeepFragment : Fragment(R.layout.fragment_keep) {
    private var binding: FragmentKeepBinding? = null

    private val keepViewModel: KeepViewModel by viewModels({ requireActivity() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
        refreshData()
        binding!!.fab.setOnClickListener {
            addKeep()
        }
    }

    private fun addKeep() {
        findNavController().navigate(R.id.action_nav_keep_to_keepAdd)
    }

    fun editKeep(keep: Keep) {
        keepViewModel.editingKeep(keep)
        findNavController().navigate(R.id.action_nav_keep_to_keepEdit)
    }

    fun deleteKeep(keep: Keep){
        keepViewModel.deleteKeep(keep)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKeepBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    private fun initVM() {
        binding!!.recyclerKeeps.layoutManager = GridLayoutManager(context, 2)
        val recyclerKeepsAdapter = RecyclerKeepsAdapter(this)
        binding!!.recyclerKeeps.adapter = recyclerKeepsAdapter
        keepViewModel.recyclerKeeps.observe(viewLifecycleOwner, {
            recyclerKeepsAdapter.data = it as ArrayList<Keep>
            recyclerKeepsAdapter.notifyDataSetChanged()
        })
    }

    private fun refreshData() {
        keepViewModel.fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}