package com.example.productapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.productapp.dao.ProductDAO;
import com.example.productapp.dto.ProductDTO;
import com.example.productapp.dto.ResponseMessage;
import com.example.productapp.repository.ProductRepository;
import com.example.productapp.retroinstance.RetroInstance;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProductViewModel extends AndroidViewModel {
    ProductRepository productRepository;
    MutableLiveData<List<ProductDTO>> products = new MutableLiveData<>();
    ProductDAO productDAO;
    Observable<Response<ResponseMessage>> responseObservable;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productDAO = new ProductDAO(application);
        products = new MutableLiveData<>();
        productRepository = RetroInstance.getRetrofitClient().create(ProductRepository.class);
    }


    public Observer<Response<ResponseMessage>> getProducts(int cursor, int limit) {
        return productRepository.getProducts(cursor, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Response<ResponseMessage>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseMessage> responseMessageResponse) {
                        if (responseMessageResponse.code() == 200) {
                            products.setValue(responseMessageResponse.body().getData().getData());
                        }
//                        products.getValue().stream().forEach(productDTO -> Log.d("", productDTO.toString()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("", "complete call");
                    }
                });
    }

    public MutableLiveData<List<ProductDTO>> getProductMultable() {
        return products;
    }

    public boolean updateProductsSqlite() {
        productDAO.updateProducts(products.getValue());
        return true;
    }

    public Observer<Boolean> updateProductsSqlite2() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            if (products.getValue().size() != 0)
                                emitter.onNext(productDAO.updateProducts(products.getValue()));
                        }
                        if (!emitter.isDisposed()) emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean)
                            Log.d("", "Success update");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ProductApp.updateSQLite ", e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.d("", "on complete update sqlite");

                    }
                });
    }


    // get products from sql_lite
    public List<ProductDTO> getProductsFromSqlite() {
        return productDAO.getProducts();
    }

    public void deleteAllProducts(){
        productDAO.deleteAll();
    }

    public List<ProductDTO> searchProductBySameNameOrPrice(String s) {
        return productDAO.searchProductBySameNameOrPrice(s);
    }


}
