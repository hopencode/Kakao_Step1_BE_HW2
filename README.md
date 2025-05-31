# 카카오테크캠퍼스 BE 1단계 2차 과제

Spring 일정 관리 앱 만들기
<br>
<br>

------------------------
## API 명세서
[POSTMAN 명세서](https://documenter.getpostman.com/view/11352518/2sB2qgddDS)

| 기능             | Method | URL                                                  | Request                                                                                                   | Response                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
|------------------|--------|------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 일정 생성        | POST   | /schedules                                           | {         “task”: String,         “writer”: String,         “email”: String,         “password”: String } | // 성공시 Status Code 201 Created {         "id": Long,         "task": String,         "writer": String,         "createDateTime": LocalDateTime,         "updateDateTime": LocalDateTime }                                                                                                                                                                                                                                                                      |
| 전체 일정 조회   | GET    | /schedules?email=메일주소&date=날짜(YYYY-MM-DD 형식) |                                                                                                           | // 성공시 200 OK [     {         "id": Long,         "task": String,         "writer": String,         "email": String,         "createDateTime": LocalDateTime,         "updateDateTime": LocalDateTime     },     {         "id": Long,         "task": String,         "writer": String,         "email": String,         "createDateTime": LocalDateTime,         "updateDateTime": LocalDateTime     }     // ... ] // 없으면 200 OK와 비어있는 배열 응답 [] |
| 선택 일정 조회   | GET    | /schedules/{id}                                      |                                                                                                           | // 해당 id의 일정이 있으면 200 OK {         "id": Long,         "task": String,         "writer": String,         "email": String,         "createDateTime": LocalDateTime,         "updateDateTime": LocalDateTime }  // 없으면 404 Not Found Error                                                                                                                                                                                                              |
| 선택한 일정 수정 | PUT    | /schedules/{id}                                      | {         “task”: String,         “writer”: String,         “email”: String,         “password”: String } | // 해당 id의 일정이 있고 비밀번호가 일치하면 200 OK {         "id": Long,         "task": String,         "writer": String,         "email": String,         "createDateTime": LocalDateTime,         "updateDateTime": LocalDateTime }  // 비밀번호가 일치하지 않으면 401 Unauthorized Error // 해당 id의 일정이 없으면 404 Not Found Error                                                                                                                      |
| 선택한 일정 삭제 | DELETE | /schedules/{id}?password=패스워드                    |                                                                                                           | // 해당 id의 일정이 있고 비밀번호가 일치하면 200 OK // 비밀번호가 일치하지 않으면 401 Unauthorized Error // 해당 id의 일정이 없으면 404 Not Found Error                                                                                                                                                                                                                                                                                                           |




ERD 다이어그램
![Image](https://github.com/user-attachments/assets/48d125c6-a588-464c-84f0-13cf65c60523)

