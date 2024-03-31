package com.study;

import com.study.domain.book.BookMapper;
import com.study.domain.book.BookRequest;
import com.study.domain.rent.RentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.print.Book;

@SpringBootTest
public class BookMapperTest {

    @Autowired
    BookMapper bookMapper;

    @Test
    void rent() {
        //given
        RentRequest rentInfo = new RentRequest();
        rentInfo.setBookId(3l);
        rentInfo.setMemberId(2l);

        //when
        bookMapper.rent(rentInfo);
        //then
        System.out.println("테스트완료");
    }

    @Test
    void 유저의대여상태확인() {
        //given
        RentRequest rentInfo = new RentRequest();
        Long memberid = 2l;

        //when
        bookMapper.findRentedBooksByUserId(memberid);
        //then
        System.out.println("테스트완료");
    }

    @Test
    void 반납() {
        //given
        RentRequest rentInfo = new RentRequest();
        rentInfo.setMemberId(2l);
        rentInfo.setBookId(12l);

        //when
        bookMapper.returnBookToday(rentInfo);
        //then
        System.out.println("테스트완료");
    }



}
