package ru.kiloqky.wakeup.view.keep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentKeepEditBinding
import ru.kiloqky.wakeup.rest.room.model.Keep
import ru.kiloqky.wakeup.viewmodels.KeepViewModel

class KeepEditFragment : Fragment(R.layout.fragment_keep_edit) {
    private var _binding: FragmentKeepEditBinding? = null

    lateinit var keep: Keep
    private val keepViewModel: KeepViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
    }


    override fun onDetach() {
        super.onDetach()
        editKeep()
//        val imm: InputMethodManager =
//            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKeepEditBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    private fun editKeep() {
        keep.keepBody = _binding!!.editKeepBody.text.toString()
        keep.keepTitle = _binding!!.editTitle.text.toString()
        keepViewModel.editKeep(keep)
    }

    private fun initVM() {
        keepViewModel.editingKeep.observe(viewLifecycleOwner, {
            keep = it
            _binding!!.editTitle.setText(keep.keepTitle, TextView.BufferType.EDITABLE)
            _binding!!.editKeepBody.setText(keep.keepBody, TextView.BufferType.EDITABLE)
        })
    }
}