package ru.netology.motivationlist.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.motivationlist.R
import ru.netology.motivationlist.adapter.MotivationAdapter
import ru.netology.motivationlist.adapter.OnInteractionListener
import ru.netology.motivationlist.databinding.FragmentFeedBinding
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.viewModel.MotivationViewModel

class FeedFragment: Fragment() {

    companion object {
        const val KEY_PARSE_DATA = "key1"
        val bundle = Bundle()
    }


    private val viewModel: MotivationViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = MotivationAdapter(object : OnInteractionListener {
//            override fun onEdit(post: Post) {
//                viewModel.editPost(post)
//
//            }

            override fun onLikeUp(motivation: Motivation) {
                viewModel.likeUp(motivation.id)

            }override fun onLikeDown(motivation: Motivation) {
                viewModel.likeDown(motivation.id)
            }

            override fun onRemove(motivation: Motivation) {
                viewModel.remove(motivation.id)
            }

            // неявный интент - открытие ссылки
//            override fun onUrlContent(motivation: Motivation) {
//                val url: String = motivation.urlContent
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                startActivity(intent)
//
//            }

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

        viewModel.data.observe(viewLifecycleOwner) { motivations ->
            adapter.submitList(motivations)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newMotivationFragment)


        }

        viewModel.edited.observe(viewLifecycleOwner) { motivation ->
            if (motivation.id == 0L) {
                return@observe
            }
            //bundle.putParcelable(KEY_PARSE_DATA, motivation)
            //findNavController().navigate(R.id.action_feedFragment_to_editFragment2, bundle)
            //findNavController().navigate(R.id.action_feedFragment_to_newPostFragment4, bundle)

        }
        return binding.root
    }
}