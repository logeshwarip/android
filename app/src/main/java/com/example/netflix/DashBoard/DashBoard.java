package com.example.netflix.DashBoard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflix.Adapter.CustomAdapter;
import com.example.netflix.ConnectionHelper;
import com.example.netflix.CustomDialog;
import com.example.netflix.R;
import com.example.netflix.SeriesDetail.SeriesDetail;
import com.example.netflix.constants.APIClient;
import com.example.netflix.constants.ApiInterface;
import com.example.netflix.models.Shows;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity {
    ArrayList<Shows> list = new ArrayList<>();
    CustomAdapter customAdapter;
    CustomDialog customDialog;
    RecyclerView recyclerView;
    SearchView search_view;
    Button search;
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

        // Setting hint text for searchview
        search_view.setQueryHint("Search Shows");

        showsList();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void showsList() {
        customDialog = new CustomDialog(DashBoard.this);
        customDialog.setCancelable(false);
        customDialog.show();

        Call<ArrayList<Shows>> call = null;
        call = apiInterface.showsList();

        call.enqueue(new Callback<ArrayList<Shows>>() {
            @Override
            public void onResponse(Call<ArrayList<Shows>> call, Response<ArrayList<Shows>> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    list = response.body();
                    if (list.size() != 0) {
                        // initializing our adapter class.
                        customAdapter = new CustomAdapter(DashBoard.this,list);
                        // adding layout manager to our recycler view.
                        recyclerView.setLayoutManager(new LinearLayoutManager(DashBoard.this));
                        recyclerView.setHasFixedSize(true);
                        //  setting adapter to our recycler view.
                        recyclerView.setAdapter(customAdapter);
                    } else {
                        Toast.makeText(DashBoard.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DashBoard.this, getString(R.string.shows_error), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Shows>> call, Throwable t) {
                customDialog.dismiss();
                Log.d("TestTag", "onFailure" + t.getMessage());
            }
        });
    }
}
