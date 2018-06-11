package lunchandlearn.android.com.hermes.interfaces

interface GoTaskListener {
    fun showProgressBar(show: Boolean)
    fun onTaskCompleted(canceled: Boolean)
}