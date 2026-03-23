package com.example.unchain.domain.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun isSameDay(time1 : Long, time2 : Long) : Boolean{
    val day1 = Calendar.getInstance().apply { timeInMillis = time1 }
    val day2 = Calendar.getInstance().apply { timeInMillis = time2 }

    //return day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR) && day1.get(Calendar.DAY_OF_YEAR) == day2.get(Calendar.DAY_OF_YEAR)
    return false
}

fun formattingStreak(value : Int) : String{
    if(value == 1 ){
       return "$value day"
    }
    return "$value days"
}

fun formattingDate(date : Long) : String{
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(Date(date))
}