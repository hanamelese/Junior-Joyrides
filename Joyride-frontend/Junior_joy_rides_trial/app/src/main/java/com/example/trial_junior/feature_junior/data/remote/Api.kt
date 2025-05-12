package com.example.trial_junior.feature_junior.data.remote

import com.example.trial_junior.feature_junior.data.remote.dto.RegisterRequest
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteBasicInterviewItem
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteInvitationItem
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteSpecialInterviewItem
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteUserItem
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteWishListItem
import com.example.trial_junior.feature_junior.data.remote.dto.UpdateProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("api/invitation")
    suspend fun getAllInvitations(): List<RemoteInvitationItem>

    @GET("api/invitation/{id}")
    suspend fun getInvitationById(@Path("id") id: Int): RemoteInvitationItem

    @POST("api/invitation")
    suspend fun addInvitation(@Body updatedInvitation: RemoteInvitationItem): Response<RemoteInvitationItem>

    @PATCH("api/invitation/{id}")
    suspend fun updateInvitationItem(
        @Path("id") id: Int,
        @Body invitationItem: RemoteInvitationItem
    ): Response<Unit>

    @DELETE("api/invitation/{id}")
    suspend fun deleteInvitation(@Path("id") id: Int): Response<Unit>

    @GET("api/basic-Interview")
    suspend fun getAllBasicInterviews(): List<RemoteBasicInterviewItem>

    @GET("api/basic-Interview/{id}")
    suspend fun getBasicInterviewById(@Path("id") id: Int): RemoteBasicInterviewItem

    @POST("api/basic-Interview")
    suspend fun addBasicInterview(@Body updatedItem: RemoteBasicInterviewItem): Response<RemoteBasicInterviewItem>

    @PATCH("api/basic-Interview/{id}")
    suspend fun updateBasicInterviewItem(
        @Path("id") id: Int,
        @Body item: RemoteBasicInterviewItem
    ): Response<Unit>

    @DELETE("api/basic-Interview/{id}")
    suspend fun deleteBasicInterview(@Path("id") id: Int): Response<Unit>

    @GET("api/special-Interview")
    suspend fun getAllSpecialInterviews(): List<RemoteSpecialInterviewItem>

    @GET("api/special-Interview/{id}")
    suspend fun getSpecialInterviewById(@Path("id") id: Int): RemoteSpecialInterviewItem

    @POST("api/special-Interview")
    suspend fun addSpecialInterview(@Body updatedItem: RemoteSpecialInterviewItem): Response<RemoteSpecialInterviewItem>

    @PATCH("api/special-Interview/{id}")
    suspend fun updateSpecialInterviewItem(
        @Path("id") id: Int,
        @Body item: RemoteSpecialInterviewItem
    ): Response<Unit>

    @DELETE("api/special-Interview/{id}")
    suspend fun deleteSpecialInterview(@Path("id") id: Int): Response<Unit>

    @GET("api/wishLists")
    suspend fun getAllWishListItems(): List<RemoteWishListItem>

    @GET("api/wishLists/{id}")
    suspend fun getWishListItemById(@Path("id") id: Int): RemoteWishListItem

    @POST("api/wishLists")
    suspend fun addWishListItem(@Body updatedItem: RemoteWishListItem): Response<RemoteWishListItem>

    @PATCH("api/wishLists/{id}")
    suspend fun updateWishListItem(
        @Path("id") id: Int,
        @Body item: RemoteWishListItem
    ): Response<Unit>

    @DELETE("api/wishLists/{id}")
    suspend fun deleteWishListItem(@Path("id") id: Int): Response<Unit>

    @POST("api/auth/register")
    suspend fun registerUser(@Body registerDTO: RegisterRequest): Response<Map<String, String>>

    @POST("api/auth/login")
    suspend fun loginUser(@Body loginDTO: Map<String, String>): Response<Map<String, String>>

    @GET("api/user/my-profile")
    suspend fun getMyProfile(): RemoteUserItem

    @PATCH("api/user/edit-profile")
    suspend fun updateProfile(@Body updateProfileDTO: UpdateProfileRequest): Response<Map<String, String>>
}