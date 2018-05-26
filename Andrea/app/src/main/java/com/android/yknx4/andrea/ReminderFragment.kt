package com.android.yknx4.andrea

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_reminder_list.*

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class ReminderFragment : Fragment() {
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         val mainView = inflater!!.inflate(R.layout.fragment_reminder_list, container, false)

        if (mainView is RecyclerView) {
            val context = mainView.context
            if (mColumnCount <= 1) {
                mainView.layoutManager = LinearLayoutManager(context)
            } else {
                mainView.layoutManager = GridLayoutManager(context, mColumnCount)
            }
            reloadData(mainView)
        }
        return mainView
    }

    fun reloadData(l:RecyclerView? = null) {
        val lv = listOf(l, list).filterNotNull().first()
        lv.adapter = MyReminderRecyclerViewAdapter(Paper.book().read("reminders", ArrayList<Reminder>()), mListener)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }



    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): Fragment {
            val fragment = ReminderFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
