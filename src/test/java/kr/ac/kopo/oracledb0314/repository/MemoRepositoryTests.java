package kr.ac.kopo.oracledb0314.repository;

import kr.ac.kopo.oracledb0314.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {

        System.out.println(memoRepository.getClass().getName());
    }

    // MemoRepository의 save(Memo Entity객체의 참조값)를 호출해서 insert한다
    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Dummy Data test" + i).build();
            memoRepository.save(memo);
        });
    }

    // MemoRepository의 findById(Memo Entity 객체의 Id로 설정된 필드값)를 호출해서 select한다
//  findById()호출되면 바로 select문을 실행한다
//  findById()는 NullpointException이 발생되지 않도록 Null체크를 한다
    @Test
    public void testselect() {
        Long mno = 100l;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("============================================================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    //MemoRepository의 getOne(Memo Entity 객체의 Id로 설정된 필드값)를 호출해서 select한다
    //getone은 호출되면 바로 실행되지 않고 Memo Entity가 필요할때 select를 실행한다
    @Transactional
    @Test
    public void testselect2() {
        Long mno = 100l;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("============================================================");


        System.out.println(memo);
    }

    // MemoRepository의 save(Memo Entity객체의 참조값)를 호출해서 Update한다
    // save()는 호출되면 먼저 select를 하기 때문에 기존에 Entity가 있을때는 update를 실행한다.
    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(95l).memoText("Update Dummy Data").build();

        Memo memo1 = memoRepository.save(memo);

        System.out.println(memo1);
    }

    // MemoRepository의 deleteById(Memo Entity의 mno)를 호출해서 delete한다
    @Test
    public void testDelete() {

        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        //1페이지당 10개 Entity
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
        System.out.println("====================================");
        System.out.println("Total pages:"+result.getTotalPages());
        System.out.println("Total Count:"+result.getTotalElements());
        System.out.println("Total Number:"+result.getNumber());
        System.out.println("page Size:"+result.getSize());
        System.out.println("Has next page?:"+result.hasNext());
        System.out.println("Is first page?:"+result.isFirst());


    }
    @Test
    public void testSort(){
        Pageable pageable=PageRequest.of(0,10, Sort.by("mno").descending());
        Page<Memo>result=memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println("number:"+memo.getMno()+",content"+memo.getMemoText());
        });
    }
    @Test
    public void testQueryMethod1(){
        List<Memo> result =memoRepository.findByMnoBetweenOrderByMnoDesc(20L,30L);
        for (Memo memo :result){
            System.out.println(memo.toString());
        }
    }
    @Test
    public void testQueryMethod2(){
        Pageable pageable =PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Memo> result =memoRepository.findByMnoBetween(20L,60L,pageable);
        for (Memo memo :result){
            System.out.println(memo.toString());
        }

        System.out.println("=============================");
        pageable  =PageRequest.of(0,10);
        result =memoRepository.findByMnoBetween(20L,60L,pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }
    @Transactional
    @Commit
    @Test
    public void  testQueryMethod3(){
        memoRepository.deleteMemoByMnoLessThan(5L);
        testPageDefault();
    }
    @Test
    public void testQueryAnnotationNative(){
        List<Memo> result=memoRepository.getNativeResult();
        for(Memo memo :result){
            System.out.println(memo);
        }
    }
    @Test
    public void testQueryAnnotationNative2(){
        List<Object[]> result=memoRepository.getNativeResult2();
        for(Object[] memoObj :result){
            System.out.println(memoObj[1]);
        }
    }
}//클래스 끝
