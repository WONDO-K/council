package com.wondo.council.service.impl;

import com.wondo.council.domain.Article;
import com.wondo.council.domain.Inquiry;
import com.wondo.council.domain.User;
import com.wondo.council.domain.enums.Role;
import com.wondo.council.dto.article.ArticleRequestDto;
import com.wondo.council.dto.exception.article.PostNotFoundException;
import com.wondo.council.dto.inquiry.InquiryDto;
import com.wondo.council.repository.InquiryRepository;
import com.wondo.council.service.InquiryService;
import com.wondo.council.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;

    private final UserService userService;

    @Override
    public void createInquiry(ArticleRequestDto inquiryRequestDto) {
        User user = userService.getMyInfo();
        String regDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).toString();
        Inquiry inquiry = Inquiry.builder()
                .title(inquiryRequestDto.getTitle())
                .content(inquiryRequestDto.getContent())
                .regDate(regDate)
                .view(0)
                .user(user)
                .build();
        inquiryRepository.save(inquiry);
        log.info("문의글 작성 완료.");
    }

    @Override
    public InquiryDto getInquiry(Long uid) {
        User user = userService.getMyInfo();
        Inquiry inquiry = inquiryRepository.findById(uid).orElseThrow(PostNotFoundException::new);
        // 문의글 작성자 혹은 관리자만 조회 가능
        if (inquiry.getUser() == user || user.getRole().equals(Role.ADMIN)) {
            inquiry.addViewCount();
            inquiryRepository.save(inquiry);
            log.info("문의글 조회 완료");
            return InquiryDto.from(inquiry);
        } else {
            log.info("작성자가 아니므로 접근 권한이 없습니다.");
            throw new AccessDeniedException("작성자가 아니므로 접근 권한이 없습니다.");
        }
    }

    @Override
    public void updateInquiry(Long uid, ArticleRequestDto inquiryRequestDto) {
        User user = userService.getMyInfo();

        Optional<Inquiry> inquiry = inquiryRepository.findById(uid);
        if (inquiry.isPresent()) {
            String upDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).toString();
            if (inquiry.get().getUser() == userService.getMyInfo()) {
                Inquiry newInquiry = Inquiry.builder()
                        .uid(uid)
                        .title(inquiryRequestDto.getTitle())
                        .content(inquiryRequestDto.getContent())
                        .view(inquiry.get().getView())
                        .user(userService.getMyInfo())
                        .regDate(inquiry.get().getRegDate())
                        .upDate(upDate)
                        .build();
                inquiryRepository.save(newInquiry);
                log.info("문의글 수정이 완료되었습니다.");
            } else {
                log.info("문의글 작성자가 아닙니다.");
                throw new AccessDeniedException("문의글 작성자가 아닙니다.");
            }
        }
    }

    @Override
    public List<InquiryDto> getInquiryList() {
        List<InquiryDto> list = inquiryRepository.findAll().stream().map(m->InquiryDto.from(m))
                .collect(Collectors.toList());
        log.info("문의 리스트 조회 완료.");
        return list;
    }
}
