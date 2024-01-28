package ru.otus.spring.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.converters.CommentConverter;
import ru.otus.spring.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter converter;

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(String id) {
        return converter.commentDtoToString(commentService.findById(id));
    }

    @ShellMethod(value = "Find comment by id", key = "cbbid")
    public String findCommentsByBookId(String id) {
        return commentService.findByBookId(id).stream()
                .map(converter::commentDtoToString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String bookId, String message) {
        var comment = commentService.create(bookId, message);
        return converter.commentDtoToString(comment);
    }

    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(String commentId, String message) {
        var comment = commentService.update(commentId, message);
        return converter.commentDtoToString(comment);
    }

    @ShellMethod(value = "Delete book by id", key = "bdel")
    public void deleteComment(String id) {
        commentService.deleteById(id);
    }
}
