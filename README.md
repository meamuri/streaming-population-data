# spark stream application

Набор технологий:
* Spark Streaming
* Scala
* MongoDB

## Задача: данные о населении

По ссылке можно найти [DataSet](https://github.com/datasets/population-city)

Перед запуском убедитесь, что *запущен MongoDB* (порт 27017): 

```bash
sudo service mongod start
```

А также убедитесь, что запущен TCP сервер, написанный на **Go**: 
[репозиторий](https://github.com/meamuri/go-population-data)

```bash
$ go run population-data.go
$ go run population-data.go -data diff  # для файла *-fm.csv 
```

### Запуск приложения

в зависимости от задачи выполните одну из команд:

```bash
$ sbt "run-main application.JobMillionaires"
$ sbt "run-main application.JobPopultaion"
$ sbt "run-main application.JobRatio"
$ sbt "run-main application.JobTop"
```

Для просмотра полученных после работы программы коллекциях:

```bash
$ mongo
> use stream
> show collections
> db.<collection_name>.find() 
# где <collection_name>, например, ratio
```