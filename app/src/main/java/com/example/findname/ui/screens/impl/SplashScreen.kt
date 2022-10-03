package com.example.findname.ui.screens.impl

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.findname.R
import com.example.findname.databinding.ScreenSplashBinding
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private var state = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.logo.animate()
            .alpha(1f)
            .setDuration(800)
            .withEndAction {
                binding.logo.animate()
                    .alpha(0f)
                    .setDuration(800)
                    .start()
            }.start()




        CoroutineScope(Dispatchers.Main).launch {
            delay(1600)
            withContext(Dispatchers.Main) {
                navController.navigate(R.id.action_splashScreen_to_menuScreenImpl)
                state = false
            }
        }




    }

    override fun onPause() {
        super.onPause()
        if (state){
            navController.navigate(R.id.action_splashScreen_to_menuScreenImpl)
        }



    }





}