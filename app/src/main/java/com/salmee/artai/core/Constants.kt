package com.salmee.artai.core

object Constants {
    const val BASE_URL = "http://203.161.50.194:5000"
    
    object Api {
        const val SIGNUP_ENDPOINT = "$BASE_URL/auth/signup"
        const val LOGIN_ENDPOINT = "$BASE_URL/auth/login"
        const val GENERTE_IMAGE =  "$BASE_URL/images/generate"
        
    }
}