package com.example.websockets

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.websockets.entity.Messages
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listener: SocketAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listener = SocketAdapter({
            listener?.askHistory(40)
        }, {
            this.runOnUiThread {
                showMessages(it)
            }
        })

        btn_send.setOnClickListener {
            listener?.sendMessage("message", et_message.text.toString())
        }
    }

    fun showMessages(text: String) {
        val mapper = ObjectMapper()

        val messages: Messages =
            mapper.readValue(text, Messages::class.java)

        var messageText = ""
        messages.items!!.forEach {
            messageText += "${it?.user}: ${it?.message} \n"
        }
        tv_messages.text = messageText
    }
}
