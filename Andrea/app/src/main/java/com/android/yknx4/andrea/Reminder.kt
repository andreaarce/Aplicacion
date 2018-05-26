package com.android.yknx4.andrea

import android.app.PendingIntent
import java.util.*
import android.app.AlarmManager
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent


/**
 * Created by yknx4 on 3/4/18.
 */

class Reminder(val id: UUID, val title: String, val time: Long, var selectedDays: List<Int>, val hour: Int, val min: Int) {
    fun generateAlarmIntents(context: Context): List<Pair<PendingIntent, Calendar>> {
        return selectedDays.map({day ->
            val appIntent = Intent(context, MainActivity::class.java)
            appIntent.putExtra("TOAST_MSG", title)
            val alarmIntent = PendingIntent.getActivity(context, id.hashCode()+day, appIntent, PendingIntent.FLAG_UPDATE_CURRENT)


            // Set the alarm to start at approximately 2:00 p.m.
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            if(calendar.get(Calendar.DAY_OF_WEEK) > day) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1)
            }
            calendar.set(Calendar.DAY_OF_WEEK, day)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, min)
            return@map Pair(alarmIntent, calendar)
        })

    }
    fun setAlarms(context: Context) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarms = generateAlarmIntents(context)
        alarms.forEach({a->
            val alarmIntent = a.first
            val calendar = a.second
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY * 7, alarmIntent)
        })
    }

    fun cancelAlarm(context: Context) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarms = generateAlarmIntents(context)
        alarms.forEach({a->
            val alarmIntent = a.first
            alarmMgr.cancel(alarmIntent)
        })

    }
}
