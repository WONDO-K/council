package com.wondo.council.service.impl;

import com.wondo.council.domain.Notice;
import com.wondo.council.domain.User;
import com.wondo.council.domain.enums.Role;
import com.wondo.council.dto.notice.NoticeRequestDto;
import com.wondo.council.repository.NoticeRepository;
import com.wondo.council.service.NoticeService;
import com.wondo.council.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final UserService userService;

    @Override
    public void createNotice(NoticeRequestDto noticeRequestDto) {
        User user = userService.getMyInfo();

        if (user.getRole().equals(Role.ADMIN)){
            String regDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).toString();
            Notice notice = Notice.builder()
                    .title(noticeRequestDto.getTitle())
                    .content(noticeRequestDto.getContent())
                    .regDate(regDate)
                    .view(0)
                    .user(user)
                    .build();
            noticeRepository.save(notice);
            log.info("공지사항 작성 완료.");
        } else {
            log.info("관리자 권한이 아닙니다.");
            throw new AccessDeniedException("관리자 권한이 아닙니다.");
        }


    }

    @Override
    public void updateNotice(Long uid, NoticeRequestDto noticeRequestDto) {
        Optional<Notice> notice = noticeRepository.findById(uid);
        if (notice.isPresent()){
            String upDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).toString();
            if (notice.get().getUser() == userService.getMyInfo()){
                Notice newNotice = Notice.builder()
                        .uid(uid)
                        .title(noticeRequestDto.getTitle())
                        .content(noticeRequestDto.getContent())
                        .view(notice.get().getView())
                        .user(userService.getMyInfo())
                        .regDate(notice.get().getRegDate())
                        .upDate(upDate)
                        .build();
                noticeRepository.save(newNotice);
                log.info("공지사항이 수정되었습니다.");
            }
        }
    }
}
