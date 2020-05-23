package com.example.luka2.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;


import com.example.luka2.R;
import com.example.luka2.db.PripremaORMLightHelper;
import com.example.luka2.MyService;
import com.example.luka2.Detail;
import com.example.luka2.db.model.Movie;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.HashMap;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Build;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import static com.example.luka2.activity.PripremaListActivity.NOTIF_STATUS;
import static com.example.luka2.activity.PripremaListActivity.NOTIF_TOAST;

public class SecondActivity extends AppCompatActivity {

    ImageView image;
    RatingBar ratingBar;
    // TextView tvReting;
    TextView title;
    TextView years;
    TextView runtime;
    TextView genre;
    TextView writer;
    TextView director;
    TextView actors;
    TextView plot;
    private PripremaORMLightHelper databaseHelper;


    private void getDetail(String imdbKey) {
        HashMap<String, String> query = new HashMap<String, String>();
        query.put( "apikey", "1c47bc5b" );
        query.put( "i", imdbKey );

        Call<Detail> call = MyService.apiInterface().getMovieData( query );
        call.enqueue( new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {

                if (response.code() == 200) {
                    Log.d( "REZ", "200" );

                    Detail resp = response.body();
                    if (resp != null) {

                        image = SecondActivity.this.findViewById( R.id.detalji_slika );

                        Picasso.with( SecondActivity.this ).load( resp.getPoster() ).into( image );

                        ratingBar = SecondActivity.this.findViewById( R.id.detalji_rating );
                        String rating = resp.getImdbRating();
                        ratingBar.setRating( Float.parseFloat( rating ) );

//                        tvReting = SecondActivity.this.findViewById(R.id.detalji_raiting);
//                        tvReting.setText("IMBD Rating " + resp.getImdbRating() + "/10");

                        title = SecondActivity.this.findViewById( R.id.detalji_title );
                        title.setText( resp.getTitle() );

                        years = SecondActivity.this.findViewById( R.id.detalji_year );
                        years.setText( "(" + resp.getYear() + ")" );

                        runtime = SecondActivity.this.findViewById( R.id.detalji_runtime );
                        runtime.setText( resp.getRuntime() );

                        genre = SecondActivity.this.findViewById( R.id.detalji_genre );
                        genre.setText( resp.getGenre() );

                        writer = SecondActivity.this.findViewById( R.id.detalji_writer );
                        writer.setText( resp.getWriter() );

                        director = SecondActivity.this.findViewById( R.id.detalji_director );
                        director.setText( resp.getDirector() );

                        actors = SecondActivity.this.findViewById( R.id.detalji_actors );
                        actors.setText( resp.getActors() );

                        plot = SecondActivity.this.findViewById( R.id.detalji_plot );
                        plot.setText( resp.getPlot() );

                    }
                }
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                Toast.makeText( SecondActivity.this, t.getMessage(), Toast.LENGTH_SHORT ).show();

            }
        } );
    }

    @Override
    protected void onResume() {
        super.onResume();
        String imdbKey = getIntent().getStringExtra( MainActivity.KEY );
        getDetail( imdbKey );
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_second );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.deletee:
                int movieDeleteId = getIntent().getExtras().getInt("delete",0);
                try {
                    int list = getDatabaseHelper().getFilmDao().deleteById(movieDeleteId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public PripremaORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, PripremaORMLightHelper.class);
        }
        return databaseHelper;
    }
}

