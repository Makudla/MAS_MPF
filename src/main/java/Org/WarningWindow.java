package Org;

import Models.Event;
import Models.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WarningWindow extends JFrame {
    public WarningWindow(Event event, Room room) {
        setTitle("Weryfikacja danych i decyzji");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Ostrzeżenie", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(255, 235, 186));
        messagePanel.setBorder(BorderFactory.createLineBorder(new Color(255, 193, 7), 1, true));
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel warningMessage1 = new JLabel("Uwaga! Sala jest mniejsza niż maksymalna ilość osób przypisana do wydarzenia.");
        warningMessage1.setFont(new Font("Arial", Font.PLAIN, 16));
        warningMessage1.setAlignmentX(Component.CENTER_ALIGNMENT);
        messagePanel.add(warningMessage1);

        JLabel warningMessage2 = new JLabel("Podejmij działanie:");
        warningMessage2.setFont(new Font("Arial", Font.PLAIN, 16));
        warningMessage2.setAlignmentX(Component.CENTER_ALIGNMENT);
        messagePanel.add(warningMessage2);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 235, 186));
        buttonPanel.setLayout(new FlowLayout());

        JButton cancelButton = new JButton("Anuluj");
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logika rezygnacji
                EventApp.openEventDetailsWindow(event);
                dispose();
                // Przejście do końca przypadku użycia
                // miejsce na dalszą logikę
            }
        });
        buttonPanel.add(cancelButton);

        JButton correctDataButton = new JButton("Popraw dane");
        correctDataButton.setBackground(new Color(76, 175, 80));
        correctDataButton.setForeground(Color.WHITE);
        correctDataButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        correctDataButton.setFont(new Font("Arial", Font.PLAIN, 16));
        correctDataButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        correctDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Dane nie zostały zapisane.");
                EventApp.openEventDetailsWindow(event);
                dispose();
            }
        });
        buttonPanel.add(correctDataButton);

        JButton confirmRoomButton = new JButton("Potwierdź salę");
        confirmRoomButton.setBackground(new Color(76, 175, 80));
        confirmRoomButton.setForeground(Color.WHITE);
        confirmRoomButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmRoomButton.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmRoomButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logika potwierdzenia sali

//                JOptionPane.showMessageDialog(null, "Aktor potwierdza wybraną salę.");
                event.setRoom(room, event.getStartDate(), event.getFinishDate());
                try {
                    EventApp.saveExtents("extents.ser");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                EventApp.openEventDetailsWindow(event);
                dispose();
            }
        });
        buttonPanel.add(confirmRoomButton);

        messagePanel.add(buttonPanel);
        mainPanel.add(messagePanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new WarningWindow());
//    }
}
