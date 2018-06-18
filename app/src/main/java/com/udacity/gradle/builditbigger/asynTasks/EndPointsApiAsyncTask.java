package com.udacity.gradle.builditbigger.asynTasks;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndPointsApiAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private EndPointsCallback endPointsCallback;

    public EndPointsApiAsyncTask(EndPointsCallback endPointsCallback) {
        this.endPointsCallback = endPointsCallback;
    }

    private void createApiService() {
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?>
                                                   abstractGoogleClientRequest) {
                        abstractGoogleClientRequest.setDisableGZipContent(false);
                    }
                });
        // end options for devappserver
        myApiService = builder.build();

    }

    @Override
    protected String doInBackground(Void... endPointsCallbacks) {
        if (myApiService == null) {
            createApiService();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        endPointsCallback.onCompleted(result);
    }

    public interface EndPointsCallback {
        void onCompleted(String joke);

        String onError();
    }
}