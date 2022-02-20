package com.xkj.poetryserver.dto;

import com.xkj.poetryserver.domain.Sentence;
import org.springframework.data.annotation.Id;

public class SentenceDTO {
    @Id
    private String id;
    private String content;
    private String writer;
    private String title;

    public SentenceDTO(Sentence sentence) {
        this.id = sentence.getId();
        this.content = sentence.getName();
        this.writer = sentence.getFrom().substring(0,sentence.getFrom().indexOf("《"));
        this.title = sentence.getFrom().substring(sentence.getFrom().indexOf("《")+1,sentence.getFrom().indexOf("》"));
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }


}
