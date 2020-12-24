package ru.kiloqky.wakeup.view.keep

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.android.ext.android.inject
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentKeepBinding
import ru.kiloqky.wakeup.rest.room.model.Keep
import ru.kiloqky.wakeup.view.keep.adapters.RecyclerKeepsAdapter
import ru.kiloqky.wakeup.viewmodels.KeepViewModel


class KeepFragment : Fragment(R.layout.fragment_keep) {
    private var binding: FragmentKeepBinding? = null

    private val keepViewModel: KeepViewModel by inject()
    private lateinit var recyclerKeepsAdapter:RecyclerKeepsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        initVM()
        refreshData()
        binding!!.fab.setOnClickListener {
            addKeep()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun initRV() {
        binding!!.recyclerKeeps.layoutManager = GridLayoutManager(context, 2)
        recyclerKeepsAdapter = RecyclerKeepsAdapter(
            {
                editKeep(it)
            }, {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("delete this keep?")
                    .setMessage("You want to delete this keep?")
                    .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                        deleteKeep(it)
                    }.setNegativeButton("No") { _: DialogInterface, _: Int -> }
                builder.show()
            })
        binding!!.recyclerKeeps.adapter = recyclerKeepsAdapter
    }

    private fun addKeep() {
        findNavController().navigate(R.id.action_nav_keep_to_keepAdd)

    }

    fun editKeep(keep: Keep) {
        keepViewModel.editingKeep(keep)
        findNavController().navigate(R.id.action_nav_keep_to_keepEdit)
    }

    fun deleteKeep(keep: Keep) {
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
        keepViewModel.recyclerKeeps.observe(viewLifecycleOwner, {
            recyclerKeepsAdapter.data = it
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