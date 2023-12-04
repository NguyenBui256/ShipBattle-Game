package Classes;

import Data.saveLoadFunction;

import java.io.Serializable;

public class Board implements Serializable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLUE = "\033[0;34m";

    public int boardSize = 0;

    public char[][] board, boardForOpponent, boardForBot;

    //boardForBot has 3 states:
    // 0: stand for unknown square - can be attack.
    // 1: stand for suspicious square - bot will prioritize attacking this square.
    // 2: stand for negative square - bot will identify this square has no enemy's ship.

    public String playerName;

    public int shipRemaining = 5, destroyedSquare = 0, playerTurn = 0;

    saveLoadFunction saveLoad = new saveLoadFunction(this);

    public Board(int boardSize)
    {
        this.boardSize = boardSize;
        board = new char[boardSize + 2][boardSize + 2];
        boardForOpponent = new char[boardSize + 2][boardSize + 2];
        boardForBot = new char[boardSize + 2][boardSize + 2];

        for(int i = 0; i <= boardSize + 1; i++)
        {
            for(int j = 0; j <= boardSize + 1; j++)
            {
                board[i][j] = 'o';
                boardForOpponent[i][j] = 'o';
                boardForBot[i][j] = 0;
            }
        }
    }

    public void show()
    {

        System.out.print(ANSI_YELLOW + "P:Patrol Boat  " + ANSI_RESET);
        System.out.print(ANSI_CYAN + "D:Destroyer Boat  " + ANSI_RESET);
        System.out.print(ANSI_GREEN + "S:Submarine  " + ANSI_RESET);
        System.out.print(ANSI_RED + "B:Battle Ship  " + ANSI_RESET);
        System.out.print(ANSI_PURPLE + "x / o : Destroyed / Enemy's Missed" + ANSI_RESET);
        System.out.println();

        if(boardSize <= 10)
        {
            System.out.print("  ");
            for(int i = 1; i <= boardSize; i++) System.out.print(ANSI_BLUE + (i + " ") + ANSI_RESET);
            System.out.println();
            char index = 'A';
            for(int i = 1; i <= boardSize; i++)
            {
                System.out.print(ANSI_BLUE + (index++ + " ") + ANSI_RESET);
                for(int j = 1; j <= boardSize; j++)
                {
                    if(boardForOpponent[i][j] == 'x') System.out.print(ANSI_PURPLE + 'x' + ANSI_RESET + " ");
                    else if(boardForOpponent[i][j] == 'm') System.out.print(ANSI_PURPLE + 'o' + " " + ANSI_RESET);
                    else
                    {
                        if(board[i][j] == 'P') System.out.print(ANSI_YELLOW + board[i][j] + ANSI_RESET + " ");
                        else if(board[i][j] == 'D') System.out.print(ANSI_CYAN + board[i][j] + ANSI_RESET + " ");
                        else if(board[i][j] == 'S') System.out.print(ANSI_GREEN + board[i][j] + ANSI_RESET + " ");
                        else if(board[i][j] == 'B') System.out.print(ANSI_RED + board[i][j] + ANSI_RESET + " ");
                        else System.out.print(board[i][j] + " ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        else
        {
            System.out.print("   ");
            for(int i = 1; i <= 9; i++) System.out.print(ANSI_BLUE + (i + "  ") + ANSI_RESET);
            for(int i = 10; i <= boardSize; i++) System.out.print(ANSI_BLUE + (i + " ") + ANSI_RESET);
            System.out.println();
            char index = 'A';
            for(int i = 1; i <= boardSize; i++)
            {
                System.out.print(ANSI_BLUE + (index++ + "  ") + ANSI_RESET);
                for(int j = 1; j <= boardSize; j++)
                {
                    if(boardForOpponent[i][j] == 'x') System.out.print(ANSI_PURPLE + 'x' + ANSI_RESET + "  ");
                    else if(boardForOpponent[i][j] == 'm') System.out.print(ANSI_PURPLE + 'o' + "  " + ANSI_RESET);
                    else
                    {
                        if(board[i][j] == 'P') System.out.print(ANSI_YELLOW + board[i][j] + ANSI_RESET + "  ");
                        else if(board[i][j] == 'D') System.out.print(ANSI_CYAN + board[i][j] + ANSI_RESET + "  ");
                        else if(board[i][j] == 'S') System.out.print(ANSI_GREEN + board[i][j] + ANSI_RESET + "  ");
                        else if(board[i][j] == 'B') System.out.print(ANSI_RED + board[i][j] + ANSI_RESET + "  ");
                        else System.out.print(board[i][j] + "  ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }

    }

    public void showFinalResult()
    {

        System.out.print(ANSI_YELLOW + "P:Patrol Boat  " + ANSI_RESET);
        System.out.print(ANSI_CYAN + "D:Destroyer Boat  " + ANSI_RESET);
        System.out.print(ANSI_GREEN + "S:Submarine  " + ANSI_RESET);
        System.out.print(ANSI_RED + "B:Battle Ship  " + ANSI_RESET);
        System.out.print(ANSI_PURPLE + "x / o : Destroyed / Enemy's Missed" + ANSI_RESET);
        System.out.println();

        if(boardSize <= 10)
        {
            System.out.print("  ");
            for(int i = 1; i <= boardSize; i++) System.out.print(ANSI_BLUE + (i + " ") + ANSI_RESET);
            System.out.println();
            char index = 'A';
            for(int i = 1; i <= boardSize; i++)
            {
                System.out.print(ANSI_BLUE + (index++ + " ") + ANSI_RESET);
                for(int j = 1; j <= boardSize; j++)
                {
                    if(boardForOpponent[i][j] == 'x')
                    {
                        if(board[i][j] == 'P') System.out.print(ANSI_YELLOW + 'x' + ANSI_RESET + " ");
                        else if(board[i][j] == 'D') System.out.print(ANSI_CYAN + 'x' + ANSI_RESET + " ");
                        else if(board[i][j] == 'S') System.out.print(ANSI_GREEN + 'x' + ANSI_RESET + " ");
                        else if(board[i][j] == 'B') System.out.print(ANSI_RED + 'x' + ANSI_RESET + " ");
                        else System.out.print(board[i][j] + " ");
                    }
                    else if(boardForOpponent[i][j] == 'm') System.out.print(ANSI_PURPLE + 'o' + " " + ANSI_RESET);
                    else
                    {
                        if(board[i][j] == 'P') System.out.print(ANSI_YELLOW + board[i][j] + ANSI_RESET + " ");
                        else if(board[i][j] == 'D') System.out.print(ANSI_CYAN + board[i][j] + ANSI_RESET + " ");
                        else if(board[i][j] == 'S') System.out.print(ANSI_GREEN + board[i][j] + ANSI_RESET + " ");
                        else if(board[i][j] == 'B') System.out.print(ANSI_RED + board[i][j] + ANSI_RESET + " ");
                        else System.out.print(board[i][j] + " ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        else
        {
            System.out.print("   ");
            for(int i = 1; i <= 9; i++) System.out.print(ANSI_BLUE + (i + "  ") + ANSI_RESET);
            for(int i = 10; i <= boardSize; i++) System.out.print(ANSI_BLUE + (i + " ") + ANSI_RESET);
            System.out.println();
            char index = 'A';
            for(int i = 1; i <= boardSize; i++)
            {
                System.out.print(ANSI_BLUE + (index++ + "  ") + ANSI_RESET);
                for(int j = 1; j <= boardSize; j++)
                {
                    if(boardForOpponent[i][j] == 'x')
                    {
                        if(board[i][j] == 'P') System.out.print(ANSI_YELLOW + 'x' + ANSI_RESET + "  ");
                        else if(board[i][j] == 'D') System.out.print(ANSI_CYAN + 'x' + ANSI_RESET + "  ");
                        else if(board[i][j] == 'S') System.out.print(ANSI_GREEN + 'x' + ANSI_RESET + "  ");
                        else if(board[i][j] == 'B') System.out.print(ANSI_RED + 'x' + ANSI_RESET + "  ");
                        else System.out.print(board[i][j] + " ");
                    }
                    else if(boardForOpponent[i][j] == 'm') System.out.print(ANSI_PURPLE + 'o' + "  " + ANSI_RESET);
                    else
                    {
                        if(board[i][j] == 'P') System.out.print(ANSI_YELLOW + board[i][j] + ANSI_RESET + "  ");
                        else if(board[i][j] == 'D') System.out.print(ANSI_CYAN + board[i][j] + ANSI_RESET + "  ");
                        else if(board[i][j] == 'S') System.out.print(ANSI_GREEN + board[i][j] + ANSI_RESET + "  ");
                        else if(board[i][j] == 'B') System.out.print(ANSI_RED + board[i][j] + ANSI_RESET + "  ");
                        else System.out.print(board[i][j] + "  ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }

    }

    public void showForOpponent()
    {

        System.out.print("Unknown  ");
        System.out.print(ANSI_PURPLE + "Destroyed   " + ANSI_RESET);
        System.out.print(ANSI_RED + "Missed" + ANSI_RESET);
        System.out.println();
        if(boardSize <= 10)
        {
            System.out.print("  ");
            for(int i = 1; i <= boardSize; i++) System.out.print(ANSI_BLUE + i + " " + ANSI_RESET);
            System.out.println();
            char index = 'A';
            for(int i = 1; i <= boardSize; i++)
            {
                System.out.print(ANSI_BLUE + index++ + " " + ANSI_RESET);
                for(int j = 1; j <= boardSize; j++)
                {
                    if(boardForOpponent[i][j] == 'x') System.out.print(ANSI_PURPLE + 'x' + ANSI_RESET + " ");
                    else if(boardForOpponent[i][j] == 'm') System.out.print(ANSI_RED + 'o' + ANSI_RESET + " ");
                    else System.out.print(boardForOpponent[i][j] + " ");
                }
                System.out.println();
            }
        }
        else
        {
            System.out.print("   ");
            for(int i = 1; i <= 9; i++) System.out.print(ANSI_BLUE + (i + "  ") + ANSI_RESET);
            for(int i = 10; i <= boardSize; i++) System.out.print(ANSI_BLUE + (i + " ") + ANSI_RESET);
            System.out.println();
            char index = 'A';
            for(int i = 1; i <= boardSize; i++)
            {
                System.out.print(ANSI_BLUE + (index++ + "  ") + ANSI_RESET);
                for(int j = 1; j <= boardSize; j++)
                {
                    if(boardForOpponent[i][j] == 'x') System.out.print(ANSI_PURPLE + 'x' + ANSI_RESET + "  ");
                    else if(boardForOpponent[i][j] == 'm') System.out.print(ANSI_RED + 'o' + ANSI_RESET + "  ");
                    else System.out.print(boardForOpponent[i][j] + "  ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("Destroyed Squares: " + destroyedSquare);
        System.out.println("Enemy's remaining ships: " + shipRemaining);
    }

}
