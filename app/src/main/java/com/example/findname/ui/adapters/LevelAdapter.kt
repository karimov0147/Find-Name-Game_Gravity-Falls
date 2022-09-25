package com.example.findname.ui.adapters

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.Storage
import com.example.findname.R
import com.example.findname.databinding.ItemBinding
import com.example.findname.source.models.TaskModel


class LevelAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = ArrayList<TaskModel>()
    private var matrix = Storage.Matrix
    private var onClickListener: ((TaskModel) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return if (matrix[position] == 0) 0
        else matrix[position]
    }

    inner class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ItemBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(id: Int) {
            binding.textView.text = "$id-level"
            if (list[id - 1].state == "false") {
                binding.apply {
                    quality.visibility = View.INVISIBLE
                    levelUp.alpha = 1f
                    leftBorder.alpha = 1f
                }
            } else {
                binding.apply {
                    quality.visibility = View.VISIBLE
                    levelUp.alpha = 0.4f
                    leftBorder.alpha = 0.6f
                }
            }
            binding.root.setOnClickListener {
                it.elevation = 10f
                it.animate()
                    .scaleY(1000f)
                    .scaleX(1000f)
                    .setInterpolator(AccelerateInterpolator())
                    .setDuration(500)
                    .start()
                val colorAnimator = ObjectAnimator.ofArgb(
                    binding.cardView,
                    "CardBackgroundColor",
                    Color.parseColor("#FFEB3B"),
                    Color.BLACK
                )
                colorAnimator.interpolator = AccelerateInterpolator()
                colorAnimator.duration = 500
                colorAnimator.start()






                onClickListener?.invoke(list[id - 1])
            }
        }
    }

    inner class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType != 0) {
            return ViewHolder1(
                LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            )
        } else {
            return ViewHolder2(
                LayoutInflater.from(parent.context).inflate(R.layout.item_free, parent, false)
            )
        }
    }

    fun initClickListener(l: ((TaskModel) -> Unit)) {
        onClickListener = l
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            (holder as ViewHolder2)
        } else {
            (holder as ViewHolder1).bind(holder.itemViewType)
        }
    }

    override fun getItemCount(): Int = matrix.size

    fun submitList(arr: ArrayList<TaskModel>) {
        list.clear()
        list.addAll(arr)
        notifyDataSetChanged()
    }
}
