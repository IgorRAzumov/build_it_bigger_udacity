

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.util.Log;

import com.udacity.gradle.builditbigger.asynTasks.EndPointsApiAsyncTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest extends AndroidTestCase {
    private static final int AWAIT_TIME_SECONDS = 10;
    private static final String ERROR_MESSAGE = "Error!Timed out or execute error";

    @Test
    public void testDoInBackground() {
        try {
            EndPointsApiAsyncTask endpointsAsyncTask = new EndPointsApiAsyncTask();
            endpointsAsyncTask.execute();
            String result = endpointsAsyncTask.get(AWAIT_TIME_SECONDS, TimeUnit.SECONDS);

            assertNotNull(result);
            assertTrue(result.length() > 0);
        } catch (Exception e) {
            Log.e(EndPointsApiAsyncTask.class.toString(), ERROR_MESSAGE);
        }
    }

}

