package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// 스프링 부트가 Component 스캔해서 스프링빈으로 자동 등록
@Repository
public class MemberRepository {

    // JPA 표준 어노테이션
    // JPA EntityManager를 등록해줌
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        // 저장
        em.persist(member);
    }

    // 단건 조회
    public Member findOne(Long id) {
        // 멤버 반환
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        //JPQL : JPA로 번역되긴 하는데, JPA는 테이블 객체로, 엔티티 객체로 조회
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
