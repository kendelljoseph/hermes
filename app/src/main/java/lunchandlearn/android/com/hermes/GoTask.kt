package lunchandlearn.android.com.hermes

import android.os.AsyncTask
import lunchandlearn.android.com.hermes.interfaces.GoTaskListener

/**
 * Represents an asynchronous login/registration task used to authenticate the user.
 */
class GoTask constructor(private val caller: GoTaskListener, private val mName: String) : AsyncTask<Void, Void, Boolean>() {

    override fun doInBackground(vararg params: Void): Boolean? {
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            return false
        }

        return true
    }

    override fun onPostExecute(success: Boolean?) {
        caller.showProgressBar(false)
        caller.onTaskCompleted(false);
    }

    override fun onCancelled() {
        caller.showProgressBar(false)
        caller.onTaskCompleted(true);
    }
}