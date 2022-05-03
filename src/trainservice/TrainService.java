package trainservice;

/**
 * Hauptklasse zum Erzeugen der gesamten Animation.
 *
 * @author Frank Sonntag
 */
public class TrainService {

    /**
     * Platzieren Sie hier Ihren Quellcode zum Erzeugen der Zuganimation mit
     * Threads.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //TODO - Implementieren Sie hier den Aufruf der Zug-Animation
        TrainController[] controllers = new TrainController[]
                {
                    new TrainController(new TrainAnimationPanel(new Train())),
                    new TrainController(new TrainAnimationPanel(new Train())),
                    new TrainController(new TrainAnimationPanel(new Train())),
                    new TrainController(new TrainAnimationPanel(new Train()))
                };

        TrainServiceGUI gui = new TrainServiceGUI(controllers);

        gui.setVisible(true);

        for(int i = 0; i < controllers.length; i++){
            controllers[i].start();
        }

    }
}
