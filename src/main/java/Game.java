import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static final int MAP_WIDTH = 3;
    public static final int MAP_HEIGHT = 3;
    private Player[][] map = new Player[3][3];
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;

    Settings settings = new Settings();

    public Settings getSettings(){
        return settings;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void start(){
        for(int i = 0; i<MAP_WIDTH; i++){
            for (int j = 0; j<MAP_HEIGHT; j++){
                map[i][j] = null;
            }
        }
        gameState = GameState.RUNNING;
        switch(settings.startingPlayer){
            case RANDOM:
                Random random = new Random();
                if(random.nextInt(2) == 0)
                    currentPlayer = Player.CROSS;
                else
                    currentPlayer = Player.CIRCLE;
                break;
            case CIRCLE:
                currentPlayer = Player.CIRCLE;
                break;
            case CROSS:
                currentPlayer = Player.CROSS;
                break;
        }
    }
    private void checkWinner(){
        if(map[0][0] != null && map[0][0] == map[0][1] && map[0][1] == map[0][2]) {
            gameState = GameState.FINISHED;
            winner = map[0][0];
            return;
        }
        if(map[1][0] != null && map[1][0] == map[1][1] && map[1][1] == map[1][2]) {
            gameState = GameState.FINISHED;
            winner = map[1][0];
            return;
        }
        if(map[2][0] != null && map[2][0] == map[2][1] && map[2][1] == map[2][2]) {
            gameState = GameState.FINISHED;
            winner = map[2][0];
            return;
        }
        if(map[0][0] != null && map[0][0] == map[1][0] && map[1][0] == map[2][0]) {
            gameState = GameState.FINISHED;
            winner = map[0][0];
            return;
        }
        if(map[0][1] != null && map[0][1] == map[1][1] && map[1][1] == map[2][1]) {
            gameState = GameState.FINISHED;
            winner = map[0][1];
            return;
        }
        if(map[0][2] != null && map[0][2] == map[1][2] && map[1][2] == map[2][2]) {
            gameState = GameState.FINISHED;
            winner = map[0][0];
            return;
        }
        if(map[0][0] != null && map[0][0] == map[1][1] && map[1][1] == map[2][2]) {
            gameState = GameState.FINISHED;
            winner = map[0][0];
            return;
        }
        if(map[0][2] != null && map[0][2] == map[1][1] && map[1][1] == map[2][0]) {
            gameState = GameState.FINISHED;
            winner = map[0][2];
            return;
        }
        for(int i = 0; i<MAP_WIDTH; i++){
            for(int j = 0; j<MAP_HEIGHT; j++){
                if(map[i][j] == null)
                    return;
            }
        }
        gameState = GameState.FINISHED;
        winner = null;
    }
    public void move(int x, int y){
        if(gameState == GameState.RUNNING && map[x][y] == null){
            map[x][y] = currentPlayer;
            checkWinner();
            if(gameState != GameState.FINISHED)
                currentPlayer = (currentPlayer == Player.CIRCLE ? Player.CROSS : Player.CIRCLE);
        }
    }
    public Player getCell(int x, int y){
            return map[x][y];
    }
    public void save(OutputStream o)throws Exception{
        PrintWriter pw = new PrintWriter(o);
        for(int y = 0; y<MAP_HEIGHT; y++){
            for(int x = 0; x<MAP_WIDTH; x++){
                if(map[x][y] == null)
                    pw.print(". ");
                else if(map[x][y] == Player.CIRCLE)
                    pw.print("O ");
                else if(map[x][y] == Player.CROSS)
                    pw.print("X ");
            }
        }
        if(gameState == GameState.FINISHED) {
            pw.print("F ");
            if(getWinner() == Player.CROSS)
                pw.print("X ");
            else if(getWinner() == Player.CIRCLE)
                pw.print("O ");
            else
                pw.print("R ");
        }
        else {
            pw.print("R ");
            if(currentPlayer == Player.CIRCLE)
                pw.print("O ");
            if(currentPlayer == Player.CROSS)
                pw.print("X ");
        }
        pw.flush();
    }
    public void load(InputStream i)throws InvalidStateFormatException{
        String znakTymczasowy = "";
        Scanner scanner = new Scanner(i);
        for(int y = 0; y<MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                znakTymczasowy = scanner.next();
                if (znakTymczasowy.equals("."))
                    map[x][y] = null;
                else if (znakTymczasowy.equals("X"))
                    map[x][y] = Player.CROSS;
                else if (znakTymczasowy.equals("O"))
                    map[x][y] = Player.CIRCLE;
                else new InvalidStateFormatException();
            }
        }
        znakTymczasowy = scanner.next();
        if(znakTymczasowy.equals("F")) {
            gameState = GameState.FINISHED;
            znakTymczasowy = scanner.next();
            if (znakTymczasowy.equals("O"))
                winner = Player.CIRCLE;
            else if (znakTymczasowy.equals("R"))
                winner = null;
            else if (znakTymczasowy.equals("X"))
                winner = Player.CROSS;
            else
                throw new InvalidStateFormatException();
        } else if (znakTymczasowy.equals("R")) {
            gameState = GameState.RUNNING;
            znakTymczasowy = scanner.next();
            if(znakTymczasowy.equals("O"))
                currentPlayer = Player.CIRCLE;
            else if(znakTymczasowy.equals("X"))
                currentPlayer = Player.CROSS;
            else
                throw new InvalidStateFormatException();
        }
    }
    public PlayerType getCurrentPlayerType(){
         if(getCurrentPlayer() == Player.CIRCLE)
             return settings.circleType;
         else
             return settings.crossType;
    }
    public int[] pickComputerMove()throws Exception{
        PlayerType currentType = getCurrentPlayerType();
        if(currentType != PlayerType.CPU_EASY)
            throw new Exception();
        int[] emptyCellX = new int[9];
        int[] emptyCellY = new int[9];
        int k = 0;
        for(int i = 0; i<MAP_WIDTH; i++){
            for(int j = 0; j<MAP_HEIGHT; j++){
                if(map[i][j] == null){
                    emptyCellX[k] = i;
                    emptyCellY[k] = j;
                    k++;
                }
            }
        }
        Random random = new Random();
        int randomDigit = random.nextInt(k);
        //move(emptyCellX[randomDigit], emptyCellY[randomDigit]);
        int[] randomCell = new int[2];
        randomCell[0] = emptyCellX[randomDigit];
        randomCell[1] = emptyCellY[randomDigit];
        return randomCell;
    }
}

//zrobić CPU_EASY, zapisywanie ustawień, wczytywanie po uruchomieniu programu