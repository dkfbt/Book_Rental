package com.study.domain.book;

import com.study.common.dto.SearchDto;
import com.study.domain.rent.RentRequest;
import com.study.domain.rent.RentResponse;
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
     * 대여중인 도서 리스트 조회
     *
     * @return 대여중인 도서 리스트
     */
    List<RentResponse> findAllRentedBooks (SearchDto params);

    /**
     * 도서 수 카운팅
     *
     * @return 도서 수
     */
    int count(SearchDto params);

    /**
     * 대여 도서 수 카운팅
     *
     * @return 도서 수
     */
    int countRentedBooks(SearchDto params);


    /**
     * 도서 대여
     *
     * @param params - 대여정보
     */
    Long rent(RentRequest params);

    /**
     * 도서가 대여가능한지 체크
     *
     * @return 대여정보
     */
    boolean isRentAble(RentRequest params);

    /**
     * 해당유저의 대여 도서 수 카운팅
     *
     * @return 도서 수
     */
    int countRent(RentRequest params);

    /**
     * 도서의 대여가능여부 불가로 변경
     *
     * @return 대여정보
     */
    int setRentalAvailableN(BookRequest params);

    /**
     * 도서의 대여가능여부 가능으로 변경
     *
     * @return 대여정보
     */
    int setRentalAvailableY(BookRequest params);

    /**
     * 해당유저의 대여중인 도서
     *
     * @return 해당유저의 미반납도서목록
     */
    List<RentResponse> findRentedBooksByUserId(Long memberid);

    /**
     * 반납
     *
     * @return
     */
     int returnBookToday(RentRequest params);

}
