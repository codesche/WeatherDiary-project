package zerobase.weather.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @ApiOperation(value = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장", notes = "이것은 노트")
    @PostMapping("/create/diary")
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     LocalDate date, @RequestBody String text) {

        diaryService.createDiary(date, text);
    }

    @ApiOperation("선택한 날짜의 모든 일기 데이터를 가져옵니다")
    @GetMapping("/read/diary")              // 조회할 때 GetMapping 많이 사용 - 일기를 날짜에 따라 조회
    List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        return diaryService.readDiary(date);
    }

    @ApiOperation("선택한 기간중의 모든 일기 데이터를 가져옵니다")
    @GetMapping("/read/diaries")               // 한 주간의 일기 조회 (날짜 범위)
    List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = ISO.DATE)
                            @ApiParam(value = "조회할 기간의 첫번째날 : yyyy-MM-dd",
                            example = "2020-02-02")
                            LocalDate startDate,
                            @RequestParam @DateTimeFormat(iso = ISO.DATE)
                            @ApiParam(value = "조회할 기간의 마지막날 : yyyy-MM-dd",
                            example = "2020-02-02")
                            LocalDate endDate) {
        return diaryService.readDiaries(startDate, endDate);
    }

    // 날씨 일기 수정
    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
        @RequestBody String text) {
        diaryService.updateDiary(date, text);
    }

    // 날씨 일기 삭제
    @DeleteMapping("/delete/diary")
    void deleteDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        diaryService.deleteDiary(date);
    }

}
