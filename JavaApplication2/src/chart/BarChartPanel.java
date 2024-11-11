/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author asma8
 */

public class BarChartPanel extends JPanel {
    private int[][] values = {{100, 200, 150, 300},{80, 220, 130, 280},{90, 210, 140, 290},{110, 230, 160, 310},{100, 190, 150, 280},{120, 200, 130, 300},{110, 210, 160, 320}};
    private String[] labels = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private Color[] colors = {new Color(104, 152, 244), new Color(252, 94, 94), new Color(82, 179, 217), new Color(255, 188, 75)};
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int barWidth = width / values.length;
        
        // Trouver la valeur maximale pour normaliser les hauteurs
        int maxVal = 0;
        for (int[] valueSet : values) {
            for (int val : valueSet) {
                if (val > maxVal) maxVal = val;
            }
        }
        
        // Dessiner chaque barre
        for (int i = 0; i < values.length; i++) {
            int x = i * barWidth + 10;
            for (int j = 0; j < values[i].length; j++) {
                int barHeight = (int) ((double) values[i][j] / maxVal * (height - 50));
                int y = height - barHeight - 30;
                g2.setColor(colors[j]);
                g2.fillRect(x + (j * (barWidth - 40) / values[i].length), y, (barWidth - 40) / values[i].length, barHeight);
            }
            
            // Afficher les labels sous chaque ensemble de barres
            g2.setColor(Color.BLACK);
            g2.drawString(labels[i], x + 10, height - 10);
        }
        
        // Légende
       // Légende affichée côte à côte
        int legendX = width - 550;  // Départ de la légende plus à gauche pour avoir de la place
        int legendY = 0;  // Position Y fixe pour aligner les éléments horizontalement

        for (int k = 0; k < colors.length; k++) {
            g2.setColor(colors[k]);
            g2.fillRect(legendX, legendY, 10, 10);  // Dessine un carré coloré pour chaque élément de la légende
            g2.setColor(Color.BLACK);
            g2.drawString(new String[]{"Weight", "Temperature", "Blood Pressure", "Blood Sugar"}[k], legendX + 15, legendY + 10);  // Affiche le texte à côté du carré coloré
            legendX += 100;  // Décale la position X pour le prochain élément de la légende
        }
    }}

