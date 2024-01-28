package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "stvort", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertLermontov", author = "ydvorzhetskiy")
    public void insertLermontov(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("persons");
        var doc = new Document().append("name", "Lermontov");
        myCollection.insertOne(doc);
    }
/*
    @ChangeSet(order = "003", id = "insertPushkin", author = "stvort")
    public void insertPushkin(PersonRepository repository) {
        repository.save(new Person("Pushkin"));
    }*/
}
/*
    merge into authors (full_name)
    key (full_name)
    values ('Author_1'),
       ('Author_2'),
               ('Author_3');

               merge into genres (name)
               key (name)
               values ('Genre_1'),
               ('Genre_2'),
               ('Genre_3'),
               ('Genre_4'),
               ('Genre_5'),
               ('Genre_6');

               merge into books (title, author_id)
               key (title)
               values ('BookTitle_1', 1),
               ('BookTitle_2', 2),
               ('BookTitle_3', 3);

               merge into books_genres (book_id, genre_id)
               values (1, 1),
               (1, 2),
               (2, 3),
               (2, 4),
               (3, 5),
               (3, 6);

               merge into comments (message, book_id)
               key (message, book_id)
               values ('Comment_1_for_book_1', 1),
               ('Comment_2_for_book_1', 1),
               ('Comment_1_for_book_2', 2),
               ('Comment_1_for_book_3', 3);*/
