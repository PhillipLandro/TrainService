package trainservice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.locks.Lock;

/**
 * Die Klasse TrainController steuert den Zugverkehr, sprich die Animation der Züge.
 * 
 * @author Frank Sonntag
 */
public class TrainController extends Thread implements ActionListener{

    private Train train;                            //Train-Objekt, das gesteuert wird.
    private TrainServiceGUI gui;                    //Fenster, in dem die Animation dargestellt wird.
    private TrainAnimationPanel trainAnimationPanel;//Panel in dem die Zug, Schienen und Ladestation gezeichnet werden.
    private final static int MOVE_DELAY = 20;       //Animationsverzögerung bei der Bewegung des Zuges
    private final static int SPEED = 10;             //Geschwindigkeit, sprich Anzahl an Einheiten (Pixel), die ein Zug pro Zeiteinheit bewegt wird
    private final static Random random = new Random();//Zufallsgenerator für das Be- und Entladen

    private Thread thread;

    private boolean running = true;

    Object monitor = new Object();

    /**
     * Konstruktor zum Erzeugen eines TrainController-Objektes.
     * 
     * @param trainAnimationPanel - Panel, indem die Animation gezeichnet wird.
     */
    public TrainController(TrainAnimationPanel trainAnimationPanel) {
        this.trainAnimationPanel = trainAnimationPanel;
        this.train = trainAnimationPanel.getTrain();

    }

    public void setThread(Thread thread){
        this.thread = thread;
    }

    /**
     * Gibt das TrainServiceGUI-Objekt zurück.
     * 
     * @return - Fenster (JFrame), in dem die Animation dargestellt wird. 
     */
    public TrainServiceGUI getGui() {
        return gui;
    }

    /**
     * Setzt das TrainServiceGUI-Objekt.
     * 
     * @param gui - neue gui
     */
    public void setGui(TrainServiceGUI gui) {
        this.gui = gui;
    }

    /**
     * Gibt das Train-Objekt zurück.
     * 
     * @return Train-Objekt
     */
    public Train getTrain() {
        return train;
    }

    /**
     * Lässt den Zug rückwärts fahren.
     * 
     * @throws InterruptedException 
     */
    public void moveTrainBackward() throws InterruptedException {
        updateTrainState("Zug fährt rückwärts");
        while (train.getPositionX() < (this.trainAnimationPanel.getWidth() - train.getImage().getWidth(trainAnimationPanel) - trainAnimationPanel.getLoadingStationPic().getWidth())) {
            train.forward(SPEED);
            trainAnimationPanel.repaint();

            Thread.sleep(MOVE_DELAY);

        }
    }
    
    /**
     * Lässt den Zug vorwärts fahren.
     * 
     * @throws InterruptedException 
     */
    public void moveTrainForward() throws InterruptedException {
        updateTrainState("Zug fährt vorwärts");
        while (train.getPositionX() > (0 - train.getImage().getWidth(trainAnimationPanel))) {
            train.backward(SPEED);
            trainAnimationPanel.repaint();

            Thread.sleep(MOVE_DELAY);

        }
    }

    /**
     * Belädt den Zug.
     * 
     * @throws InterruptedException 
     */
    public void loadTrain() throws InterruptedException {

        while (train.getLoadLevel() < 100) {
            train.load(1);
            updateTrainState("Zug wird beladen: " + train.getLoadLevel() + "%");

            Thread.sleep(random.nextInt(200) + 10);

        }
    }

    /**
     * Entlädt den Zug.
     * 
     * @throws InterruptedException 
     */
    public void unloadTrain() throws InterruptedException {
        while (train.getLoadLevel() > 0) {
            train.unload(1);
            updateTrainState("Zug wird entladen: " + train.getLoadLevel() + "%");

            Thread.sleep(random.nextInt(200) + 10);

        }

    }

    /**
     * Bewegt den Zug zur Ladestation, belädt den Zug, bewegt den Zug aus dem Bild hinaus
     * und entlädt den Zug.
     * 
     * @throws InterruptedException 
     */
    public void doWholeJob() throws InterruptedException {
        moveTrainBackward();
        loadTrain();
        moveTrainForward();
        unloadTrain();
    }

    /**
     * Aktualisiert in der TrainServiceGUI das entsprechende Label, das den Status
     * des Zuges anzeigt.
     * 
     * @param state - Status-Botschaft
     */
    protected void updateTrainState(String state) {
        train.setStateMessage(state);
        if (gui != null) {
            gui.setJLabelText(train);
        }
    }
    
    /**
     * Gibt das TrainAnimationPanel-Objekt zurück.
     * 
     * @return 
     */
    public TrainAnimationPanel getTrainAnimationPanel() {
        return trainAnimationPanel;
    }

    @Override
    public void run() {
        while(true){
            try {
                this.doWholeJob();
            } catch (InterruptedException e) {
                // Das TrainController Objekt fungiert als Monitor
                synchronized (this){
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.interrupt();
        JButton button = (JButton) e.getSource();
        if(e.getActionCommand().equals("stop")){
            button.setText("Weiter");
            button.setActionCommand("resume");
        }
        else{
            button.setText("Pause");
            button.setActionCommand("stop");
        }
    }
}
