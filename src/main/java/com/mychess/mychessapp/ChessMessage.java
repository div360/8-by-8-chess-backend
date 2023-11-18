package com.mychess.mychessapp;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChessMessage {
    private String fenString;
    private int sender;
}
