/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainservice;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Die Klasse TrainServiceGUI erzeugt ein Fenster, in dem die TrainAnimationPanels
 * der übergebenen TrainController dargestellt werden.
 * 
 * @author Frank Sonntag
 */
public class TrainServiceGUI extends JFrame {

    //Components of the GUI
    JPanel jPanel4Animation = new JPanel();
    JPanel jPanelSouth = new JPanel();
    //Hashtable der ein JLabel zu einem Train-Objekt zuordnet
    Hashtable<Train, JLabel> htLabels = new Hashtable<Train, JLabel>();
    //Array von TrainController, die die Animation steuern
    TrainController[] trainController;

    /**
     * Konstruktor der TrainServiceGUI
     *
     * @param trainController - Array von TrainController entsprechend der
     * Anzahl an Animationen der Züge
     */
    public TrainServiceGUI(TrainController[] trainController) {
        this.trainController = trainController;    
        for (int i = 0; i < trainController.length; i++) {
            trainController[i].setGui(this);
        }
        initComponents();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            
        }
    }

    /**
     * Initialisiert die GUI-Komponenten und baut das Fenster auf.
     */
    private void initComponents() {

        //Beende den Prozess beim Schließen des Fensters
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Maximiere das Fenster
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Hintergrundfarbe des Fensters: Weiß
        this.setBackground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);

        this.setTitle("Zugverkehr");
        //setze als Layout das Borderlayout
        getContentPane().setLayout(new BorderLayout());
        //füge einen umfassenden JPanel hinzu
        getContentPane().add(jPanel4Animation, BorderLayout.CENTER);

        /*
         * setze das Layout des JPanels so, dass für jeden Train(Controller) im
         * GridLayout ein AnimationPanel als Komponente hinzugefügt wird.
         */
        jPanel4Animation.setLayout(new GridLayout(trainController.length, 1));
        jPanel4Animation.setBackground(Color.WHITE);
        for (int i = 0; i < trainController.length; i++) {
            jPanel4Animation.add(trainController[i].getTrainAnimationPanel());
        }

        /*
         * Erzeuge für jedes Train(Controller)-Objekt ein JPanel mit Button und
         * Label im GridLayout
         */
        getContentPane().add(jPanelSouth, BorderLayout.SOUTH);
        jPanelSouth.setLayout(new FlowLayout());
        for (int i = 0; i < trainController.length; i++) {
            JPanel jPanel = new JPanel();
            JLabel jLabel = new JLabel("Pausiert");
            jLabel.setFont(new Font("Dialog", Font.BOLD, 16));
            jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Zug " + (i + 1)));
            jPanel.setLayout(new GridLayout(2, 1));
            jPanelSouth.add(jPanel);
            jPanel.add(jLabel);
            JButton jButton = new JButton("Pause");
            jButton.setActionCommand("stop");
            jButton.addActionListener(trainController[i]);
            jPanel.add(jButton);
            //speichere die Kombination von Train-Objekt und Label im HashTable htLabels für den späteren Zugriff
            htLabels.put(trainController[i].getTrain(), jLabel);
        }

        jPanelSouth.setPreferredSize(new Dimension(100, 100));
        setVisible(true);



    }

    /**
     * Ändert den Label-Text auf den aktuellen Status-Text des übergebenen
     * Train-Objektes. Ruft train.getStateMessage() auf und setzt den Text des
     * JLabel entsprechend.
     *
     * @param train - Train-Objekt, dessen Status geändert werden soll.
     */
    public void setJLabelText(Train train) {
        JLabel jLabel = htLabels.get(train);
        if (jLabel != null) {
            jLabel.setText(train.getStateMessage());
        }
    }
}
