package com.salmee.artai.core

object Constants {
    const val BASE_URL = "https://dc91-196-136-5-2.ngrok-free.app/api"
    
    object Api {
        const val SIGNUP_ENDPOINT = "$BASE_URL/auth/signup"
        const val LOGIN_ENDPOINT = "$BASE_URL/auth/login"
        const val GENERTE_IMAGE =  "$BASE_URL/images/generate"
        
    }
}