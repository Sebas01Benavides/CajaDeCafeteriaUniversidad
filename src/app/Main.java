package app;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.util.logging.Level;
import ui.LoginUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *
 * @author Sebas
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new HiFiLookAndFeel());
            } catch (UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("DEBUG (app.Main): HiFiLookAndFeel aplicado con éxito."); // Mensaje de éxito
            
            // Una vez que el Look and Feel está configurado, muestra la ventana de Login
            LoginUI loginUI = new LoginUI();
            loginUI.setVisible(true);
        });
    }
}
