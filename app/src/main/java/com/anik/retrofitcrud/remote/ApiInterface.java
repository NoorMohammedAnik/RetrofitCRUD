package com.anik.retrofitcrud.remote;

import com.anik.retrofitcrud.Constant;
import com.anik.retrofitcrud.model.Contacts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("retrofit/POST/readcontacts.php")
    Call<List<Contacts>> getContacts();

    @FormUrlEncoded
    @POST("retrofit/POST/addcontact.php")
    public Call<Contacts> insertUser(
            @Field("name") String name,
            @Field("email") String email);


    //for signup
    @FormUrlEncoded
    @POST("retrofit/POST/signup.php")
     Call<Contacts> signUp(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password);



    //for login
    @FormUrlEncoded
    @POST("retrofit/POST/login.php")
    Call<Contacts> login(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password);


    @FormUrlEncoded
    @POST("retrofit/POST/editcontact.php")
    public Call<Contacts> editUser(
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email);


    @FormUrlEncoded
    @POST("retrofit/POST/deletecontact.php")
    Call<Contacts> deleteUser(
            @Field("id") int id
    );




    //for live data search
    @GET("retrofit/GET/getcontacts.php")
    Call<List<Contacts>> getContact(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

}