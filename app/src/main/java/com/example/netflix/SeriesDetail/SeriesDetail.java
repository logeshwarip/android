package com.example.netflix.SeriesDetail;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.Adapter.SpinnerAdapter;
import com.example.netflix.CustomDialog;
import com.example.netflix.R;
import com.example.netflix.constants.APIClient;
import com.example.netflix.constants.ApiInterface;
import com.example.netflix.models.Series;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesDetail extends AppCompatActivity {
    Integer id;
    CustomDialog customDialog;
    ApiInterface apiInterface = APIClient.getRetrofit().create(ApiInterface.class);
    private Spinner spinner;
    TextView titleText, desText, seasonText;
    ImageView thumnailImage;
    ArrayList<Series> list = new ArrayList<>();
    SpinnerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);
        spinner = findViewById(R.id.season_spinner);
        titleText = findViewById(R.id.title);
        desText = findViewById(R.id.description);
        thumnailImage = findViewById(R.id.thumnailImage);
        seasonText = findViewById(R.id.season);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.episode));
        id = getIntent().getIntExtra("episodesId",0);

        episodesMethod(id);
    }
    private void episodesMethod(Integer data) {
        customDialog = new CustomDialog(SeriesDetail.this);
        customDialog.setCancelable(false);
        customDialog.show();

        Call<ArrayList<Series>> call = null;
        call = apiInterface.season(data);

        call.enqueue(new Callback<ArrayList<Series>>() {
            @Override
            public void onResponse(Call<ArrayList<Series>> call, Response<ArrayList<Series>> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    ArrayList<Series> seriesList = new ArrayList<>();
                    seriesList = response.body();
                    for(int i = 0;i<seriesList.size(); i++){
                        Series series = new Series();
                        series.setName(seriesList.get(i).getName());
                        series.setId(seriesList.get(i).getId());
                        series.setSeason(seriesList.get(i).getSeason());
                        series.setRuntime(seriesList.get(i).getRuntime());
                        if (seriesList.get(i).getImage() != null) {
                            series.setImageurl(seriesList.get(i).getImage().getOriginal());
                        }else {
                            series.setImageurl(null);
                        }
                        series.setSummary(seriesList.get(i).getSummary());
                        series.setAirdate(seriesList.get(i).getAirdate());
                        series.setAirtime(seriesList.get(i).getAirtime());
                        series.setRuntime(seriesList.get(i).getRuntime());
                        list.add(series);
                    }
                    spinnerAdapter();
                } else {
                    Toast.makeText(SeriesDetail.this, getString(R.string.series_error), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Series>> call, Throwable t) {
                customDialog.dismiss();
                Log.d("TestTag", "onFailure" + t.getMessage());
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    public void spinnerAdapter(){
        adapter = new SpinnerAdapter(this, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Series series = (Series) list.get(position);
                displaySeriesDetails(series);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void displaySeriesDetails(Series series){
      titleText.setText(series.getName());
        seasonText.setText("Name:"+series.getName() +"\n"+"\n"+ "Duration:"+series.getRuntime() +"\n"+"\n"+ "Release date:"+series.getAirdate());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            desText.setText(Html.fromHtml(series.getSummary(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            desText.setText(Html.fromHtml(series.getSummary()).toString());
        }
        if (series.getImageurl() != null) {
            Picasso.with(getApplicationContext()).load(series.getImageurl()).into(thumnailImage);
        }else {
            Picasso.with(getApplicationContext()).load(R.drawable.netflix_splash).into(thumnailImage);
        }
    }
}