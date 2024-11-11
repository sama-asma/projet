/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chart;

/**
 *
 * @author asma8
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomTable extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public CustomTable() {
        // Configurer les colonnes du tableau
        String[] columnNames = {"Jour", "Weight", "Temperature", "Blood Pressure", "Blood Sugar"};
        
        // Données d'exemple pour le tableau
        Object[][] data = {
            {"Saturday", 70, 36.5, "120/80", 90},
            {"Sunday", 71, 36.6, "122/82", 92},
            {"Monday", 72, 36.7, "118/78", 88},
            {"Tuesday", 73, 36.5, "121/80", 85},
            {"Wednesday", 70, 36.4, "119/79", 89},
            {"Thursday", 69, 36.6, "122/80", 91},
            {"Friday", 68, 36.7, "117/76", 87}
        };

        // Créer le modèle de table avec les colonnes et les données
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);

        // Appliquer un style à l'en-tête
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(204, 171, 216));
        table.getTableHeader().setForeground(Color.BLACK);

        // Appliquer un style aux lignes du tableau
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);

        // Ajouter le tableau dans un JScrollPane
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // Méthode pour récupérer le tableau, si besoin
    public JTable getTable() {
        return table;
    }
}
  // Créer une instance de CustomTable
       // CustomTable customTable = new CustomTable();
        // Ajouter customTable au JFrame
        
       /* jPanel3.setLayout(new BorderLayout());
        jPanel3.add(customTable, BorderLayout.CENTER); */
