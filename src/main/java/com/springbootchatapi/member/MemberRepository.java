package com.springbootchatapi.member;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;  // 이 부분 추가

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 그리고 existsById와 existsByEmail 메소드는
    // MemberRepository 인터페이스에 추가해야 합니다:

    Logger logger = LoggerFactory.getLogger(MemberRepository.class);

    boolean existsById(String id);
    boolean existsByName(String name);
    boolean existsByEmail(String email);

    Optional<Member> findById(String id);

    default Member findByIdCustom(String id) {
        logger.info("Fetching member by id: {}", id);
        return findById(id).orElse(null);
    }
}
