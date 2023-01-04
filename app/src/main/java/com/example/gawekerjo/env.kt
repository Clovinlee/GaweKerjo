package com.example.gawekerjo

object env {
    const val APP_NAME="GaweKerjo"
    const val APP_ENV="local"

//    const val API_URL ="http://127.0.0.1:8000/api/"
//    const val API_URL = "http://192.168.1.6/api/"
    const val API_URL = "https://b575-2001-448a-5130-7a29-618c-2c68-2783-4530.ap.ngrok.io/api/"
    //const val API_URL = "https://f86d-2001-448a-5020-7e5b-569-3b84-2fb6-ec4d.ap.ngrok.io/api/"
    const val USER_ENDPOINT = "users"

    const val DB_CONNECTION="mysql"
    const val DB_HOST="localhost"
    const val DB_PORT="3306"
    const val DB_DATABASE="gawekerjo"
    const val DB_USERNAME="root"
    const val DB_PASSWORD=""
}