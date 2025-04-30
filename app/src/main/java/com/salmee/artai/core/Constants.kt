package com.salmee.artai.core

object Constants {
    const val BASE_URL = "https://84b9-102-41-134-181.ngrok-free.app"
    
    object Api {
        const val SIGNUP_ENDPOINT = "$BASE_URL/auth/signup"
        const val LOGIN_ENDPOINT = "$BASE_URL/auth/login"
        const val GENERTE_IMAGE =  "$BASE_URL/images/generate"
        
    }
}