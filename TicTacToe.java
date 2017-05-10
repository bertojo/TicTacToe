import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private Scanner sc;
    private String[][] playingField;
    public static final int GRID_SIZE = 3;
    private Random generator;
    private boolean gameOver;
    
    public TicTacToe() {
        sc = new Scanner(System.in);
        playingField = initialisePlayingField();
        gameOver = false;
        generator = new Random();
    }
    
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.run();
    }
    
    private void run() {
        runTutorial();
        runGameLoop();
    }
    
    private void runGameLoop() {
        String winner = "";
        while (!gameOver) {
            boolean validMove = false;
            
            showCurrentMap();
            if (checkWinLose().equals("X")) {
                winner = "X";
                break;
            } else if (checkWinLose().equals("O")) {
                winner = "O";
                break;
            }
            
            //Player's move
            while (!validMove) {
                int playerChoiceX = playerInputX();
                int playerChoiceY = playerInputY();
                validMove = updateMap(playerChoiceX, playerChoiceY, "X");
                if (!validMove) {
                    System.out.println("That slot has already been taken!");
                }
            }
            
            showCurrentMap();
            
            if (checkWinLose().equals("X")) {
                winner = "X";
                break;
            } else if (checkWinLose().equals("O")) {
                winner = "O";
                break;
            } else if (checkWinLose().equals("S")) {
                winner = "S";
                break;
            }
            
            aiTurn();
        }
        
        if (winner.equals("X")) {
            System.out.println("Congrats! You win!");
        } else if (winner.equals("S")) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("You lost.");
        }
    }

    //Check if game is over, and returns who won.
    private String checkWinLose() {
        String winner = ".";
        
        winner = checkRows("X", winner);
        winner = checkCols("X", winner);
        winner = checkDiag1("X", winner);
        winner = checkDiag2("X", winner);

        
        winner = checkRows("O", winner);
        winner = checkCols("O", winner);
        winner = checkDiag1("O", winner);
        winner = checkDiag2("O", winner);

        winner = checkStalemate(winner);
        
        return winner;
    }
    
    //Check if its a tie.
    private String checkStalemate(String winner) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (playingField[i][j].equals(".")) {
                    return winner;
                }
            }
        }
        return "S";
    }
    
    
    private String checkRows(String target, String winner) {
        if (!winner.equals(".")) {
            return winner;
        }
        int count = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (!playingField[i][j].equals(target)) {
                    count++;
                    break;
                }
            }
        }
        if (count == 3) {
            return winner;
        } else {
            return target;
        }
    }
    
    private String checkCols(String target, String winner) {
        if (!winner.equals(".")) {
            return winner;
        }
        int count = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (!playingField[j][i].equals(target)) {
                    count++;
                    break;
                }
            }
        }
        if (count == 3) {
            return winner;
        } else {
            return target;
        } 
    }

    //To check the diagonal from top right to bottom left
    private String checkDiag2(String target, String winner) {
        if (!winner.equals(".")) {
            return winner;
        }
        if (playingField[0][2].equals(target) &&
            playingField[1][1].equals(target) &&
            playingField[2][0].equals(target)) {
            return target;
        } else {
            return winner;
        }
    }
    
    //To check the diagonal from top left to bottom right
    private String checkDiag1(String target, String winner) {
        if (!winner.equals(".")) {
            return winner;
        }
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (i == j) {
                    if (!playingField[i][j].equals(target)) {
                        return winner;
                    }
                }
            }
        }
        return target;
    }
    
    //Selects a random point on the playing field to play on.
    private void aiTurn() {
        aiThink();
        
        int x = generator.nextInt(3);
        int y = generator.nextInt(3);
        
        while (!updateMap(x, y, "O")) {
            x = generator.nextInt(3);
            y = generator.nextInt(3);
        }
    }
    
    private void aiThink() {
        System.out.println("Good move. Now it's my turn!");
        try {
            Thread.sleep(500);  //Just to add abit of character to the AI.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    //Updates the coordinate on the playing field with the specified key. If it is already taken, then return false.
    private boolean updateMap(int x, int y, String key) {
        if (!playingField[x][y].equals(".")) {
            return false;
        }
        this.playingField[x][y] = key;
        return true;
    }
    
    private int playerInputX() {
        String choice = "";
        while (true) {
            System.out.println("Please enter an X coordinate (1 <= X <= 3): ");
            choice = sc.next();

            if (choice.equals("3") || choice.equals("2") || choice.equals("1")) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        return Integer.parseInt(choice) - 1;
    }
    
    private int playerInputY() {
        String choice = "";
        while (true) {
            System.out.println("Please enter a Y coordinate (1 <= Y <= 3): ");
            choice = sc.next();
            
            if (choice.equals("3") || choice.equals("2") || choice.equals("1")) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        return Integer.parseInt(choice) - 1;
    }

    private void showCurrentMap() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(this.playingField[i][j]);
            }
            System.out.println();
        }
    }
    
    private String[][] initialisePlayingField() {
        String[][] field = new String[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                field[i][j] = ".";
            }
        }
        return field;
    }
    
    private void runTutorial() {
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("You will be playing as \"X\" and you start first.");
    }

}
