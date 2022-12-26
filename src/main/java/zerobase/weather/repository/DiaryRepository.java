package zerobase.weather.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {

    // 날짜별 조회
    List<Diary> findAllByDate(LocalDate date);

    // 기간별 조회
    List<Diary> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    // 수정을 위한 함수
    Diary getFirstByDate(LocalDate date);

}
