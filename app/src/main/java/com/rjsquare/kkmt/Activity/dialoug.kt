package com.rjsquare.kkmt.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityDialougBinding

class dialoug : AppCompatActivity() {
    companion object {
        lateinit var DB_Dialoug: ActivityDialougBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Dialoug = DataBindingUtil.setContentView(this, R.layout.activity_dialoug)
//        setContentView(R.layout.activity_dialoug)
    }
}