package com.study.domain.rent;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RentRequest {

    private Long rentalNo;              //대여id
    private Long bookId;                //북id
    private Long memberId;              //대여자
    private LocalDateTime rentalDate;   //대여일
    private LocalDateTime returnDate;   //실제반납일
}
