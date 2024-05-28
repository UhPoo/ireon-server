package com.uhpoo.ireon.api.controller.lost.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 실종동물 게시글 신고 상세 조회 응답 DTO
 *
 * @author 최예지
 */
@Data
@NoArgsConstructor
public class LostDetailReportResponse {
    private Long lostReportId;
    private String reason;

    private Long lostId;
    private String title;
    private String author;
    private String content;
    private String animalType;
    private String animalDetail;
    private String animalGender;
    private Integer animalAge;
    private Boolean neutralized;
    private String lostStatus;
    private String zipcode;
    private String roadAddress;
    private String jibunAddress;
    private String detailAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String phoneNumber;
    private Boolean clipped;
    private String createdDate;

    @Builder
    public LostDetailReportResponse(Long lostReportId, String reason, Long lostId, String title, String author, String content, String animalType, String animalDetail, String animalGender, Integer animalAge, Boolean neutralized, String lostStatus, String zipcode, String roadAddress, String jibunAddress, String detailAddress, BigDecimal latitude, BigDecimal longitude, String phoneNumber, Boolean clipped, String createdDate) {
        this.lostReportId = lostReportId;
        this.reason = reason;
        this.lostId = lostId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.animalType = animalType;
        this.animalDetail = animalDetail;
        this.animalGender = animalGender;
        this.animalAge = animalAge;
        this.neutralized = neutralized;
        this.lostStatus = lostStatus;
        this.zipcode = zipcode;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.detailAddress = detailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.clipped = clipped;
        this.createdDate = createdDate;
    }
}