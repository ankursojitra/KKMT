package com.rjsquare.kkmt.Activity.SearchCompany

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivitySearchCompanyListBinding

class Search_company_list : AppCompatActivity() {
    companion object{
        lateinit var DB_SearchCompany:ActivitySearchCompanyListBinding
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_SearchCompany = DataBindingUtil.setContentView(this,R.layout.activity_search_company_list)
//        setContentView(R.layout.activity_search_company_list)
    }
}