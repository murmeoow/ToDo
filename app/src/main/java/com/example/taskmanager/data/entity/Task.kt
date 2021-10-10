package com.example.taskmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val _id : Int?,
    val taskName : String,
    val taskDescription: String,
    val taskDate : Date,
    val status: Boolean
    )