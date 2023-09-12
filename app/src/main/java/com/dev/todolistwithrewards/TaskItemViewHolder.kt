package com.dev.todolistwithrewards

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev.todolistwithrewards.databinding.TaskItemCellBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val color = ContextCompat.getColor(context, R.color.custom_red)
    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name
        binding.score.text = taskItem.score.toString()
        binding.score.setTextColor(Color.parseColor("#FF0786CA"))
        binding.score.setBackgroundResource(R.drawable.yellow_circle)

        resizeBackgroundImage(binding.score.background)


        if (taskItem.isCompleted()) {
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.score.setBackgroundResource(R.drawable.light_green_circle)
            resizeBackgroundImage(binding.score.background)
            taskItem.completedDate = LocalDate.now()
            binding.completedTime.text =
                buildString {
                    append(taskItem.completedDate!!.dayOfMonth.toString())
                    append(".")
                    append(taskItem.completedDate!!.monthValue.toString())
                    append(".")
                    append(taskItem.completedDate!!.year)
                }
            binding.completedTime.setTextColor(Color.parseColor("#FF0786CA"))
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }

        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        /*if (taskItem.dueTime != null) {
            binding.dueTime.text = timeFormatter.format(taskItem.dueTime)
        } else
            binding.dueTime.text = ""*/
    }

    private fun resizeBackgroundImage(background: Drawable?) {
        val currentBackgroundDrawable: Drawable? = background
        if (currentBackgroundDrawable is BitmapDrawable) {
            // Get the desired width and height in pixels
            val desiredWidthPx = context.resources.getDimensionPixelSize(R.dimen.desired_width)
            val desiredHeightPx = context.resources.getDimensionPixelSize(R.dimen.desired_height)

            // Resize the drawable to the desired dimensions
            val resizedDrawable = BitmapDrawable(
                context.resources,
                Bitmap.createScaledBitmap(
                    currentBackgroundDrawable.bitmap,
                    desiredWidthPx,
                    desiredHeightPx,
                    false
                )
            )

            // Set the resized drawable as the new background
            binding.score.background = resizedDrawable
            binding.score.gravity = Gravity.CENTER
        }
    }
}