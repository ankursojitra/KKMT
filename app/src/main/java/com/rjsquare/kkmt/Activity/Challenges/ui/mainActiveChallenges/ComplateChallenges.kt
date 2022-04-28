package com.rjsquare.kkmt.Activity.Challenges.ui.mainActiveChallenges

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Adapter.ActiveChallengesAdapter
import com.rjsquare.kkmt.Model.ActiveChallengesModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.FragmentComplateChallengesBinding

class ComplateChallenges : Fragment() {
    private lateinit var mRrComplatechallenges: RecyclerView
    private lateinit var mTxtNoComplatechallenges: TextView
    lateinit var mActiveChallengesModel: ActiveChallengesModel
    lateinit var mArray_ActiveChallengesModel: ArrayList<ActiveChallengesModel>
    lateinit var DB_ComplateChallenges: FragmentComplateChallengesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DB_ComplateChallenges = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_complate_challenges,
            container,
            false
        )
        var rootView = DB_ComplateChallenges.root
        try {

            framesData()
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
                DB_ComplateChallenges.txtNoComplatechallenges.visibility = View.GONE
            } else {
                DB_ComplateChallenges.txtNoComplatechallenges.visibility = View.VISIBLE
            }

            val loActiveChallengesAdapter: ActiveChallengesAdapter
            loActiveChallengesAdapter = ActiveChallengesAdapter(
                requireActivity(), mArray_ActiveChallengesModel
            )

            DB_ComplateChallenges.rrComplatechallenges.adapter = loActiveChallengesAdapter


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
            ComplateChallenges().apply {
                arguments = Bundle().apply {
                }
            }
    }
}