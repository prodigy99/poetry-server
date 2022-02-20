package com.xkj.poetryserver.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fakeUserInfo")
@Data
public class FakeUserInfo {
    @Id
    private String id;
    private String username;
    private String avatar;
}
