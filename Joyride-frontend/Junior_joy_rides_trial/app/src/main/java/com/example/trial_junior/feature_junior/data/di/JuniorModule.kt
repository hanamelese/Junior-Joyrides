package com.example.trial_junior.feature_junior.data.di

import android.content.Context
import androidx.room.Room
import com.example.trial_junior.feature_junior.data.TokenManager
import com.example.trial_junior.feature_junior.data.local.InvitationDao
import com.example.trial_junior.feature_junior.data.local.JuniorDatabase
import com.example.trial_junior.feature_junior.data.local.BasicInterviewDao
import com.example.trial_junior.feature_junior.data.local.SpecialInterviewDao
import com.example.trial_junior.feature_junior.data.local.UserDao
import com.example.trial_junior.feature_junior.data.local.WishListDao
import com.example.trial_junior.feature_junior.data.remote.Api
import com.example.trial_junior.feature_junior.data.repo.BasicInterviewListRepoImpl
import com.example.trial_junior.feature_junior.data.repo.InvitationListRepoImpl
import com.example.trial_junior.feature_junior.data.repo.SpecialInterviewListRepoImpl
import com.example.trial_junior.feature_junior.data.repo.UserListRepoImpl
import com.example.trial_junior.feature_junior.data.repo.WishListRepoImpl
import com.example.trial_junior.feature_junior.domain.repo.BasicInterviewListRepo
import com.example.trial_junior.feature_junior.domain.repo.InvitationListRepo
import com.example.trial_junior.feature_junior.domain.repo.SpecialInterviewListRepo
import com.example.trial_junior.feature_junior.domain.repo.UserListRepo
import com.example.trial_junior.feature_junior.domain.repo.WishListRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JuniorModule {

    @Provides
    fun providesRetrofitApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(tokenManager: TokenManager): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val original = chain.request()
                val token = tokenManager.getToken()
                val requestBuilder = original.newBuilder()
                if (token != null) {
                    requestBuilder.header("Authorization", "Bearer $token")
                }
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:3000/")
            .client(client)
            .build()
    }

    @Provides
    fun providesInvitationDao(database: JuniorDatabase): InvitationDao {
        return database.invitationDao
    }

    @Provides
    fun providesBasicInterviewDao(database: JuniorDatabase): BasicInterviewDao {
        return database.basicInterviewDao
    }

    @Provides
    fun providesSpecialInterviewDao(database: JuniorDatabase): SpecialInterviewDao {
        return database.specialInterviewDao
    }

    @Provides
    fun providesWishListDao(database: JuniorDatabase): WishListDao {
        return database.wishListDao
    }

    @Provides
    fun providesUserDao(database: JuniorDatabase): UserDao {
        return database.userDao
    }

    @Singleton
    @Provides
    fun providesRoomDb(
        @ApplicationContext appContext: Context
    ): JuniorDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            JuniorDatabase::class.java,
            name = "junior_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesInvitationRepo(
        db: JuniorDatabase,
        api: Api,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): InvitationListRepo {
        return InvitationListRepoImpl(db.invitationDao, api, dispatcher)
    }

    @Provides
    @Singleton
    fun providesBasicInterviewRepo(
        db: JuniorDatabase,
        api: Api,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): BasicInterviewListRepo {
        return BasicInterviewListRepoImpl(db.basicInterviewDao, api, dispatcher)
    }

    @Provides
    @Singleton
    fun providesSpecialInterviewRepo(
        db: JuniorDatabase,
        api: Api,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): SpecialInterviewListRepo {
        return SpecialInterviewListRepoImpl(db.specialInterviewDao, api, dispatcher)
    }

    @Provides
    @Singleton
    fun providesWishListRepo(
        db: JuniorDatabase,
        api: Api,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): WishListRepo {
        return WishListRepoImpl(db.wishListDao, api, dispatcher)
    }

    @Provides
    @Singleton
    fun providesUserRepo(
        db: JuniorDatabase,
        api: Api,
        @IoDispatcher dispatcher: CoroutineDispatcher,
        tokenManager: TokenManager
    ): UserListRepo {
        return UserListRepoImpl(db.userDao, api, tokenManager, dispatcher)
    }
}