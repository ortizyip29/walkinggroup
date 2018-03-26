package com.example.junhosung.aquagroupwalkingapp.proxy;

/**
 * Created by Junho Sung on 3/9/2018.
 */

import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
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
    Call<Void> stopMonitors(@Path("idA") Long userId, @Path("idB") Long targetId);

    // Get Who is Monitored By a User:

    @GET("/users/{id}/monitoredByUsers")
    Call<List<User>> getMonitoredByById(@Path("id") Long userId);

    // Make it so that User is Monitored By Another User:

    @POST("/users/{id}/monitoredByUsers")
    Call<List<User>> addNewMonitoredBy(@Path("id") Long userId,@Body User targetUser);

    // Stop Being Monitored By a User: A stops being monitored by B

    @DELETE("/users/{idA}/monitoredByUsers/{idB}")
    Call<List<User>> stopMonitoredBy(@Path("idA") Long userId, @Path("idB") Long targetId);

    @GET("/groups")
    Call<List<Group>> getGroups();

    @POST("/groups")
    Call<Group> createGroup(@Body Group group);

    @GET("/groups/{id}")
    Call<Group> getGroupDetails(@Path("id") Long groupId);

    // delete Group

    @DELETE("/groups/{id}")
    Call<List<Group>> deleteGroup(@Path("id") Long groupId);

    // Update group details - pass a group object with updated details

    @POST("/groups/{id}")
    Call<Group> updateGroupDetails(@Path("id") Long groupId, @Body Group group);

    @POST("/groups/{id}/memberUsers")
    Call<List<User>> addNewMemberToGroup(@Path("id") Long groupId, @Body User user);

    @DELETE("/groups/{groupId}/memberUsers/{userId}")
    Call<Void> deleteMemberOfGroup(@Path("groupId") Long groupId, @Path("userId") Long userId);

    @GET("/groups/{id}/memberUsers")
    Call<List<User>> getGroupMembers(@Path("id") Long groupId);

    @POST("/users/{id}")
    Call<User> updateUser(@Path("id") Long userId, @Body User user);

    @GET("/messages")
    Call<List<Message>> getMessages();

    @GET("/messages")
    Call<List<Message>> getEmergencyMessages(@Query("is-emergency") boolean is_emergency);

    @GET("/messages")
    Call<List<Message>> getGroupMessages(@Query("togroup") Long groupId);

    @GET("/messages")
    Call<List<Message>> getGroupEmergencyMessages(@Query("togroup") Long groupId, @Query("is-emergency") boolean is_emergency);

    @GET("/messages")
    Call<List<Message>> getUserMessages(@Query("foruser") Long userId);

    @GET("/messages")
    Call<List<Message>> getUserUnreadMessages(@Query("foruser") Long userId, @Query("status") String readUnread);

    @GET("/messages")
    Call<List<Message>> getUserReadMessages(@Query("foruser") Long userId, @Query("status") String read);

    @GET("/messages")
    Call<List<Message>> getUserUnreadEmergencyMessages(@Query("foruser") Long userId, @Query("status") String unread, @Query("is-emergency")
                                                       boolean is_emergency);

    @GET("/messages/{id}")
    Call<Message> getMessageById(@Path("id") Long userId);

    // Messages - @POST

    @POST("/messages/togroup/{groupId}")
    Call<Message> newMsgToGroup(@Path("groupId") Long groupId, @Body Message newMessage);

    @POST("/messages/toparentsof/{userId}") // to Monitoring
    Call<Message> sendMsgToParents(@Path("userId") Long userId, @Body Message newMessage);

    @POST("/messages/{messageId}/readby/{userId}") // in practical use, send only true since you shouldn't be able to mark a read message as unread
    Call<User> msgMarkAsRead(@Path("messageId") Long messageId, @Path("userId") Long userId,@Body boolean trueOrFalse);

    // Messages - @Delete

    @DELETE("/messages/{id}")
    Call<Void> deleteMsg(@Path("id") Long messageId);

}
