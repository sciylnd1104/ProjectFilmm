package com.example.filmkita;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.filmkita.adapter.MovieListAdapter;
import com.example.filmkita.model.Movie;
import com.example.filmkita.network.ApiService;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopularMovie extends AppCompatActivity {
    MovieListAdapter movieListAdapter;
    RecyclerView recyclerView;
    private int page = 1;
    GridLayoutManager gridLayoutManager;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie);

        movieListAdapter = new MovieListAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieListAdapter);

        loadData();
    }

    private void loadData() {
        apiService = new ApiService();
        apiService.getPopularMovies(page, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Movie movie = (Movie) response.body();
                //Long.valueOf(""+movie.getResults());
                if (movie!=null){
                if (movieListAdapter != null) {
                    movieListAdapter.addAll(movie.getResults());
                } }else {
                    Toast.makeText(getBaseContext(), "Tidak Ada Data", Toast.LENGTH_LONG).show();

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {

                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getBaseContext(), "Request Timeout", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Connect Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
