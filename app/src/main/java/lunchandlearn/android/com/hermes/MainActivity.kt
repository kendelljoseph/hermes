package lunchandlearn.android.com.hermes

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.ArrayAdapter

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import android.text.TextUtils

import kotlinx.android.synthetic.main.activity_main.*
import lunchandlearn.android.com.hermes.beans.GitHubUser
import lunchandlearn.android.com.hermes.interfaces.GoTaskListener

/**
 * A screen that offers a field to be sent to a server with a click of a button
 */
class MainActivity : AppCompatActivity(), GoTaskListener { // LoaderCallbacks<Cursor>,
    /**
     * Keep track of the task to ensure we can cancel it if requested.
     */
    private var mGoTask: GetGitHubUserTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the login form.
        // populateAutoComplete()

        btnGo.setOnClickListener { validateAndSendData() }
    }

    /**
     * Validates the name input data and sends it to the server.
     */
    private fun validateAndSendData() {
        // If the task is already running, don't try to run again.
        if (mGoTask != null) {
            return
        }

        // Reset errors
        nameTextInput.error = null

        val nameStr = nameTextInput.text.toString()

        if (authenticate()) {
            // Show a progress spinner, and kick off a background task
            showProgressBar(true)
            mGoTask = GetGitHubUserTask(this, nameStr)
            mGoTask!!.execute(null as Void?)
        }
    }

    /**
     * Returns if the input is valid enough to submit the form
     */
    private fun authenticate(): Boolean {
        // Store values at the time of the login attempt.
        val nameStr = nameTextInput.text.toString()
        var focusView: View? = null
        var isValid = true

        // Check for a valid name.
        if (TextUtils.isEmpty(nameStr)) {
            nameTextInput.error = getString(R.string.error_name_required)
//            focusView = nameTextInput
            isValid = false
        } else if (!isNameValid(nameStr)) {
            nameTextInput.error = getString(R.string.error_invalid_name)
//            focusView = nameTextInput
            isValid = false
        }

        if (! isValid) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        }

        return isValid
    }

    private fun isNameValid(text: String): Boolean {
        return text.length > 4
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    override fun showProgressBar(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 0 else 1).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_form.visibility = if (show) View.GONE else View.VISIBLE
                        }
                    })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 1 else 0).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_progress.visibility = if (show) View.VISIBLE else View.GONE
                        }
                    })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    // Cleanup tasks after the GoTask is finished or canceled
    override fun onTaskCompleted(canceled: Boolean) {
        mGoTask = null
        if (!canceled) {
            //finish() // Close the activity
        }
    }

    override fun showFailure(response: String) {
        txtResponse.setText(response)
    }

    override fun showSuccess(response: GitHubUser?) {
        var text: String = "Login: " + response?.login +
        "\nBio: " + response?.bio +
        "\nGit URL: " + response?.htmlUrl

        txtResponse.setText(text)
    }


    /****************************************************
     * UNUSED CODE - came from the login template we started with
     ****************************************************/

//    private fun populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return
//        }
//
//        loaderManager.initLoader(0, null, this)
//    }
//
//    private fun mayRequestContacts(): Boolean {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(nameTextInput, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok,
//                            { requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS) })
//        } else {
//            requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS)
//        }
//        return false
//    }
//
//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
//                                            grantResults: IntArray) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete()
//            }
//        }
//    }
//
//    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor> {
//        return CursorLoader(this,
//                // Retrieve data rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//
//                // Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email
//                .CONTENT_ITEM_TYPE),
//
//                // Show primary email addresses first. Note that there won't be
//                // a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
//    }
//
//    override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor) {
//        val emails = ArrayList<String>()
//        cursor.moveToFirst()
//        while (!cursor.isAfterLast) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS))
//            cursor.moveToNext()
//        }
//
//        addEmailsToAutoComplete(emails)
//    }
//
//    override fun onLoaderReset(cursorLoader: Loader<Cursor>) {
//
//    }
//
//    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        val adapter = ArrayAdapter(this@MainActivity,
//                android.R.layout.simple_dropdown_item_1line, emailAddressCollection)
//
//        nameTextInput.setAdapter(adapter)
//    }
//
//    object ProfileQuery {
//        val PROJECTION = arrayOf(
//                ContactsContract.CommonDataKinds.Email.ADDRESS,
//                ContactsContract.CommonDataKinds.Email.IS_PRIMARY)
//        val ADDRESS = 0
//        val IS_PRIMARY = 1
//    }
//
//    // This object is basically a singleton common to all instances of MainActivity.kt
//    // https://antonioleiva.com/objects-kotlin/
//    companion object {
//
//        /**
//         * Id to identity READ_CONTACTS permission request.
//         */
//        private val REQUEST_READ_CONTACTS = 0
//
//        /**
//         * A dummy authentication store containing known user names and passwords.
//         * TODO: remove after connecting to a real authentication system.
//         */
//        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
//    }
}
