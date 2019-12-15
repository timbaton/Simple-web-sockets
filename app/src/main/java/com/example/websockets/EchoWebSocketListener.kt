package com.example.websockets

import android.util.Log
import okhttp3.*
import okio.ByteString
import org.json.JSONObject

class SocketAdapter(
    private var messageSendCallback: () -> Unit,
    private val messageGotCallback: (String) -> Unit
) {

    private var client: OkHttpClient = OkHttpClient()

    val request =
        Request.Builder().url("wss://backend-chat.cloud.technokratos.com/chat").build()
    var ws: WebSocket? = null

    init {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response?) {
                super.onOpen(webSocket, response)
                val json = JSONObject().put("device_id", "22813371488")
                webSocket.send(json.toString())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("my tag", "Receiving : $text")

                if (text.contains("items")) {
                    messageGotCallback(text)
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d("my tag", "Receiving bytes : " + bytes.hex())
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1, null)
                Log.d("my tag", "Closing : $code / $reason")
            }

            override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
                Log.d("my tag", "Error : " + t.message)

                // in some reasons we have to recreate websocket...
                ws = client.newWebSocket(request, this)
                val json = JSONObject().put("device_id", "22813371488")
                ws?.send(json.toString())
                messageSendCallback()
            }
        }

        ws = client.newWebSocket(request, listener)
    }


    fun sendMessage(type: String, value: String) {
        val json = JSONObject().put(type, value)

        ws?.send(json.toString())
    }

    fun askHistory(limit: Int) {
        ws?.send("{ \"history\": { \"limit\": $limit}}")
    }
}