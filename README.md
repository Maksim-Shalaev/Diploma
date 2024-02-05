#  Порядок запуска тестируемого приложения: ##
1. Открыть IDEA
2. Открыть Docker Desktop
3. Выбрать одну из доступных БД по условию задания (MySQL и PostgreSQL) для тестирования данного сервиса, путём раскомментрировать/ закомментировать настройки для соответствующей БД в файлах [docker-compose.yml](docker-compose.yml) и [application.properties](application.properties)
4. Для запуска контейнера с БД, в терминале IDEA набрать команду `docker-compose up`
5. Для запуска симулятора банковского сервиса из каталога `gate-simulator` в терминале набрать команду `npm start`
6. Для запуска самого сервиса, в терминале набрать команду `java -jar artifacts/aqa-shop.jar`
7. Открыть приложение на странице [http://localhost:8080/](http://localhost:8080/)

## Предустановленное ПО: ##
1. IntelliJ IDEA 2021.1.1 (Community Edition)
2. Docker Desktop 4.26.1 (131620)
3. Node js v.20.11.0
 


 
    