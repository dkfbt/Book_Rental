package com.study.domain.admin;

import com.study.common.dto.SearchDto;
import com.study.common.paging.Pagination;
import com.study.common.paging.PagingResponse;
import com.study.domain.book.BookMapper;
import com.study.domain.book.BookResponse;
import com.study.domain.rent.RentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final BookMapper bookMapper;

    /**
     * 모든 대여중인 도서
     * @param params
     * @return PK
     */
    @Transactional
    public PagingResponse<RentResponse> findAllRentedBooks(final SearchDto params){
        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = bookMapper.count(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);

        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<RentResponse> list = bookMapper.findAllRentedBooks(params);
        return new PagingResponse<>(list, pagination);
    }



}
