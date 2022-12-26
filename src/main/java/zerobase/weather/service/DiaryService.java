package zerobase.weather.service;

import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DiaryRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DiaryService {

    @Value("${openweathermap.key}")
    private String apikey;

    // DB 연동
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(LocalDate date, String text) {
        // open weather map에서 날씨 데이터 가져오기
        String weatherData = getWeatherString();

        // 받아온 날씨 데이터 json 파싱하기
        Map<String, Object> parseWeather = parseWeather(weatherData);

        // 파싱된 데이터 + 일기 값 db에 넣기
        Diary nowDiary = new Diary();
        nowDiary.setWeather(parseWeather.get("main").toString());
        nowDiary.setIcon(parseWeather.get("icon").toString());
        nowDiary.setTemperature((Double)parseWeather.get("temp"));
        nowDiary.setText(text);
        nowDiary.setDate(date);

        // DB에 저장
        diaryRepository.save(nowDiary);
    }

    public List<Diary> readDiary(LocalDate date) {
        return diaryRepository.findAllByDate(date);
    }

    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    // OpenWeatherMap에서 데이터 받아오기
    private String getWeatherString() {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apikey;
        try {
            URL url = new URL(apiUrl);

            // apiUrl을 Http형식으로 연결
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 응답코드
            int responseCode = connection.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));        // 정상
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));        // Error
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            // 버퍼에 넣어두었던 데이터를 하나하나 읽어옴
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);             // StringBuilder 객체에 결과값을 쌓음
            }

            br.close();
            return response.toString();

        } catch (Exception e) {
            return "failed to get response";
        }
    }

    // 날씨 데이터를 String으로 받아서 JSONParser를 이용해서 파싱
    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();

        // 받아온 데이터 (json) 사용 가능하게 파싱
        JSONObject mainData = (JSONObject) jsonObject.get("main");                  // weather.main
        resultMap.put("temp", mainData.get("temp"));                                // main.temp
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));                             // weather.icon
        return resultMap;
    }



}
