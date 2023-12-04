package Classes;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Starting{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static final String ANSI_BLUE = "\033[0;34m";

    Settings settings = new Settings();

    public Starting() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    }

    public void startingActions(Board Player1, Board Player2) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        Scanner sc = new Scanner(System.in);

      FloatControl volumeControl = (FloatControl) settings.choosingSound.getControl(FloatControl.Type.MASTER_GAIN);
      volumeControl.setValue(6.0f);

        while(true)
        {
            if(settings.musicOn) settings.menuMusic.loop(Clip.LOOP_CONTINUOUSLY);

            System.out.print(ANSI_CYAN + "\n" +
                    " ________   ___  ___   ___   ________        ________   ________   _________   _________   ___        _______      \n" +
                    "|\\   ____\\ |\\  \\|\\  \\ |\\  \\ |\\   __  \\      |\\   __  \\ |\\   __  \\ |\\___   ___\\|\\___   ___\\|\\  \\      |\\  ___ \\     \n" +
                    "\\ \\  \\___|_\\ \\  \\\\\\  \\\\ \\  \\\\ \\  \\|\\  \\     \\ \\  \\|\\ /_\\ \\  \\|\\  \\\\|___ \\  \\_|\\|___ \\  \\_|\\ \\  \\     \\ \\   __/|    \n" +
                    " \\ \\_____  \\\\ \\   __  \\\\ \\  \\\\ \\   ____\\     \\ \\   __  \\\\ \\   __  \\    \\ \\  \\      \\ \\  \\  \\ \\  \\     \\ \\  \\_|/__  \n" +
                    "  \\|____|\\  \\\\ \\  \\ \\  \\\\ \\  \\\\ \\  \\___|      \\ \\  \\|\\  \\\\ \\  \\ \\  \\    \\ \\  \\      \\ \\  \\  \\ \\  \\____ \\ \\  \\_|\\ \\ \n" +
                    "    ____\\_\\  \\\\ \\__\\ \\__\\\\ \\__\\\\ \\__\\          \\ \\_______\\\\ \\__\\ \\__\\    \\ \\__\\      \\ \\__\\  \\ \\_______\\\\ \\_______\\\n" +
                    "   |\\_________\\\\|__|\\|__| \\|__| \\|__|           \\|_______| \\|__|\\|__|     \\|__|       \\|__|   \\|_______| \\|_______|\n" +
                    "                                                                                                  \n" +
                    "                                                                                                                   \n" + ANSI_RESET);

            System.out.println(ANSI_RED +"1.Play new game" + ANSI_RESET);
            System.out.println(ANSI_YELLOW +"2.Load game" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "3.Leaderboard" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "4.Rules" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "5.Setting" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "6.Exit" + ANSI_RESET);
            System.out.println("Enter your choice:");
            String option = sc.nextLine();
            settings.choosingSound.setMicrosecondPosition(0);
            settings.choosingSound.start();

            switch (option){
                case "1":
                    System.out.println("Enter board size (From 8 -> 20):");
                    int boardSize = Integer.parseInt(sc.nextLine());
                    Player1 = new Board(boardSize);
                    Player2 = new Board(boardSize);
                    Player1.playerTurn = 1;
                    Player2.playerTurn = 0;
                    play(Player1, Player2, settings, sc);
                    break;
                case "2":
                    settings.menuMusic.stop();
                    volumeControl = (FloatControl) settings.ingameMusic.getControl(FloatControl.Type.MASTER_GAIN);
                    volumeControl.setValue(-5.0f);
                    try
                    {
                        Player1.saveLoad.load("data1.dat");
                        Player2.saveLoad.load("data2.dat");
                    }
                    catch (NullPointerException e)
                    {
                        System.out.println(ANSI_RED + "NO PREVIOUS DATA!" + ANSI_RESET);
                        System.out.println("Press enter to continue:");
                        sc.nextLine();
                        break;
                    }

                    System.out.println();
                    String message = ANSI_CYAN + "LOADING GAME...\n\n" + ANSI_RESET;

                    char[] chars = message.toCharArray();

                    for(int i = 0; i < chars.length; i++)
                    {
                        System.out.print(chars[i]);
                        Thread.sleep(100);
                    }

                    Ingame ingame = new Ingame();

                    if(Player2.playerName.equals("Engine"))
                    {
                        ingame.playWithBot(Player1, Player2, settings, sc);
                        settings.ingameMusic.stop();
                    }
                    else
                    {
                        ingame.playWithPlayer(Player1, Player2, settings, sc);
                        settings.ingameMusic.stop();
                    }
                    break;
                case "3":
                    showLeaderBoard(sc, settings);
                    break;
                case "4":
                    showRule(sc, settings);
                    break;
                case "5":
                    System.out.println("1.Turn " + ANSI_GREEN + "ON" + ANSI_RESET + " background music");
                    System.out.println("2.Turn " + ANSI_RED + "OFF" + ANSI_RESET + " background music");
                    System.out.println("3.Return");

                    option = sc.nextLine();
                    if(option.equals("1")) {
                        System.out.println(ANSI_GREEN + "MUSIC TURNED ON !\n" + ANSI_RESET);
                        settings.musicOn = true;
                        settings.menuMusic.setMicrosecondPosition(0);
                        settings.menuMusic.start();
                    }
                    else if(option.equals("2"))
                    {
                        System.out.println(ANSI_RED + "MUSIC TURNED OFF !\n" + ANSI_RESET);
                        settings.menuMusic.stop();
                        settings.musicOn = false;
                    }
                    else if(option.equals("3")) break;
                    else System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
                    break;
                case "6":
                    System.out.println(ANSI_YELLOW + "Goodbye!!" + ANSI_RESET);
                    settings.menuMusic.stop();
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public void play(Board Player1, Board Player2, Settings settings, Scanner sc) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {

        settings.menuMusic.stop();
        if(settings.musicOn) settings.ingameMusic.loop(Clip.LOOP_CONTINUOUSLY);

        FloatControl volumeControl = (FloatControl) settings.ingameMusic.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(-5.0f);

        Ingame ingame = new Ingame();

        String option = null;
        while(true)
        {

            System.out.println(ANSI_YELLOW + "Playmode:" + ANSI_RESET);
            System.out.println("1." + ANSI_CYAN + " Player " + ANSI_RESET + "vs " + ANSI_CYAN + "Player" + ANSI_RESET);
            System.out.println("2." + ANSI_CYAN + " Player " + ANSI_RESET + "vs " + ANSI_RED + " Bot" + ANSI_RESET);
            System.out.println("3. Setting");
            System.out.println("Enter your choice:");
            option = sc.nextLine();
            if(option.equals("1") || option.equals("2") || option.equals("3")) break;
            else System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
        }
        switch(option)
        {
            case "1":
                while(true)
                {
                    boolean legitName = true;
                    System.out.println("Enter first player's name:");
                    Player1.playerName = sc.nextLine();
                    settings.choosingSound.setMicrosecondPosition(0);
                    settings.choosingSound.start();

                    String check = Player1.playerName.toLowerCase().trim();
                    if(check.equals("engine")) legitName = false;

                    if(!legitName)
                    {
                        System.out.println(ANSI_RED + "Invalid name, try another" + ANSI_RESET);
                        continue;
                    }

                    System.out.println("Enter second player's name:");
                    Player2.playerName = sc.nextLine();
                    settings.choosingSound.setMicrosecondPosition(0);
                    settings.choosingSound.start();
                    check = Player2.playerName.toLowerCase().trim();
                    if(check.equals("engine")) legitName = false;


                    if(legitName) break;
                    else System.out.println(ANSI_RED + "Invalid name, try another" + ANSI_RESET);
                }

                System.out.println(ANSI_RED + Player1.playerName + ANSI_RESET + " ships placement:");
                Player1.show();
                System.out.println(ANSI_YELLOW + "Placing options:" + ANSI_RESET);
                System.out.println(ANSI_RED + "1.Autofill" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "2.Customize" + ANSI_RESET);
                System.out.println("Enter your choice:");
                switch(sc.nextLine())
                {
                    case "1":
                        settings.choosingSound.setMicrosecondPosition(0);
                        settings.choosingSound.start();
                        autoPlacement(sc, Player1);
                        Player1.show();
                        System.out.println("Press Enter to continue: (Clear screen)");
                        sc.nextLine();
                        for (int i = 0; i < 50; ++i) System.out.println();
                        break;
                    case "2":
                        settings.choosingSound.setMicrosecondPosition(0);
                        settings.choosingSound.start();
                        shipPlacement(sc,Player1,settings);
                        break;
                }

                System.out.println(ANSI_YELLOW + Player2.playerName + ANSI_RESET+ " ships placement:");
                Player2.show();
                System.out.println(ANSI_YELLOW + "Placing options:" + ANSI_RESET);
                System.out.println(ANSI_RED + "1.Autofill" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "2.Customize" + ANSI_RESET);
                System.out.println("Enter your choice:");
                switch(sc.nextLine())
                {
                    case "1":
                        settings.choosingSound.setMicrosecondPosition(0);
                        settings.choosingSound.start();
                        autoPlacement(sc, Player2);
                        Player2.show();
                        System.out.println("Press Enter to continue: (Clear screen)");
                        sc.nextLine();
                        for (int i = 0; i < 50; ++i) System.out.println();
                        break;
                    case "2":
                        settings.choosingSound.setMicrosecondPosition(0);
                        settings.choosingSound.start();
                        shipPlacement(sc,Player2, settings);
                        break;
                }
                ingame.playWithPlayer(Player1, Player2, settings ,sc);
                break;
            case "2":
                while(true)
                {
                    System.out.println("Enter player's name:");
                    Player1.playerName = sc.nextLine();
                    settings.choosingSound.setMicrosecondPosition(0);
                    settings.choosingSound.start();

                    if(Player1.playerName.toLowerCase().trim().equals("engine")) System.out.println(ANSI_RED + "Invalid name, try another" + ANSI_RESET);
                    else break;
                }
                settings.choosingSound.setMicrosecondPosition(0);
                settings.choosingSound.start();
                System.out.println(ANSI_RED + Player1.playerName + ANSI_RESET + " ships placement:");
                Player1.show();
                System.out.println("Fill options:");
                System.out.println("1.Autofill");
                System.out.println("2.Manual");
                switch(sc.nextLine())
                {
                    case "1":
                        settings.choosingSound.setMicrosecondPosition(0);
                        settings.choosingSound.start();
                        autoPlacement(sc, Player1);
                        Player1.show();
                        System.out.println("Press Enter to continue: (Clear screen)");
                        sc.nextLine();
                        for (int i = 0; i < 50; ++i) System.out.println();
                        break;
                    case "2":
                        settings.choosingSound.setMicrosecondPosition(0);
                        settings.choosingSound.start();
                        shipPlacement(sc,Player1,settings);
                        break;
                }
                Player2.playerName = "Engine";
                autoPlacement(sc, Player2);
                Player1.playerTurn = 1;
                Player2.playerTurn = 0;
                ingame.playWithBot(Player1, Player2, settings, sc);
                break;
            case "3":
                while(true)
                {
                    System.out.println("1.Turn " + ANSI_GREEN + "ON" + ANSI_RESET + " background music.");
                    System.out.println("2.Turn " + ANSI_RED + "OFF" + ANSI_RESET + " background music.");
                    System.out.println("3.Return.");
                    System.out.println("Enter your choice:");
                    option = sc.nextLine();
                    if(option.equals("1")) {
                        System.out.println(ANSI_GREEN + "MUSIC TURNED ON !\n" + ANSI_RESET);
                        settings.musicOn = true;
                        settings.ingameMusic.setMicrosecondPosition(0);
                        settings.ingameMusic.start();
                    }
                    else if(option.equals("2"))
                    {
                        System.out.println(ANSI_RED + "MUSIC TURNED OFF !\n" + ANSI_RESET);
                        settings.musicOn = false;
                        settings.ingameMusic.stop();
                    }
                    else if(option.equals("3")) break;
                    else System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
                }
                play(Player1, Player2, settings, sc);
                break;
        }


     }

    public void shipPlacement(Scanner sc, Board Player, Settings settings)
    {
        System.out.println("Placing first Patrol Boat: 2 squares");
        placingShip(sc, 'P', Player, settings);
        Player.show();
        System.out.println("Placing second Patrol Boat: 2 squares");
        placingShip(sc, 'P', Player, settings);
        Player.show();
        System.out.println("Placing Destroyer Boat: 4 squares");
        placingShip(sc, 'D', Player, settings);
        Player.show();
        System.out.println("Placing Submarine: 3 squares");
        placingShip(sc, 'S', Player, settings);
        Player.show();
        System.out.println("Placing Battle Ship: 5 squares");
        placingShip(sc, 'B', Player, settings);
        Player.show();

        System.out.println("Press Enter to continue: (Clear screen)");
        sc.nextLine();
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    public void placingShip(Scanner sc, char typeOfShip, Board Player, Settings settings)
    {
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
                if(!(row >= 1 && row <= Player.boardSize && colnum >= 1 && colnum <= Player.boardSize))
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


            int numberOfSquare = 0;
            if(typeOfShip == 'P') numberOfSquare = 2;
            else if(typeOfShip == 'D') numberOfSquare = 4;
            else if(typeOfShip == 'S') numberOfSquare = 3;
            else if(typeOfShip == 'B') numberOfSquare = 5;

            if(row - numberOfSquare + 1 >= 1) System.out.print("Up / ");
            if(row + numberOfSquare - 1 <= Player.boardSize) System.out.print("Down / ");
            if(colnum - numberOfSquare + 1 >= 1) System.out.print("Left / ");
            if(colnum + numberOfSquare - 1 <= Player.boardSize) System.out.print("Right / ");
            System.out.println();
            String choice = "";


            while(true) //Kiểm tra hướng nhập vào có hợp lệ
            {
                System.out.println("Choose direction (Type in text): ");
                choice = sc.nextLine();
                settings.choosingSound.setMicrosecondPosition(0);
                settings.choosingSound.stop();
                settings.choosingSound.start();
                choice = choice.toLowerCase();
                if(choice.equals("up") || choice.equals("down") || choice.equals("left") || choice.equals("right")) break;
            }

            //Kiểm tra chồng lấn tàu
            boolean checkAvailableSpace = true;
            switch (choice) {
                case "up":
                    for (int i = max(1,colnum - 1); i <= colnum + 1; i++) {
                        for (int j = max(1, row - numberOfSquare); j <= row + 1; j++) {
                            if (i >= 1 && i <= Player.boardSize && j >= 1 && j <= Player.boardSize && Player.board[j][i] != 'o') {
                                checkAvailableSpace = false;
                                break;
                            }
                        }
                        if (!checkAvailableSpace) break;
                    }
                    if (checkAvailableSpace)
                        for (int i = row - numberOfSquare + 1; i <= row; i++) Player.board[i][colnum] = typeOfShip;
                    break;
                case "down":
                    for (int i = max(1,colnum - 1); i <= colnum + 1; i++) {
                        for (int j = max(1,row - 1); j <= min(Player.boardSize, row + numberOfSquare); j++) {
                            if (i >= 1 && i <= Player.boardSize && j >= 1 && j <= Player.boardSize && Player.board[j][i] != 'o') {
                                checkAvailableSpace = false;
                                break;
                            }
                        }
                        if (!checkAvailableSpace) break;
                    }
                    if (checkAvailableSpace)
                        for (int i = row; i < row + numberOfSquare; i++) Player.board[i][colnum] = typeOfShip;
                    break;
                case "left":
                    for (int i = max(1, colnum - numberOfSquare); i <= colnum + 1; i++) {
                        for (int j = max(1,row - 1); j <= row + 1; j++) {
                            if (i >= 1 && i <= Player.boardSize && j >= 1 && j <= Player.boardSize && Player.board[j][i] != 'o') {
                                checkAvailableSpace = false;
                                break;
                            }
                        }
                        if (!checkAvailableSpace) break;
                    }
                    if (checkAvailableSpace)
                        for (int i = colnum - numberOfSquare + 1; i <= colnum; i++) Player.board[row][i] = typeOfShip;
                    break;
                case "right":
                    for (int i = max(1,colnum - 1); i <= colnum + numberOfSquare; i++) {
                        for (int j =  max(1,row - 1); j <= row + 1; j++) {
                            if (i >= 1 && i <= Player.boardSize && j >= 1 && j <= Player.boardSize && Player.board[j][i] != 'o') {
                                checkAvailableSpace = false;
                                break;
                            }
                        }
                        if (!checkAvailableSpace) break;
                    }
                    if (checkAvailableSpace)
                        for (int i = colnum; i < colnum + numberOfSquare; i++) Player.board[row][i] = typeOfShip;
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid choice!!" + ANSI_RESET);
            }
            if(checkAvailableSpace)
            {
                System.out.println(ANSI_CYAN + "Your ship is ready!!" + ANSI_RESET);
                break;
            }
            else System.out.println(ANSI_RED + "Ships overlapping, try another!!" + ANSI_RESET);
        }
    }

    public void autoPlacement(Scanner sc, Board Player)
    {
        autoFill(Player, 'P');
        autoFill(Player, 'P');
        autoFill(Player, 'D');
        autoFill(Player, 'S');
        autoFill(Player, 'B');
    }

    public void autoFill(Board Player, char typeOfShip)
    {
        Random generator = new Random();
        while(true)
        {
            int row = generator.nextInt(Player.boardSize) + 1, colnum = generator.nextInt(Player.boardSize) + 1;

            int numberOfSquare = 0;
            if(typeOfShip == 'P') numberOfSquare = 2;
            else if(typeOfShip == 'D') numberOfSquare = 4;
            else if(typeOfShip == 'S') numberOfSquare = 3;
            else if(typeOfShip == 'B') numberOfSquare = 5;

            List<String> list = new ArrayList<>();
            if(row - numberOfSquare + 1 >= 1) list.add("up");
            if(row + numberOfSquare - 1 <= Player.boardSize) list.add("down");
            if(colnum - numberOfSquare + 1 >= 1) list.add("left");
            if(colnum + numberOfSquare - 1 <= Player.boardSize) list.add("right");
            String choice;

            while(true) //Kiểm tra hướng có hợp lệ
            {
                choice = list.get(generator.nextInt(list.size()));
                if(choice.equals("up") || choice.equals("down") || choice.equals("left") || choice.equals("right")) break;
            }

            //Kiểm tra chồng lấn tàu
            boolean checkAvailableSpace = true;
            switch (choice) {
                case "up":
                    for (int i = max(1,colnum - 1); i <= colnum + 1; i++) {
                        for (int j = max(1, row - numberOfSquare); j <= row + 1; j++) {
                            if (i >= 1 && i <= Player.boardSize && j >= 1 && j <= Player.boardSize && Player.board[j][i] != 'o') {
                                checkAvailableSpace = false;
                                break;
                            }
                        }
                        if (!checkAvailableSpace) break;
                    }
                    if (checkAvailableSpace)
                        for (int i = row - numberOfSquare + 1; i <= row; i++) Player.board[i][colnum] = typeOfShip;
                    break;
                case "down":
                    for (int i = max(1,colnum - 1); i <= colnum + 1; i++) {
                        for (int j = max(1,row - 1); j <= min(Player.boardSize, row + numberOfSquare); j++) {
                            if (i >= 1 && i <= Player.boardSize && j >= 1 && j <= Player.boardSize && Player.board[j][i] != 'o') {
                                checkAvailableSpace = false;
                                break;
                            }
                        }
                        if (!checkAvailableSpace) break;
                    }
                    if (checkAvailableSpace)
                        for (int i = row; i < row + numberOfSquare; i++) Player.board[i][colnum] = typeOfShip;
                    break;
                case "left":
                    for (int i = max(1, colnum - numberOfSquare); i <= colnum + 1; i++) {
                        for (int j = max(1,row - 1); j <= row + 1; j++) {
                            if (i >= 1 && i <= Player.boardSize && j >= 1 && j <= Player.boardSize && Player.board[j][i] != 'o') {
                                checkAvailableSpace = false;
                                break;
                            }
                        }
                        if (!checkAvailableSpace) break;
                    }
                    if (checkAvailableSpace)
                        for (int i = colnum - numberOfSquare + 1; i <= colnum; i++) Player.board[row][i] = typeOfShip;
                    break;
                case "right":
                    for (int i = max(1,colnum - 1); i <= colnum + numberOfSquare; i++) {
                        for (int j =  max(1,row - 1); j <= row + 1; j++) {
                            if (i >= 1 && i <= Player.boardSize && j >= 1 && j <= Player.boardSize && Player.board[j][i] != 'o') {
                                checkAvailableSpace = false;
                                break;
                            }
                        }
                        if (!checkAvailableSpace) break;
                    }
                    if (checkAvailableSpace)
                        for (int i = colnum; i < colnum + numberOfSquare; i++) Player.board[row][i] = typeOfShip;
                    break;
            }
            if(checkAvailableSpace) break;
        }
    }

    public void showLeaderBoard(Scanner sc, Settings settings) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        settings.menuMusic.stop();
        if(settings.musicOn)
        {
            settings.endSound.setMicrosecondPosition(0);
            settings.endSound.start();
        }
        Scanner input;
        List<Leader> list = new ArrayList<>();
        try{
            input = new Scanner(new File("src/Classes/leaderBoard.txt"));
            while(input.hasNext())
            {
                    String name = input.nextLine();
                    String numberOfShots = input.nextLine();
                    String numberOfShips = input.nextLine();
                    list.add(new Leader(name, numberOfShots, numberOfShips));
            }
        } catch (FileNotFoundException | NoSuchElementException e) {
            System.out.println();
            System.out.println(ANSI_RED + "Leaderboard currently has no data" + ANSI_RESET);
            System.out.println();
            return;
        }
        list.sort(new Comparator<Leader>() {
            @Override
            public int compare(Leader o1, Leader o2) {
                return o1.numberOfShots.compareTo(o2.numberOfShots);
            }
        });
        System.out.println(String.format("%-"+35+"s", " ") +ANSI_CYAN + "LEADERBOARD" + ANSI_RESET);
        System.out.println();
        System.out.println(String.format("%-"+10+"s"+ " | " + "%-"+25+"s" + " | " + "%-"+25+"s" + " | "
                + "%-"+25+"s","Ranking","Player Name","Shots taken","Ships remaining"));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        for(int i = 0; i < min(5,list.size()); i++)
        {
            System.out.printf("%-"+10+"s"+ " | " + "%-"+25+"s" + " | " + "%-"+25+"s" + " | "
                    + "%-"+25+"s" + "\n", i + 1, list.get(i).name, list.get(i).numberOfShots, list.get(i).numberOfShips);
            System.out.println("-----------------------------------------------------------------------------------------------------");
        }
        System.out.println("Press enter to continue");
        sc.nextLine();
        for(int i = 0; i < 50; i++) System.out.println();
        settings.endSound.stop();
    }

    public void showRule(Scanner sc, Settings settings) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        settings.menuMusic.stop();
        if(settings.musicOn)
        {
            settings.endSound.setMicrosecondPosition(0);
            settings.endSound.start();
        }
        System.out.println(ANSI_RED + "* Sea Battle is a game for two players.\n* The game is played on four grids, two for each player.\n" +
                "* The grids are typically square – usually 10×10 – and the individual squares in the grid are identified by letter and number.\n" +
                "* On one grid the player arranges ships and records the shots by the opponent.\n" +
                "* On the other grid the player records their own shots." + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Place your battle ships, including:" + ANSI_RESET);
        System.out.println("    + 2 Patrol Boat: 1x2");
        System.out.println("    + 1 Destroyer Boat: 1x4");
        System.out.println("    + 1 Submarine: 1x3");
        System.out.println("    + 1 Battle Ship: 1x5");
        System.out.println(ANSI_YELLOW + "Each turn, you can choose a location to attack, the game ends once all of your opponent's units are destroyed." + ANSI_RESET);
        System.out.println(ANSI_CYAN + "\nGoodluck, havefun!!!" + ANSI_RESET);
        System.out.println();
        System.out.println(ANSI_YELLOW + "* Game powered by" + ANSI_RESET + ANSI_RED + " [ProPTIT]-D22-NguyenBui256- *" + ANSI_RESET);
        System.out.println();
        System.out.println("Press enter to continue");
        sc.nextLine();
        for (int i = 0 ; i < 50; i++) System.out.println();
        settings.endSound.stop();
    }



}
