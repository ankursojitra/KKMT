package com.rjsquare.kkmt.Activity.Challenges.ui.mainActiveChallenges

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rjsquare.kkmt.Adapter.ActiveChallengesAdapter
import com.rjsquare.kkmt.Model.ActiveChallengesModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.FragmentChallengActiveBinding

class ChallengActive : Fragment() {

        lateinit var mArray_ActiveChallengesModel: ArrayList<ActiveChallengesModel>
    lateinit var mActiveChallengesModel: ActiveChallengesModel
    lateinit var DB_ChallengeActive: FragmentChallengActiveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        var lf = inflater.inflate(R.layout.fragment_challeng_active, container, false)
        DB_ChallengeActive =
            DataBindingUtil.inflate(inflater, R.layout.fragment_challeng_active, container, false)
        var rootView = DB_ChallengeActive.root
        try {

//        framesData()
            framesAdapter()
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
        return rootView
    }

    private fun framesData() {
        try {
            mArray_ActiveChallengesModel = ArrayList()
            mActiveChallengesModel = ActiveChallengesModel()

            mArray_ActiveChallengesModel.add(mActiveChallengesModel)
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

    }

    fun framesAdapter() {
        try {

            if (mArray_ActiveChallengesModel.size > 0) {
                DB_ChallengeActive.txtNoActivechallenges.visibility = View.GONE
            } else {
                DB_ChallengeActive.txtNoActivechallenges.visibility = View.VISIBLE
            }

            val loActiveChallengesAdapter: ActiveChallengesAdapter
//                if (mHomeModelArrayList_old == null) {
            loActiveChallengesAdapter = ActiveChallengesAdapter(
                requireActivity(), mArray_ActiveChallengesModel
            )

            DB_ChallengeActive.rrActivechallenges.adapter = loActiveChallengesAdapter


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
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ChallengActive().apply {
                arguments = Bundle().apply {

                }
            }
    }
}