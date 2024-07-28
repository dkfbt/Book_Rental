package com.study.domain.book;

import com.study.common.dto.MessageDto;
import com.study.common.dto.SearchDto;
import com.study.common.file.FileUtils;
import com.study.common.paging.PagingResponse;
import com.study.domain.file.FileRequest;
import com.study.domain.file.FileResponse;
import com.study.domain.file.FileService;
import com.study.domain.member.MemberResponse;
import com.study.domain.rent.RentRequest;
import com.study.domain.rent.RentResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final FileService fileService;
    private final FileUtils fileUtils;

    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }


    // 쿼리 스트링 파라미터를 Map에 담아 반환
    private Map<String, Object> queryParamsToMap(final SearchDto queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }


    // 도서 작성 페이지
    @GetMapping("/book/write.do")
    public String openBookWrite(@RequestParam(value = "id", required = false) final Long id, Model model) {
        if (id != null) {
            BookResponse book = bookService.findBookById(id);
            model.addAttribute("book", book);
        }
        return "book/write";
    }


    // 도서 리스트 페이지
    @GetMapping("/book/list.do")
    public String openBookList(HttpServletRequest request, @ModelAttribute("params") final SearchDto params, Model model) {
        PagingResponse<BookResponse> response = bookService.findAllBooks(params);
        model.addAttribute("response", response);

        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");
        List<RentResponse> rentList = bookService.findRentedBooksByUserId(member.getId());
        model.addAttribute("rentList", rentList);
        return "book/list";
    }


    // 도서 상세 페이지
    @GetMapping("/book/view.do")
    public String openBookView(HttpServletRequest request, @RequestParam final Long id, Model model) {
        BookResponse book = bookService.findBookById(id);
        model.addAttribute("book", book);

        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");
        List<RentResponse> rentList = bookService.findRentedBooksByUserId(member.getId());
        //대여리스트에 해당책이 있는지 검사
        boolean isRented = rentList.stream()
                           .anyMatch(bookRentResponse -> bookRentResponse.getBookId().equals(id));


        model.addAttribute("isRented", isRented);
        return "book/view";
    }


    // 신규 도서 생성
    @PostMapping("/book/save.do")
    public String saveBook(final BookRequest params, Model model) {

        List<FileRequest> files = fileUtils.uploadFiles(params.getFiles());
        //썸네일 저장할때의 절대경로
        params.setCoverFile(files.get(0).getUploadDateFolder() +"/"+ files.get(0).getSaveName());

        Long id = bookService.saveBook(params);
        fileService.saveThumbnailFiles(id, files);  //파일첨부와 거의 동일하게 복제
        MessageDto message = new MessageDto("도서등록이 완료되었습니다.", "/book/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }


    // 기존 도서 수정
    @PostMapping("/book/update.do")
    public String updateBook(final BookRequest params, final SearchDto queryParams, Model model) {

        // 1. 도서 정보 수정
        bookService.updateBook(params);

        // 2. 파일 업로드 (to disk)
        List<FileRequest> uploadFiles = fileUtils.uploadFiles(params.getFiles());

        // 3. 파일 정보 저장 (to database)
        fileService.saveFiles(params.getBookId(), uploadFiles);

        // 4. 삭제할 파일 정보 조회 (from database)
        List<FileResponse> deleteFiles = fileService.findAllFileByIds(params.getRemoveFileIds());

        // 5. 파일 삭제 (from disk)
        fileUtils.deleteFiles(deleteFiles);

        // 6. 파일 삭제 (from database)
        fileService.deleteAllFileByIds(params.getRemoveFileIds());

        MessageDto message = new MessageDto("도서 수정이 완료되었습니다.", "/book/list.do", RequestMethod.GET, queryParamsToMap(queryParams));

        return showMessageAndRedirect(message, model);
    }


    // 도서 삭제
    @PostMapping("/book/delete.do")
    public String deleteBook(@RequestParam final Long id, final SearchDto queryParams, Model model) {
        bookService.deleteBookById(id);
        MessageDto message = new MessageDto("도서 삭제가 완료되었습니다.", "/book/list.do", RequestMethod.GET, queryParamsToMap(queryParams));
        return showMessageAndRedirect(message, model);
    }


    //도서 대여
    @PostMapping("/book/rent.do")
    @ResponseBody
    public MessageDto rentBook(HttpServletRequest request, @RequestBody RentRequest params, Model model) {

        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");
        MessageDto message = new MessageDto("초기화", "/book/list.do", RequestMethod.GET, null);
        try {
            params.setMemberId(member.getId());
            long result = bookService.rentBook(params);
            if(result == -1){
                message.setMessage("대여권수초과");
            }else{
                message.setMessage("대여완료");
            }
        } catch (Exception e) {
            message.setMessage("대여실패");
        }
        return message;
    }


    // 도서 반납
    @PostMapping("/book/return.do")
    public String returnBook(HttpServletRequest request, final RentRequest params, Model model) {
        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");
        params.setMemberId(member.getId());
        int result = bookService.returnBookToday(params);
        if (result >= 1){
            MessageDto message = new MessageDto("도서반납이 완료되었습니다.", "/book/list.do", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }else{
            MessageDto message = new MessageDto("도서반납에 실패하였습니다.", "/book/list.do", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }
    }
}
