package com.example.trial_junior.feature_junior.domain.repo

import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem


interface SpecialInterviewListRepo {
    suspend fun getAllSpecialInterviews(): List<SpecialInterviewItem>
    suspend fun getAllSpecialInterviewsFromLocalCache(): List<SpecialInterviewItem>
    suspend fun getAllSpecialInterviewsFromRemote()
    suspend fun getSingleSpecialInterviewItemById(id: Int): SpecialInterviewItem?
    suspend fun addSpecialInterviewItem(item: SpecialInterviewItem)
    suspend fun updateSpecialInterviewItem(item: SpecialInterviewItem)
    suspend fun deleteSpecialInterviewItem(item: SpecialInterviewItem)
}