package com.example.checkcheck.common.dtos

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
    val id : Long,
    email : String,
    password: String,
    authority: Collection<GrantedAuthority>,
) : User(email,password,authority)