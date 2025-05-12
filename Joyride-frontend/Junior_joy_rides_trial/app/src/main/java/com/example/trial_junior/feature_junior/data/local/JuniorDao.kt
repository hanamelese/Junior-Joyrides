package com.example.trial_junior.feature_junior.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trial_junior.feature_junior.data.local.dto.LocalBasicInterviewItem
import com.example.trial_junior.feature_junior.data.local.dto.LocalInvitationItem
import com.example.trial_junior.feature_junior.data.local.dto.LocalSpecialInterviewItem
import com.example.trial_junior.feature_junior.data.local.dto.LocalUserItem
import com.example.trial_junior.feature_junior.data.local.dto.LocalWishListItem


@Dao
interface InvitationDao {
    @Query("SELECT * FROM invitation")
    fun getAllInvitationItems(): List<LocalInvitationItem>

    @Query("SELECT * FROM invitation WHERE id = :id")
    suspend fun getSingleInvitationItemById(id: Int): LocalInvitationItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllInvitationItems(invitations: List<LocalInvitationItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInvitationItem(invitation: LocalInvitationItem): Long

    @Delete
    suspend fun deleteInvitationItem(invitation: LocalInvitationItem)
}

@Dao
interface BasicInterviewDao {
    @Query("SELECT * FROM basic_interview")
    fun getAllBasicInterviewItems(): List<LocalBasicInterviewItem>

    @Query("SELECT * FROM basic_interview WHERE id = :id")
    suspend fun getSingleBasicInterviewItemById(id: Int): LocalBasicInterviewItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllBasicInterviewItems(items: List<LocalBasicInterviewItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBasicInterviewItem(item: LocalBasicInterviewItem): Long

    @Delete
    suspend fun deleteBasicInterviewItem(item: LocalBasicInterviewItem)
}

@Dao
interface SpecialInterviewDao {
    @Query("SELECT * FROM special_interview")
    fun getAllSpecialInterviewItems(): List<LocalSpecialInterviewItem>

    @Query("SELECT * FROM special_interview WHERE id = :id")
    suspend fun getSingleSpecialInterviewItemById(id: Int): LocalSpecialInterviewItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllSpecialInterviewItems(items: List<LocalSpecialInterviewItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSpecialInterviewItem(item: LocalSpecialInterviewItem): Long

    @Delete
    suspend fun deleteSpecialInterviewItem(item: LocalSpecialInterviewItem)
}

@Dao
interface WishListDao {
    @Query("SELECT * FROM wish_list")
    fun getAllWishListItems(): List<LocalWishListItem>

    @Query("SELECT * FROM wish_list WHERE id = :id")
    suspend fun getSingleWishListItemById(id: Int): LocalWishListItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllWishListItems(items: List<LocalWishListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWishListItem(item: LocalWishListItem): Long

    @Delete
    suspend fun deleteWishListItem(item: LocalWishListItem)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Int): LocalUserItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: LocalUserItem)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: Int)
}