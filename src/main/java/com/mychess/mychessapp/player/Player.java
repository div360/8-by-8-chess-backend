package com.mychess.mychessapp.player;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    @Id
    private String id;
    private String name;
    private String color;
}
