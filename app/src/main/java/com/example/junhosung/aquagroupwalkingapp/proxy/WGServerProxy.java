package com.example.junhosung.aquagroupwalkingapp.proxy;

/**
 * Created by Junho Sung on 3/9/2018.
 */

import com.example.junhosung.aquagroupwalkingapp.model.User2;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
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

    // Get Who a User Monitors:

    @GET("/users/{id}/monitorsUsers")
    Call<List<User2>> getMonitorsById(@Path("id") Long userId);

    // Make it so that User Monitors Another User:

    @POST("/users/{id}/monitorsUsers")
    Call<User2> addNewMonitors(@Path("id") Long userId, @Field("id") Long targetId);

    // Stop Monitoring a User: A stops monitoring B

    @DELETE("/users/{idA}/monitorsUsers/{idB}")
    Call<List<User2>> stopMonitors(@Path("idA") Long userId, @Path("idB") long targetId);

    // Get Who is Monitored By a User:

    @GET("/users/{id}/monitoredByUsers")
    Call<List<User2>> getMonitoredByById(@Path("id") Long userId);

    // Make it so that User is Monitored By Another User:

    @POST("/users{id}/monitoredByUsers")
    Call<User2> addNewMonitoredBy(@Path("id") long userId,@Field("id") Long targetId);

    // Stop Being Monitored By a User: A stops being monitored by B

    @DELETE("/users/{idA}/monitoredByUsers/{idB}")
    Call<List<User2>> stopMonitoredBy(@Path("idA") long userId, @Field("id") Long targetId);








    /**
     * MORE GOES HERE:
     * - Monitoring
     * - Group
     */
}
