package com.study.domain.review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequest {

    private Long reviewId;     // 리뷰 번호 (PK)
    private Long bookId;       // 도서 번호 (FK)
    private String content;    // 내용
    private String writer;     // 작성자(리뷰어)

}
