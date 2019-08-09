package com.agence.caol.activities

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.concurrent.TimeoutException
import android.app.ProgressDialog
import androidx.annotation.VisibleForTesting
import com.agence.caol.R
import com.cplus.android.utilities.Utils


abstract class BaseActivity : AppCompatActivity() {


    abstract fun setupView()

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    /**
     * @param throwable to identify the tag of error
     * @return appropriate error message
     */
    fun fetchErrorMessage(throwable: Any?): String {
        var errorMsg = resources.getString(R.string.error_msg_unknown)

        if (!Utils.isNetworkConnected(this)) {
            errorMsg = resources.getString(R.string.error_msg_no_internet)
        } else if (throwable is TimeoutException) {
            errorMsg = resources.getString(R.string.error_msg_timeout)
        } else if (throwable is String) {
            errorMsg = throwable
        }

        return errorMsg
    }

    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(this)
    }

    fun showProgressDialog(message: String = getString(R.string.loading)) {
        progressDialog.setMessage(message)
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showDialog(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.app_name))
            .setMessage(message)
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                // continue with delete
            }
            .setNegativeButton(android.R.string.no) { dialog, which ->
                // do nothing
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun showAlert() {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(resources.getString(R.string.app_name))
        alert.setMessage(resources.getString(R.string.online_logout_desc))


        alert.setPositiveButton(resources.getString(R.string.online_logout_ok)) { _, _ ->
            //Utils.clearPreference(this)
            //Intent(this, AuthActivity::class.java)
            //finish()
        }

        alert.setNegativeButton(resources.getString(R.string.online_logout_cancel)) { _, _ ->
            // what ever you want to do with No option.
        }

        alert.show()
    }

}
