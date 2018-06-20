package geekbrains.ru.jokesviewlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class JokesActivity extends AppCompatActivity {
    public static final String JOKE_KEY = "jokes-intent-key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        if (savedInstanceState == null) {
            initJokeFragment();
        }
    }

    private void initJokeFragment() {
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(JOKE_KEY)) {
            throw new RuntimeException(this.toString() + getString(R.string.error_inbox_intent_extra));
        }

        String joke = intent.getStringExtra(JOKE_KEY);
        Bundle bundle = new Bundle();
        bundle.putString(JOKE_KEY, joke);
        Fragment jokeFragment = JokesFragment.newInstance(JOKE_KEY, joke);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_jokes_activity_container, jokeFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

}
