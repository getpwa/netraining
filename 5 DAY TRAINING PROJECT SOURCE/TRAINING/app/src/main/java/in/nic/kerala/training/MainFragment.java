package in.nic.kerala.training;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MainFragment extends Fragment {
    GridView grid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        grid=(GridView)view.findViewById(R.id.grid);
        grid.setAdapter(new ImageAdapter(getActivity()));

        return view;

    }


}

