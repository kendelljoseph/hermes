package lunchandlearn.android.com.hermes.interfaces

import lunchandlearn.android.com.hermes.beans.GitHubUser

interface GoTaskListener {
    fun showFailure(response: String)
    fun showSuccess(response: GitHubUser?)
    fun showProgressBar(show: Boolean)
    fun onTaskCompleted(canceled: Boolean)
}