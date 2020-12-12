package com.products;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Node {

    @POST("/user_registration/")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String phone,
                                    @Field("phone") String email,
                                    @Field("password") String password);
    @POST("/login/")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    @GET("/getProducts")
    Call<List<GetAllProductsPojo>> getOrders();

    @POST("/addproduct/")
    @FormUrlEncoded
    Observable<String> product(@Field("name") String name,
                                    @Field("price") String price,
                                    @Field("quantity") String quantity,
                                    @Field("description") String description);


}
