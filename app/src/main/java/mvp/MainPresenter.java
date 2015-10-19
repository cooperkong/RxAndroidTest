package mvp;

import android.util.Log;

import api.ApiService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kongw1 on 19/10/15.
 */
public class MainPresenter {

    private final View view;

    public MainPresenter(View view){
        this.view = view;
    }

    public void fetchData(){
        ApiService api = new ApiService();
        api.getGitHubUser("cooperkong")
                .flatMap(
                        user -> Observable.just(user.login)
                )
                .compose(applySchedulers())
                .subscribe(
                        username -> view.setName(username)
                );
    }


    <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
