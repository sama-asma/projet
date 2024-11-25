/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Projet;

import chart.BarChartPanel;
import db.DataBaseConnection;
import db.Session;
import java.awt.BorderLayout;
import java.awt.Point;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import sante.HealthCheck;
import sante.Main;
import sante.Settings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
/**
 *
 * @author asma8
 */
public class HomeUser extends javax.swing.JFrame {

    /**
     * Creates new form HomeUser
     */
    public HomeUser() {
        initComponents();
        setLocationRelativeTo(null);
        hello.setText("Hello," + Session.getUsername() + ".");
        BarChartPanel chartPanel = new BarChartPanel();
        jPanel1.setLayout(new BorderLayout()); // Permet à chartPanel de s'adapter à jPanel1
        jPanel1.add(chartPanel);
        updateTableFromDatabase(new Date());
        dateChose.addPropertyChangeListener("date",evt -> {
            Date selectDate = (Date) evt.getNewValue();
            updateTableFromDatabase(selectDate);
        });
        

    }

    public  void  updateTableFromDatabase(Date selectedDate) {
        String[] columnNames = {"Days", "Weight", "Temperature", "Blood Pressure", "Blood Sugar"};
        Object[][] data = new Object[7][5]; // 7 jours, 5 colonnes

        // Requête SQL pour récupérer les données des 7 derniers jours
        String query = """
        SELECT DAYOFWEEK(date) AS day_of_week, weight, temperature, blood_pressure, blood_sugar 
        FROM infohealth 
        WHERE id_user = ? 
          AND date BETWEEN DATE_SUB(?, INTERVAL 6 DAY) AND ?
        ORDER BY date ASC
    """;

        try {
            Connection conn = DataBaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.getUserId()); // Utilisateur connecté
            stmt.setDate(2, new java.sql.Date(selectedDate.getTime())); // Date de début (7 jours avant)
            stmt.setDate(3, new java.sql.Date(selectedDate.getTime())); // Date de fin (date sélectionnée)

            ResultSet rs = stmt.executeQuery();

            // Tableau pour marquer les jours présents
            boolean[] dayPresent = new boolean[7];

            // Remplir les données depuis la base de données
            while (rs.next()) {
                int dayOfWeek = rs.getInt("day_of_week"); // 1 = dimanche, 7 = samedi
                int dayIndex = (dayOfWeek + 5) % 7; // Remap pour 0 = samedi, 6 = vendredi

                dayPresent[dayIndex] = true;
                data[dayIndex][0] = getDayName(dayIndex + 1); // Nom du jour
                data[dayIndex][1] = rs.getFloat("weight");
                data[dayIndex][2] = rs.getFloat("temperature");
                data[dayIndex][3] = rs.getString("blood_pressure") != null ? rs.getString("blood_pressure") : "N/A";
                data[dayIndex][4] = rs.getObject("blood_sugar") != null ? rs.getInt("blood_sugar") : "N/A";
            }

            // Remplir les jours manquants avec des valeurs par défaut
            for (int i = 0; i < dayPresent.length; i++) {
                if (!dayPresent[i]) {
                    data[i][0] = getDayName(i + 1);
                    data[i][1] = "N/A"; // Valeurs par défaut
                    data[i][2] = "N/A";
                    data[i][3] = "N/A";
                    data[i][4] = "N/A";
                }
            }

            // Mettre à jour le modèle de la table
            tableData.setModel(new DefaultTableModel(data, columnNames));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data from database: " + e.getMessage());
        }
    }

// Méthode pour obtenir le nom du jour
    private  String getDayName(int dayOfWeek) {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPanel();
        btnLogOut = new javax.swing.JLabel();
        btnHome = new javax.swing.JLabel();
        btnAccount = new javax.swing.JLabel();
        btnBill = new javax.swing.JLabel();
        btnMed = new javax.swing.JLabel();
        btnNot = new javax.swing.JLabel();
        btnSet = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        hello = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();
        dateChose = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home");
        setBackground(new java.awt.Color(240, 240, 240));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(204, 171, 216));

        btnLogOut.setBackground(new java.awt.Color(204, 171, 216));
        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/log-out.png"))); // NOI18N
        btnLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogOutMouseClicked(evt);
            }
        });

        btnHome.setBackground(new java.awt.Color(204, 171, 216));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/home (2).png"))); // NOI18N
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });

        btnAccount.setBackground(new java.awt.Color(204, 171, 216));
        btnAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/account.png"))); // NOI18N
        btnAccount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAccountMouseClicked(evt);
            }
        });

        btnBill.setBackground(new java.awt.Color(204, 171, 216));
        btnBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/page.png"))); // NOI18N
        btnBill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBillMouseClicked(evt);
            }
        });

        btnMed.setBackground(new java.awt.Color(204, 171, 216));
        btnMed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/pill (1).png"))); // NOI18N
        btnMed.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMedMouseClicked(evt);
            }
        });

        btnNot.setBackground(new java.awt.Color(204, 171, 216));
        btnNot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/bell.png"))); // NOI18N
        btnNot.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNotMouseClicked(evt);
            }
        });

        btnSet.setBackground(new java.awt.Color(204, 171, 216));
        btnSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/gear.png"))); // NOI18N
        btnSet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSetMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(btnAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(btnHome)
                .addGap(25, 25, 25)
                .addComponent(btnAccount)
                .addGap(25, 25, 25)
                .addComponent(btnBill)
                .addGap(25, 25, 25)
                .addComponent(btnMed)
                .addGap(25, 25, 25)
                .addComponent(btnNot)
                .addGap(25, 25, 25)
                .addComponent(btnSet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addComponent(btnLogOut)
                .addGap(27, 27, 27))
        );

        getContentPane().add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 730, 190));

        hello.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        hello.setText("Hello,");
        getContentPane().add(hello, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 6, 290, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableData.setRowHeight(30);
        jScrollPane1.setViewportView(tableData);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 730, 190));
        getContentPane().add(dateChose, new org.netbeans.lib.awtextra.AbsoluteConstraints(645, 10, 140, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogOutMouseClicked
        // TODO add your handling code here:
        int choix = JOptionPane.showConfirmDialog(this, "are you sure to log out ?", "Log out", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choix == 0) {
            dispose();
            Main frame = new Main();
            frame.setVisible(true);
        }
    }//GEN-LAST:event_btnLogOutMouseClicked

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked

    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAccountMouseClicked
        dispose();
        Sante frame = new Sante();
        frame.setVisible(true);

    }//GEN-LAST:event_btnAccountMouseClicked

    private void btnMedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMedMouseClicked
        dispose();
        Medication frame = new Medication();
        frame.setVisible(true);
    }//GEN-LAST:event_btnMedMouseClicked

    private void btnNotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotMouseClicked
        // Créez un JDialog pour afficher la liste des notifications
        Notification notification = new Notification();
        JDialog notificationDialog = new JDialog(this, "Notifications", true);
        notificationDialog.setSize(260, 210);
        notificationDialog.setLocationRelativeTo(this);
        notificationDialog.add(notification);
        notificationDialog.setVisible(true);
    }//GEN-LAST:event_btnNotMouseClicked

    private void btnSetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSetMouseClicked
        dispose();
        Settings frame = new Settings();
        frame.setVisible(true);
    }//GEN-LAST:event_btnSetMouseClicked

    private void btnBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillMouseClicked
        dispose();
        HealthCheck frame = new HealthCheck();
        frame.setVisible(true);
    }//GEN-LAST:event_btnBillMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAccount;
    private javax.swing.JLabel btnBill;
    private javax.swing.JLabel btnHome;
    private javax.swing.JLabel btnLogOut;
    private javax.swing.JLabel btnMed;
    private javax.swing.JLabel btnNot;
    private javax.swing.JLabel btnSet;
    public static com.toedter.calendar.JDateChooser dateChose;
    private javax.swing.JLabel hello;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel menu;
    private javax.swing.JTable tableData;
    // End of variables declaration//GEN-END:variables
}
