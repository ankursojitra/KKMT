package com.rjsquare.kkmt.Fragment

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.FragmentEmployeeHistoryBinding


class EmployeeHistory : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    //    private lateinit var mCircularProgress2: CircularProgressIndicator
//    private lateinit var mCircularProgress3: CircularProgressIndicator
//    private lateinit var mCircularProgress4: CircularProgressIndicator
    lateinit var DB_FEmployeeHistory: FragmentEmployeeHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        DB_FEmployeeHistory =
            DataBindingUtil.inflate(inflater, R.layout.fragment_employee_history, container, false)
//        var rootview = inflater.inflate(R.layout.fragment_employee_history, container, false)
        try {
//        var circularProgress =
//            rootview.findViewById<CircularProgressIndicator>(R.id.circular_progress1);
//
//        mCircularProgress2 =
//            rootview.findViewById<CircularProgressIndicator>(R.id.circular_progress2)
//        mCircularProgress3 =
//            rootview.findViewById<CircularProgressIndicator>(R.id.circular_progress3)
//        mCircularProgress4 =
//            rootview.findViewById<CircularProgressIndicator>(R.id.circular_progress4)

            DB_FEmployeeHistory.circularProgress1.maxProgress = 100.0
            DB_FEmployeeHistory.circularProgress1.setCurrentProgress(70.0)
            DB_FEmployeeHistory.circularProgress1.setProgress(70.0, 100.0)
            DB_FEmployeeHistory.circularProgress1.progress // returns 70
            DB_FEmployeeHistory.circularProgress1.maxProgress // returns 100

            DB_FEmployeeHistory.circularProgress2.maxProgress = 100.0
            DB_FEmployeeHistory.circularProgress2.setCurrentProgress(50.0)

            DB_FEmployeeHistory.circularProgress3.maxProgress = 100.0
            DB_FEmployeeHistory.circularProgress3.setCurrentProgress(30.0)

            DB_FEmployeeHistory.circularProgress4.maxProgress = 100.0
            DB_FEmployeeHistory.circularProgress4.setCurrentProgress(10.0)

            HomeActivity.mCntLoader.visibility = View.GONE
            EmpHistoryView = true
        } catch (NE: NullPointerException) {
            NE.printStackTrace()
        } catch (IE: IndexOutOfBoundsException) {
            IE.printStackTrace()
        } catch (AE: ActivityNotFoundException) {
            AE.printStackTrace()
        } catch (E: IllegalArgumentException) {
            E.printStackTrace()
        } catch (RE: RuntimeException) {
            RE.printStackTrace()
        } catch (E: Exception) {
            E.printStackTrace()
        }
        return DB_FEmployeeHistory.root
    }

    companion object {
        var EmpHistoryView = false

        @JvmStatic
        fun newInstance(

        ) =
            EmployeeHistory().apply {
                arguments = Bundle().apply {

                }
            }
    }
}