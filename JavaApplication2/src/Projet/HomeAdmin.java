
package Projet;

import db.DataBaseConnection;
import db.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.font.TextAttribute.FONT;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import raven.cell.TableActionCellRender;
import sante.Main;
import sante.Settings;
import java.sql.SQLException;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionEvent;

/**
 *
 * @author asma8
 */

public class HomeAdmin extends javax.swing.JFrame {

    /**
     * Creates new form HomeAdmin
     */
    public HomeAdmin() {
        initComponents();
        loadUsers();
        //tabUsers.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
        //tabUsers.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
        tabUsers.getColumnModel().getColumn(3).setCellRenderer(new ActionCellRenderer());
        // Définir l'editor pour gérer les actions sur les labels
        tabUsers.getColumnModel().getColumn(3).setCellEditor(new ActionCellEditor(tabUsers));
        JTableHeader header = tabUsers.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

    }

    // récupérer les utilisateurs depuis bdd et afficher dans la table
    public void loadUsers() {
        // Connexion à la base de données
        try (Connection conn = DataBaseConnection.connect()) {
            if (conn == null || conn.isClosed()) {
                JOptionPane.showMessageDialog(this, "Database connection is not available.");
                return;
            }
            String query = "SELECT id_user, username, email, phone_number FROM users WHERE id_user <> 1";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            //la table
            DefaultTableModel model = (DefaultTableModel) tabUsers.getModel();
            model.setRowCount(0); // Réinitialiser les lignes de la table

            // Remplir la table avec les données
            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                // Ajouter une ligne dans la table
                model.addRow(new Object[]{username, email, phoneNumber});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + ex.getMessage());
        }
    }

    // Renderer pour afficher des labels dans la colonne "Actions"
    class ActionCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel editLabel = new JLabel("Edit");
            JLabel deleteLabel = new JLabel("Delete");
            JLabel viewLabel = new JLabel("View");
            // Style pour les labels
            editLabel.setForeground(Color.BLUE);
            editLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            deleteLabel.setForeground(Color.RED);
            deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            viewLabel.setForeground(Color.black);
            viewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            panel.add(viewLabel);
            panel.add(new JLabel(" | ")); // Séparateur
            panel.add(editLabel);
            panel.add(new JLabel(" | ")); // Séparateur
            panel.add(deleteLabel);

            return panel;
        }
    }

// Editor pour gérer les clics sur les labels
    class ActionCellEditor extends AbstractCellEditor implements TableCellEditor {

        private JPanel panel;
        private JTable table;
        private int currentRow;

        public ActionCellEditor(JTable table) {
            this.table = table;

            // Création du panneau avec les labels
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel viewLabel = new JLabel("View");
            JLabel editLabel = new JLabel("Edit");
            JLabel deleteLabel = new JLabel("Delete");

            // Style pour les labels
            viewLabel.setForeground(Color.black);
            viewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            editLabel.setForeground(Color.BLUE);
            editLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            deleteLabel.setForeground(Color.RED);
            deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Ajout des listeners
            viewLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Action d'édition
                    viewUser(currentRow);
                }
            });
            editLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Action d'édition
                    editUser(currentRow);
                }
            });

            deleteLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Action de suppression
                    deleteUser(currentRow);
                }
            });
            panel.add(viewLabel);
            panel.add(new JLabel(" | "));
            panel.add(editLabel);
            panel.add(new JLabel(" | "));
            panel.add(deleteLabel);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.currentRow = row; // Stocker la ligne en cours
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null; // Pas de valeur spécifique à retourner
        }
        

        private void viewUser(int row) {
            String userName = table.getValueAt(row, 0).toString();
            String email = (String) table.getValueAt(row, 1);
            Session.setUsername(userName);
          
            try{
                Connection conn = DataBaseConnection.connect();
                String query = "SELECT id_user FROM users WHERE email = ?";
                PreparedStatement stm = conn.prepareStatement(query);
                stm.setString(1, email); 
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        Session.setUserId(rs.getInt("id_user"));  // Récupérer l'ID de l'utilisateur
                    }
            }
        }catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(table, "Erreur lors de la modification de l'utilisateur : " + e.getMessage());
                }
          dispose();
          ViewInfoUser frame = new ViewInfoUser();
          frame.setVisible(true);
          stopCellEditing();
        }
        private void editUser(int row) {
            String email = table.getValueAt(row, 1).toString();
            String currentUsername = (String) table.getValueAt(row, 0);
            String currentEmail = (String) table.getValueAt(row, 1);
            String currentPhoneNum = (String) table.getValueAt(row, 2);
            String newUsername = currentUsername ;
            String newEmail = currentEmail;
            String newPhoneNumber = currentPhoneNum;
            // Boîte de dialogue pour modifier les informations
            JTextField nameField = new JTextField(currentUsername);
            JTextField emailField = new JTextField(currentEmail);
            JTextField phoneField = new JTextField(currentPhoneNum);
            Object[] message = {
                "Username :", nameField,
                "Email :", emailField,
                "Phone Number :", phoneField,
                    
            };

            int option = JOptionPane.showConfirmDialog(table, message, "Modifier l'utilisateur", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
              if((nameField.getText().trim().isEmpty()) || (emailField.getText().trim().isEmpty()) ){
                  JOptionPane.showMessageDialog(null, "username and email are required");
                  return;}
              else{
                newUsername = nameField.getText();
                newEmail = emailField.getText();
                newPhoneNumber = phoneField.getText();}
                }
                try {
                    Connection connect = DataBaseConnection.connect();
                    if (connect.isClosed() || connect == null) {
                        JOptionPane.showMessageDialog(table, "La connexion à la base de données a échoué.");
                        return; // Sortir si la connexion n'est pas valide
                    }
                    int userId = -1;  // Valeur par défaut si l'utilisateur n'est pas trouvé
                    String query = "SELECT id_user FROM users WHERE email = ?";
                    PreparedStatement stm = connect.prepareStatement(query);
                    stm.setString(1, email); 
                    try (ResultSet rs = stm.executeQuery()) {
                        if (rs.next()) {
                            userId = rs.getInt("id_user");  // Récupérer l'ID de l'utilisateur
                        }
                    }
                    PreparedStatement stmt = connect.prepareStatement("UPDATE users SET username = ?, email = ?, phone_number = ? WHERE id_user = ?");
                    stmt.setString(1, newUsername);
                    stmt.setString(2, newEmail);
                    stmt.setString(3, newPhoneNumber);
                    stmt.setInt(4, userId);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        // Mettre à jour la table localement
                        table.setValueAt(newUsername, row, 0);
                        table.setValueAt(newEmail, row, 1);
                        table.setValueAt(newPhoneNumber, row, 2);
                        JOptionPane.showMessageDialog(table, "User successfully updated!");
                    } else {
                        JOptionPane.showMessageDialog(table, "Error: User not found!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(table, "Erreur lors de la modification de l'utilisateur : " + e.getMessage());
                }
                stopCellEditing();
            }

        private void deleteUser(int row) {
            String email = table.getValueAt(row, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(table, "Do you really want to delete this user?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Assurer que la connexion est valide
                try {
                    Connection connect = DataBaseConnection.connect();
                    if (connect.isClosed() || connect == null) {
                        JOptionPane.showMessageDialog(table, "La connexion à la base de données a échoué.");
                        return; // Sortir si la connexion n'est pas valide
                    }

                    // Préparer la requête SQL pour supprimer l'utilisateur
                    try (PreparedStatement stmt = connect.prepareStatement("DELETE FROM users WHERE email = ?")) {
                        stmt.setString(1, email);

                        // Exécuter la requête de suppression
                        int rowsAffected = stmt.executeUpdate();

                        // Vérifier si l'utilisateur a été supprimé
                        if (rowsAffected > 0) {
                            ((DefaultTableModel) table.getModel()).removeRow(row); // Supprimer la ligne du tableau
                            JOptionPane.showMessageDialog(table, "User successfully Deleted!");
                            return;
                        } else {
                            JOptionPane.showMessageDialog(table, "Error: User not found!");
                            return;
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(table, "Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
                        e.printStackTrace();  // Afficher l'erreur pour déboguer
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(table, "Erreur de connexion à la base de données : " + e.getMessage());
                    e.printStackTrace();  // Afficher l'erreur pour déboguer
                }
            }
            stopCellEditing();
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
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabUsers = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home");
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

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Users");

        tabUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Username", "Email Address", "Phone Number", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabUsers.setRowHeight(30);
        tabUsers.setSelectionBackground(new java.awt.Color(153, 204, 255));
        jScrollPane1.setViewportView(tabUsers);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 700, 360));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Projet/back-health (1).jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 450));
        jLabel2.getAccessibleContext().setAccessibleName("backgound");

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

    private void btnNotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotMouseClicked
        Notification notification = new Notification();
        JDialog notificationDialog = new JDialog(this, "Notifications", true);
        notificationDialog.setSize(260, 210);
        notificationDialog.setLocationRelativeTo(this);
        notificationDialog.add(notification);
        notificationDialog.setVisible(true);

    }//GEN-LAST:event_btnNotMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        dispose();
        MsgUser msg = new MsgUser();
        msg.setVisible(true);                       
    }//GEN-LAST:event_jLabel5MouseClicked

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
        java.util.logging.Logger.getLogger(HomeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(HomeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(HomeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(HomeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new HomeAdmin().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnHome;
    private javax.swing.JLabel btnLogOut;
    private javax.swing.JLabel btnNot;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel menu;
    public javax.swing.JTable tabUsers;
    // End of variables declaration//GEN-END:variables
}
