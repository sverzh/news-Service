## Описание функционала

RESTful web-сервис, реализующий функционал для работы
с системой управления новостями

### Выставленные REST-сервисы:

###Получение всех новостей:

GET /rest/news

###Получение новости c id = newsId:
GET /rest/news/{newsId}

###Создание новости:

POST /rest/news

###Изменение новости c id = newsId:

PUT /rest/news/{newsId}

###Удаление новости c id = newsId:

DELETE /rest/news/{newsId}

###Получение всех комментариев новости с id=newsId:

GET /rest/news/{newsId}/comments

###Получение комментария с id = commentId, у новости c id = newsId:

GET /rest/news/{newsId}/comments/{commentId}

###Создание комментария у новости id = newsId:

POST /rest/news/{newsId}/comments

###Изменение комментария с id = commentId, у новости c id = newsId:

PUT /rest/news/{newsId}/comments/{commentId}

###Удаление комментария с id = commentId, у новости c id = newsId:

DELETE /rest/news/{newsId}/comments/{commentId}