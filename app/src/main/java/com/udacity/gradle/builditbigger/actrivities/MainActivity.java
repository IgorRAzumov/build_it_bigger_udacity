package com.udacity.gradle.builditbigger.actrivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.asynTasks.EndPointsApiAsyncTask;
import com.udacity.gradle.builditbigger.fragments.MainActivityFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import geekbrains.ru.jokesviewlib.JokesActivity;


public class MainActivity extends AppCompatActivity
        implements MainActivityFragment.OnActivityCallback {
    @BindView(R.id.pb_activity_main_progress)
    ProgressBar progressBar;
    private EndPointsApiAsyncTask.EndPointsCallback endPointsCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initEndPointCallback();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    protected void onDestroy() {
        super.onDestroy();
        endPointsCallback = null;
    }

    @Override
    public void startJoke() {
        EndPointsApiAsyncTask task = new EndPointsApiAsyncTask();
        task.setEndPointsCallback(endPointsCallback);
        task.execute();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initEndPointCallback() {
        endPointsCallback = new EndPointsApiAsyncTask.EndPointsCallback() {
            @Override
            public void onCompleted(String joke) {
                Intent intent = new Intent(MainActivity.this, JokesActivity.class);
                intent.putExtra(JokesActivity.JOKE_KEY, joke);
                startActivity(intent);
                loadIsEnd();
            }

            @Override
            public void onError() {
               loadIsEnd();
               showMessage(R.string.error_load_joke);
            }
        };
    }

    private void showMessage(int message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void loadIsEnd() {
        progressBar.setVisibility(View.INVISIBLE);
        Fragment mainFragment = getSupportFragmentManager()
                .findFragmentByTag(getString(R.string.mainFragmentTag));
        if(mainFragment != null){
            ((MainActivityFragment)mainFragment).onLoadingEnded();
        }
    }

}
