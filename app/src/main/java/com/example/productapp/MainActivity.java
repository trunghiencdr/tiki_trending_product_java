package com.example.productapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productapp.dto.ProductDTO;
import com.example.productapp.util.SpacesItemDecoration;
import com.example.productapp.view.ProductAdapter;
import com.example.productapp.viewmodel.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    ProductViewModel productViewModel;
    FloatingActionButton btnUpdateProducts;
    CompositeDisposable disposable = new CompositeDisposable();
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    SearchView searchView;
    View view;
    int cursor = 0;
    int limit = 20;
    Toolbar toolbar;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.relative_layout_main);


        setControls();
        setEvents();

        productViewModel.getProductMultable().observe(this, new Observer<List<ProductDTO>>() {
            @Override
            public void onChanged(List<ProductDTO> productDTOS) {
                productViewModel.updateProductsSqlite();
                productAdapter.submitList(productViewModel.getProductsFromSqlite());
            }
        });


    }

    private void setEvents() {
//        btnUpdateProducts.setOnClickListener(view -> {
//            if (isNetworkAvailable()) {
//                disposable.add(productViewModel.getProducts(cursor, limit));
//            } else {
//                Snackbar snackbar = Snackbar
//                        .make(view, "You are in offline", Snackbar.LENGTH_LONG);
//                snackbar.show();
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                productAdapter.submitList(productViewModel.searchProductBySameNameOrPrice(s));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                productAdapter.submitList(productViewModel.searchProductBySameNameOrPrice(s));
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    private void setControls() {
        setBackground();
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productAdapter = new ProductAdapter(ProductDTO.itemCallback, this);


        recyclerView = findViewById(R.id.recycler_view_product);
        recyclerView.setLayoutManager(new GridLayoutManager(this,
                2));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setAdapter(productAdapter);
        List<ProductDTO> list = productViewModel.getProductsFromSqlite();
        productAdapter.submitList(list);



        searchView = findViewById(R.id.search_view_product);
        toolbar = findViewById(R.id.tool_bar_relative);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_drag_indicator_24));
        if(isNetworkAvailable()){
            productViewModel.deleteAllProducts();
            productViewModel.getProducts(cursor, limit);
        }else{
            Snackbar snackbar = Snackbar
                    .make(view, "You are in offline", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    private void setBackground() {
        Picasso.get()
                .load("https://salt.tikicdn.com/ts/upload/de/18/7d/cd34c8e0e0a70051af53c06c149efa5b.png")
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        // Todo: Do something with your bitmap here
                        view.setBackground(new BitmapDrawable(bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }



                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        System.out.println("come in create menu option");
        this.getMenuInflater().inflate(R.menu.menu_toolbar_trending_product, menu);
        System.out.println("Return true");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println( "come item selected");
        switch (item.getItemId()) {
            case R.id.menu_trending_product_update_sqlite:
                Log.d("", "come item update selected");
                if (isNetworkAvailable()) {
                    productViewModel.deleteAllProducts();
                    productViewModel.getProducts(cursor, limit);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(view, "You are in offline", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}