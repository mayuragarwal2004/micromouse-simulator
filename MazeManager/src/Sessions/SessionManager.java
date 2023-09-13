package Sessions;

import Gameplay.*;

import java.util.*;

public class SessionManager {
    private Map<String, GameManager> gameMap;
    private static SessionManager manager = null;

    public static SessionManager getSessionManager() {
        if (manager == null) {
            manager = new SessionManager();
        }
        return manager;
    }

    private SessionManager() {
        gameMap = new HashMap<>();
    }

    public void addGame(String id, GameManager manager) {
        gameMap.put(id, manager);
    }

    public GameMove playGame(String id, Move m) {
        return gameMap.get(id).playMove(m);
    }

    public void removeGame(String id) {
        gameMap.remove(id);
    }

    public GameManager getGameManager(String id) {
        return gameMap.get(id);
    }

    public MoveHistory getHistory(String id) {
        return gameMap.get(id).getHistory_test();
    }

    public void showGames() {
        Iterator iterator = gameMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapElement
                    = (Map.Entry) iterator.next();
            System.out.print(mapElement.getKey());

            System.out.printf("\t\t\ttest:\t%s\n", ((GameManager) (mapElement.getValue())).getHistory_test());
            System.out.printf("\t\t\tfinal:\t%s\n", ((GameManager) (mapElement.getValue())).getHistory_final());
        }
    }

    public String getGame(String id) {
        String x = "";

        GameManager gm = gameMap.get(id);

        x += String.format("{\"test\":%s,", gm.getHistory_test());
        x += String.format("\"final\":%s}", gm.getHistory_test());
        return x;
    }

    //Expected Format: ID N [1110,1001...][1001,1111...]
    public String CreateMazeSession(String s) {
//        String str[] = s.split(" ");
//        String id = str[0];
//        int N = Integer.parseInt(str[1]);
//        String s1 = s.substring(s.indexOf('['), s.indexOf(']'));
//        String s2 = s.substring(s.lastIndexOf('['), s.lastIndexOf(']'));
//        System.out.println(s1);
//        System.out.println(s2);
//        addGame(id, new GameManager(N, N, (s1.split(",")), (s2.split(","))));
        return "NO";
    }

    public String Execute(String s) {

        String str[] = s.split(" ");
        String id = str[0].trim();
        String command = str[1].trim();
        if (command.equalsIgnoreCase("F")) {
            return getStringFromMove(playGame(id, Move.FORWARD));
        } else if (command.equalsIgnoreCase("B")) {
            return getStringFromMove(playGame(id, Move.BACK));
        } else if (command.equalsIgnoreCase("reset")) {
            gameMap.get(id).reset();
            return "RESET";
        } else if (command.equalsIgnoreCase("L")) {
            return getStringFromMove(playGame(id, Move.LEFT));
        } else if (command.equalsIgnoreCase("R")) {
            return getStringFromMove(playGame(id, Move.RIGHT));
        } else if (command.equalsIgnoreCase("W")) {
            return gameMap.get(id).GetWall();
        } else {
            return null;
        }
    }

    String getStringFromMove(GameMove move) {
        return move.status + " " + move.walls;
    }

    int[] getIntArray(String line) {
        String str[] = line.split(" ");
        int arr[] = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            arr[i] = Integer.parseInt(str[i]);
        }
        return arr;
    }
}
