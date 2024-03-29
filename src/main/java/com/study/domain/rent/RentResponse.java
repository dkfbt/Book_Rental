package com.study.domain.rent;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class RentResponse {
    private Long rentalNo;              //대여id
    private Long bookId;                //북id
    private Long memberId;              //대여자
    private LocalDateTime rentalDate;   //대여일
    private LocalDateTime withdrawDate; //반납예정일
    private LocalDateTime returnDate;   //실제반납일

    private LocalDateTime crDate;   // 생성일시
    private Long crMemberId;
    private LocalDateTime mdDate;   // 최종 수정일시
    private Long mdMemberId;

    private String rentalAvailableYn;   //대여가능여부 "Y","N"


}
