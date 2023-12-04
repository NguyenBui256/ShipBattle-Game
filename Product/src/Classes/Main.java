package Classes;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        Starting start = new Starting();
        Scanner sc = new Scanner(System.in);
        Board Player1 = null, Player2 = null;
        start.startingActions(Player1, Player2);
    }
}