package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.models.Book;
import ru.otus.spring.repositories.CommentRepository;

@Component
@RequiredArgsConstructor
public class MongoCommentCascadeDelete extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);

        var source = event.getSource();
        var id = source.get("_id").toString();
        commentRepository.deleteAllByBookId(id);
    }
}
