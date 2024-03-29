package com.study.domain.file;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class FileRequest {

    private Long id;                // 파일 번호 (PK)
    private Long postId;            // 게시글 번호 (FK)
    private Long bookId;            // 책 번호 (FK)
    private String originalName;    // 원본 파일명
    private String saveName;        // 저장 파일명
    private long size;              // 파일 크기
    private String uploadDateFolder;      // 자동생성되는 파일등록일 이름의 폴더

    @Builder
    public FileRequest(String originalName, String saveName, long size) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.size = size;
        this.uploadDateFolder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
