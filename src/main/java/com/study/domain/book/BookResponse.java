package com.study.domain.book;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookResponse {

    private Long bookId;
    private String isbn;
    private String bookName;
    private String coverFile;
    private String writer;
    private String pbComp;
    private LocalDateTime pbDate;
    private Integer price;

    private LocalDateTime crDate;// 생성일시
    private Long crMemberId;
    private Long mdMemberId;// 최종 수정일시
    private LocalDateTime mdDate;

}
