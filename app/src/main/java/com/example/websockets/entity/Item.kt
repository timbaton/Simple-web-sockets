package com.example.websockets.entity


import com.fasterxml.jackson.annotation.JsonProperty

data class Item(
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("message")
    val message: String?,
    @JsonProperty("user")
    val user: String?
)