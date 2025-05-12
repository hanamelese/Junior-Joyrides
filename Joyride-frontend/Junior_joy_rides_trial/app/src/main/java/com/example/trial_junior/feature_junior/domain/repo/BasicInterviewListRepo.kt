package com.example.trial_junior.feature_junior.domain.repo

import com.example.trial_junior.feature_junior.domain.model.BasicInterviewItem

interface BasicInterviewListRepo {
    suspend fun getAllBasicInterviews(): List<BasicInterviewItem>
    suspend fun getAllBasicInterviewsFromLocalCache(): List<BasicInterviewItem>
    suspend fun getAllBasicInterviewsFromRemote()
    suspend fun getSingleBasicInterviewItemById(id: Int): BasicInterviewItem?
    suspend fun addBasicInterviewItem(item: BasicInterviewItem)
    suspend fun updateBasicInterviewItem(item: BasicInterviewItem)
    suspend fun deleteBasicInterviewItem(item: BasicInterviewItem)
}