package lunchandlearn.android.com.hermes.beans
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GitHubUser(
        @SerializedName("login") @Expose val login: String,
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("node_id") @Expose val nodeId: String,
        @SerializedName("avatar_url") @Expose val avatarUrl: String,
        @SerializedName("gravatar_id") @Expose val gravatarId: String,
        @SerializedName("url") @Expose val url: String,
        @SerializedName("html_url") @Expose val htmlUrl: String,
        @SerializedName("followers_url") @Expose val followersUrl: String,
        @SerializedName("following_url") @Expose val followingUrl: String,
        @SerializedName("gists_url") @Expose val gistsUrl: String,
        @SerializedName("starred_url") @Expose val starredUrl: String,
        @SerializedName("subscriptions_url") @Expose val subscriptionsUrl: String,
        @SerializedName("organizations_url") @Expose val organizationsUrl: String,
        @SerializedName("repos_url") @Expose val reposUrl: String,
        @SerializedName("events_url") @Expose val eventsUrl: String,
        @SerializedName("received_events_url") @Expose val receivedEventsUrl: String,
        @SerializedName("type") @Expose val type: String,
        @SerializedName("site_admin") @Expose val siteAdmin: Boolean,
        @SerializedName("name") @Expose val name: String,
        @SerializedName("company") @Expose val company: String,
        @SerializedName("blog") @Expose val blog: String,
        @SerializedName("location") @Expose val location: Any,
        @SerializedName("email") @Expose val email: Any,
        @SerializedName("hireable") @Expose val hireable: Any,
        @SerializedName("bio") @Expose val bio: Any,
        @SerializedName("public_repos") @Expose val publicRepos: Int,
        @SerializedName("public_gists") @Expose val publicGists: Int,
        @SerializedName("followers") @Expose val followers: Int,
        @SerializedName("following") @Expose val following: Int,
        @SerializedName("created_at") @Expose val createdAt: String,
        @SerializedName("updated_at") @Expose val updatedAt: String
)