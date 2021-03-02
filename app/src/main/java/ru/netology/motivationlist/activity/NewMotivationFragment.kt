package ru.netology.motivationlist.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.motivationlist.R
import ru.netology.motivationlist.databinding.FragmentNewMotivationBinding
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.util.AndroidUtils
import ru.netology.motivationlist.viewModel.MotivationViewModel

class NewMotivationFragment : Fragment() {

    private val viewModel: MotivationViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewMotivationBinding.inflate(inflater, container, false)

//        val details = arguments?.getParcelable<Motivation>(FeedFragment.KEY_PARSE_DATA)
//        binding.newContent.setText(details?.content)
//        binding.newUrlContent.setText(details?.urlContent)
//        binding.newUrlImage.setText(details?.urlImage)
//        binding.newAuthor.setText(details?.author)

        binding.ok.setOnClickListener {
            if (!binding.newContent.text.isNullOrBlank()) {
                viewModel.changeContent(binding.newContent.text.toString())
                viewModel.changeUrlContent(binding.newUrlContent.text.toString())
                viewModel.changeUrlImage(binding.newUrlImage.text.toString())
                viewModel.changeAuthor(binding.newAuthor.text.toString())

                viewModel.saveMotivation()
                findNavController().navigate(R.id.action_newMotivationFragment_to_feedFragment)
            } else {
                Snackbar.make(binding.root, R.string.error_empty_content, BaseTransientBottomBar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok) {
                            findNavController().navigate(R.id.action_newMotivationFragment_to_feedFragment)
                        }
                        .show()
            }

            AndroidUtils.hideKeyboard(requireView())
        }
        return binding.root
    }

}