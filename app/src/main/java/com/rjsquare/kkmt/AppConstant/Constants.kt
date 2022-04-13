package com.rjsquare.kkmt.AppConstant

class Constants {
    companion object{

        //Base URL
        var BASE_URL: String = "https://kkmtapp.com/developing/api/"

        val YES = "Yes"
        val NO = "No"
        val Pending = "panding"
        val Approve = "approve"
        val paramKey_MobileNo = "phoneno"
        val paramKey_FirstName = "firstname"
        val paramKey_LastName = "lastname"
        val paramKey_EmailAddress = "email"
        val paramKey_DOB = "dob"
        val paramKey_Gender = "gender"
        val paramKey_OPT = "otp"
        val paramKey_CustomerId = "customer_id"
        val paramKey_BussinessId = "business_id"
        val paramKey_EmployeeId = "employee_id"
        val paramKey_Document = "document"
        val paramKey_Selfie = "selfie"
        val paramKey_PageNo = "pageno"
        val paramKey_ReviewId = "review_id"
        val paramKey_UserId = "user_id"
        val paramKey_VideoId = "video_id"
        val paramKey_Credit = "credit"
        val paramKey_Usertype = "usertype"
        val paramKey_limit = "limit"
        val paramKey_Reason = "reason"
        val paramKey_Image = "image"
        val paramKey_KKMTID = "text"
        val paramKey_BusinessName = "busssines_name"
        val paramKey_BeconList = "becon_list[]"
        val User = "U"

        //Beacon Type
        val slaveBeacon = "C6"
        val masterBeacon = "C7"

        //User Preference data Store Key
        val Pref_UserDataModel: String = "userDataModel"
        val Pref_UserLogedIn: String = "user_logedin"

        //Api Status Code
        var ResponseSucess = "200"
        var ResponseUnauthorized = "203"
        var ResponseEmpltyList = "202"

        var UserLevel = 4

        var PrefName = "KKMTDATA"
        var SpinTime = "SpinTime"

    }


}