package com.example.netflix.DashBoard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflix.Adapter.CustomAdapter;
import com.example.netflix.Adapter.SearchAdapter;
import com.example.netflix.ConnectionHelper;
import com.example.netflix.CustomDialog;
import com.example.netflix.R;
import com.example.netflix.constants.APIClient;
import com.example.netflix.constants.ApiInterface;
import com.example.netflix.models.SearchSeries;
import com.example.netflix.models.Series;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity {
    ArrayList<Series> list = new ArrayList<>();
    ArrayList<SearchSeries> searchseries = new ArrayList<>();
    CustomAdapter customAdapter;
    SearchAdapter searchAdapter;
    CustomDialog customDialog;
    RecyclerView recyclerView;
    SearchView search_view;
    Button search;
    TextView no_data;
    ConnectionHelper helper;
    boolean isInternet;
    ApiInterface apiInterface = APIClient.getRetrofit().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        helper = new ConnectionHelper(DashBoard.this);
        isInternet = helper.isConnectingToInternet();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        search_view = (SearchView) findViewById(R.id.search_view);
        search = (Button) findViewById(R.id.search);
        no_data = (TextView) findViewById(R.id.no_data);

        // Setting hint text for searchview
        search_view.setQueryHint(getString(R.string.search_series));

        // check net connectivity
        if (isInternet) {
            seriesList();
        } else {
            Toast.makeText(DashBoard.this, getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternet) {
                    filterList(search_view.getQuery().toString());
                }else {
                    Toast.makeText(DashBoard.this, getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        search_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_view.onActionViewExpanded();
            }
        });
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            } @Override
            public boolean onQueryTextChange(String newText) {
            if (newText.trim().length()==0){
                if (list.size() != 0) {
                    no_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    customAdapter = new CustomAdapter(DashBoard.this,list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DashBoard.this));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(customAdapter);
                } else {
                    no_data.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
            return true;
            }
        }
        );
    }

    private void seriesList() {
        customDialog = new CustomDialog(DashBoard.this);
        customDialog.setCancelable(false);
        customDialog.show();

        Call<ArrayList<Series>> call = null;
        call = apiInterface.seriesList();

        call.enqueue(new Callback<ArrayList<Series>>() {
            @Override
            public void onResponse(Call<ArrayList<Series>> call, Response<ArrayList<Series>> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    list = response.body();
                    if (list.size() != 0) {
                        no_data.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        // initializing our adapter class.
                        customAdapter = new CustomAdapter(DashBoard.this,list);
                        // adding layout manager to our recycler view.
                        recyclerView.setLayoutManager(new LinearLayoutManager(DashBoard.this));
                        recyclerView.setHasFixedSize(true);
                        //  setting adapter to our recycler view.
                        recyclerView.setAdapter(customAdapter);
                    } else {
                        no_data.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(DashBoard.this, getString(R.string.series_error), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Series>> call, Throwable t) {
                customDialog.dismiss();
                Log.d("TestTag", "onFailure" + t.getMessage());
            }
        });
    }

    private void filterList(String data) {
        customDialog = new CustomDialog(DashBoard.this);
        customDialog.setCancelable(false);
        customDialog.show();

        Call<ArrayList<SearchSeries>> call = null;
        call = apiInterface.filterList(data);

        call.enqueue(new Callback<ArrayList<SearchSeries>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchSeries>> call, Response<ArrayList<SearchSeries>> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    searchseries = response.body();
                    if (searchseries.size() != 0) {
                        no_data.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        // initializing our adapter class.
                        searchAdapter = new SearchAdapter(DashBoard.this,searchseries);
                        // adding layout manager to our recycler view.
                        recyclerView.setLayoutManager(new LinearLayoutManager(DashBoard.this));
                        recyclerView.setHasFixedSize(true);
                        //  setting adapter to our recycler view.
                        recyclerView.setAdapter(searchAdapter);
                    } else {
                        no_data.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(DashBoard.this, getString(R.string.series_error), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<SearchSeries>> call, Throwable t) {
                customDialog.dismiss();
                Log.d("TestTag", "onFailure" + t.getMessage());
            }
        });
    }
}
