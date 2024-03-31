package com.study.domain.rent;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class RentResponse {
    private Long rentalNo;              //대여id
    private Long bookId;                //북id
        private String bookName;            //책이름
    private Long memberId;              //대여자
        private String coverFile;           //커버이미지
        private String memberName;          //대여자이름
    private LocalDateTime rentalDate;   //대여일
    private LocalDateTime withdrawDate; //반납예정일
    private LocalDateTime returnDate;   //실제반납일

    private LocalDateTime crDate;   // 생성일시
    private Long crMemberId;
    private LocalDateTime mdDate;   // 최종 수정일시
    private Long mdMemberId;


}
