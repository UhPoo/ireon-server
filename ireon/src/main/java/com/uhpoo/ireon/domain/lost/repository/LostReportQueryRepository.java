package com.uhpoo.ireon.domain.lost.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 실종동물 게시글 신고 조회 레포지토리
 *
 * @author 최예지
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LostReportQueryRepository {
    private final JPAQueryFactory queryFactory;
}