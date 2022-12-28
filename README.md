## 날씨 일기 프로젝트

## **프로젝트 주제**

- **SpringBoot를 활용한 날씨 일기 프로젝트**

## **프로젝트 구조**
![image](https://user-images.githubusercontent.com/110509654/209637521-457d8566-1c5c-477f-b15c-a3be6a7dc9da.png)


## **ERD**
![image](https://user-images.githubusercontent.com/110509654/209636593-13e3e922-8874-4bad-9173-5154689240f3.png)


## 사용 기술 스택
- **Language : Java**
- **DBMS : MySQL**
- **Java Version : Java 8**
- **IDE : Intellij IDEA 2022.2.3 (Ultimate Edition)**
- **Test Program : Postman**
- **etc : SpringBoot Logging, Swagger(API document)**

## API 정보(Swagger 활용)
![image](https://user-images.githubusercontent.com/110509654/209657586-03b06142-74b9-4a0e-acfe-2ca61c404bc2.png)


## 프로젝트 기능

**날씨 데이터 저장**
- 일기 텍스트와 날씨를 이용하여 데이터베이스에 일기 저장

**날씨 데이터 조회**
- 선택된 날짜의 모든 일기 데이터를 가져옴
- 선택한 기간중의 모든 일기 데이터를 가져옴


**날씨 저장 스케쥴링**
![image](https://user-images.githubusercontent.com/110509654/209658888-c2512aba-2be7-4eff-b691-21db4d50cec7.png)

- 캐싱을 적용하여 해당 로직 처리
- 매일 새벽 1시에 외부api에서 전일 날씨 데이터 얻어옴
- DB에 해당 데이터 저장
- 전일 날씨 데이터 필요한 경우 DB에서 얻어옴


**API document**
- Swagger 활용하여 각 기능별 API자료 참고사이트 구현

