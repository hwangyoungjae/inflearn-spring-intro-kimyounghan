package com.youngjae.inflearnkimyounghanlecture.repository;

import com.youngjae.inflearnkimyounghanlecture.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
}
