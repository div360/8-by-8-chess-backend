package com.mychess.mychessapp.chessData;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChessData {
    private String fenString;
    private int moveCount;
    private ArrayList<HashMap<String, String>> moveHistory;
    private ArrayList<HashMap<String, String>> capturedPieces;
    private Date timeStamp;
}
