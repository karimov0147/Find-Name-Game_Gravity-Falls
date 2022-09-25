package com.example.findname.ui.screens.impl

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.findname.R
import com.example.findname.databinding.DialogAnswerBinding
import com.example.findname.databinding.DialogHelpBinding
import com.example.findname.databinding.DialogWinCustomBinding
import com.example.findname.databinding.ScreenGameBinding
import com.example.findname.repository.impl.GameRepositoryImpl
import com.example.findname.source.models.TaskModel
import com.example.findname.source.utills.Presets
import com.example.findname.ui.presenters.GamePresenter
import com.example.findname.ui.presenters.impl.GamePresenterImpl
import com.example.findname.ui.screens.GameScreen
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class GameScreenImpl : Fragment(R.layout.screen_game) , GameScreen  {

    private val binding by viewBinding(ScreenGameBinding :: bind)

    private lateinit var task :TaskModel
    var answerArrayList = ArrayList<AppCompatButton>()
    var variantArrayList = ArrayList<AppCompatButton>(12)
    var typedAnswer = ArrayList<String?>()
    var typedVariants = ArrayList<Boolean>(12)
    private val args : GameScreenImplArgs by navArgs()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val presenter : GamePresenter = GamePresenterImpl(GameRepositoryImpl.getInstance()!!, this )


    override fun onViewCreated(mainView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainView, savedInstanceState)
        binding.container.apply{
            scaleX = 100f
            scaleY = 100f

            this.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
                .start()
        }

        binding.help.setOnClickListener {
            presenter.onHelpClicked()
        }



        presenter.getStartLevel(args.task)
        loadAnswerHolder(task.answer.length)
        presenter.initViews(task)
//        variantArrayList = ArrayList<AppCompatButton>(task.answer.length)
        variantArrayList.addAll(getButtonsFromGroup(0 , binding.letters1 , onClickVariant))
        variantArrayList.addAll(getButtonsFromGroup(6 , binding.leters2 , onClickVariant))
        binding.answerLine2.visibility = View.INVISIBLE
        answerArrayList.addAll(getButtonsFromGroup(0 , binding.answerLine1 ,onClickAnswer))
        if (task.answer.length > 6)
            binding.answerLine2.visibility = View.VISIBLE
        answerArrayList.addAll(getButtonsFromGroup(6 , binding.answerLine2 , onClickAnswer))

        initQuaestion()


    }


    override fun initViews(task: TaskModel , score : Int) {
        binding.level.text = "Level ${task.id.toString()}"
        binding.score.text = score.toString()
    }

    override fun loadFirstQuestion(task: TaskModel) {
        this.task = task
    }


    override fun loadQuestion(task: TaskModel) {
//        this.task = task
        navController.navigate(GameScreenImplDirections.actionGameScreenImplSelf(task))
//        answerArrayList.clear()
//        loadList()

    }

    override fun onCorrectAnswer() {
        val customView = View.inflate(requireContext() , R.layout.dialog_win_custom  , null)
        val bindCustomView = DialogWinCustomBinding.bind(customView)
//        val bindCustomView = DialogWinCustomBinding.inflate(LayoutInflater.from(requireContext()) , null , false )
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(bindCustomView.root)
        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                dialog.show()
            }
        }

        bindCustomView.menuBtn.setOnClickListener{
            dialog.dismiss()
            presenter.onBack()
        }
        bindCustomView.nextBtn.setOnClickListener {

            if (task.id == 27) {
                Toast.makeText(requireContext(), "Game Over", Toast.LENGTH_SHORT).show()
            } else {
                presenter.loadTask(task.id)
                initQuaestion()
            }
            dialog.dismiss()
        }



    }

    override fun onWrongAnswer() {
        if (Build.VERSION.SDK_INT >= 26) {
            (requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(
                VibrationEffect.createWaveform(LongArray(4){110}, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            (requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(150)
        }
        val snackBar = Snackbar.make(binding.root, "Wrong Answer", Snackbar.LENGTH_LONG)
        snackBar.anchorView = binding.image
        snackBar.setBackgroundTint(Color.RED)
        snackBar.show()
    }

    override fun onHelpClicked() {
        val customView = View.inflate(requireContext() , R.layout.dialog_help , null)
        val dialogBind = DialogHelpBinding.bind(customView)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(customView)
        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        dialogBind.apply {
            oneAnswer.setOnClickListener {
                if (presenter.getScore() >= 20){
                    presenter.setScore(presenter.getScore()-20)
                    binding.score.text = presenter.getScore().toString()
                    presenter.onAnswerClicked()
                    dialog.dismiss()
                }
                else{
                    dialog.dismiss()
                    val snackBar = Snackbar.make(binding.root, "Insufficient funds", Snackbar.LENGTH_SHORT)
                    snackBar.anchorView = binding.image
                    snackBar.setBackgroundTint(Color.RED)
                    snackBar.show()
                }
            }
            allAnswers.setOnClickListener {
                presenter.onShareClicked()
                dialog.dismiss()
            }
        }




    }

    override fun onAnswerOpenClicked() {
        val answerView = View.inflate(requireContext() , R.layout.dialog_answer , null)
        val answerBind = DialogAnswerBinding.bind(answerView)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(answerView)
        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        answerBind.textView6.text = "  ${task.answer}  "
        dialog.show()

        answerBind.close.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onShareClicked() {
        val intent2: Intent = Intent()
        intent2.setAction (Intent.ACTION_SEND)
        intent2.type = "text/plain"
        intent2.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.disney.disneyplus&hl=ru&gl=US")
        startActivity(Intent.createChooser(intent2, "Share via"))
    }

    override fun onBack() {
        navController.navigateUp()
    }

    override fun startFireWorks() {
        binding.konnfetiView.start(
            Presets.mix()
        )
        if (Build.VERSION.SDK_INT >= 26) {
            (requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(
                VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            (requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(150)
        }
    }


//    private fun task: TaskModel{
//        return Storage.list[level]
//    }

























    private fun getButtonsFromGroup(startIndex : Int , viewGroupId : View , onClickListener: View.OnClickListener ) : ArrayList<AppCompatButton> {
        var viewGroup: ViewGroup = viewGroupId as ViewGroup
        var buttons = ArrayList<AppCompatButton>(viewGroup.childCount)

        for (i in 0 until viewGroup.childCount) {
            var button = viewGroup.getChildAt(i) as AppCompatButton
            button.setOnClickListener(onClickListener)
            button.tag = i + startIndex
            buttons.add(button)
        }
        return buttons
    }

    private var onClickVariant =  fun(v:View?) {
       if (task.id > 27) return

       val index = v!!.tag as Int

       val indexAnswer: Int = typedAnswer.indexOf(null)
       if (indexAnswer == -1) return

       val task = task
       var variant = task.variants
       val text: String = java.lang.String.valueOf(variant[index])
       answerArrayList[indexAnswer].text = text
       v.visibility = View.INVISIBLE
       typedAnswer[indexAnswer] = text
       typedVariants[index] = false
       checkWinner()
   }

    private var onClickAnswer = fun (v: View?) {
        if (task.id > 27) return
        val index = v?.tag as Int
        val text: String = typedAnswer[index].toString()

        val task = task
        var variant = task.variants

        for (i in 0 until 12) {
            val textVariant = variant[i].toString()
            if (!typedVariants[i] && textVariant == text) {
                answerArrayList[index].text = ""
                variantArrayList[i].visibility = View.VISIBLE
                typedAnswer[index] = null
                typedVariants[i] = true
                return
            }
        }


    }

    fun loadAnswerHolder(size : Int){
        var viewGroup = binding.answerLine1
        var viewGroup2 = binding.answerLine2

//        viewGroup.removeAllViewsInLayout()
//        viewGroup2.removeAllViewsInLayout()



        if (size <= 6){
            for (i in 0 until size){
                var view = LayoutInflater.from(requireContext()).inflate(R.layout.answer_holder , viewGroup , false)
                viewGroup.addView(view)
            }
        }
        else if (size>6){
            var line1 = size -6
            for (i in 0 until 6){
                var view = LayoutInflater.from(requireContext()).inflate(R.layout.answer_holder , viewGroup , false)
                viewGroup.addView(view)
            }
            for (i in 0 until line1){
                var view = LayoutInflater.from(requireContext()).inflate(R.layout.answer_holder , viewGroup , false)
                viewGroup2.addView(view)
            }
        }



    }

    private fun initQuaestion(){
        var task = task
        var answer = task.answer
        var variants = task.variants
        presenter.initViews(task)
//        binding.score.text
        /**
         * image set
         */
        val id: Int = resources.getIdentifier("${task.image}", "drawable", requireContext().packageName)
        Glide.with(binding.image)
            .load(id)
            .into(binding.image)

        typedAnswer.clear()




        for (i in 0 until task.answer.length){
            answerArrayList
            val state = i < task.answer.length
            val button = answerArrayList[i]
            button.visibility = if (state) View.VISIBLE else View.GONE
            button.text = ""
            if (state)
                typedAnswer.add(null)
        }

        for (i in 0 until 12) {
            val button: Button = variantArrayList[i]
            button.text = variants[i].toString()
            button.visibility = View.VISIBLE
            typedVariants.add(true)
        }
    }

    private fun checkWinner() {
        val task = task
        val answer: String = task.answer
        val typedAnswerLetter = StringBuilder()
        for (i in typedAnswer.indices) {
            typedAnswerLetter.append(typedAnswer[i])
        }
        val userAnswer = typedAnswerLetter.toString()
        if (userAnswer.length != answer.length) return
        if (userAnswer == answer) {
            presenter.setScore(presenter.getScore()+10)
            presenter.onCorrectAnswer()
            presenter.startFireWorks()
            presenter.onCompleteLevel(task.id)

        } else {
            presenter.onWrongAnswer()
        }
    }








//private fun initView(){
//    binding.apply {
//        letter1.setOnClickListener(this@GameScreenImpl)
//        letter2.setOnClickListener(this@GameScreenImpl)
//        letter4.setOnClickListener(this@GameScreenImpl)
//        letter3.setOnClickListener(this@GameScreenImpl)
//        letter5.setOnClickListener(this@GameScreenImpl)
//        letter6.setOnClickListener(this@GameScreenImpl)
//        letter7.setOnClickListener(this@GameScreenImpl)
//        letter8.setOnClickListener(this@GameScreenImpl)
//        letter9.setOnClickListener(this@GameScreenImpl)
//        letter10.setOnClickListener(this@GameScreenImpl)
//        letter11.setOnClickListener(this@GameScreenImpl)
//        letter12.setOnClickListener(this@GameScreenImpl)
//
//
//    }
//}

//
//private fun addHolder (legth : Int){
//    var viewGroup = binding.answerLine1
//    for (i in 0 until legth){
//        var view = LayoutInflater.from(requireContext()).inflate(R.layout.answer_holder , viewGroup , false)
//        view.setOnClickListener(this)
//        viewGroup.addView(view)
//    }
}