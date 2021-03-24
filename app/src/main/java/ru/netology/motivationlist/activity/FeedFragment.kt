package ru.netology.motivationlist.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.motivationlist.R
import ru.netology.motivationlist.adapter.MotivationAdapter
import ru.netology.motivationlist.adapter.OnInteractionListener
import ru.netology.motivationlist.databinding.FragmentFeedBinding
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.entity.MotivationEntity


import ru.netology.motivationlist.viewModel.MotivationViewModel


class FeedFragment : Fragment() {

    companion object {
    }

    private val viewModel: MotivationViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = MotivationAdapter(object : OnInteractionListener {

            override fun onFilterNameAuthor(motivation: Motivation) {
                viewModel.isClickName(motivation)

                Snackbar.make(
                    binding.root,
                    R.string.filter_name,
                    BaseTransientBottomBar.LENGTH_INDEFINITE
                )
                    .setAction(android.R.string.ok) {
                        viewModel.removeFilter()
                    }.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    .show()

            }

            override fun onLikeUp(motivation: Motivation) {
                viewModel.likeUp(motivation.id)
            }

            override fun onLikeDown(motivation: Motivation) {
                viewModel.likeDown(motivation.id)
            }

            override fun onRemove(motivation: Motivation) {
                viewModel.remove(motivation.id)
            }

            // неявный интент - отправка текста в сообщение чата
            override fun onShare(motivation: Motivation) {
                viewModel.share(motivation.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, motivation.author + "\n" + motivation.content)
                    //putExtra(Intent.EXTRA_TEXT, motivation.author)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)

            }
        })

        binding.recyclerview.adapter = adapter

        viewModel.mediator.observe(viewLifecycleOwner) {}

        viewModel.dataName.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newMotivationFragment)

        }

        return binding.root
    }
}