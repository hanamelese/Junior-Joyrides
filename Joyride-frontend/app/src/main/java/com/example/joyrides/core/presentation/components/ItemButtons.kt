package com.example.trial_junior.core.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.CheckCircle

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.trial_junior.core.util.ContentDescriptions


@Composable
fun HostedButton(
    onToggleHostedClick: () -> Unit,
    color: Color,
    upcoming: Boolean,
    modifier: Modifier = Modifier
){
    IconButton(onClick = {
        onToggleHostedClick()
    },
        modifier = modifier
    ) {
        if(!upcoming){
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = ContentDescriptions.USE_ITEM,
                tint = color,
                modifier = Modifier.size(48.dp)
            )
        }else {
            EmptyCircle(color = color)
        }

    }
}

@Composable
fun EmptyCircle(color: Color, strokeWidth: Float = 9f){
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val radius = 39.0F
            drawCircle(
                color,
                center = center,
                radius = radius,
                style = Stroke(width = strokeWidth)
            )
        }
    )
}

@Composable
fun EditButton(
    onEditClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onEditClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.FolderOpen,
            contentDescription = ContentDescriptions.UPDATE_ITEM,
            tint = color,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun DeleteButton(
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onDeleteClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.DeleteOutline,
            contentDescription = ContentDescriptions.DELETE_ITEM,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(32.dp)
        )
    }
}