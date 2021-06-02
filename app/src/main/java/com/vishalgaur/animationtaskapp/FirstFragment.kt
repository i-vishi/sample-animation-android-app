package com.vishalgaur.animationtaskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.Group
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

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// handle back press
		activity?.onBackPressedDispatcher?.addCallback(
			this,
			object : OnBackPressedCallback(true) {
				override fun handleOnBackPressed() {
					when (currState) {
						2 -> {
							slideDownAnimation(binding.card2)
							contentHideAnimation(binding.card1SummaryGroup, 0)
							contentShowAnimation(binding.card1ContentGroup, 800)
							binding.card1Btn.text = getString(R.string.card1_btn_text)
							currState = 1
						}
						3 -> {
							slideDownAnimation(binding.card3)
							binding.card1Btn.text = getString(R.string.card2_btn_text)
							currState = 2
						}
						else -> {
							this.remove()
							activity?.onBackPressed()
						}
					}
				}
			})
	}

	private fun setViews() {
		binding.card2.visibility = View.INVISIBLE
		binding.card3.visibility = View.INVISIBLE
		contentShowAnimation(binding.card1ContentGroup, 0)
		binding.card1SummaryGroup.visibility = View.INVISIBLE
		binding.card1Btn.text = getString(R.string.card1_btn_text)
		binding.card1Btn.setOnClickListener {
			currState = when (currState) {
				1 -> {
					slideUpAnimation(binding.card2)
					contentHideAnimation(binding.card1ContentGroup, 0)
					contentShowAnimation(binding.card1SummaryGroup, 800)
					binding.card1Btn.text = getString(R.string.card2_btn_text)
					2
				}
				2 -> {
					slideUpAnimation(binding.card3)
					binding.card1Btn.text = getString(R.string.card3_btn_text)
					3
				}
				else -> {
					slideDownAnimation(binding.card3)
					slideDownAnimation(binding.card2)
					binding.card1Btn.text = getString(R.string.card1_btn_text)
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

	private fun contentShowAnimation(viewGroup: Group, timeOffset: Int) {
		val allViews = viewGroup.referencedIds.map { view?.findViewById<View>(it) }
		val animate = AlphaAnimation(0F, 1F)
		animate.duration = 750
		animate.fillAfter = true
		animate.startOffset = timeOffset + animate.startOffset
		allViews.forEach {
			it?.startAnimation(animate)
		}
//		viewGroup.startAnimation(animate)
		viewGroup.visibility = View.VISIBLE
	}

	private fun contentHideAnimation(viewGroup: Group, timeOffset: Int) {
		val allViews = viewGroup.referencedIds.map { view?.findViewById<View>(it) }
		val animate = AlphaAnimation(1F, 0F)
		animate.duration = 750
		animate.fillAfter = true
		animate.startOffset = timeOffset + animate.startOffset
		allViews.forEach {
			it?.startAnimation(animate)
		}
//		viewGroup.startAnimation(animate)
		viewGroup.visibility = View.GONE
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}