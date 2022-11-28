package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 조회시 성능 최적화
@RequiredArgsConstructor
public class MemberService {

    // memberRepository 인젝션이 됨
    // 필요한 의존 객체의 "타입"에 해당하는 빈을 찾아 주입
//    @Autowired
    private final MemberRepository memberRepository;

    // 생성자 인젝션
//    @Autowired
    // 생성자가 한개면 자동으로 오토와이어 인젝션을 해줌 스프링이
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional // 쓰기는 데이터 변경이 필요함, 기본은 readOnly false
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
//    @Transactional(readOnly = true) // 조회시 성능 최적화
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
