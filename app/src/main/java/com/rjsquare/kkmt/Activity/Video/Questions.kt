package com.rjsquare.kkmt.Activity.Video

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.VideoDetail_Model
import com.rjsquare.kkmt.databinding.ActivityQuestionsBinding


class Questions : AppCompatActivity(), View.OnClickListener {

    var SelectedOption = 0
    var QuestionNo = 0
    var TotalQUestions = 0
    var QuistionsOver = false
    lateinit var QuistionInfo: VideoDetail_Model.VideoDetail.QuestionData

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_Question: ActivityQuestionsBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Question = DataBindingUtil.setContentView(this, R.layout.activity_questions)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            DB_Question.txtNextquestion.setOnClickListener(this)

            TotalQUestions = VideoPlayer.VideoData.question!!.size

            SetUpQuestion(QuestionNo)
            DB_Question.cntWatchagain.setOnClickListener(this)
            DB_Question.cntWatchmore.setOnClickListener(this)
            DB_Question.txtAlertok.setOnClickListener(this)
            DB_Question.rdOption1.setOnCheckedChangeListener(object :
                CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if (isChecked) {
                        DB_Question.rdOption2.isChecked = false
                        DB_Question.rdOption3.isChecked = false
                        DB_Question.rdOption4.isChecked = false
                        SelectedOption = 1

                        DeSelectTextColor()
                        DB_Question.rdOption1.setTextColor(
                            ContextCompat.getColor(
                                this@Questions,
                                R.color.radio_green
                            )
                        )
                    }
                }
            })
            DB_Question.rdOption2.setOnCheckedChangeListener(object :
                CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if (isChecked) {
                        DB_Question.rdOption1.isChecked = false
                        DB_Question.rdOption3.isChecked = false
                        DB_Question.rdOption4.isChecked = false
                        SelectedOption = 2
                        DeSelectTextColor()
                        DB_Question.rdOption2.setTextColor(
                            ContextCompat.getColor(
                                this@Questions,
                                R.color.radio_green
                            )
                        )
                    }
                }
            })
            DB_Question.rdOption3.setOnCheckedChangeListener(object :
                CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if (isChecked) {
                        DB_Question.rdOption1.isChecked = false
                        DB_Question.rdOption2.isChecked = false
                        DB_Question.rdOption4.isChecked = false
                        SelectedOption = 3
                        DeSelectTextColor()
                        DB_Question.rdOption3.setTextColor(
                            ContextCompat.getColor(
                                this@Questions,
                                R.color.radio_green
                            )
                        )
                    }
                }
            })
            DB_Question.rdOption4.setOnCheckedChangeListener(object :
                CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if (isChecked) {
                        DB_Question.rdOption1.isChecked = false
                        DB_Question.rdOption2.isChecked = false
                        DB_Question.rdOption3.isChecked = false
                        SelectedOption = 4
                        DeSelectTextColor()
                        DB_Question.rdOption4.setTextColor(
                            ContextCompat.getColor(
                                this@Questions,
                                R.color.radio_green
                            )
                        )
                    }
                }
            })

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

    private fun CheckAns(SelectedOption: Int) {
//        if (!QuistionInfo.answer!!.equals(SelectedOption.toString(),true)) {
//            DB_Question.cntWrongAns.visibility = View.VISIBLE
//        } else {
            ResetOptions()
//            SetUpQuestion(++QuestionNo)
//        }
    }

    private fun DeSelectTextColor() {
        DB_Question.rdOption1.setTextColor(
            ContextCompat.getColor(
                this@Questions,
                R.color.white
            )
        )
        DB_Question.rdOption2.setTextColor(
            ContextCompat.getColor(
                this@Questions,
                R.color.white
            )
        )
        DB_Question.rdOption3.setTextColor(
            ContextCompat.getColor(
                this@Questions,
                R.color.white
            )
        )
        DB_Question.rdOption4.setTextColor(
            ContextCompat.getColor(
                this@Questions,
                R.color.white
            )
        )
    }

    private fun SetUpQuestion(QuestionNo: Int) {
        QuistionInfo = VideoPlayer.VideoData.question!![QuestionNo]
        DB_Question.txtQueNo.text = "Question " + (QuestionNo + 1)
        DB_Question.txtQuestion.text = QuistionInfo.question
        DB_Question.rdOption1.text = QuistionInfo.answer1
        DB_Question.rdOption2.text = QuistionInfo.answer2
        DB_Question.rdOption3.text = QuistionInfo.answer3
        DB_Question.rdOption4.text = QuistionInfo.answer4
    }

    override fun onClick(view: View?) {
        try {
            if (view == DB_Question.txtNextquestion) {
//                if (SelectedOption == 0) {
//                    DB_Question.txtAlertmsg.text = "Please select one option."
//                    DB_Question.cntAlert.visibility = View.VISIBLE
//                } else if (TotalQUestions == (QuestionNo + 1)) {
//                    var ResultIntent = Intent(this, ResultQuestion::class.java)
//                    startActivity(ResultIntent)
//                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
//                    VideoPlayer.VideoPlayerActivity.finish()
//                    finish()
//                } else {
//                    CheckAns(SelectedOption)
//                }
                if (QuestionNo == 0){
                    SelectedOption = 1
                    CheckAns(SelectedOption)
                    QuestionNo++
                }else{
                    var ResultIntent = Intent(this, ResultQuestion::class.java)
                    startActivity(ResultIntent)
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                    VideoPlayer.VideoPlayerActivity.finish()
                    finish()
                }

            } else if (view == DB_Question.cntWatchagain) {
                finish()
                overridePendingTransition(R.anim.activity_back_in,R.anim.activity_back_out)
            } else if (view == DB_Question.cntWatchmore) {
                VideoPlayer.VideoPlayerActivity.finish()
                finish()
                overridePendingTransition(R.anim.activity_back_in,R.anim.activity_back_out)
            } else if (view == DB_Question.txtAlertok) {
                DB_Question.cntAlert.visibility = View.GONE
            }
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

    private fun ResetOptions() {
        SelectedOption = 0
        DB_Question.rdOption1.isChecked = false
        DB_Question.rdOption2.isChecked = false
        DB_Question.rdOption3.isChecked = false
        DB_Question.rdOption4.isChecked = false
        DeSelectTextColor()
    }

}