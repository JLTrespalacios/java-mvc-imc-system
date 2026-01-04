package app;

import controller.PersonaController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PersonaController controller = new PersonaController();
            controller.iniciar();
        });
    }
}
