package com.example.checkcheck.common.authority

import com.example.checkcheck.common.dtos.CustomUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

const val EXPIRATION_MILLISECONDS : Long = 1000 * 60 * 30L


@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    private lateinit var secretKey : String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    /**
     * access token 발급
     */
    fun createToken(authentication : Authentication) : TokenInfo {
        val authorities : String = authentication
            .authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        val memberId = (authentication.principal as CustomUser).id

        // Access Token
        val accessToken = Jwts
            .builder()
            .subject(authentication.name)
            .claim("auth", authorities)
            .claim("userId", memberId)
            .issuedAt(now)
            .expiration(accessExpiration)
            .signWith(key, Jwts.SIG.HS256)
            .compact()

        return TokenInfo("Bearer", accessToken)
    }


    /**
     * 토큰에서 권한 추출
     */
    fun getAuthentication(token : String) : Authentication {
        val claims : Claims = getClaims(token)

        val auth = claims["auth"] ?: throw RuntimeException("유효하지 않은 토큰입니다!")
        val userId = claims["userId"] ?: throw RuntimeException("유효하지 않은 토큰입니다!")

        // 권한 정보 추출
        val authorities : Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal : UserDetails = CustomUser(userId.toString().toLong(), claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    /**
     * 토큰 검증
     */
    fun validateToken(token : String) : Boolean {
        try {
            val claims = getClaims(token)
            return claims.expiration.after(Date())
        } catch (e : Exception) {
            println(e.message)
        }
        return false

    }

    /**
     * 클레임 추출
     */
    private fun getClaims(token : String) : Claims =
        Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}
