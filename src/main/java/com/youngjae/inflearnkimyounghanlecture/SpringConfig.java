package com.youngjae.inflearnkimyounghanlecture;

import com.youngjae.inflearnkimyounghanlecture.aop.TimeTraceAop;
import com.youngjae.inflearnkimyounghanlecture.repository.JpaMemberRepository;
import com.youngjae.inflearnkimyounghanlecture.repository.MemberRepository;
import com.youngjae.inflearnkimyounghanlecture.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;
    private EntityManager em;
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager em, MemberRepository memberRepository) {
        this.dataSource = dataSource;
        this.em = em;
        this.memberRepository = memberRepository;
    }

//    @Bean
//    public MemberRepository memberRepository() {
////        return new MemoryMemberRepository();
////        return new JdbcMemberRepository(dataSource);
////        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
}
