package com.example.luka2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luka2.R;
import com.example.luka2.RecyclerTouchListener;
import com.example.luka2.adapter.MyAdapter;
import com.example.luka2.db.PripremaORMLightHelper;
import com.example.luka2.db.model.Actor;
import com.example.luka2.db.model.IMDBmovie;
import com.example.luka2.db.model.Movie;
import com.example.luka2.MyService;
import com.example.luka2.Example;
import com.example.luka2.Search;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Button btnSearch;
    EditText movieName;
    int position = 0;
    private PripremaORMLightHelper databaseHelper;
    Actor a;

    private Toolbar toolbar;


    public static String KEY = "KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        int position=getIntent().getExtras().getInt("position",0);

        btnSearch = findViewById( R.id.btn_search );
        movieName = findViewById( R.id.ime_filma );
        recyclerView = findViewById( R.id.rvLista );

        try {
            a =  getDatabaseHelper().getActorDao().queryForId(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*recyclerView.addOnItemTouchListener( new RecyclerTouchListener( getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Search movie = adapter.get( position );
                Intent i = new Intent( MainActivity.this, com.example.zavrsni_9.activities.SecondActivity.class );
                i.putExtra( KEY, movie.getImdbID() );
                startActivity( i );
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );*/
        recyclerView.addOnItemTouchListener( new RecyclerTouchListener( getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Search movie = adapter.get( position );
                IMDBmovie m = new IMDBmovie();
                m.setmId2(movie.getImdbID());
                m.setmName(movie.getTitle());
                m.setmUser(a);

                try {
                    getDatabaseHelper().getFilmDao().create(m);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Film je dodat",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );

        search();
    }


    private void search() {
        btnSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovieByName( movieName.getText().toString() );
            }
        } );
    }

    private void getMovieByName(String name) {
        Map<String, String> query = new HashMap<String, String>();
        query.put( "apikey", "1c47bc5b" );
        query.put( "s", name );

        Call<Example> call = MyService.apiInterface().getMovieByName( query );
        call.enqueue( new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                if (response.code() == 200) {
                    Example searches = response.body();


                    ArrayList<Search> search = new ArrayList<>();

                    for (Search e : searches.getSearch()) {
                        search.add( e );
                    }


                    searches.getResponse();

                    layoutManager = new LinearLayoutManager( MainActivity.this );
                    recyclerView.setLayoutManager( layoutManager );

                    adapter = new MyAdapter( MainActivity.this, search );
                    recyclerView.setAdapter( adapter );


                    Toast.makeText( MainActivity.this, "Prikaz filmova", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( MainActivity.this, "Nema filmova", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText( MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState( savedInstanceState );
        savedInstanceState.putInt( "position", position );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public PripremaORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, PripremaORMLightHelper.class);
        }
        return databaseHelper;
    }

//    private void setupToolbar() {
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        final ActionBar actionBar = getSupportActionBar();
//
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.show();
//        }
//    }


}
