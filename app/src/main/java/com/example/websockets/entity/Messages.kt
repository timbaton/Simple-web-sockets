package com.example.websockets.entity


import com.fasterxml.jackson.annotation.JsonProperty

data class Messages(
    @JsonProperty("first")
    val first: Int?,
    @JsonProperty("items")
    val items: List<Item?>?
)