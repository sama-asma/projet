/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Projet;

import db.DataBaseConnection;
import db.Session;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import sante.HealthCheck;
import sante.Main;
import sante.Settings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author asma8
 */
public class Medication extends javax.swing.JFrame {

    /**
     * Creates new form Medication
     */
    public Medication() {
        initComponents();
        setLocationRelativeTo(null); //cantrer frame
        jScrollPane1.setVisible(false);

        loadMedications();

    }

    public void loadMedications() {
        String[] columnNames = {"Name", "Amount", "Time", "Delete"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        String query = "SELECT id_med, name, amount, time FROM medicament WHERE id_user = ? ORDER BY time ASC";
        try {
            Connection conn = DataBaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.getUserId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_med");
                String name = rs.getString("name");
                int amount = rs.getInt("amount");
                String time = rs.getString("time");

                // Ajout des données au modèle
                model.addRow(new Object[]{name, amount, time, id}); // Stocker l'ID dans la colonne "Delete"
            }

            medicationTable.setModel(model);
            jScrollPane1.setVisible(true);

            // Renderer pour afficher "Delete" dans la colonne
            medicationTable.getColumn("Delete").setCellRenderer(new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel deleteLabel = new JLabel("Delete");
                    deleteLabel.setForeground(Color.RED);
                    deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    return deleteLabel;
                }
            });

            // Editor pour gérer les clics dans la colonne "Delete"
            medicationTable.getColumn("Delete").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    JLabel deleteLabel = new JLabel("Delete");
                    deleteLabel.setForeground(Color.RED);
                    deleteLabel.setToolTipText("Click to delete");

                    // Gérer le clic sur le label
                    deleteLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int id = (int) table.getValueAt(row, 3); // Récupérer l'ID de la ligne
                            deleteMedication(id, model);
                        }
                    });

                    return deleteLabel;
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading medications: " + e.getMessage());
        }
    }

    private void deleteMedication(int medicationId, DefaultTableModel model) {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this medication?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM medicament WHERE id_med = ?";
            try {
                Connection conn = DataBaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, medicationId);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Medication deleted successfully!");

                // Rafraîchir la table
                loadMedications();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting medication: " + e.getMessage());
            }
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
        jPanel2 = new javax.swing.JPanel();
        name = new javax.swing.JLabel();
        amount = new javax.swing.JLabel();
        not = new javax.swing.JLabel();
        nameMed = new javax.swing.JTextField();
        amountMed = new javax.swing.JTextField();
        notHour = new javax.swing.JComboBox<>();
        notMin = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        ajouter = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        medicationTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("My Medication");
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

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));

        jPanel2.setBackground(new java.awt.Color(233, 221, 221));

        name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        name.setText("Name:");

        amount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        amount.setText("Amount:");

        not.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        not.setText("Notification:");

        nameMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameMedActionPerformed(evt);
            }
        });

        notHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", " " }));

        notMin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", " " }));

        jPanel3.setBackground(new java.awt.Color(218, 205, 205));
        jPanel3.setPreferredSize(new java.awt.Dimension(20, 20));

        ajouter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/plus-sign (1).png"))); // NOI18N
        ajouter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ajouter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ajouterMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ajouter, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ajouter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name)
                    .addComponent(nameMed, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(amount)
                    .addComponent(amountMed, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(not)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(notHour, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(notMin, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name)
                    .addComponent(amount)
                    .addComponent(not, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameMed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(amountMed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(notHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(notMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, 21, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("My Medication:");

        medicationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "name", "amount", "time", "delete"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(medicationTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 560, 350));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/back-health (1).jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 450));

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

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        dispose();
        HomeUser frame = new HomeUser();
        frame.setVisible(true);
    }//GEN-LAST:event_btnHomeMouseClicked

    private void nameMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameMedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameMedActionPerformed

    private void btnAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAccountMouseClicked
        dispose();
        Sante frame = new Sante();
        frame.setVisible(true);
    }//GEN-LAST:event_btnAccountMouseClicked

    private void btnNotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotMouseClicked
        // TODO add your handling code here:
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

    private void ajouterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ajouterMouseClicked

        String query = "INSERT INTO medicament (name, amount, time, id_user) VALUES (?, ?, ?, ?)";
        String name = nameMed.getText();
        int amount = Integer.parseInt(amountMed.getText());
        String time = notHour.getSelectedItem() + ":" + notMin.getSelectedItem() + ":00";
        if (name.trim().isEmpty() && time.trim().isEmpty() && (amount < 0)) {
            JOptionPane.showMessageDialog(this, "All field are required!");
            return;
        }
        try {
            Connection conn = DataBaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, amount);
            stmt.setString(3, time);
            stmt.setInt(4, Session.getUserId()); // ID de l'utilisateur connecté
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Medication added successfully!");
                loadMedications();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding medication: " + e.getMessage());
        }
    }//GEN-LAST:event_ajouterMouseClicked

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
            java.util.logging.Logger.getLogger(Medication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Medication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Medication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Medication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Medication().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ajouter;
    private javax.swing.JLabel amount;
    private javax.swing.JTextField amountMed;
    private javax.swing.JLabel btnAccount;
    private javax.swing.JLabel btnBill;
    private javax.swing.JLabel btnHome;
    private javax.swing.JLabel btnLogOut;
    private javax.swing.JLabel btnMed;
    private javax.swing.JLabel btnNot;
    private javax.swing.JLabel btnSet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable medicationTable;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel name;
    private javax.swing.JTextField nameMed;
    private javax.swing.JLabel not;
    private javax.swing.JComboBox<String> notHour;
    private javax.swing.JComboBox<String> notMin;
    // End of variables declaration//GEN-END:variables
}
