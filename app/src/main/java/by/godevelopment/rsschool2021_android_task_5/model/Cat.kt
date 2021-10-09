package by.godevelopment.rsschool2021_android_task_5.model

import java.io.Serializable

data class Cat(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
) : Serializable
