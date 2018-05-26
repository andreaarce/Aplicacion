package com.android.yknx4.andrea

import android.app.Fragment
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.fasterxml.uuid.Generators
import android.media.RingtoneManager
import android.media.Ringtone




class MainActivity : AppCompatActivity(), OnFragmentInteractionListener, OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: Reminder) {
         MaterialDialog.Builder(this)
                .title("Do you want to delete ${item.title}?")
                 .negativeText("Nein")
                .positiveText("Yes")
                 .onPositive { _, _ -> removeItem(item); reload() }
                 .show()
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var selectedFragment: Fragment? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        selectedFragment = when (item.itemId) {
            R.id.navigation_home -> {
                fab.hide()
                PlaceHolder.newInstance("a", "b")
            }
            R.id.navigation_dashboard -> {
                fab.hide()
                PlaceHolder.newInstance("a", "b")
            }
            R.id.navigation_notifications -> {
                fab.show()
                ReminderFragment.newInstance(1)
            }
            else -> {
                return@OnNavigationItemSelectedListener false
            }
        }
        fragmentManager.beginTransaction().replace(R.id.content_fragment, selectedFragment).commit()
        return@OnNavigationItemSelectedListener true
    }

    fun addItem(i: Reminder) {
        val current = Paper.book().read("reminders", ArrayList<Reminder>())
        current.add(i)
        i.setAlarms(this)
        Paper.book().write("reminders", current)
        Toast.makeText(this, "Added ${i.title}", Toast.LENGTH_SHORT).show()
    }

    fun removeItem(r : Reminder) {
        val current = Paper.book().read("reminders", ArrayList<Reminder>())
        val toRem = current.find { lr -> lr.id == r.id }
        r.cancelAlarm(this)
        current.remove(toRem)
        Paper.book().write("reminders", current)
        Toast.makeText(this, "Removed ${r.title}", Toast.LENGTH_SHORT).show()
    }

    fun reload() {
        val sFragment = selectedFragment
        if(sFragment is ReminderFragment){
            sFragment.reloadData()
        }
    }

    private val onFabClick = View.OnClickListener {
//        addItem()
        MaterialDialog.Builder(this)
                .title("Create a Reminder")
                .customView(R.layout.pick_time_dialog, true)
                .positiveText("Create")
                .negativeText("Cancel")
                .onPositive { dialog, _ ->
                    val timeBasedGenerator = Generators.timeBasedGenerator()
                    val timePicker = dialog.view.findViewById<TimePicker>(R.id.timePicker)
                    val newRem = Reminder(
                            timeBasedGenerator.generate(),
                            dialog.view.findViewById<EditText>(R.id.titleTV).text.toString(),
                            0,
                            dialog.view.findViewById<com.dpro.widgets.WeekdaysPicker>(R.id.weekdays).selectedDays,
                            timePicker.currentHour,
                            timePicker.currentMinute
                    )
                    addItem(newRem)
                    reload()
                }
                .show()
        reload()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager.beginTransaction().replace(R.id.content_fragment, PlaceHolder.newInstance("a", "b")).commit()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fab.setOnClickListener(onFabClick)
        if(intent.hasExtra("TOAST_MSG")) {
            Toast.makeText(this, intent.getStringExtra("TOAST_MSG"), Toast.LENGTH_LONG).show()
            try {
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val r = RingtoneManager.getRingtone(applicationContext, notification)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
