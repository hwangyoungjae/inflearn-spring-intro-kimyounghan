package com.youngjae.inflearnkimyounghanlecture.service;

import com.youngjae.inflearnkimyounghanlecture.domain.Member;
import com.youngjae.inflearnkimyounghanlecture.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

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
        Optional<Member> expected = memberService.findOne(member.getId());

        // Then
        assertThat(expected.get()).isEqualTo(member);
    }
}