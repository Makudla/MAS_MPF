package Org;

import Models.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EventSelectionWindow {
    private JFrame frame;
    private JTable table;
    private List<Models.Event> events;

    public EventSelectionWindow(List<Models.Event> events) {
        this.events = events;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Lista Wydarzeń");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240)); // Jasnoszary kolor tła

        JLabel headerLabel = new JLabel("Lista Wydarzeń", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(51, 51, 51)); // Ciemny kolor tekstu
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        String[] columnNames = {"IdWydarzenia", "Nazwa", "Data Od", "Data Do", "Status", "Akcja"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(false);
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(241, 241, 241)); // Kolor zaznaczenia wiersza
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(221, 221, 221)); // Kolor siatki
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setBackground(new Color(242, 242, 242)); // Kolor tła nagłówka tabeli
        tableHeader.setForeground(new Color(51, 51, 51)); // Kolor tekstu nagłówka tabeli
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(221, 221, 221)));

        for (Models.Event event : events) {
            Object[] row = new Object[]{
                    event.getEventID(),
                    event.getName(),
                    event.getStartDate().toString(),
                    event.getFinishDate().toString(),
                    "Zaplanowane", // Można dodać logikę do uzyskania statusu
                    "Wybierz"
            };
            tableModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Wyjdź");
        cancelButton.setBackground(new Color(244, 67, 54)); // Czerwony kolor
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> System.exit(0));
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

                        // Pobierz wartość z przycisku, który został kliknięty
                        String buttonText = button.getText();

                        // Przeszukaj tabelę, aby znaleźć ID wydarzenia dla wiersza
                        Object value = table.getModel().getValueAt(selectedRow, 0);

                        // Sprawdź czy wartość jest typu String
                        if (value instanceof String) {
                            String eventIdString = (String) value;
                            Event event = EventApp.getEventById(eventIdString);

                            if (event != null) {
                                EventApp.openEventDetailsWindow(event); // Otwórz szczegóły wydarzenia
                                frame.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Nie znaleziono wydarzenia o ID: " + eventIdString, "Błąd", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Niepoprawny format ID wydarzenia.", "Błąd", JOptionPane.ERROR_MESSAGE);
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

