package geekbrains.ru.jokesviewlib;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static geekbrains.ru.jokesviewlib.JokesActivity.JOKE_KEY;


public class JokesFragment extends Fragment {
    private TextView jokeText;

    public static Fragment newInstance(String jokeKey, String joke) {
        Bundle args = new Bundle();
        args.putString(jokeKey, joke);
        Fragment jokesFragment = new JokesFragment();
        jokesFragment.setArguments(args);
        return jokesFragment;
    }

    public JokesFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args == null || !args.containsKey(JOKE_KEY)) {
            throw new RuntimeException(getContext().toString() + " "
                    + getString(R.string.er_inbox_bundle_key));
        }
        jokeText.setText(args.getString(JOKE_KEY));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);
        jokeText = view.findViewById(R.id.tv_fr_jokes_joke);
        return view;
    }

}
