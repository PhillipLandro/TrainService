package trainservice;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Die Klasse Train repräsentiert die relevanten Eigenschaften eines Zuges. 
 * 
 * @author Frank Sonntag
 */
public class Train{

    private Image image;                //geladenes Bild eines Zuges
    private int positionX = 0;          //horizontale Position des Zuges
    private String stateMessage = "";   //Status-Botschaft des Zuges
    private int loadLevel = 0;          //Niveau der Beladung des Zuges

    /**
     * Setzt die horizontale Position des Zuges
     * 
     * @param positionX 
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    
    /**
     * Gibt das Beladungsniveau (0-100) in ganzen Prozent zurück.
     * 
     * @return 
     */
    public int getLoadLevel() {
        return loadLevel;
    }

    /**
     * Setzt die Status-Meldung des Zuges.
     * 
     * @param stateMessage - Status-Meldung
     */
    public void setStateMessage(String stateMessage) {
        this.stateMessage = stateMessage;
    }

    /**
     * Gibt das geladene Bild des Zuges zurück.
     * 
     * @return 
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gibt die horizontale Position des Zuges zurück.
     * 
     * @return 
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Gibt die aktuelle Status-Meldung zurück.
     * 
     * @return 
     */
    public String getStateMessage() {
        return stateMessage;
    }

    /**
     * Bewegt den Zug um die gegebene Anzahl an Einheiten nach vorne.
     * 
     * @param speed - Einheiten, um die der Zug bewegt wird.
     */
    public void forward(int speed) {
        this.positionX = this.positionX + speed;
    }
    
    /**
     * Bewegt den Zug um die gegebene Anzahl an Einheiten zurück.
     * 
     * @param speed - Einheiten, um die der Zug bewegt wird.
     */
    public void backward(int speed) {
        this.positionX = this.positionX - speed;
    }

    /**
     * Belädt den Zug um die Anzahl der übergebenen Anteile.
     * 
     * @param percentage - Anteil, um die der Zug beladen wird.
     */
    public void load(int percentage) {
        this.loadLevel = this.loadLevel + percentage;
        if (this.loadLevel > 100) {
            this.loadLevel = 100;
        }
    }
    
     /**
     * Entlädt den Zug um die Anzahl der übergebenen Anteile.
     * 
     * @param percentage - Anteil, um die der Zug entladen wird.
     */
    public void unload(int percentage) {
        this.loadLevel = this.loadLevel - percentage;
        if (this.loadLevel < 0) {
            this.loadLevel = 0;
        }
    }

    /**
     * Konstruktor: Erzeugt ein neues Train-Objekt.
     * 
     */
    public Train() {
        //Laden des Bildes des Zuges.
        try {
            image = ImageIO.read(Train.class.getResourceAsStream("train.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
