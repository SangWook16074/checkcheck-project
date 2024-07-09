package com.example.checkcheck.member.repository

import com.example.checkcheck.member.entity.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    @Test
    fun `멤버 생성 테스트`() {
        val member = Member(
            id = 1,
            email = "test@test.com",
            password = "testtest1@",
            name = "test"
        )

        val result = memberRepository.save(member)

        assertThat(result.id).isSameAs(1L)
        assertThat(result.name).isSameAs("test")
        assertThat(result.email).isSameAs("test@test.com")
        assertThat(result.password).isSameAs("testtest1@")
    }

    @Test
    fun `이메일 멤버 조회 테스트`() {
        val member = Member(
            id = 1,
            email = "test@test.com",
            password = "testtest1@",
            name = "test"
        )

        memberRepository.save(member)

        val result = memberRepository.findByEmail(member.email)!!

        assertThat(result.id).isSameAs(1L)
        assertThat(result.email).isSameAs("test@test.com")
        assertThat(result.name).isSameAs("test")
        assertThat(result.password).isSameAs("testtest1@")
    }
}