package lunchandlearn.android.com.hermes

import android.os.AsyncTask
import lunchandlearn.android.com.hermes.beans.GitHubUser
import lunchandlearn.android.com.hermes.interfaces.GitHubUserRetrofitInterface
import lunchandlearn.android.com.hermes.interfaces.GoTaskListener
import retrofit.RestAdapter

/**
 * Represents an asynchronous task used to get user information from GitHub
 */
class GetGitHubUserTask constructor(private val caller: GoTaskListener, private val mName: String) : AsyncTask<Void, Void, GitHubUser>() {

     var successResponse: GitHubUser? = null
     var failureResponse: String = "Unknown Error"

    override fun doInBackground(vararg params: Void): GitHubUser? {

        try {
            val url: String = "https://api.github.com"

            var radapter = RestAdapter.Builder().setEndpoint(url).build()
            var restInt = radapter.create(GitHubUserRetrofitInterface::class.java)

            successResponse = restInt.getUser(mName)

            // This is the async way of doing it. You can do this on your main thread without an AsyncTask
//            restInt.getUser(
//                object: Callback<GitHubUser> {
//                    override fun success(model: GitHubUser, retrofitResponse: Response) {
//                        successResponse = model;
//                    }
//
//                    override fun failure(error: RetrofitError?) {
//                        failureResponse = error.toString()
////                        response = GitHubUser(errorMessage = "Error not talking to server")
//                    }
//                }
//            )
        } catch (e: InterruptedException) {
//            failureResponse = "Interrupted Exception"
            return successResponse
        }

        return successResponse
    }

    override fun onPostExecute(response: GitHubUser?) {
        caller.showSuccess(response)
        caller.showProgressBar(false)
        caller.onTaskCompleted(false)
    }

    override fun onCancelled() {
        caller.showProgressBar(false)
        caller.onTaskCompleted(true)
    }
}