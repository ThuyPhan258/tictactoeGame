import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class App{
    public static Queue<Player> x_players = new LinkedList<>();
    public static Queue<Player> o_players = new LinkedList<>();
    public static Stack<Player> winners = new Stack<>();
    public static Stack<Player> losers = new Stack<>();
    public static PlayerManager player_manager = new PlayerManager();

    static String[] board;
    static String turn;

    public static void displayMenu() {
        System.out.println("--------------------------------------");
        System.out.println("1. Enter player info");
        System.out.println("2. Add players into queue");
        System.out.println("3. Start new game");
        System.out.println("4. Display last n winners");
        System.out.println("5. Display last n losers");
        System.out.println("--------------------------------------");
        System.out.print("Please enter your choice: ");
    }

    public static void addNewPlayer() {
        player_manager.addNewPlayer();
    }

    public static void findAndAddPlayersToQueue() {
        Scanner scanner = new Scanner(System.in);
        Player playerX = null, playerO = null;
        while (playerX == null) {
            System.out.print("Enter name of the next Player X: ");
            String name = scanner.nextLine();
            playerX = player_manager.findPlayerByName(name);
            if (playerX == null) {
                System.out.printf("Cannot find player with name '%s'. Please try again.\n", name);
            } else {
                x_players.add(playerX);
                System.out.printf("Player '%s' added to Player X queue.\n", name);
            }
        }
        while (playerO == null) {
            System.out.print("Enter name of the next Player O: ");
            String name = scanner.nextLine();
            playerO = player_manager.findPlayerByName(name);
            if (playerO == null) {
                System.out.printf("Cannot find player with name '%s'. Please try again.\n", name);
            } else {
                o_players.add(playerO);
                System.out.printf("Player '%s' added to Player O queue.\n", name);
            }
        }
    }

    static String checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = board[2] + board[4] + board[6];
                    break;
            }
            // For X winner
            if (line.equals("XXX")) {
                return "X";
            }

            // For O winner
            else if (line.equals("OOO")) {
                return "O";
            }
        }

        for (int a = 0; a < 9; a++) {
            if (Arrays.asList(board).contains(
                    String.valueOf(a + 1))) {
                break;
            } else if (a == 8) {
                return "draw";
            }
        }
        // To enter the X Or O at the exact place on board.
        System.out.println(turn + "'s turn; enter a slot number to place " + turn + " in:");
        return null;
    }

    static void printBoard() {
        System.out.println("|---|---|---|");
        System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
        System.out.println("|---|---|---|");
    }

    public static void startNewGame() {
        Player playerX, playerO;
        playerX = x_players.poll();
        if (playerX == null) {
            System.out.println("Could not find next player X");
        }
        playerO = o_players.poll();
        if (playerO == null) {
            System.out.println("Could not find next player O");
        }
        // Start the game and push new winner/loser into winners or losers stack
        Scanner in = new Scanner(System.in);
        board = new String[9];
        turn = "X";
        String winner = null;
        for (int a = 0; a < 9; a++) {
            board[a] = String.valueOf(a + 1);
        }
        System.out.println("Welcome to 3x3 Tic Tac Toe.");
        printBoard();
        System.out.println(
                "X will play first. Enter a slot number to place X in:");
        while (winner == null) {
            int numInput;

            // Exception handling.
            // numInput will take input from user like from 1 to 9.
            // If it is not in range from 1 to 9.
            // then it will show you an error "Invalid input."
            try {
                numInput = in.nextInt();
                if (!(numInput > 0 && numInput <= 9)) {
                    System.out.println(
                            "Invalid input; re-enter slot number:");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input; re-enter slot number:");
                continue;
            }

            // This game has two player x and O.
            // Here is the logic to decide the turn.
            if (board[numInput - 1].equals(
                    String.valueOf(numInput))) {
                board[numInput - 1] = turn;
                if (turn.equals("X")) {
                    turn = "O";
                } else {
                    turn = "X";
                }
                printBoard();
                winner = checkWinner();
            } else {
                System.out.println(
                        "Slot already taken; re-enter slot number:");
            }
        }

        // If no one win or lose from both player x and O.
        // then here is the logic to print "draw".
        if (winner.equalsIgnoreCase("draw")) {
            System.out.println(
                    "It's a draw! Thanks for playing.");
        }

        // For winner -to display Congratulations! message.
        else {
            System.out.println( "Congratulations! " + winner + "'s have won! Thanks for playing.");
            if (winner == "X") {
                winners.push(playerX);
                losers.push(playerO);
            } else {
                winners.push(playerO);
                losers.push(playerX);
            }
        }
    }

    public static void showLastWinners() {
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        while (n == 0) {
            System.out.print("Enter number of winners you want to see: ");
            n = scanner.nextInt();
            if (n <= 0) {
                System.out.println("Please enter a valid number!");
            }
        }
        System.out.printf("The top %d winners:", n);
        for (int i = 0; i < n; i++) {
            try {
                Player found = winners.pop();
                if (found != null) {
                    System.out.printf("Name: %s Age: %d\n", found.getName(), found.getAge());
                }
            } catch (Exception e) {}
        }
    }

    public static void showLastLosers() {
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        while (n == 0) {
            System.out.print("Enter number of losers you want to see: ");
            n = scanner.nextInt();
            if (n <= 0) {
                System.out.println("Please enter a valid number!");
            }
        }
        System.out.printf("The top %d losers:", n);
        for (int i = 0; i < n; i++) {
            try {
                Player found = losers.pop();
                if (found != null) {
                    System.out.printf("Name: %s Age: %d\n", found.getName(), found.getAge());
                }
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            displayMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addNewPlayer();
                    break;
                case 2:
                    findAndAddPlayersToQueue();
                    break;
                case 3:
                    startNewGame();
                    break;
                case 4:
                    showLastWinners();
                    break;
                case 5:
                    showLastLosers();
                    break;
                default:
                    System.out.println("You entered an invalid option. Please try again.");
                    break;
            }
        }
    }
}
