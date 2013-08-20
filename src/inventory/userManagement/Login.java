/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.userManagement;

import inventory.core.ProjectBOMStockMain;
import java.awt.Color;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Kind
 */
public class Login extends inventory.myClasses.MyJPanel {

    /**
     * Creates new form Login
     */
    public Login() {
        super();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        loginButton = new inventory.myClasses.MyButton();
        registerButton = new inventory.myClasses.MyButton();
        passwordTextField = new inventory.myClasses.MyPasswordTextField();
        idTextField = new inventory.myClasses.MyTextField();

        idLabel.setText("ID");

        passwordLabel.setText("Password");

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        passwordTextField.setBackground(new java.awt.Color(255,255,255,152));
        passwordTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passwordTextFieldKeyTyped(evt);
            }
        });

        idTextField.setBackground(new java.awt.Color(255,255,255,152));
        idTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idTextFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registerButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordLabel)
                            .addComponent(idLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(idTextField))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idLabel)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton)
                    .addComponent(registerButton))
                .addContainerGap(14, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        // TODO add your handling code here:
        ProjectBOMStockMain.setPage(inventory.core.ProjectBOMStockMain.PageList.indexOf(evt.getActionCommand()));
    }//GEN-LAST:event_registerButtonActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
        loginEvent();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void loginEvent(){
        try {
            ResultSet rs = inventory.core.DBConnection.executeQuery("SELECT id, role_id FROM inventory.user WHERE user='"+this.idTextField.getText()+"' AND password=password('"+this.passwordTextField.getText()+"');");
            
            if(rs.next()){
                inventory.core.MainFrame.user_id = rs.getInt("id");
                inventory.core.MainFrame.role = rs.getInt("role_id");
                
                switch(inventory.core.MainFrame.role){
                    case 1:
                        ((inventory.userPage.UserMain)ProjectBOMStockMain.getPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("UserMain"))).loadDataByName("");
                        ProjectBOMStockMain.setPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("AdminMain"));
                        break;
                    case 2:
                        ((inventory.userPage.UserMain)ProjectBOMStockMain.getPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("UserMain"))).loadDataByName("");
                        ProjectBOMStockMain.setPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("UserMain"));
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(this, "You need a Permission. Ask to Administrator","Warning",JOptionPane.OK_OPTION);
                        break;
                }
                if(inventory.core.MainFrame.role != 4){
                    this.idTextField.setText("");
                    this.passwordTextField.setText("");
                }
            }else{
                inventory.core.MainFrame.user_id = 0;
                inventory.core.MainFrame.role = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void passwordTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTextFieldKeyTyped
        // TODO add your handling code here:
        if(evt.getKeyChar() == '\n'){
            loginEvent();
        }
    }//GEN-LAST:event_passwordTextFieldKeyTyped

    private void idTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idTextFieldKeyTyped
        // TODO add your handling code here:
        if(evt.getKeyChar() == '\n'){
            this.passwordTextField.grabFocus();
        }
    }//GEN-LAST:event_idTextFieldKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField idTextField;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTextField;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables

    public void setComponetsColor(Color color) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.loginButton.setBackground(color);
        this.registerButton.setBackground(color);
        this.idTextField.setBackground(color);
        this.passwordTextField.setBackground(color);
    }

    @Override
    public void LoadData() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void myInitComponents() {
        this.initComponents();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
