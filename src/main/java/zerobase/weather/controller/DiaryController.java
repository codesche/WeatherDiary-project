package zerobase.weather.controller;

import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

import java.time.LocalDate;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/create/diary")
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     LocalDate date, @RequestBody String text) {

        diaryService.createDiary(date, text);
    }

    @GetMapping("/read/diary")              // 조회할 때 GetMapping 많이 사용 - 일기를 날짜에 따라 조회
    List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        return diaryService.readDiary(date);
    }

    @GetMapping("/read/diaries")               // 한 주간의 일기 조회 (날짜 범위)
    List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
                            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
        return diaryService.readDiaries(startDate, endDate);
    }


}
