/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;
import ui.LoginUI;
import javax.swing.SwingUtilities;
/**
 *
 * @author Sebas
 */
public class Main {
    public static void main(String[] args) {
        // Usa SwingUtilities.invokeLater para asegurar que la interfaz gráfica
        // se cree en el hilo de despacho de eventos de Swing.
        SwingUtilities.invokeLater(() -> {
            LoginUI loginUI = new LoginUI();
            loginUI.setVisible(true);
        });
    }
}
