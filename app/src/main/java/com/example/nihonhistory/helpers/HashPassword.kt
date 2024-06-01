package com.example.nihonhistory.helpers

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object HashPassword {
    fun hashPassword(password: String): String {
        val passwordEncoder = BCryptPasswordEncoder()
        return passwordEncoder.encode(password)
    }

    fun isPasswordMatch(rawPassword: String, encodedPassword: String): Boolean {
        val passwordEncoder = BCryptPasswordEncoder()
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}