package Classes;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Ingame {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public Ingame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    }

    public void playWithPlayer(Board Player1, Board Player2, Settings settings, Scanner sc) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        if(settings.musicOn) settings.ingameMusic.loop(Clip.LOOP_CONTINUOUSLY);
        else settings.ingameMusic.stop();
        while(true)
        {
            if(Player1.playerTurn == 1) System.out.println("It's " + ANSI_RED + Player1.playerName + ANSI_RESET + " turn!");
            else System.out.println("It's " + ANSI_YELLOW + Player2.playerName + ANSI_RESET + " turn!");
            System.out.println("1.Review your board");
            System.out.println("2.Attack opponent");
            System.out.println("3.Save and exit to menu");
            String option = sc.nextLine();
            settings.choosingSound.setMicrosecondPosition(0);
            settings.choosingSound.stop();
            settings.choosingSound.start();
            switch (option){
                case "1":
                    if(Player1.playerTurn == 1) Player1.show();
                    else Player2.show();
                    break;
                case "2":
                    if(Player1.playerTurn == 1)
                    {
                        Player2.showForOpponent();
                        System.out.println("Your remaining ships: " + Player1.shipRemaining);
                        System.out.println();
                    }
                    else
                    {
                        Player1.showForOpponent();
                        System.out.println("Your remaining ships: " + Player2.shipRemaining);
                        System.out.println();
                    }

                    if(Player1.playerTurn == 1) attack(Player1, Player2, settings, sc);
                    else attack(Player2, Player1, settings, sc);

                    break;
                case "3":
                    Player1.saveLoad.save("data1.dat");
                    Player2.saveLoad.save("data2.dat");
                    System.out.println(ANSI_YELLOW + "GAME SAVED!!" + ANSI_RESET);
                    System.out.println("Press enter to continue:");
                    sc.nextLine();
                    for(int i = 0; i < 50; i++) System.out.println();
                    settings.ingameMusic.stop();
                    return;
                default:
                    System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
                    break;
            }
          
            if(Player1.shipRemaining == 0 || Player2.shipRemaining == 0) //Điều kiện kết thúc trò chơi + cập nhật bảng xếp hạng
            {
                settings.ingameMusic.stop();
                if(settings.musicOn)
                {
                    settings.endSound.setMicrosecondPosition(0);
                    settings.endSound.start();
                }
                try {
                    File file = new File("src/Classes/leaderBoard.txt");
                    if (!file.exists()) file.createNewFile();
                    PrintWriter pw = new PrintWriter(file, StandardCharsets.UTF_8);
                    if (Player1.shipRemaining == 0) {
                        System.out.println(ANSI_YELLOW + Player2.playerName + ANSI_RESET + " won the game!!");
                        pw.println(Player2.playerName);
                        pw.println(Player2.destroyedSquare);
                        pw.println(Player2.shipRemaining);
                        pw.close();
                    } else {
                        System.out.println(ANSI_RED + Player1.playerName + ANSI_RESET + ANSI_CYAN + " won the game!!" + ANSI_RESET);
                        pw.println(Player1.playerName);
                        pw.println(Player1.destroyedSquare);
                        pw.println(Player1.shipRemaining);
                        pw.close();
                    }
                }catch (IOException e){
                    System.out.println("Exeption occurred: ");
                    e.printStackTrace();
                }

                System.out.println("Press enter to show " + ANSI_RED + Player1.playerName + ANSI_RESET + " board:");
                sc.nextLine();
                settings.choosingSound.setMicrosecondPosition(0);
                settings.choosingSound.stop();
                settings.choosingSound.start();
                Player1.showFinalResult();

                System.out.println("Press enter to show " + ANSI_YELLOW + Player2.playerName + ANSI_RESET + " board:");
                sc.nextLine();
                settings.choosingSound.setMicrosecondPosition(0);
                settings.choosingSound.stop();
                settings.choosingSound.start();
                Player2.showFinalResult();

                System.out.println("Press enter to continue");
                sc.nextLine();
                settings.choosingSound.setMicrosecondPosition(0);
                settings.choosingSound.stop();
                settings.choosingSound.start();
                for(int i = 0; i < 50; i++) System.out.println();
                settings.endSound.stop();
                return;
            }
        }
    }

    public void playWithBot(Board Player1, Board Player2, Settings settings, Scanner sc) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {

        if(settings.musicOn) settings.ingameMusic.loop(Clip.LOOP_CONTINUOUSLY);
        else settings.ingameMusic.stop();
        while(true)
        {
            if(Player1.playerTurn == 1)
            {
                System.out.println("It's " + ANSI_RED + Player1.playerName + ANSI_RESET + " turn!");
                System.out.println("1.Review your board");
                System.out.println("2.Attack opponent");
                System.out.println("3.Save and exit to menu");
                String option = sc.nextLine();
                settings.choosingSound.setMicrosecondPosition(0);
                settings.choosingSound.start();
                switch (option){
                    case "1":
                        Player1.show();
                        break;
                    case "2":
                        Player2.showForOpponent();
                        System.out.println("Your remaining ships: " + Player1.shipRemaining);
                        System.out.println();
                        attack(Player1, Player2, settings, sc);

                        break;
                    case "3":
                        Player1.saveLoad.save("data1.dat");
                        Player2.saveLoad.save("data2.dat");
                        System.out.println(ANSI_YELLOW + "GAME SAVED!!" + ANSI_RESET);
                        System.out.println("Press enter to continue:");
                        sc.nextLine();
                        for(int i = 0; i < 50; i++) System.out.println();
                        settings.ingameMusic.stop();
                        return;
                    default:
                        System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
                        break;
                }
            }
            else
            {
                System.out.print("It's " + ANSI_YELLOW + Player2.playerName + ANSI_RESET + " turn! ");
                String message = "Engine is deciding...\n";
                char[] chars = message.toCharArray();
                for(int i = 0; i < chars.length; i++)
                {
                    System.out.print(ANSI_YELLOW + chars[i] + ANSI_RESET);
                    Thread.sleep(50);
                }
                botAttack(Player2, Player1, settings, sc);
            }


            if(Player1.shipRemaining == 0 || Player2.shipRemaining == 0) //Điều kiện kết thúc trò chơi + cập nhật bảng xếp hạng
            {
                settings.ingameMusic.stop();
                if(settings.musicOn)
                {
                    settings.endSound.setMicrosecondPosition(0);
                    settings.endSound.start();
                }
                try {
                    File file = new File("src/Classes/leaderBoard.txt");
                    if (!file.exists()) file.createNewFile();
                    PrintWriter pw = new PrintWriter(file, StandardCharsets.UTF_8);
                    if (Player1.shipRemaining == 0) {
                        System.out.println(ANSI_YELLOW + Player2.playerName + ANSI_RESET + " won the game!!");
                    } else {
                        System.out.println(ANSI_RED + Player1.playerName + ANSI_RESET + ANSI_CYAN + " won the game!!" + ANSI_RESET);
                        pw.println(Player1.playerName);
                        pw.println(Player1.destroyedSquare);
                        pw.println(Player1.shipRemaining);
                        pw.close();
                    }
                }catch (IOException e){
                    System.out.println("Exeption occurred: ");
                    e.printStackTrace();
                }

                System.out.println("Press enter to show " + ANSI_RED + Player1.playerName + ANSI_RESET + " board:");
                sc.nextLine();
                settings.choosingSound.setMicrosecondPosition(0);
                settings.choosingSound.start();
                Player1.showFinalResult();

                System.out.println("Press enter to show " + ANSI_YELLOW + Player2.playerName + ANSI_RESET + " board:");
                sc.nextLine();
                settings.choosingSound.setMicrosecondPosition(0);
                settings.choosingSound.start();
                Player2.showFinalResult();

                System.out.println("Press enter to continue");
                sc.nextLine();
                settings.choosingSound.setMicrosecondPosition(0);
                settings.choosingSound.start();
                for(int i = 0; i < 50; i++) System.out.println();
                settings.endSound.stop();
                return;
            }
        }
    }

    public void attack(Board Player1, Board Player2, Settings settings, Scanner sc) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        int colnum = 0, row = 0;
        while(true) //Kiểm tra tọa độ nhập vào có hợp lệ
        {
            boolean checkLocationSyntax = true;
            System.out.println(ANSI_YELLOW + "Enter location: " + ANSI_RESET);

            String location = sc.nextLine();
            settings.choosingSound.setMicrosecondPosition(0);
            settings.choosingSound.stop();
            settings.choosingSound.start();

            location = location.toUpperCase();
            location = location.trim();
            if(location.length() < 2 || location.length() > 3)
            {
                System.out.println(ANSI_RED + "Invalid location, try another!" + ANSI_RESET);
                checkLocationSyntax = false;
                continue;
            }
            row = location.charAt(0) - 'A' + 1;
            StringBuilder col = new StringBuilder();
            for (int i = 1; i < location.length(); i++) {
                col.append(location.charAt(i));
                if (!(location.charAt(i) >= '0' && location.charAt(i) <= '9')) checkLocationSyntax = false;
            }
            if(checkLocationSyntax)
            {
                colnum = Integer.parseInt(col.toString());
                if(!(row >= 1 && row <= Player2.boardSize && colnum >= 1 && colnum <= Player2.boardSize))
                {
                    System.out.println(ANSI_RED + "Out of bound, try another!!" + ANSI_RESET);
                    continue;
                }
            }
            else
            {
                System.out.println(ANSI_RED + "Out of bound, try another!!" + ANSI_RESET);
                continue;
            }

            //Kiểm tra trùng ô đã tấn công
            if(Player2.boardForOpponent[row][colnum] == 'o') break;
            else System.out.println(ANSI_RED + "ALREADY DESTROYED, TRY ANOTHER!!" + ANSI_RESET);
        }

        if(Player2.board[row][colnum] != 'o') //Bắn trúng đích
        {
            System.out.println(ANSI_GREEN + "TARGET DESTROYED!!" + ANSI_RESET);
            settings.hitSound.setMicrosecondPosition(0);
            settings.hitSound.start();
            boolean checkShipAlive = false;
            int numberOfSquare = 0;
            if(Player2.board[row][colnum] == 'P') numberOfSquare = 2;
            else if(Player2.board[row][colnum] == 'D') numberOfSquare = 4;
            else if(Player2.board[row][colnum] == 'S') numberOfSquare = 3;
            else if(Player2.board[row][colnum] == 'B') numberOfSquare = 5;
            for(int index = 1; index < numberOfSquare; index++)
            {
                if((row + index <= Player2.boardSize && Player2.board[row+index][colnum] == Player2.board[row][colnum] && Player2.boardForOpponent[row+index][colnum] == 'o')
                || (row - index >= 1 && Player2.board[row-index][colnum] == Player2.board[row][colnum] && Player2.boardForOpponent[row-index][colnum] == 'o')
                || (colnum + index <= Player2.boardSize && Player2.board[row][colnum + index] == Player2.board[row][colnum] && Player2.boardForOpponent[row][colnum+index] == 'o')
                || (colnum - index >= 1 && Player2.board[row][colnum - index] == Player2.board[row][colnum] && Player2.boardForOpponent[row][colnum-index] == 'o'))
                {
                    checkShipAlive = true;
                    break;
                }
            }
            if(!checkShipAlive)
            {
                Player2.shipRemaining -= 1;
                switch (Player2.shipRemaining){
                    case 4:
                        settings.firstBlood.setMicrosecondPosition(0);
                        settings.firstBlood.start();
                        break;
                    case 3:
                        settings.doubleKill.setMicrosecondPosition(0);
                        settings.doubleKill.start();
                        break;
                    case 2:
                        settings.tripleKill.setMicrosecondPosition(0);
                        settings.tripleKill.start();
                        break;
                    case 1:
                        settings.quadraKill.setMicrosecondPosition(0);
                        settings.quadraKill.start();
                        break;
                    case 0:
                        settings.pentaKill.setMicrosecondPosition(0);
                        settings.pentaKill.start();
                        break;
                }
                System.out.println(ANSI_CYAN + "ENEMY'S SHIP SUNK!!" + ANSI_RESET);
            }
            Player2.boardForOpponent[row][colnum] = 'x';
            Player1.destroyedSquare += 1;
        }
        else //Bắn trượt
        {
            System.out.println(ANSI_RED + "TARGET MISSED!!" + ANSI_RESET);
            settings.missSound.setMicrosecondPosition(0);
            settings.missSound.start();
            Player2.boardForOpponent[row][colnum] = 'm';
            Player1.destroyedSquare += 1;
            //Đổi lượt
            Player1.playerTurn = 1 - Player1.playerTurn;
            Player2.playerTurn = 1 - Player1.playerTurn;
        }
        System.out.println("Press any key to continue:");
        sc.nextLine();
        for(int i = 0; i < 50; i++) System.out.println();
    }

    public void botAttack(Board Player1, Board Player2, Settings settings, Scanner sc) throws UnsupportedAudioFileException, IOException, LineUnavailableException {


        Random generator = new Random();
        int row = 0, colnum = 0;
        boolean prioritizedLocation = false;
        for(int i = 1; i <= Player2.boardSize; i++)
        {
            for(int j = 1; j <= Player2.boardSize; j++)
            {
                if(Player2.boardForBot[i][j] == 1 && Player2.boardForOpponent[i][j] == 'o' && (Player2.boardForOpponent[i-1][j] == 'x' || Player2.boardForOpponent[i][j-1] == 'x'
                        || Player2.boardForOpponent[i+1][j] == 'x' || Player2.boardForOpponent[i][j+1] == 'x'))
                {
                    prioritizedLocation = true;
                    row = i;
                    colnum = j;
                    break;
                }
            }
            if(prioritizedLocation) break;
        }
        if(!prioritizedLocation) //Nếu không có ô ưu tiên thì sẽ random
        {
            while(true)
            {
                row = generator.nextInt(Player2.boardSize) + 1;
                colnum = generator.nextInt(Player2.boardSize) + 1;
                //Kiểm tra trùng ô đã tấn công
                if(Player2.boardForOpponent[row][colnum] == 'o' && Player2.boardForBot[row][colnum] != 2) break;
            }
        }

        System.out.print(String.format("Engine fired at " + ANSI_RED + "%c%d\n", 'A' + row - 1, colnum) + ANSI_RESET);

        if(Player2.board[row][colnum] != 'o') //Bắn trúng đích
        {
            Player2.boardForBot[row][colnum] = 2;
            if(!prioritizedLocation)
            {
                for(int i = Math.max(1, row - 4); i < row; i++) if(Player2.boardForOpponent[i][colnum] == 'o' && Player2.boardForBot[i][colnum] != 2) Player2.boardForBot[i][colnum] = 1;
                for(int i = Math.max(1, colnum - 4); i < colnum; i++) if(Player2.boardForOpponent[row][i] == 'o' && Player2.boardForBot[row][i] != 2) Player2.boardForBot[row][i] = 1;
                for(int i = colnum + 1; i <= Math.min(colnum + 4, Player2.boardSize); i++) if(Player2.boardForOpponent[row][i] == 'o' && Player2.boardForBot[row][i] != 2) Player2.boardForBot[row][i] = 1;
                for(int i = row + 1; i <= Math.min(row + 4, Player2.boardSize); i++) if(Player2.boardForOpponent[i][colnum] == 'o' && Player2.boardForBot[i][colnum] != 2) Player2.boardForBot[i][colnum] = 1;
            }
            else
            {
                if(Player2.boardForOpponent[row-1][colnum] == 'x' ) //Up
                {
                    for(int i =  Math.max(1, colnum - 4); i < Math.min(Player2.boardSize, colnum + 5); i++)
                    {
                        if(i != colnum && Player2.boardForBot[row-1][i] == 1) Player2.boardForBot[row-1][i] = 0;
                    }
                }else if(Player2.boardForOpponent[row+1][colnum] == 'x') //Down
                {
                    for(int i =  Math.max(1, colnum - 5); i < Math.min(Player2.boardSize, colnum + 5); i++)
                    {
                        if(i != colnum && Player2.boardForBot[row+1][i] == 1) Player2.boardForBot[row+1][i] = 0;
                    }
                }else if(Player2.boardForOpponent[row][colnum-1] == 'x') //Left
                {
                    for(int i =  Math.max(1, row - 5); i < Math.min(Player2.boardSize, row + 5); i++)
                    {
                        if(i != row && Player2.boardForBot[i][colnum-1] == 1) Player2.boardForBot[i][colnum-1] = 0;
                    }
                }else if(Player2.boardForOpponent[row][colnum+1] == 'x') //Right
                {
                    for(int i =  Math.max(1, row - 5); i < Math.min(Player2.boardSize, row + 5); i++)
                    {
                        if(i != row && Player2.boardForBot[i][colnum+1] == 1) Player2.boardForBot[i][colnum+1] = 0;
                    }
                }
            }
            System.out.println(ANSI_GREEN + "TARGET DESTROYED!!" + ANSI_RESET);
            settings.hitSound.setMicrosecondPosition(0);
            settings.hitSound.start();
            boolean checkShipAlive = false;
            int numberOfSquare = 0;
            if(Player2.board[row][colnum] == 'P') numberOfSquare = 2;
            else if(Player2.board[row][colnum] == 'D') numberOfSquare = 4;
            else if(Player2.board[row][colnum] == 'S') numberOfSquare = 3;
            else if(Player2.board[row][colnum] == 'B') numberOfSquare = 5;
            for(int index = 1; index < numberOfSquare; index++)
            {
                if((row + index <= Player2.boardSize && Player2.board[row+index][colnum] == Player2.board[row][colnum] && Player2.boardForOpponent[row+index][colnum] == 'o')
                        || (row - index >= 1 && Player2.board[row-index][colnum] == Player2.board[row][colnum] && Player2.boardForOpponent[row-index][colnum] == 'o')
                        || (colnum + index <= Player2.boardSize && Player2.board[row][colnum + index] == Player2.board[row][colnum] && Player2.boardForOpponent[row][colnum+index] == 'o')
                        || (colnum - index >= 1 && Player2.board[row][colnum - index] == Player2.board[row][colnum] && Player2.boardForOpponent[row][colnum-index] == 'o'))
                {
                    checkShipAlive = true;
                    break;
                }
            }

            if(!checkShipAlive)
            {
                if(Player2.boardForOpponent[row-1][colnum] == 'x' ) //Up
                {
                    for(int i = Math.min(Player2.boardSize,row + 2); i <= Math.min(row + 4, Player2.boardSize); i++) if(Player2.boardForBot[i][colnum] == 1) Player2.boardForBot[i][colnum] = 0;
                    for(int i =  Math.max(1, row - numberOfSquare); i <= row; i++)
                    {
                        for(int j = -1; j <= 1; j++) //Marking negative squares
                        {
                            Player2.boardForBot[i][colnum + j] = 2;
                            Player2.boardForBot[i-1][colnum + j] = 2;
                            Player2.boardForBot[i+1][colnum + j] = 2;
                        }
                    }
                }else if(Player2.boardForOpponent[row+1][colnum] == 'x') //Down
                {
                    for(int i = Math.max(1, row - 4); i < row - 1; i++) if(Player2.boardForBot[i][colnum] == 1) Player2.boardForBot[i][colnum] = 0;
                    for(int i =  row; i <= Math.min(Player2.boardSize, row + numberOfSquare) ; i++)
                    {
                        for(int j = -1; j <= 1; j++) //Marking negative squares
                        {
                            Player2.boardForBot[i][colnum + j] = 2;
                            Player2.boardForBot[i-1][colnum + j] = 2;
                            Player2.boardForBot[i+1][colnum + j] = 2;
                        }
                    }
                }else if(Player2.boardForOpponent[row][colnum-1] == 'x') //Left
                {
                    for(int i = Math.min(Player2.boardSize,colnum + 2); i <= Math.min(colnum + 4, Player2.boardSize); i++) if(Player2.boardForBot[row][i] == 1) Player2.boardForBot[row][i] = 0;
                    for(int i =  Math.max(1, colnum - numberOfSquare); i <= colnum; i++)
                    {
                        for(int j = -1; j <= 1; j++) //Marking negative squares
                        {
                            Player2.boardForBot[row + j][i] = 2;
                            Player2.boardForBot[row + j][i-1] = 2;
                            Player2.boardForBot[row + j][i+1] = 2;
                        }
                    }
                }else if(Player2.boardForOpponent[row][colnum+1] == 'x') //Right
                {
                    for(int i = Math.max(1, colnum - 4); i < colnum - 1; i++) if(Player2.boardForBot[row][i] == 1) Player2.boardForBot[row][i] = 0;
                    for(int i =  colnum; i <= Math.min(Player2.boardSize, colnum + numberOfSquare); i++)
                    {
                        for(int j = -1; j <= 1; j++) //Marking negative squares
                        {
                            Player2.boardForBot[row + j][i] = 2;
                            Player2.boardForBot[row + j][i-1] = 2;
                            Player2.boardForBot[row + j][i+1] = 2;
                        }
                    }
                }

                Player2.shipRemaining -= 1;
                switch (Player2.shipRemaining){
                    case 4:
                        settings.firstBlood.setMicrosecondPosition(0);
                        settings.firstBlood.start();
                        break;
                    case 3:
                        settings.doubleKill.setMicrosecondPosition(0);
                        settings.doubleKill.start();
                        break;
                    case 2:
                        settings.tripleKill.setMicrosecondPosition(0);
                        settings.tripleKill.start();
                        break;
                    case 1:
                        settings.quadraKill.setMicrosecondPosition(0);
                        settings.quadraKill.start();
                        break;
                    case 0:
                        settings.pentaKill.setMicrosecondPosition(0);
                        settings.pentaKill.start();
                        break;
                }
                System.out.println(ANSI_CYAN + "ENEMY'S SHIP SUNK!!" + ANSI_RESET);
            }
            Player2.boardForOpponent[row][colnum] = 'x';
            Player1.destroyedSquare += 1;
        }
        else //Bắn trượt
        {
            System.out.println(ANSI_RED + "TARGET MISSED!!" + ANSI_RESET);
            Player2.boardForBot[row][colnum] = 2;
            settings.missSound.setMicrosecondPosition(0);
            settings.missSound.start();
            Player2.boardForOpponent[row][colnum] = 'm';
            Player1.destroyedSquare += 1;

            //Đổi lượt
            Player1.playerTurn = 1 - Player1.playerTurn;
            Player2.playerTurn = 1 - Player1.playerTurn;
        }
        System.out.println("Press enter to continue:");
        sc.nextLine();
        for(int i = 0; i < 50; i++) System.out.println();
    }

}
