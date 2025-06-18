
package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SomUtils {
    public static void tocarSom(String caminho) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(caminho));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
