package api;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by kongw1 on 5/10/15.
 */
public interface GitHubService {
    @GET("/users/{user}")
    Observable<User> getGitHubUser(@Path("user")String username);
}
