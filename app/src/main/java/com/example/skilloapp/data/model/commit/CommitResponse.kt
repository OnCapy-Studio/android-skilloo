package com.example.skilloapp.data.model.commit

import com.example.skilloapp.data.model.common.Sort

data class CommitResponse(
    val content: List<CommitItem>,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val sort: Sort
)