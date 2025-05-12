package com.example.joyrides.feature_junior.domain.util

sealed class SortingDirection {
    object Up: SortingDirection()
    object Down: SortingDirection()
}