package trainservice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Die Klasse TrainAnimationPanel zeichnet Zug, Schienen und Ladestation. 
 * 
 * @author Frank Sonntag
 */
public class TrainAnimationPanel extends JPanel {
    
    Train train;                        //zugeordnetes Train-Objekt
    BufferedImage railPic;              //geladenes Bild der Schienen
    BufferedImage loadingStationPic;    //geladenes Bild der Ladestation

    /**
     * Gibt das Bild der Ladestation zurück.
     * 
     * @return BufferedImage der Ladestation
     */
    public BufferedImage getLoadingStationPic() {
        return loadingStationPic;
    }

    /**
     * Gibt das Bild der Schienen zurück.
     * 
     * @return BufferedImage der Schienen
     */
    public BufferedImage getRailPic() {
        return railPic;
    }

    /**
     * Gibt das Train-Objekt zurück
     * @return - Train-Objekt
     */
    public Train getTrain() {
        return train;
    }

    /**
     * Setzt das Train-Objekt neu.
     * 
     * @param train 
     */
    public void setTrain(Train train) {
        this.train = train;
    }

   /**
    * Erzeugt einen Neuen TrainAnimationPanel als Swing-Komponente.
    * 
    * @param train - Train-Objekt, das in dem Panel dargestellt werden soll.
    */
    public TrainAnimationPanel(Train train) {
        this.setBackground(Color.WHITE);
        this.train = train;
        //Laden des Bildes der Schienen.
        try {
            railPic = ImageIO.read(Train.class.getResourceAsStream("schiene.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Laden des Bildes der Ladestation.
        try {
            loadingStationPic = ImageIO.read(Train.class.getResourceAsStream("ladestation.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * Zeichnet Zug, Schienen und Ladestation auf die Zeichenfläche des Swing-Panels.
     * 
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {

            g.drawImage(train.getImage(), train.getPositionX(), loadingStationPic.getHeight() - train.getImage().getHeight(this), this);

            //Zeichne alle Schienenstränge über die gesamte Bildbreite
            for (int i = 0; railPic.getWidth() * i < this.getWidth(); i++) {
                g.drawImage(railPic, railPic.getWidth() * i, loadingStationPic.getHeight(), this);
            }

            g.drawImage(loadingStationPic, this.getWidth() - loadingStationPic.getWidth(), 0, this);
        } catch (Exception e) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
