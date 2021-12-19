package com.example.moodyapplication.util

import java.util.regex.Pattern

class RegisterValidation {
    private val REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,}"
    private val REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"

    fun emailValidation(email: String): Boolean{
        val patterns = Pattern.compile(REGEX_EMAIL)
        val matcher = patterns.matcher(email)
        return matcher.matches()
    }

    fun passwordValidation(password: String): Boolean{
        val patterns = Pattern.compile(REGEX_PASSWORD)
        val matcher = patterns.matcher(password)
        return matcher.matches()
    }
}