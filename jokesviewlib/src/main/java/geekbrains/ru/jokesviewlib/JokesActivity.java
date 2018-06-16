package geekbrains.ru.jokesviewlib;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JokesActivity extends AppCompatActivity {
    public static final String JOKES_INTENT_KEY = "joke-intent-key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        Intent intent = getIntent();
        if(intent == null || !intent.hasExtra(JOKES_INTENT_KEY)){
            throw new RuntimeException(this.toString() + getString(R.string.error_inbox_intent_extra));
        }
        String joke = intent.getStringExtra(JOKES_INTENT_KEY);


        Fragment jokesFragment = getSupportFragmentManager()
                .findFragmentByTag(getString(R.string.jokes_fragment_tag));
        if(jokesFragment!= null && jokesFragment instanceof JokesFragment){
            ((JokesFragment)jokesFragment).showJoke(joke);
        }
    }
}
