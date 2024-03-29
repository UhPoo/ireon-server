package com.uhpoo.ireon.domain.common.animal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 동물 종류
 *
 * @author 최영환
 */
@Getter
@RequiredArgsConstructor
public enum AnimalType {

    DOG("개"),
    CAT("고양이"),
    HAMSTER("햄스터"),
    ETC("기타");

    private final String text;
}
