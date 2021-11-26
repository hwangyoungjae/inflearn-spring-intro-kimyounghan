package com.youngjae.inflearnkimyounghanlecture.service;

import com.youngjae.inflearnkimyounghanlecture.domain.Member;
import com.youngjae.inflearnkimyounghanlecture.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given, 이러한 상황이 주어져서
        Member member = new Member();
        member.setName("spring");

        // when, 이걸 실행했을때
        Long saveId = memberService.join(member);

        // then, 결과가 이게 나와야대
        Member findMember = memberService.findOne(saveId).get();
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when, then
        memberService.join(member1);
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> memberService.join(member2))
                .withMessage("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
        Member member = new Member();
        member.setName("spring");
        memberService.join(member);
        Member member1 = new Member();
        member1.setName("spring2");
        memberService.join(member1);

        List<Member> members = memberService.findMembers();
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        // Given
        Member member = new Member();
        member.setName("spring");
        memberService.join(member);

        // When
        Optional<Member> expected1 = memberService.findOne(member.getId());
        Optional<Member> expected2 = memberService.findOne(member.getName());

        // Then
        assertThat(expected1.get()).isEqualTo(member);
        assertThat(expected2.get()).isEqualTo(member);
    }
}
