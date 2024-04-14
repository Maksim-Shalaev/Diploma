# Порядок запуска тестируемого приложения: ##

1. Открыть IDEA, командой в терминале `git clone https://github.com/Maksim-Shalaev/Diploma` скачать данный репозиторий
2. Запустить Docker c контейнерами, командой в терминале `docker-compose up`
3. Для запуска самого сервиса (с БД MySQL), в терминале набрать
   команду `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
4. Для запуска самого сервиса (с БД PostgreSQL), в терминале набрать
   команду `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
5. Открыть приложение на странице [http://localhost:8080/](http://localhost:8080/)


# Порядок запуска авто-тестов для данного приложения:
1. Для тестирования БД MySQL в терминале набрать `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
2. Для тестирования БД PostgreSQL в терминале набрать `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`

# Порядок остановки тестируемого приложения:

1. Для остановки сервиса в терминале нажать сочетание клавиш `Ctrl + C`
2. Для остановки Docker в терминале командой `docker-compose down`

## Предустановленное ПО: ##

1. IntelliJ IDEA 2021.1.1 (Community Edition)
2. Docker Desktop 4.26.1 (131620)

 


 
    