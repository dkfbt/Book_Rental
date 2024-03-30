package com.study.domain.book;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class BookResponse {

    private Long bookId;
    private String isbn;
    private String bookName;
    private String coverFile;
    private String writer;
    private String pbComp;
    private LocalDate pbDate;
    private Integer price;
    private String rentalAvailableYn;

    private LocalDateTime crDate;   // 생성일시
    private Long crMemberId;
    private LocalDateTime mdDate;   // 최종 수정일시
    private Long mdMemberId;
    private String crMemberName;    //생성자 이름
    private String mdMemberName;    //수정자 이름


}
