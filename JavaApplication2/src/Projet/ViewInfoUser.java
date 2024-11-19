/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Projet;

import static Projet.HomeUser.dateChose;
import chart.BarChartPanel;
import db.DataBaseConnection;
import db.Session;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sante.Main;

/**
 *
 * @author asma8
 */
public class ViewInfoUser extends javax.swing.JFrame {

    /**
     * Creates new form ViewInfoUser
     */
    public ViewInfoUser() {
        initComponents();
        setLocationRelativeTo(null);
        user.setText("Usrname: " + Session.getUsername() + ".");
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
            tabInfo.setModel(new DefaultTableModel(data, columnNames));

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
        jLabel5 = new javax.swing.JLabel();
        btnNot = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabInfo = new javax.swing.JTable();
        dateChose = new com.toedter.calendar.JDateChooser();
        user = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        jLabel5.setBackground(new java.awt.Color(204, 171, 216));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/message (1).png"))); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
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

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(btnHome)
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addGap(25, 25, 25)
                .addComponent(btnNot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addComponent(btnLogOut)
                .addGap(27, 27, 27))
        );

        getContentPane().add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 450));

        tabInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabInfo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabInfo.setRowHeight(30);
        jScrollPane1.setViewportView(tabInfo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(23, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 383, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(67, 67, 67)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 670, 340));
        getContentPane().add(dateChose, new org.netbeans.lib.awtextra.AbsoluteConstraints(645, 10, 140, -1));

        user.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        getContentPane().add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 250, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogOutMouseClicked
        int choix = JOptionPane.showConfirmDialog(this, "are you sure to log out ?", "Log out", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choix == 0) {
            dispose();
            Main frame = new Main();
            frame.setVisible(true);
        }
    }//GEN-LAST:event_btnLogOutMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        dispose();
        MsgUser msg = new MsgUser();
        msg.setVisible(true);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void btnNotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotMouseClicked
        Notification notification = new Notification();
        JDialog notificationDialog = new JDialog(this, "Notifications", true);
        notificationDialog.setSize(260, 210);
        notificationDialog.setLocationRelativeTo(this);
        notificationDialog.add(notification);
        notificationDialog.setVisible(true);
    }//GEN-LAST:event_btnNotMouseClicked

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        dispose();
        HomeAdmin home = new HomeAdmin();
        home.setVisible(true);        
        
    }//GEN-LAST:event_btnHomeMouseClicked

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
            java.util.logging.Logger.getLogger(ViewInfoUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewInfoUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewInfoUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewInfoUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewInfoUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnHome;
    private javax.swing.JLabel btnLogOut;
    private javax.swing.JLabel btnNot;
    public static com.toedter.calendar.JDateChooser dateChose;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel menu;
    private javax.swing.JTable tabInfo;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables
}