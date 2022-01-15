package com.example.moodyapplication.util

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegisterValidationTest{
    private lateinit var registerValidation: RegisterValidation

    @Before
    fun setUp(){
        registerValidation = RegisterValidation()
    }

    @Test
    fun emailInvalidationWithEmailInvalidReturnFalse(){
        val validation = registerValidation.emailValidation("kawther@.com")
        assertEquals(false, validation)
    }

    @Test
    fun emailInvalidationWithEmailInvalidReturnTrue(){
        val validation = registerValidation.emailValidation("kawther1234@gmail.com")
        assertEquals(true, validation)
    }

    @Test
    fun passwordInvalidationWithPasswordInvalidReturnFalse(){
        val validation = registerValidation.passwordValidation("gbrtg54")
        assertEquals(false, validation)
    }

    @Test
    fun passwordInvalidationWithPasswordInvalidReturnTrue(){
        val validation = registerValidation.passwordValidation("gbdfg@E6rrg")
        assertEquals(true, validation)
    }
}