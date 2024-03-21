package kr.ac.kopo.oracledb0314.repository;

import kr.ac.kopo.oracledb0314.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;
    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo=Memo.builder().memoText("Dummy Data test"+i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testselect(){
        Long mno= 100l;

        Optional<Memo> result= memoRepository.findById(mno);

        System.out.println("============================================================");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testselect2(){
    Long mno= 100l;

    Memo memo= memoRepository.getOne(mno);

    System.out.println("============================================================");


    System.out.println(memo);
    }
    @Test
    public void testUpdate(){
        Memo memo =Memo.builder().mno(95l).memoText("Update Dummy Data").build();

        Memo memo1=memoRepository.save(memo);

        System.out.println(memo1);
    }
    @Test
    public void testDelete(){

        Long mno=100L;

        memoRepository.deleteById(mno);
    }

}//클래스 끝
