package zerobase.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

@Service
public class DiaryService {

    @Value("${openweathermap.key}")
    private String apikey;

    public void createDiary(LocalDate date, String text) {
        // open weather map에서 날씨 데이터 가져오기
        String weatherData = getWeatherString();
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
}
