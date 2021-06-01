package com.vishalgaur.animationtaskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import com.vishalgaur.animationtaskapp.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

	private var _binding: FragmentFirstBinding? = null

	private var currState = 1

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		_binding = FragmentFirstBinding.inflate(inflater, container, false)

		setViews()
		return binding.root

	}

	private fun setViews() {
		binding.card2.visibility = View.INVISIBLE
		binding.card1Btn.text = getString(R.string.card1_btn_text)
		binding.card1Btn.setOnClickListener {
			currState = when (currState) {
				1 -> {
					slideUpAnimation(binding.card2)
					2
				}
				else -> {
					slideDownAnimation(binding.card2)
					1
				}
			}
		}
	}

	private fun slideUpAnimation(view: View) {
		val animate = TranslateAnimation(0F, 0F, view.height.toFloat(), 0F)
		animate.duration = 500
		view.startAnimation(animate)
		view.visibility = View.VISIBLE
	}

	private fun slideDownAnimation(view: View) {
		val animate = TranslateAnimation(0F, 0F, 0F, view.height.toFloat())
		animate.duration = 500
		view.startAnimation(animate)
		view.visibility = View.GONE
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}