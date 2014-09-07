package scottso.assist911;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondTutorialFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_secondtutorialfragment, container, false);

        final TextView endButton = (TextView) view.findViewById(R.id.button_endtutorial);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endButtonClicked();
            }
        });
        return view;
    }

    private void endButtonClicked() {
        getActivity().finish();
    }
}
