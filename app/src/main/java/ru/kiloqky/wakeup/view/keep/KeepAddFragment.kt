package ru.kiloqky.wakeup.view.keep

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentKeepEditBinding
import ru.kiloqky.wakeup.rest.room.keep.model.Keep
import ru.kiloqky.wakeup.viewmodels.KeepViewModel

class KeepAddFragment : Fragment(R.layout.fragment_keep_edit) {
    private lateinit var binding: FragmentKeepEditBinding

    lateinit var keep: Keep
    private val keepViewModel: KeepViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keep = Keep()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKeepEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        keep.keepBody = binding.editKeepBody.text.toString()
        keep.keepTitle = binding.editTitle.text.toString()
        if (keep.keepTitle.isNotBlank()) {
            keepViewModel.addKeep(keep)
        }
        val imm: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 1)
    }
}
