package com.wondo.council.service.impl;

import com.wondo.council.domain.Article;
import com.wondo.council.domain.Reply;
import com.wondo.council.domain.User;
import com.wondo.council.dto.exception.article.PostNotFoundException;
import com.wondo.council.dto.reply.ReplyDto;
import com.wondo.council.dto.reply.ReplyRequestDto;
import com.wondo.council.repository.ArticleRepository;
import com.wondo.council.repository.ReplyRepository;
import com.wondo.council.service.ReplyService;
import com.wondo.council.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Override
    public Reply createReply(Long replyUid, ReplyRequestDto replyRequestDto) {

        User user = userService.getMyInfo();
        Article article = articleRepository.findById(replyRequestDto.getArticleUid()).orElseThrow(()-> new PostNotFoundException());

        //댓글 그룹번호 NVL 함수 NULL 이면 0 NULL이 아니면 최댓값
        Long replyRef = replyRepository.findByNvlRef(replyRequestDto.getArticleUid());

        // replyUid가 null 이면 댓글 null이 아니면 대댓글 저장
        // 댓글 저장
        if (replyUid == null){
                System.out.println("null 진입");
                Reply comments = replyRepository.save(Reply.builder()
                        .content(replyRequestDto.getContent())
                        .regDate(LocalDateTime.now().toString())
                        .user(user)
                        .article(article)
                        .ref(replyRef + 1l)
                        .step(0l)
                        .refOrder(0l)
                        .childNum(0l)
                        .parentNum(0l)
                        .build());
            log.info("댓글 저장 완료.");
            return comments;
        } else {
            // 대댓글 저장
            // 부모 댓글 데이터
            Reply parentReply = replyRepository.findById(replyUid).orElseThrow(()-> new IllegalArgumentException("작성할 댓글이 없습니다."));
            Long refOrderResult = refOrderAndUpdate(parentReply);
            System.out.println("refOrderResult : " + refOrderResult);
            if (refOrderResult == null){
                log.info("refOrderResult == null -> 댓글 작성 오류.");
                return null;
            }
            
            // 대댓글 저장
            Reply childReply = replyRepository.save(Reply.builder()
                            .content(replyRequestDto.getContent())
                            .regDate(LocalDateTime.now().toString())
                            .user(user)
                            .article(article)
                            .ref(parentReply.getRef())
                            .step(parentReply.getStep()+1l)
                            .refOrder(refOrderResult)
                            .childNum(0l)
                            .parentNum(replyUid)
                            .build());

            // 부모 댓글의 자식 컬럼수 +1 업데이트
            replyRepository.updateChildNum(parentReply.getUid());
            log.info("대댓글 저장 완료.");
            return childReply;
        }
    }

    private Long refOrderAndUpdate(Reply reply) {
        Long saveStep = reply.getStep()+1l;
        Long refOrder = reply.getRefOrder();
        Long childNum = reply.getChildNum();
        Long ref = reply.getRef();

        // 부모 댓글 그룹의 childNum (자식 수)
        Long childNumSUm = replyRepository.fincBySumChildNum(ref);

        // 부모 댓글 그룹의 최댓값 step
        Long maxStep = replyRepository.findByNvlMaxStep(ref);

        // 저장할 대댓글 step과 그룹내의 최댓값 step 조건 비교
        /*
        * step +1 < 그룹 리스트에서 max step 값 -> ChildNum sum + 1 = No Update
        * step +1 = 그룹 리스트에서 max step 값 -> refOrder + ChildNum+1 = Update
        * step +1 > 그룹 리스트에서 max step 값  refOrder + 1 * UPDATE
        * */

        if (saveStep < maxStep){
            return childNumSUm + 1l;
        } else if (saveStep == maxStep) {
            replyRepository.updateRefOrderPlus(ref, refOrder+childNum);
            return refOrder + childNum + 1l;
        } else if (saveStep > maxStep) {
            replyRepository.updateRefOrderPlus(ref,refOrder);
            System.out.println(refOrder + 1l);
            System.out.println(refOrder.getClass().getName());
            return refOrder + 1l;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyDto> getReplyList(Long uid) {
        List<ReplyDto> list = replyRepository.findAllByArticleUidOrderByRefDescRefOrder(uid).stream().map(m->ReplyDto.from(m))
                .collect(Collectors.toList());
        log.info("특정 게시물 댓글 리스트 조회 완료.");
        return list;
    }
}
