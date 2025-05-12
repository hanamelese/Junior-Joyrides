
package com.example.joyrides.feature_junior.domain.model

interface ListItem {
    val id: Int?
    val upcoming: Boolean
    val approved: Boolean
}

data class InvitationItem(
    val childName: String,
    val guardianEmail: String,
    val specialRequests: String,
    val address: String,
    val date: String,
    val time: Long,
    override val upcoming: Boolean,
    override val approved: Boolean,
    val guardianPhone: Long,
    val age: Int,
    val userId: Int,
    override val id: Int? = null
) : ListItem
