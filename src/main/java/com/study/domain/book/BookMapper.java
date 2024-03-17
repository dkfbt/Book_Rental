package com.study.domain.book;

import com.study.common.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    /**
     * 도서 저장
     *
     * @param params - 도서 정보
     */
    void save(BookRequest params);

    /**
     * 도서 상세정보 조회
     *
     * @param id - PK
     * @return 도서 상세정보
     */
    BookResponse findById(Long id);

    /**
     * 도서 수정
     *
     * @param params - 도서 정보
     */
    void update(BookRequest params);

    /**
     * 도서 삭제
     *
     * @param id - PK
     */
    void deleteById(Long id);

    /**
     * 도서 리스트 조회
     *
     * @return 도서 리스트
     */
    List<BookResponse> findAll(SearchDto params);

    /**
     * 도서 수 카운팅
     *
     * @return 도서 수
     */
    int count(SearchDto params);

}
