package com.udacity.gradle.builditbigger.actrivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.asynTasks.EndPointsApiAsyncTask;
import com.udacity.gradle.builditbigger.fragments.MainActivityFragment;

import geekbrains.ru.jokesviewlib.JokesActivity;


public class MainActivity extends AppCompatActivity
        implements MainActivityFragment.OnActivityCallback {
    private EndPointsApiAsyncTask.EndPointsCallback endPointsCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
       new EndPointsApiAsyncTask(endPointsCallback).execute();
    }

    private void initEndPointCallback() {
        endPointsCallback = new EndPointsApiAsyncTask.EndPointsCallback() {
            @Override
            public void onCompleted(String joke) {
                Intent intent = new Intent(MainActivity.this, JokesActivity.class);
                intent.putExtra(JokesActivity.JOKES_INTENT_KEY, "dfgdgdsgsdg");
            }

            @Override
            public String onError() {
                return null;
            }
        };
    }

}
