package com.study.domain.review;

import com.study.common.paging.PagingResponse;
import com.study.domain.member.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    // 신규 리뷰 생성
    @PostMapping("/books/{bookId}/reviews")
    public ReviewResponse saveReview(HttpServletRequest request, @PathVariable final Long bookId, @RequestBody final ReviewRequest params) {
        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");
        params.setWriter(member.getId());
        Long id = reviewService.saveReview(params);

        return reviewService.findReviewById(id);
    }


    // 리뷰 리스트 조회
    @GetMapping("/books/{bookId}/reviews")
    public PagingResponse<ReviewResponse> findAllReview(@PathVariable final Long bookId, final ReviewSearchDto params) {
        return reviewService.findAllReview(params);
    }


    // 리뷰 상세정보 조회
    @GetMapping("/books/{bookId}/reviews/{id}")
    public ReviewResponse findReviewById(@PathVariable final Long bookId, @PathVariable final Long id) {
        return reviewService.findReviewById(id);
    }


    // 기존 리뷰 수정
    @PatchMapping("/books/{bookId}/reviews/{id}")
    public ReviewResponse updateReview(@PathVariable final Long bookId, @PathVariable final Long id, @RequestBody final ReviewRequest params) {
        reviewService.updateReview(params);
        return reviewService.findReviewById(id);
    }


    // 리뷰 삭제
    @DeleteMapping("/books/{bookId}/reviews/{id}")
    public Long deleteReview(@PathVariable final Long bookId, @PathVariable final Long id) {
        return reviewService.deleteReview(id);
    }

}
