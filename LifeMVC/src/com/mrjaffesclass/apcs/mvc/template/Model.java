package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;

/**
 * The model represents the data that the app uses.
 *
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

    private final Messenger mvcMessaging;

    private boolean[][] board;
    private boolean isBlackMove = true;

    public Model(Messenger messages) {
        mvcMessaging = messages;
        this.board = new boolean[10][10];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = false;
            }
        }

    }

    public void init() {
        mvcMessaging.subscribe("newGame", this);
        mvcMessaging.subscribe("checkState", this);
    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        if (messagePayload != null) {
            System.out.println("MSG: received by model: " + messageName + " | " + messagePayload.toString());
        } else {
            System.out.println("MSG: received by model: " + messageName + " | No data sent");
        }

        if (messageName.equals("gameStart")) {
            boolean isGame = true;
            while (isGame) {
                boolean[][] newBoard = flow(board);
                board = newBoard;
                try {
                    Thread.sleep(250);
                }
                catch(InterruptedException e) {
                    
                }
            }

        }
    }

    public boolean[][] flow(boolean[][] arr) {
        boolean[][] retArr = new boolean[arr.length][arr[0].length];
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[0].length; c++) {
                retArr[r][c] = isLive(arr, r, c);
            }
        }
        return retArr;
    }

    public boolean isLive(boolean[][] arr, int row, int col) {
        int count = 0; 
        for (int r = row-1; row < row+2; row++) {
            for (int c = col-1; col < col+2; col++) {
                if (r == row && c == col) {
                    continue;
                }
                if (arr[r][c]) {
                    count++;
                }
            }
        }
        
        if (arr[row][col]) {
            if (count < 2 || count > 3) {
                return false;
            }
            if (count == 2 || count == 3) {
                return true;
            }
        }
        if (!arr[row][col]) {
            if (count == 3) {
                return true;
            }
        }
        return false;
    }
}
