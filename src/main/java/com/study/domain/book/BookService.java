package com.study.domain.book;

import com.study.common.dto.SearchDto;
import com.study.common.paging.Pagination;
import com.study.common.paging.PagingResponse;
import com.study.domain.member.MemberMapper;
import com.study.domain.rent.RentRequest;
import com.study.domain.rent.RentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;


    /**
     * 도서 정보 저장
     * @param params - 도서 정보
     * @return PK
     */
    @Transactional
    public Long saveBook(final BookRequest params) {
        bookMapper.save(params);
        return params.getBookId();
    }

    /**
     * 도서 상세정보 조회
     * @param bookId - UK
     * @return 도서 상세정보
     */
    public BookResponse findBookById(final Long bookId) {
        return bookMapper.findById(bookId);
    }

    /**
     * 도서 정보 수정
     * @param params - 도서 정보
     * @return PK
     */
    @Transactional
    public Long updateBook(final BookRequest params) {
        bookMapper.update(params);
        return params.getBookId();
    }

    /**
     * 도서 정보 삭제 (도서 상태 삭제로 변경)
     * @param id - PK
     * @return PK
     */
    @Transactional
    public Long deleteBookById(final Long id) {
        bookMapper.deleteById(id);
        return id;
    }



    /**
     * 도서 리스트
     * @param params - search conditions
     * @return 도서 리스트
     */
    public PagingResponse<BookResponse> findAllBooks(final SearchDto params) {
        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = bookMapper.count(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);

        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<BookResponse> list = bookMapper.findAll(params);
        return new PagingResponse<>(list, pagination);
    }

    /**
     * 도서 대여
     * @param book - 도서 ID
     * @return PK
     */
    @Transactional
    public Long rentBook(RentRequest book) {
        BookRequest bookrequest = new BookRequest();
        bookrequest.setBookId(book.getBookId());
        boolean isRentableBook = bookMapper.isRentAble(book);
        int rentCount = bookMapper.countRent(book);
        if(isRentableBook==true && rentCount<5){
            bookMapper.rent(book);
            bookMapper.setRentalAvailableN(bookrequest);
        }
        //빌리는걸 성공했으면 해당책은 rental가능여부를 "N"으로 변경
        return book.getBookId();
    }

}
