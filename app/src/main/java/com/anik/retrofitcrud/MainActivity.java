package com.anik.retrofitcrud;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anik.retrofitcrud.model.Contacts;
import com.anik.retrofitcrud.remote.ApiClient;
import com.anik.retrofitcrud.remote.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private List<Contacts> contactsList;
    private ApiInterface apiInterface;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

       fetchData("users", "");

    }

    public void fetchData(String type, String key){
        Call<List<Contacts>> call = apiInterface.getContact(type,key);
        call.enqueue(new Callback<List<Contacts>>() {
            @Override
            public void onResponse(Call<List<Contacts>> call, Response<List<Contacts>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                contactsList = response.body();
                adapter = new Adapter(MainActivity.this, contactsList);
                recyclerView.setAdapter(adapter);


                adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchData("users", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchData("users", newText);
                return false;
            }
        });
        return true;
    }



    //menu item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(this, EditorActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //when activity is resumed this method is called
    @Override
    protected void onResume() {
        super.onResume();
        fetchData("users", "");
    }
}