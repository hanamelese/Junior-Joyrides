package com.example.trial_junior.feature_junior.domain.util

sealed class SortingDirection {
    object Up: SortingDirection()
    object Down: SortingDirection()
}