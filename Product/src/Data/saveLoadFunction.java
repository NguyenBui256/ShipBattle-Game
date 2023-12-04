package Data;

import Classes.Board;

import java.io.*;

public class saveLoadFunction {

    Board player;
    public saveLoadFunction(Board Player)
    {
        this.player = Player;
    }

    public void save(String fileName)
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));

            Board ds = new Board(player.boardSize);
            ds.board = player.board;
            ds.boardForOpponent = player.boardForOpponent;
            ds.playerName = player.playerName;
            ds.destroyedSquare = player.destroyedSquare;
            ds.shipRemaining = player.shipRemaining;
            ds.playerTurn = player.playerTurn;
            ds.boardForBot = player.boardForBot;

            oos.writeObject(ds);

        }
        catch(Exception ignored)
        {

        }
    }

    public void load(String fileName)
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)));
            Board ds = (Board)ois.readObject();

            player.board = ds.board;
            player.boardForOpponent = ds.boardForOpponent;
            player.playerName = ds.playerName;
            player.destroyedSquare = ds.destroyedSquare;
            player.shipRemaining = ds.shipRemaining;
            player.playerTurn = ds.playerTurn;
        }
        catch(Exception ignored)
        {

        }
    }
}
