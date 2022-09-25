package com.example.findname.ui.screens.impl

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.findname.R
import com.example.findname.databinding.DialogDownloadBinding
import com.example.findname.databinding.ScreenMenuBinding
import com.example.findname.ui.presenters.MenuPresenter
import com.example.findname.ui.presenters.impl.MenuPresenterImpl
import com.example.findname.ui.screens.MenuScreen
import kotlinx.coroutines.*


class MenuScreenImpl : Fragment(R.layout.screen_menu) , MenuScreen{
    private val binding by viewBinding( ScreenMenuBinding::bind )
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val presenter : MenuPresenter = MenuPresenterImpl(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onStartView()






        binding.startButton.setOnClickListener{
            presenter.onClickStartBtn()

        }

    }
    override fun goToLevelScreen() {

        var downloadView = View.inflate(requireContext() , R.layout.dialog_download , null)
        var dialogBind = DialogDownloadBinding.bind(downloadView)
        var dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(downloadView)
        var dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

                val anim = ValueAnimator.ofInt(5, 95)
                anim.duration = 2000
                anim.interpolator = AccelerateDecelerateInterpolator()
                anim.addUpdateListener { animation ->
                    val animProgress = animation.animatedValue as Int
//                    Toast.makeText(requireContext(), animProgress.toString(), Toast.LENGTH_SHORT).show()
                    dialogBind.seekBar.progress = animProgress
                    if (animation.animatedValue ==95){
                        dialog.dismiss()
                        navController.navigate(R.id.action_menuScreenImpl_to_levelsScreenImpl)
                    }
                }
                anim.start()

//
//
//            }
//
////            dialog.dismiss()
//
//
//        }


    }

    override fun startAnimation() {
        binding.startButton.apply {
            scaleX = 0f
            scaleY = 0f
        }
        binding.startButton.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        binding.logo1.translationX = -200f
        binding.logo2.translationX = 200f

        binding.logo1.animate()
            .translationX(0f)
            .setDuration(1000)
            .start()
        binding.logo2.animate()
            .translationX(0f)
            .setDuration(1000)
            .start()

    }

}