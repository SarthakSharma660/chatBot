package com.example.chatbot.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.R
import com.example.chatbot.data.Message
import com.example.chatbot.utils.BotResponse
import com.example.chatbot.utils.Time
import com.example.chatbot.utils.constants.OPEN_GOOGLE
import com.example.chatbot.utils.constants.OPEN_SEARCH
import com.example.chatbot.utils.constants.RECEIVE_ID
import com.example.chatbot.utils.constants.SEND_ID
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MessagingAdapter
    private val botlist = listOf("Salmon","utti","Igor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()
        clickEvent()
        val random =(0..2). random()
        customMessage("Hello! Today you're speaking with ${botlist[random]}, How may I help you?")
    }
    private fun recyclerView(){
        adapter= MessagingAdapter()
        rv_messages.adapter= adapter
        rv_messages.layoutManager= LinearLayoutManager(applicationContext)
    }
    private fun sendMessage(){
        val message=et_message.text.toString()
        val timeStamp=Time.timestamp()

        if (message.isNotEmpty()){
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID,timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)

            botResponse(message)
        }
    }
    private fun botResponse(message: String){
        val timestamp=Time.timestamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val response= BotResponse.basicResponses(message)
                adapter.insertMessage(Message(message, RECEIVE_ID, timestamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)

                when(response){
                    OPEN_GOOGLE ->{

                        val site=Intent(Intent.ACTION_VIEW)
                        site.data= Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH ->{

                        val site=Intent(Intent.ACTION_VIEW)
                        val searchTerm: String?= message.substringAfter("search")
                        site.data= Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
    private fun clickEvent(){
        btn_send.setOnClickListener {
            sendMessage()
        }
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }
    private fun customMessage(message :String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timestamp = Time.timestamp()
                adapter.insertMessage(Message(message, RECEIVE_ID, timestamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
}