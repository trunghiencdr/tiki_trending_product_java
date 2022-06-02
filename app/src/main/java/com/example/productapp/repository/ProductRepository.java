package com.example.productapp.repository;


import com.example.productapp.dto.ResponseMessage;

import io.reactivex.Observable;
import kotlin.ParameterName;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductRepository {

    @GET("shopping-trend/api/trendings/hub?")
    Observable<Response<ResponseMessage>> getProducts(@Query("cursor") int cursor,
                                                      @Query("limit") int limit);
}
