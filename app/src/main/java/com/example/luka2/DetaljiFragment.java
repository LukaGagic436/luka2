package com.example.luka2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.luka2.R;
import com.example.luka2.Detail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetaljiFragment extends Fragment {
    private ImageView image;
    private TextView title;
    private TextView year;
    private RatingBar ratingBar;
    private TextView runtime;
    private TextView genre;
    private TextView writer;
    private TextView director;
    private TextView actors;
    private TextView plot;
    Context context;

    ArrayList<Detail> detailItem;
    String id;

    private int position = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.activity_second, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position", 0);
        }


        Picasso.with(context).load(detailItem.get(position).getPoster()).into(image);

        ratingBar = view.findViewById(R.id.detalji_rating);
        String rating = detailItem.get(position).getImdbRating();
        ratingBar.setRating(Float.parseFloat(rating));


        title = view.findViewById(R.id.detalji_title);
        title.setText(detailItem.get(position).getTitle());

        year = view.findViewById(R.id.detalji_year);
        year.setText(detailItem.get(position).getYear());

        runtime = view.findViewById(R.id.detalji_runtime);
        runtime.setText(detailItem.get(position).getRuntime());

        genre = view.findViewById(R.id.detalji_genre);
        genre.setText(detailItem.get(position).getGenre());

        writer = view.findViewById(R.id.detalji_writer);
        writer.setText(detailItem.get(position).getWriter());

        director = view.findViewById(R.id.detalji_director);
        director.setText(detailItem.get(position).getDirector());

        actors = view.findViewById(R.id.detalji_actors);
        actors.setText(detailItem.get(position).getActors());

        plot = view.findViewById(R.id.detalji_plot);
        plot.setText(detailItem.get(position).getPlot());
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("position", position);
    }

    public void setContent(final int position) {

        this.position = position;

    }
}
