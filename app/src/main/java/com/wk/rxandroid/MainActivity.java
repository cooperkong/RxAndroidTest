package com.wk.rxandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.Arrays;
import java.util.List;

import api.ApiService;
import mvp.MainPresenter;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Single;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements mvp.View{
    private MainPresenter presenter;
    public static String currentThreadName() {
        return Thread.currentThread().getName();
    }

    private Button registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        registerBtn = (Button) findViewById(R.id.btnRegister);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()

        );
        Observable<TextViewTextChangeEvent> emailText = RxTextView.textChangeEvents((TextView) findViewById(R.id.edtEmail));
        Observable<TextViewTextChangeEvent> usernameText = RxTextView.textChangeEvents((TextView) findViewById(R.id.edtUserName));
        usernameText.filter(
            s -> s.text().length() > 2
        )
                .subscribe(
                        s -> Log.d("wenchao", "[typed]" + s.text())
                );
        Observable<Boolean> usernameValid = RxTextView.textChanges((TextView) findViewById(R.id.edtUserName)).map(
                e -> e.length() > 2
        ).distinctUntilChanged();
        Observable<Boolean> emailValid = RxTextView.textChanges((TextView) findViewById(R.id.edtEmail)).map(
                e -> e.length() > 2
        ).distinctUntilChanged();
        Observable<Boolean> combined = Observable.combineLatest(usernameValid, emailValid,
                (a,b) -> a && b);

        combined.subscribe(
                result -> {
                    registerBtn.setEnabled(result);
                    Log.d("wenchao", "called "+ result);
                }
        );

        presenter = new MainPresenter(this);
        presenter.fetchData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setName(String username) {
        TextView e = (TextView) findViewById(R.id.edtEmail);
        e.setText(username);
    }
}
