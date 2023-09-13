package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> findMembers = memberJpaRepository.findAll();
        assertThat(findMembers.size()).isEqualTo(2);

        // count 검증
        Long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        Long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);

    }

    @Test
    @DisplayName("이름과 나이로 조회하는 로직 검증")
    public void findByUsernameAndAgeCreaterThen() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);

//        memberJpaRepository.save(member1);
//        memberJpaRepository.save(member2);
        memberRepository.save(member1);
        memberRepository.save(member2);

//        List<Member> findMember = memberJpaRepository.findByUsernameAndAgeCreaterThan("AAA", 15);
        List<Member> findMember = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        assertThat(findMember.get(0).getUsername()).isEqualTo("AAA");
        assertThat(findMember.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void testQuery() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> findMember = memberRepository.findUser("AAA", 10);
        assertThat(findMember.get(0)).isEqualTo(member1);

    }

}