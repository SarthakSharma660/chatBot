package com.example.chatbot.utils

import com.example.chatbot.utils.constants.OPEN_GOOGLE
import com.example.chatbot.utils.constants.OPEN_SEARCH

object BotResponse {
    fun basicResponses(message :String):String {
        val random=(0..2).random()
        val message=message.toLowerCase()
        return when{
//            Hello
           message.contains("hello") ->
            {
                when(random){
                    0->"Hello there!"
                    1->"sup"
                    2->"Namaste"
                    else ->"error"
                }
            }
//            how are You
            message.contains("how are you?") ->
            {
                when(random){
                    0->"I'm doing fine!!"
                    1->"I'm good How are you??"
                    2->"Pretty good!! What about you??"
                    else ->"error"
                }
            }
//            Flip a coin
            message.contains("flip") && message.contains("coin")-> {
                var flip=(0..1).random()
                val  result= if(flip==0)"heads" else "tails"
                "I fliped a coin and it landed on $result"
            }
//            For Maths Problem
            message.contains("solve")->{
                val equation: String?= message.substringAfter("solve")
                return try {
                    val answer= SolveMath.solveMath(equation ?:"0")
                    answer.toString()
                }catch (e:Exception){
                    "I am a noob in Math "
                }

            }
//            To Get the Current Time
            message.contains("time") && message.contains("?")->{
                Time.timestamp()
            }
//            Open Google
            message.contains("open") && message.contains("google") ->{
                OPEN_GOOGLE
            }
//            For Search
            message.contains("search") ->{
                OPEN_SEARCH

            }
            else -> {
                when (random) {
                    0 -> "I Don't Understand"
                    1 -> "Idk"
                    2 -> "Try asking something else"
                    else -> "error"
                }
            }
        }
    }
}