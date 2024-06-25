package Org;

import Models.Event;
import Models.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class RoomSelectionWindow {
    private JFrame frame;
    private JTable table;
    private List<Room> rooms;
    private Models.Event event;

    public RoomSelectionWindow(List<Room> rooms, Event event) {
        this.rooms = rooms;
        this.event = event;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Lista Dostępnych Sal");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel headerLabel = new JLabel("Lista Dostępnych Sal", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(51, 51, 51));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        String[] columnNames = {"Numer sali", "Nazwa sali", "Max liczba osób", "Projektor", "Akcja"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(false);
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(241, 241, 241));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(221, 221, 221));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        for (Room room : rooms) {
            Object[] row = new Object[]{
                    room.getRoomID(),
                    room.getName(),
                    room.getMaxPersonCapacity(),
                    room.getIsProjectorInstalled() ? "Tak" : "Nie",
                    "Wybierz salę"
            };
            tableModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Anuluj");
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> {
            EventApp.openEventDetailsWindow(event);
            frame.dispose();});
        mainPanel.add(cancelButton, BorderLayout.SOUTH);

        table.getColumn("Akcja").setCellRenderer(new ButtonRenderer());
        table.getColumn("Akcja").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private JButton button;
            private boolean isPushed;
            private int selectedRow;

            {
                button = new JButton();
                button.setOpaque(true);
                button.setBackground(new Color(76, 175, 80)); // Zielony kolor
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fireEditingStopped();

                        // Przeszukaj tabelę, aby znaleźć ID wydarzenia dla wiersza
                        Object value = table.getModel().getValueAt(selectedRow, 0);

                        // Sprawdź czy wartość jest typu Integer
                        if (value instanceof Integer) {
                            Room room = EventApp.getRoomById((int) value);

                            if (room != null) {
                                if (!room.isRoomSuitableForEvent(event.getMaxAttendeeNumber(), event.getStartDate(), event.getFinishDate())) {
                                    new WarningWindow(event,room);
                                    frame.dispose();
                                } else {
                                    event.setRoom(room, event.getStartDate(), event.getFinishDate());
                                    try {
                                        EventApp.saveExtents("extents.ser");
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    EventApp.openEventDetailsWindow(event);
                                    frame.dispose();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Nie znaleziono sali ", "Błąd", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Niepoprawny format ID sali.", "Błąd", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                selectedRow = row;
                button.setText("Wybierz");
                isPushed = true;
                return button;
            }

            @Override
            public Object getCellEditorValue() {
                isPushed = false;
                return "Wybierz";
            }

            @Override
            public boolean stopCellEditing() {
                isPushed = false;
                return super.stopCellEditing();
            }

            @Override
            protected void fireEditingStopped() {
                super.fireEditingStopped();
            }
        });

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}
