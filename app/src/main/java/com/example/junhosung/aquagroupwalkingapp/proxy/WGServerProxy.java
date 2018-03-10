package com.example.junhosung.aquagroupwalkingapp.proxy;

/**
 * Created by Junho Sung on 3/9/2018.
 */

import com.example.junhosung.aquagroupwalkingapp.model.User2;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The ProxyBuilder class will handle the apiKey and token being injected as a header to all calls
 * This is a Retrofit interface.
 */
public interface WGServerProxy {
    @GET("getApiKey")
    Call<String> getApiKey(@Query("groupName") String groupName, @Query("sfuUserId") String sfuId);

    @POST("/users/signup")
    Call<User2> createNewUser(@Body User2 user2);

    @POST("/login")
    Call<Void> login(@Body User2 user2WithEmailAndPassword);

    @GET("/users")
    Call<List<User2>> getUsers();

    @GET("/users/{id}")
    Call<User2> getUserById(@Path("id") Long userId);

    @GET("/users/byEmail")
    Call<User2> getUserByEmail(@Query("email") String email);

    // Monitoring







    /**
     * MORE GOES HERE:
     * - Monitoring
     * - Groups
     */
}
