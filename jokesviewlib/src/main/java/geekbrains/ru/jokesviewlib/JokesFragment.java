package geekbrains.ru.jokesviewlib;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class JokesFragment extends Fragment {
    private TextView textView;

    public JokesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);
        textView = view.findViewById(R.id.tv_fr_jokes_joke);
        return view;
    }

    public void showJoke(String joke) {
        textView.setText(joke);
    }
}
