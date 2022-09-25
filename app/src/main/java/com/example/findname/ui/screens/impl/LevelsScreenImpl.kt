package com.example.findname.ui.screens.impl

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.Storage
import com.example.findname.R
import com.example.findname.databinding.ScreenLevelsBinding
import com.example.findname.repository.impl.GameRepositoryImpl
import com.example.findname.source.models.TaskModel
import com.example.findname.ui.adapters.LevelAdapter
import com.example.findname.ui.presenters.LevelsPresenter
import com.example.findname.ui.presenters.impl.LevelsPresenterImpl
import com.example.findname.ui.screens.LevelsScreen
import kotlinx.coroutines.*

class LevelsScreenImpl : Fragment(R.layout.screen_levels) ,LevelsScreen{

    private val binding by viewBinding( ScreenLevelsBinding::bind )
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private var adapter : LevelAdapter = LevelAdapter()
    private val presenter : LevelsPresenter = LevelsPresenterImpl(GameRepositoryImpl.getInstance()!! ,this )


    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initUiComponents()
        binding.RecyclerView.adapter = adapter
        adapter.initClickListener {
            presenter.onClickLevel(it)

        }
    }

    override fun initAdapter(list : List<TaskModel>) {
                var array = ArrayList<TaskModel>()
                array.clear()
                array.addAll(list)
                adapter.submitList(array)



//        Toast.makeText(requireContext(), list.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun goToGameScreen(taskModel: TaskModel) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(600)
            withContext(Dispatchers.Main) {
                navController.navigate(LevelsScreenImplDirections.actionLevelsScreenImplToGameScreenImpl(taskModel))
            }
        }
    }






}