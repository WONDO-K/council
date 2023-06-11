package com.wondo.council.repository;

import com.wondo.council.domain.Article;
import com.wondo.council.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select coalesce(max(r.ref), 0) from Reply r where r.article.uid = ?1")
    Long findByNvlRef(Long uid);

    @Modifying
    @Query("UPDATE Reply set childNum = childNum + 1 where uid = :uid")
    void updateChildNum(@Param("uid") Long uid);

    @Query("select sum(childNum) from Reply where ref=?1")
    Long fincBySumChildNum(Long ref);

    @Query("select max(step) from Reply where ref=?1")
    Long findByNvlMaxStep(Long ref);

    @Modifying
    @Query("update Reply set refOrder = refOrder+1 where ref=?1 and refOrder > ?2")
    void updateRefOrderPlus(Long ref, Long refOrder);

    List<Reply> findAllByArticleUidOrderByRefDescRefOrder(Long uid);

    List<Reply> findByParentNum(Long uid);
}
