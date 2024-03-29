package com.study.domain.book;

import com.study.common.dto.MessageDto;
import com.study.common.dto.SearchDto;
import com.study.common.file.FileUtils;
import com.study.common.paging.PagingResponse;
import com.study.domain.file.FileRequest;
import com.study.domain.file.FileResponse;
import com.study.domain.file.FileService;
import com.study.domain.rent.RentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String openBookList(@ModelAttribute("params") final SearchDto params, Model model) {
        PagingResponse<BookResponse> response = bookService.findAllBooks(params);
        model.addAttribute("response", response);
        //파일 이미지 가져와서 뿌려줘야함. 또는 타임리프쪽에서 ajax로
        return "book/list";
    }


    // 도서 상세 페이지
    @GetMapping("/book/view.do")
    public String openBookView(@RequestParam final Long id, Model model) {
        BookResponse book = bookService.findBookById(id);
        model.addAttribute("book", book);
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


    @PostMapping("/book/rent.do")
    public String rentBook(@RequestParam final RentRequest params, Model model, final SearchDto queryParams) {
        try {
            // 대여 서비스 호출 (대여 로직 구현 필요)
            bookService.rentBook(params);
            MessageDto message = new MessageDto("도서 대여가 완료되었습니다.", "/book/list.do", RequestMethod.GET, queryParamsToMap(queryParams));
            return showMessageAndRedirect(message, model);
        } catch (Exception e) {
            // 예외 처리 (도서가 대여 불가능한 상태일 때의 처리 등)
            MessageDto message = new MessageDto("도서 대여에 실패했습니다: " + e.getMessage(), "/book/list.do", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }
    }
}
