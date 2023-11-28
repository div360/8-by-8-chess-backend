package com.mychess.mychessapp.user;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class User {
    @Id
    private String id;
    private String name;
    private String color;
    private boolean isOnline;
}
