package lunchandlearn.android.com.hermes.interfaces

import lunchandlearn.android.com.hermes.beans.GitHubUser
import retrofit.http.GET
import retrofit.http.Path

interface GitHubUserRetrofitInterface {
//    @GET("/users/beyondtheteal")
//    fun getUser(uscb: Callback<GitHubUser>)

//    @GET("/users/beyondtheteal")
//    fun getUser(): GitHubUser

    @GET("/users/{user_id}")
    fun getUser(@Path(value = "user_id", encode = true) userId: String): GitHubUser
}

