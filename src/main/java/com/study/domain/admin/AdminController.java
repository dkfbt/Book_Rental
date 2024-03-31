package com.study.domain.admin;

import com.study.common.dto.SearchDto;
import com.study.common.paging.PagingResponse;
import com.study.domain.book.BookResponse;
import com.study.domain.book.BookService;
import com.study.domain.member.MemberResponse;
import com.study.domain.member.MemberService;
import com.study.domain.rent.RentResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final BookService bookService;
    private final AdminService adminService;

    // 어드민 로그인 페이지
    @GetMapping("/admin/login.do")
    public String openLogin() {
        return "admin/login";
    }

    // 회원 정보 리스트
    @GetMapping("/admin/member/list.do")
    public String getMemberList(@ModelAttribute("params") final SearchDto params, Model model) {
        PagingResponse<MemberResponse> response = memberService.findAllMembers(params);
        model.addAttribute("response", response);
        return "member/list";
    }


    // 관리자의 회원가입
    @GetMapping("/admin/member/write.do")
    public String insertMember(@RequestParam(value = "id", required = false) final String userId, Model model) {
        if (userId != null) {
            MemberResponse member = memberService.findMemberByLoginId(userId);
            model.addAttribute("member", member);
        }
        return "admin/memberInsert";
    }

    // 대여중인 도서 리스트 페이지
    @GetMapping("/admin/book/list.do")
    public String openBookList(HttpServletRequest request, @ModelAttribute("params") final SearchDto params, Model model) {
        PagingResponse<RentResponse> response = adminService.findAllRentedBooks(params);
        model.addAttribute("response", response);
        return "admin/rentedBookList";
    }

}
