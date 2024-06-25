package Org;

import Models.Event;
import Models.Room;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;

public class EventDetailsWindow {
    private JFrame detailsFrame;
    private Event event;
    private DefaultTableModel tableModel;

    public EventDetailsWindow(Event event) {
        this.event = event;
        initialize();
    }

    private void initialize() {
        detailsFrame = new JFrame("Szczegóły Wydarzenia");
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int) screenSize.getHeight();
        int preferredHeight = screenHeight - 100;
        int preferredWidth = 1000;
        detailsFrame.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        detailsFrame.setMinimumSize(new Dimension(preferredWidth, preferredHeight));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Szczegóły Wydarzenia", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(51, 51, 51));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        detailsPanel.add(createDetailLabel("Nazwa wydarzenia: " + event.getName()));
        detailsPanel.add(createDetailLabel("Status: Zaplanowane"));
        detailsPanel.add(createDetailLabel("Max liczba uczestników: " + event.getMaxAttendeeNumber()));
        detailsPanel.add(createDetailLabel("Data rozpoczęcia: " + event.getStartDate()));
        detailsPanel.add(createDetailLabel("Data zakończenia: " + event.getFinishDate()));
        detailsPanel.add(createDetailLabel("Opis: " + event.getDescription()));
        detailsPanel.add(createDetailLabel("Lista sponsorów: Sponsor A, Sponsor B, Sponsor C"));
        detailsPanel.add(createDetailLabel("Harmonogram: Zobacz szczegóły"));
        detailsPanel.add(createDetailLabel("Bilety: Zobacz szczegóły"));
        detailsPanel.add(createDetailLabel("Przypisane sale:"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(preferredWidth, 300));
        detailsPanel.add(scrollPane);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("DataOd");
        tableModel.addColumn("DataDo");
        tableModel.addColumn("IdSali");
        tableModel.addColumn("Nazwa");
        tableModel.addColumn("IlośćOsób");
        tableModel.addColumn("Projektor");

        JTable table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        buttonPanel.add(createButton("Rezerwacja sali", new Color(76, 175, 80), event));
        buttonPanel.add(createButton("Usunięcie sali", new Color(244, 67, 54), event));
        buttonPanel.add(createButton("Dodaj wystawcę", new Color(76, 175, 80), event));
        buttonPanel.add(createButton("Usuń wystawcę", new Color(244, 67, 54), event));
        buttonPanel.add(createButton("Dodaj harmonogram", new Color(76, 175, 80), event));
        buttonPanel.add(createButton("Usuń harmonogram", new Color(244, 67, 54), event));
        buttonPanel.add(createButton("Wyjdź", new Color(244, 67, 54), event));

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadReservedRooms();

        detailsFrame.add(mainPanel);
        detailsFrame.pack();
        detailsFrame.setLocationRelativeTo(null);
        detailsFrame.setVisible(true);

//        System.out.println("Frame size: " + detailsFrame.getSize());
//        System.out.println("Preferred size: " + detailsFrame.getPreferredSize());
//        System.out.println("Minimum size: " + detailsFrame.getMinimumSize());
    }

    private void loadReservedRooms() {
        for (Map<String, Object> reservation : event.getReservedRooms()) {
            Room reservationRoom = (Room) reservation.get("room");
            LocalDate reservationDateFrom = (LocalDate) reservation.get("dateFrom");
            LocalDate reservationDateTo = (LocalDate) reservation.get("dateTo");
            int capacity = reservationRoom.getMaxPersonCapacity();
            boolean hasProjector = reservationRoom.getIsProjectorInstalled();

            tableModel.addRow(new Object[]{
                    reservationDateFrom,
                    reservationDateTo,
                    reservationRoom.getRoomID(),
                    reservationRoom.getName(),
                    capacity,
                    (hasProjector ? "Tak" : "Nie")
            });
        }
    }

    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(51, 51, 51));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

    private JButton createButton(String text, Color color, Event event) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            switch (text) {
                case "Rezerwacja sali":
                    try {
                        EventApp.openRoomSelectionWindow(event);
                        detailsFrame.dispose();
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Usunięcie sali":
                    // Obsługa usunięcia sali
                    break;
                case "Dodaj wystawcę":
                    // Obsługa dodania wystawcy
                    break;
                case "Usuń wystawcę":
                    // Obsługa usunięcia wystawcy
                    break;
                case "Dodaj harmonogram":
                    // Obsługa dodania harmonogramu
                    break;
                case "Usuń harmonogram":
                    // Obsługa usunięcia harmonogramu
                    break;
                case "Wyjdź":
                    detailsFrame.dispose();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Nieobsługiwany przycisk: " + text);
            }
        });

        return button;
    }
}
