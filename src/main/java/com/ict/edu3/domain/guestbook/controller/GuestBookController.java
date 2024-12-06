package com.ict.edu3.domain.guestbook.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ict.edu3.domain.auth.vo.DataVO;
import com.ict.edu3.domain.guestbook.service.GuestBookService;
import com.ict.edu3.domain.guestbook.vo.GuestBookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/api/guestbook")
public class GuestBookController {
    @Autowired
    private GuestBookService guestBookService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/list")
    public DataVO getGuestBookList() {
        DataVO dataVO = new DataVO();
        try {
            List<GuestBookVO> list = guestBookService.getGuestBookList();
            dataVO.setSuccess(true);
            dataVO.setMessage("게스트북 조회 성공");
            dataVO.setData(list);
        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("게스트북 조회 실패");
        }
        return dataVO;
    }

    @GetMapping("/detail/{gb_idx}")
    public DataVO getGuestBookDetail(@PathVariable String gb_idx) {
        DataVO dataVO = new DataVO();
        try {
            // log.info("gb_idx : " + gb_idx);
            GuestBookVO gvo = guestBookService.getGuestBookById(gb_idx);
            if (gvo == null) {
                dataVO.setSuccess(false);
                dataVO.setMessage("게스트북 상세보기 실패");
                return dataVO;
            }
            dataVO.setSuccess(true);
            dataVO.setMessage("게스트북 상세보기 성공");
            dataVO.setData(gvo);
        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("게스트북 상세보기 실패");
        }
        return dataVO;
    }

    @GetMapping("/delete/{gb_idx}")
    public DataVO getGuestBookDelete(@PathVariable String gb_idx, Authentication authentication) {
        DataVO dataVO = new DataVO();
        try {
            // 로그인 여부 확인
            if (authentication == null) {
                dataVO.setSuccess(false);
                dataVO.setMessage("로그인이 필요합니다.");
                return dataVO;
            }
            int result = guestBookService.getGuestBookDelete(gb_idx);
            if (result == 0) {
                dataVO.setSuccess(false);
                dataVO.setMessage("게스트북 삭제 실패");
                return dataVO;
            }
            dataVO.setSuccess(true);
            dataVO.setMessage("게스트북 삭제 성공");

        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("게스트북 삭제 오류 발생");
        }
        return dataVO;
    }

    @PutMapping("/update/{gb_idx}")
    public DataVO getGuestBookUpdate(@PathVariable String gb_idx, @RequestBody GuestBookVO gvo,
            Authentication authentication) {
        DataVO dataVO = new DataVO();
        try {
            // 로그인 여부 확인
            if (authentication == null) {
                dataVO.setSuccess(false);
                dataVO.setMessage("로그인이 필요합니다.");
                return dataVO;
            }

            // 파라미터 확인
            int result = guestBookService.getGuestBookUpdate(gvo);

            if (result == 0) {
                log.info("result=2");
                dataVO.setSuccess(false);
                dataVO.setMessage("게스트북 수정 실패");
                return dataVO;
            }
            dataVO.setSuccess(true);
            dataVO.setMessage("게스트북 수정 성공");

        } catch (Exception e) {
            log.info("Exception");
            dataVO.setSuccess(false);
            dataVO.setMessage("게스트북 수정 오류 발생");
        }
        return dataVO;
    }

    @PostMapping("/write")
    public DataVO getGuestBookWrite(
            @ModelAttribute("data") GuestBookVO gvo,
            Authentication authentication) {

        DataVO dataVO = new DataVO();
        try {
            // 로그인 여부 확인
            if (authentication == null) {
                dataVO.setSuccess(false);
                dataVO.setMessage("로그인이 필요합니다.");
                return dataVO;
            }
            // 로그인한 사람의 id 추출
            gvo.setGb_id(authentication.getName());
            gvo.setGb_pw(passwordEncoder.encode(gvo.getGb_pw()));

            MultipartFile file = gvo.getFile();
            if (file.isEmpty()) {
                gvo.setGb_filename("");
            } else {
                UUID uuid = UUID.randomUUID();
                String f_name = uuid.toString() + "_" + file.getOriginalFilename();
                gvo.setGb_filename(f_name);

                // Windows 외부 경로 설정
                String path = "D:\\upload";
                File uploadDir = new File(path);
                // application.yml 수정 : file.upload.dir=D:/uploads

                // 디렉토리가 없으면 생성
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 파일 저장
                file.transferTo(new File(uploadDir, f_name));
            }

            // 게스트북 쓰기
            int result = guestBookService.getGuestBookWrite(gvo);

            if (result == 0) {
                dataVO.setSuccess(false);
                dataVO.setMessage("게스트북 쓰기 실패");
                return dataVO;
            }
            dataVO.setSuccess(true);
            dataVO.setMessage("게스트북 쓰기 성공");

        } catch (Exception e) {
            log.info("Exception : " + e);
            dataVO.setSuccess(false);
            dataVO.setMessage("게스트북 쓰기 오류 발생");
        }
        return dataVO;
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("d://upload").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new FileNotFoundException("File not found: " + filename);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
