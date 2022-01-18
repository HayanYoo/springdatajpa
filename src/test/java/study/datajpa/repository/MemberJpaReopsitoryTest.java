package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaReopsitoryTest {

    @Autowired MemberJpaReopsitory memberJpaReopsitory;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberJpaReopsitory.save(member);

        Member findMember = memberJpaReopsitory.find(savedMember.getId());

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaReopsitory.save(member1);
        memberJpaReopsitory.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberJpaReopsitory.findById(member1.getId()).get();
        Member findMember2 = memberJpaReopsitory.findById(member2.getId()).get();
        Assertions.assertThat(findMember1).isEqualTo(member1);
        Assertions.assertThat(findMember2).isEqualTo(member2);

        findMember1.setUsername("member!!!!!");


//        // 리스트 조회 검증
//        List<Member> all = memberJpaReopsitory.findAll();
//        Assertions.assertThat(all.size()).isEqualTo(2);
//
//        //카운트 검증
//        long count = memberJpaReopsitory.count();
//        Assertions.assertThat(count).isEqualTo(2);
//
//        // 삭제 검증
//        memberJpaReopsitory.delete(member1);
//        memberJpaReopsitory.delete(member2);
//
//        long deletedCount = memberJpaReopsitory.count();
//        Assertions.assertThat(deletedCount).isEqualTo(0);

    }


    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberJpaReopsitory.save(m1);
        memberJpaReopsitory.save(m2);

        List<Member> result = memberJpaReopsitory.findByUsernameAndAgeGreaterThan("AAA", 15);

        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(20);
        Assertions.assertThat(result.size()).isEqualTo(1);


    }

    @Test
    public void bulkUpdate() {
        //given
        memberJpaReopsitory.save(new Member("member1", 10));
        memberJpaReopsitory.save(new Member("member2", 19));
        memberJpaReopsitory.save(new Member("member3", 20));
        memberJpaReopsitory.save(new Member("member4", 21));
        memberJpaReopsitory.save(new Member("member5", 40));

        //when
        int resultCount = memberJpaReopsitory.bulkAgePlus(20);

        //then
        Assertions.assertThat(resultCount).isEqualTo(3);

    }
}