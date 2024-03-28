package kr.ac.kopo.oracledb0314.repository;

import kr.ac.kopo.oracledb0314.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QPageRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    // Query Method 작성
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    Page<Memo> findByMnoBetween(Long from,Long to,Pageable pageable);

    //특정 mno 값을 갖는 Memo Entity만 삭제할 때
    void deleteMemoByMno(Long mno);
    //mno 미만인 Memo Entity만 삭제할 때
    void deleteMemoByMnoLessThan(Long mno);
    //mno 이하인 Memo Entity만 삭제할 때
    void deleteMemoByMnoLessThanEqual(Long mno);

    //Query Native 문 실행방법
    @Query(value = "select * from tbl_memo where mno >= 70",nativeQuery = true)
    List<Memo> getNativeResult();

    @Query(value = "select * from tbl_memo where mno >= 80",nativeQuery = true)
    List<Objects[]> getNativeResult2();
}
