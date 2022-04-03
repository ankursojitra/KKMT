package com.rjsquare.kkmt.Activity.Store

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityStoreItemRedeemConfirmBinding

class StoreItemRedeemConfirm : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_StoreItemRedeemConfirm: ActivityStoreItemRedeemConfirmBinding
        var DeliveryAddress = ""
        var IsPickUpStore = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_StoreItemRedeemConfirm =
            DataBindingUtil.setContentView(this, R.layout.activity_store_item_redeem_confirm)
//        setContentView(R.layout.activity_store_item_redeem_confirm)
        try {
            ApplicationClass.StatusTextWhite(this, true)

            DB_StoreItemRedeemConfirm.chDeliveryLocation.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickupLocation.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked = true
                    }
                } else {
                    IsPickUpStore = false
                    DB_StoreItemRedeemConfirm.chPickupLocation.isChecked = false
                    SetUi()
                }
            }
            DB_StoreItemRedeemConfirm.chPickupLocation.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickupLocation.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chPickupLocation.isChecked = true
                    }
                } else {
                    IsPickUpStore = true
                    if (DB_StoreItemRedeemConfirm.chPickUp1.isChecked) {
                        DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp1.text.toString()
                    } else if (DB_StoreItemRedeemConfirm.chPickUp2.isChecked) {
                        DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp2.text.toString()
                    } else if (DB_StoreItemRedeemConfirm.chPickUp3.isChecked) {
                        DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp3.text.toString()
                    }
                    DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked = false
                    SetUi()
                }
            }
            DB_StoreItemRedeemConfirm.chPickUp1.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chPickUp1.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp2.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp3.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chPickUp1.isChecked = true
                    }
                } else {
                    DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp1.text.toString()
                    DB_StoreItemRedeemConfirm.chPickUp2.isChecked = false
                    DB_StoreItemRedeemConfirm.chPickUp3.isChecked = false
                }
            }
            DB_StoreItemRedeemConfirm.chPickUp2.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chPickUp1.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp2.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp3.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chPickUp2.isChecked = true
                    }
                } else {
                    DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp2.text.toString()
                    DB_StoreItemRedeemConfirm.chPickUp1.isChecked = false
                    DB_StoreItemRedeemConfirm.chPickUp3.isChecked = false
                }
            }
            DB_StoreItemRedeemConfirm.chPickUp3.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chPickUp1.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp2.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp3.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chPickUp3.isChecked = true
                    }
                } else {
                    DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp3.text.toString()
                    DB_StoreItemRedeemConfirm.chPickUp2.isChecked = false
                    DB_StoreItemRedeemConfirm.chPickUp1.isChecked = false
                }
            }
            DB_StoreItemRedeemConfirm.edtPincode.setOnEditorActionListener(object :
                TextView.OnEditorActionListener {
                override fun onEditorAction(v: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
//                        var imm = getSystemService(
//                            Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        ApplicationClass.HiddenKeyBoard(
                            this@StoreItemRedeemConfirm,
                            DB_StoreItemRedeemConfirm.cntTopView
                        )
                        return true;
                    }
                    return false;
                }

            })
            DB_StoreItemRedeemConfirm.chPickupLocation.isChecked = true
            IsPickUpStore = true

            DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp1.text.toString()

            DB_StoreItemRedeemConfirm.cntItemredeemSubmit.setOnClickListener(this)
            DB_StoreItemRedeemConfirm.cntRedeemConfirm.setOnClickListener(this)
            DB_StoreItemRedeemConfirm.cntRedeemCancel.setOnClickListener(this)
            DB_StoreItemRedeemConfirm.imgBack.setOnClickListener(this)
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

    private fun SetUi() {
        if (DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked) {
            DB_StoreItemRedeemConfirm.cntDeliveryLocation.visibility = View.VISIBLE
            DB_StoreItemRedeemConfirm.cntPickUpLocation.visibility = View.GONE
        } else if (DB_StoreItemRedeemConfirm.chPickupLocation.isChecked) {
            DB_StoreItemRedeemConfirm.cntDeliveryLocation.visibility = View.GONE
            DB_StoreItemRedeemConfirm.cntPickUpLocation.visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View?) {
        if (view == DB_StoreItemRedeemConfirm.cntItemredeemSubmit) {
            if (!IsPickUpStore) {
                if (!DB_StoreItemRedeemConfirm.edtAddress1.text.toString().trim()
                        .equals("", true)
                ) {
                    if (!DB_StoreItemRedeemConfirm.edtAddress2.text.toString().trim()
                            .equals("", true)
                    ) {
                        DeliveryAddress =
                            DB_StoreItemRedeemConfirm.edtAddress1.text.toString() +
                                    ", " + DB_StoreItemRedeemConfirm.edtAddress2.text.toString() +
                                    ", " + DB_StoreItemRedeemConfirm.edtPincode.text.toString()
                    } else {
                        DeliveryAddress =
                            DB_StoreItemRedeemConfirm.edtAddress1.text.toString() +
                                    ", " + DB_StoreItemRedeemConfirm.edtPincode.text.toString()
                    }
                }
            }
            if (!IsPickUpStore && DB_StoreItemRedeemConfirm.edtAddress1.text.toString().trim()
                    .equals("", true) &&
                DB_StoreItemRedeemConfirm.edtPincode.text.toString().trim()
                    .equals("", true)
            ) {
                Toast.makeText(this, "Enter address.", Toast.LENGTH_SHORT).show()
                return
            }
            SetUpConfirmUI()
            DB_StoreItemRedeemConfirm.cntConfirmation.visibility = View.GONE
            DB_StoreItemRedeemConfirm.cntConfirmation.visibility = View.VISIBLE
        } else if (view == DB_StoreItemRedeemConfirm.cntRedeemConfirm) {
            Store.thisStoreActivity.finish()
            finish()
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
        } else if (view == DB_StoreItemRedeemConfirm.cntRedeemCancel) {
            DB_StoreItemRedeemConfirm.cntConfirmation.visibility = View.GONE
        }else if (view == DB_StoreItemRedeemConfirm.imgBack) {
            onBackPressed()
        }
    }

    private fun SetUpConfirmUI() {
        if (IsPickUpStore) {
            DB_StoreItemRedeemConfirm.txtRedeemaddressSel.text =
                DB_StoreItemRedeemConfirm.chPickupLocation.text.toString()
        } else {
            DB_StoreItemRedeemConfirm.txtRedeemaddressSel.text =
                DB_StoreItemRedeemConfirm.chDeliveryLocation.text.toString()
        }
        DB_StoreItemRedeemConfirm.txtRedeemaddressLbl.text = DeliveryAddress
    }
}