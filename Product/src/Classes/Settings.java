package Classes;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Settings {

    AudioInputStream audioInputStream;

    boolean musicOn = true;
    Clip menuMusic, choosingSound, ingameMusic, hitSound, missSound, firstBlood, doubleKill, tripleKill, quadraKill, pentaKill, endSound;
    public Settings() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        //Main menu background music
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/Menu_Music.wav"));
        this.menuMusic = AudioSystem.getClip();
        this.menuMusic.open(this.audioInputStream);

        //Ingame Background Music
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/Ingame_Music.wav"));
        this.ingameMusic = AudioSystem.getClip();
        this.ingameMusic.open(this.audioInputStream);

        //Hit sound
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/Hit Sound.wav"));
        this.hitSound = AudioSystem.getClip();
        this.hitSound.open(this.audioInputStream);

        //Miss sound
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/Miss Sound.wav"));
        this.missSound = AudioSystem.getClip();
        this.missSound.open(this.audioInputStream);

        //Choosing sound
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/Choosing sound.wav"));
        this.choosingSound = AudioSystem.getClip();
        this.choosingSound.open(this.audioInputStream);

        //FirstBlood sound
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/FirstBlood.wav"));
        this.firstBlood = AudioSystem.getClip();
        this.firstBlood.open(this.audioInputStream);

        //DoubleKill sound
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/DoubleKill.wav"));
        this.doubleKill = AudioSystem.getClip();
        this.doubleKill.open(this.audioInputStream);

        //TripleKill sound
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/TripleKill.wav"));
        this.tripleKill = AudioSystem.getClip();
        this.tripleKill.open(this.audioInputStream);

        //QuadraKill sound
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/Quadra Kill - Sound Effect.wav"));
        this.quadraKill = AudioSystem.getClip();
        this.quadraKill.open(this.audioInputStream);

        //Penta sound
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/PentaKill.wav"));
        this.pentaKill = AudioSystem.getClip();
        this.pentaKill.open(this.audioInputStream);

        //End Sound
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("src/Audio/WinningMusic.wav"));
        this.endSound = AudioSystem.getClip();
        this.endSound.open(this.audioInputStream);
    }
}
