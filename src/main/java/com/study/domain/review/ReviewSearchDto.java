package com.study.domain.review;

import com.study.common.dto.SearchDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSearchDto extends SearchDto {

    private Long bookId;    // 도서 번호 (FK)

}
