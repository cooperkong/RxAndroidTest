package api;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by kongw1 on 5/10/15.
 */
public class ApiService {
    private final GitHubService service;

    public ApiService(){
        Retrofit retrocit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrocit.create(GitHubService.class);
    }

    public Observable<User> getGitHubUser(String username){
        return service.getGitHubUser(username);
    }
}
