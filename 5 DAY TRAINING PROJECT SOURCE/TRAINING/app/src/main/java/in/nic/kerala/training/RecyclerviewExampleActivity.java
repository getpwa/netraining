package in.nic.kerala.training;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewExampleActivity extends AppCompatActivity {
    private List<listvalues> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private listadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_example);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new listadapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
    }

    private void prepareMovieData() {
        listvalues movie = new listvalues("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new listvalues("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new listvalues("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new listvalues("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new listvalues("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new listvalues("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new listvalues("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new listvalues("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new listvalues("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new listvalues("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new listvalues("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new listvalues("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new listvalues("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new listvalues("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new listvalues("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new listvalues("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }
}

