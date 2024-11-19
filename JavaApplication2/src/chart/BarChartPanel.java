/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chart;

import Projet.HomeUser;
import db.DataBaseConnection;
import db.Session;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Date;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author asma8
 */
public class BarChartPanel extends JPanel {

    private int[][] values;
    private String[] labels = new String[7];
    private Color[] colors = {new Color(104, 152, 244), new Color(252, 94, 94), new Color(82, 179, 217), new Color(255, 188, 75)};

    // Constructeur pour récupérer les données de la base de données
    public BarChartPanel() {
        HomeUser.dateChose.setDate(new Date());
        HomeUser.dateChose.addPropertyChangeListener("date", new DateChangeListener());

        values = loadDataFromDatabase(new Date());  // Charger les données depuis la base de données
        
    }

    // Méthode pour charger les données depuis la base de données
    private int[][] loadDataFromDatabase(Date selectedDate) {
        int[][] dataArray = new int[7][4];  // 7 jours, 4 types de données (poids, température, pression, glycémie)
        int userId = Session.getUserId();
        for (int i = 0; i < labels.length; i++) {
            labels[i] = "Inconnu";
        }
        // Requête pour récupérer les données de la base de données
        String query = "SELECT DAYOFWEEK(date) AS day_of_week, date, weight, temperature, blood_pressure, blood_sugar FROM infohealth WHERE id_user = ? AND date <= ? ORDER BY date ASC";  // Adaptez votre requête SQL

        try {
            Connection conn = DataBaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setDate(2, new java.sql.Date(selectedDate.getTime()));
            ResultSet rs = stmt.executeQuery();

            // Remplir le tableau avec les résultats de la requête
            boolean[] dayPresent = new boolean[7];
            while (rs.next()) {
                int dayOfWeek = rs.getInt("day_of_week");
                int dayIndex = (dayOfWeek + 5) % 7;

                if (dayIndex >= 0 && dayIndex < 7) {
                    dayPresent[dayIndex] = true; // Marquer le jour comme présent

                    // Remplir les données
                    dataArray[dayIndex][0] = (int) rs.getFloat("weight");
                    dataArray[dayIndex][1] = (int) rs.getFloat("temperature");
                    String bloodPressure = rs.getString("blood_pressure");
                    if (bloodPressure != null && bloodPressure.contains("/")) {
                        String[] pressureValues = bloodPressure.split("/");
                        dataArray[dayIndex][2] = Integer.parseInt(pressureValues[0].trim());
                    }
                    Float bloodSugar = rs.getObject("blood_sugar") != null ? rs.getFloat("blood_sugar") : null;
                    dataArray[dayIndex][3] = (bloodSugar != null) ? bloodSugar.intValue() : 0;
                    labels[dayIndex] = getDayName(dayIndex + 1);
                }
            }

// Remplir les jours absents avec des valeurs par défaut
            for (int i = 0; i < dayPresent.length; i++) {
                if (!dayPresent[i]) {
                    dataArray[i][0] = 0;
                    dataArray[i][1] = 0;
                    dataArray[i][2] = 0;
                    dataArray[i][3] = 0;
                    labels[i] = getDayName(i + 1); // Assigner le nom du jour
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from database: " + e.getMessage());
        }

        return dataArray;  // Convertir la liste en tableau et retourner les données
    }
    // Méthode pour convertir le jour de la semaine en index

    private String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                return "Unkown";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int barWidth = width / 28;//(values.length * 4);

        // Trouver la valeur maximale pour normaliser les hauteurs
        int maxVal = 0;
        for (int[] valueSet : values) {
            for (int val : valueSet) {
                if (val > maxVal) {
                    maxVal = val;
                }
            }
        }

        // Dessiner chaque barre
        for (int i = 0; i < values.length; i++) {
            if (labels[i].equals("Inconnu")) {
                break;
            }
            int x = i * barWidth * 4 + 10;
            for (int j = 0; j < values[i].length; j++) {
                int barHeight = (int) ((double) values[i][j] / maxVal * (height - 50));
                int y = height - barHeight - 30;
                g2.setColor(colors[j]);
                g2.fillRect(x + (j * barWidth), y, (barWidth - 5), barHeight);
            }

            // Afficher les labels sous chaque ensemble de barres
            g2.setColor(Color.BLACK);
            if (labels[i] != null) {
                g2.drawString(labels[i], x + 10, height - 10);
            }
        }

        // Légende
        int legendX = 80;  // Départ de la légende plus à gauche pour avoir de la place
        int legendY = 0;  // Position Y fixe pour aligner les éléments horizontalement

        for (int k = 0; k < colors.length; k++) {
            g2.setColor(colors[k]);
            g2.fillRect(legendX, legendY, 10, 10);  // Dessine un carré coloré pour chaque élément de la légende
            g2.setColor(Color.BLACK);
            g2.drawString(new String[]{"Weight", "Temperature", "Blood Pressure", "Blood Sugar"}[k], legendX + 15, legendY + 10);  // Affiche le texte à côté du carré coloré
            legendX += 100;  // Décale la position X pour le prochain élément de la légende
        }
    }
    // Classe interne pour gérer le changement de date

    private class DateChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("date".equals(evt.getPropertyName())) {
                Date selectedDate = (Date) evt.getNewValue();
                values = loadDataFromDatabase(selectedDate);
                repaint(); // Redessiner le graphique avec les nouvelles données
            }
        }
    }
}
