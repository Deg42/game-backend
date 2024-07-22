package com.deg.gamebackend.game;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private Map<String, Position> playerPositions = new HashMap<>();

    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position{" + "x=" + x + ", y=" + y + '}';
        }
    }

    public void addPlayer(String playerId, int x, int y) {
        playerPositions.put(playerId, new Position(x, y));
    }

    public void movePlayer(String playerId, int newX, int newY) {
        Position position = playerPositions.get(playerId);
        if (position != null) {
            position.x = newX;
            position.y = newY;
        }
    }

    public Position getPlayerPosition(String playerId) {
        return playerPositions.get(playerId);
    }

    public Map<String, Position> getAllPositions() {
        return playerPositions;
    }
}
