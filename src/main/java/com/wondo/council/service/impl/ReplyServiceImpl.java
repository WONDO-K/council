package com.wondo.council.service.impl;

import com.wondo.council.domain.Article;
import com.wondo.council.domain.Reply;
import com.wondo.council.domain.User;
import com.wondo.council.dto.exception.article.PostNotFoundException;
import com.wondo.council.dto.reply.ReplyDto;
import com.wondo.council.dto.reply.ReplyRequestDto;
import com.wondo.council.dto.reply.ReplyUpdateRequestDto;
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
import java.util.Optional;
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
        Article article = articleRepository.findById(replyRequestDto.getArticleUid()).orElseThrow(() -> new PostNotFoundException());

        //댓글 그룹번호 NVL 함수 NULL 이면 0 NULL이 아니면 최댓값
        Long replyRef = replyRepository.findByNvlRef(replyRequestDto.getArticleUid());

        // replyUid가 null 이면 댓글 null이 아니면 대댓글 저장
        // 댓글 저장
        if (replyUid == null) {
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
            Reply parentReply = replyRepository.findById(replyUid).orElseThrow(() -> new IllegalArgumentException("작성할 댓글이 없습니다."));
            Long refOrderResult = refOrderAndUpdate(parentReply);
            System.out.println("refOrderResult : " + refOrderResult);
            if (refOrderResult == null) {
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
                    .step(parentReply.getStep() + 1l)
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
        Long saveStep = reply.getStep() + 1l;
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

        if (saveStep < maxStep) {
            return childNumSUm + 1l;
        } else if (saveStep == maxStep) {
            replyRepository.updateRefOrderPlus(ref, refOrder + childNum);
            return refOrder + childNum + 1l;
        } else if (saveStep > maxStep) {
            replyRepository.updateRefOrderPlus(ref, refOrder);
            System.out.println(refOrder + 1l);
            System.out.println(refOrder.getClass().getName());
            return refOrder + 1l;
        }
        return null;
    }


    @Override
    public void deleteReply(Reply reply) {
        // 부모 댓글인 경우, 자식 댓글들을 재귀적으로 삭제
        if (reply.getParentNum() == 0) {
            log.info("최상위 부모 댓글입니다.");
            deleteChildReplies(reply);
        } else {
            log.info("자식 댓글입니다.");
            // 최상위 부모가 아니기 때문에 parentReply 객체는 reply의 uid를 꺼내서 불러온다.
            // 처음에는 착각해서 reply.getParentNum()으로 객체를 불러와 같은 부모 댓글을 가지는
            // 자식 댓글이 모두 삭제 되는 현상이 발생했다.
            Reply parentReply = replyRepository.findById(reply.getUid()).orElse(null);
            // 최상위 부모가 아닌 하위 부모 댓글(자식이지만 자식을 가지고 있는 부모 댓글인 경우)
            if (parentReply.getChildNum() != 0){
                log.info("자식 댓글을 가지고 있는 자식 댓글입니다.");
                deleteChildReplies(parentReply);
            }
            // 자식을 가지고 있는 최상위 부모, 하위 부모 댓글들은 자식 댓글을 삭제하고 아래로 이동한다.
            // 자식 댓글이 없는 자식 댓글인 경우 아래의 댓글 삭제로 이동한다.
        }
        // 댓글 삭제
        replyRepository.delete(reply);
        log.info("댓글 삭제 완료.");

        // 자식 댓글이 없는 자식댓글도 childNum을 업데이트 한다.
        // 삭제된 댓글의 부모 댓글의 childNum 업데이트
        if (reply.getParentNum() != 0) {
            updateChildNum(reply.getParentNum());
        }

    }

    private void deleteChildReplies(Reply parentReply) {
        List<Reply> childReplies = replyRepository.findByParentNum(parentReply.getUid());
        for (Reply childReply : childReplies) {
            // 자식 댓글이 또 다른 자식을 가지고 있는 부모 댓글인 경우, 모두 삭제
            if (childReply.getChildNum() > 0) {
                deleteChildReplies(childReply);
            }

            // 자식 댓글 삭제
            replyRepository.delete(childReply);
            log.info("자식 댓글들이 모두 삭제되었습니다.");
        }
    }

    private void updateChildNum(Long parentNum){
        Reply parentReply = replyRepository.findById(parentNum).orElse(null);
        if (parentReply != null){
            if (parentReply.getChildNum() != 0){
                parentReply.setChildNum(parentReply.getChildNum()-1);
                replyRepository.save(parentReply);
            }
            log.info("자식 댓글 수 업데이트 완료.");
        }
    }
    @Override
    @Transactional(readOnly = true)
    public List<ReplyDto> getReplyList(Long uid) {
        List<ReplyDto> list = replyRepository.findAllByArticleUidOrderByRefDescRefOrder(uid).stream().map(m -> ReplyDto.from(m))
                .collect(Collectors.toList());
        log.info("특정 게시물 댓글 리스트 조회 완료.");
        return list;
    }

    @Override
    public void updateReply(Long uid, ReplyUpdateRequestDto replyUpdateRequestDto) {
        User user = userService.getMyInfo();
        Optional<Reply> reply = replyRepository.findById(uid);
        Article article = articleRepository.findById(reply.get().getArticle().getUid()).orElseThrow(()-> new PostNotFoundException());

        if (reply.isPresent()){
            if (reply.get().getUser() == user){
                Reply newReply = Reply.builder()
                        .uid(reply.get().getUid())
                        .content(replyUpdateRequestDto.getContent())
                        .regDate(reply.get().getRegDate())
                        .user(user)
                        .article(reply.get().getArticle())
                        .ref(reply.get().getRef())
                        .step(reply.get().getStep())
                        .refOrder(reply.get().getRefOrder())
                        .childNum(reply.get().getChildNum())
                        .parentNum(reply.get().getParentNum())
                        .build();
                replyRepository.save(newReply);
                log.info("댓글 수정 완료.");
            }
        }

    }
}
