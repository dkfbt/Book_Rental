package com.study.domain.book;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookRequest {

   private Long bookId;
    private String isbn;
    private String bookName;
    private String coverFile;
    private String writer;
    private String pbComp;
    private LocalDate pbDate;
    private Integer price;
    private List<MultipartFile> files = new ArrayList<>();    // 첨부파일 List
    private List<Long> removeFileIds = new ArrayList<>();     // 삭제할 첨부파일 id List

}
