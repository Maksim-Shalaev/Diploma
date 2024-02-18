#  Порядок запуска тестируемого приложения: ##
1. Открыть IDEA
2. Запустить Docker c контейнерами, командой в терминале `docker-compose up`
3. Для запуска самого сервиса (с БД MySQL), в терминале набрать команду `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
4. Для запуска самого сервиса (с БД PostgreSQL), в терминале набрать команду `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
5. Открыть приложение на странице [http://localhost:8080/](http://localhost:8080/)  


#  Порядок остановки тестируемого приложения:


1. Для остановки сервиса в терминале нажать сочетание клавиш `Ctrl + C`
2. Для остановки Docker в терминале командой `docker-compose down`    


## Предустановленное ПО: ##
1. IntelliJ IDEA 2021.1.1 (Community Edition)
2. Docker Desktop 4.26.1 (131620)
3. Node js v.20.11.0
 


 
    