package com.example.junhosung.aquagroupwalkingapp.proxy;

/**
 * Created by Junho Sung on 3/9/2018.
 */

import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Call<User> createNewUser(@Body User user);

    @POST("/login")
    Call<Void> login(@Body User userWithEmailAndPassword);

    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") Long userId);

    @GET("/users/byEmail")
    Call<User> getUserByEmail(@Query("email") String email);

    // Get Who a User Monitors:

    @GET("/users/{id}/monitorsUsers")
    Call<List<User>> getMonitorsById(@Path("id") Long userId);

    // Make it so that User Monitors Another User:


    @POST("/users/{id}/monitorsUsers")
    Call<List<User>> addNewMonitors(@Path("id") Long userId, @Body User targetUser);

    // Stop Monitoring a User: A stops monitoring B

    @DELETE("/users/{idA}/monitorsUsers/{idB}")
    Call<List<User>> stopMonitors(@Path("idA") Long userId, @Path("idB") Long targetId);

    // Get Who is Monitored By a User:

    @GET("/users/{id}/monitoredByUsers")
    Call<List<User>> getMonitoredByById(@Path("id") Long userId);

    // Make it so that User is Monitored By Another User:

    @POST("/users{id}/monitoredByUsers")
    Call<User> addNewMonitoredBy(@Path("id") Long userId,@Field("id") Long targetId);

    // Stop Being Monitored By a User: A stops being monitored by B

    @DELETE("/users/{idA}/monitoredByUsers/{idB}")
    Call<List<User>> stopMonitoredBy(@Path("idA") Long userId, @Field("idB") Long targetId);

    @GET("/groups")
    Call<List<Group>> getGroups();

    @GET("/groups/{id}")
    Call<Group> getGroupDetails(@Path("id") Long groupId);

    @DELETE("/groups/{id}")
    Call<List<Group>> deleteGroup(@Path("id") Long groupId);







    /**
     * MORE GOES HERE:
     * - Monitoring
     * - Group
     */
}
