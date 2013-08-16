/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.userManagement;

import java.awt.Color;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Kind
 */
public class UserManage extends inventory.myClasses.MyJPanel {

    private String order_by = "user";
    private String order = "ASC";
    
    private ArrayList<Integer> ids = null;
    private ArrayList<String> userArrayList = null;
    private ArrayList<String> nameArrayList = null;
    private ArrayList<String> roleArrayList =  null;
    private ArrayList<String> contactArrayList =  null;
    
    /**
     * Creates new form UserManage
     */
    public UserManage() {
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

        userLabel = new javax.swing.JLabel();
        userScrollPane = new javax.swing.JScrollPane();
        userList = new javax.swing.JList();
        nameLabel = new javax.swing.JLabel();
        nameScrollPane = new javax.swing.JScrollPane();
        nameList = new javax.swing.JList();
        roleScrollPane = new javax.swing.JScrollPane();
        roleList = new javax.swing.JList();
        contactScrollPane = new javax.swing.JScrollPane();
        contactList = new javax.swing.JList();
        roleLabel = new javax.swing.JLabel();
        contactLabel = new javax.swing.JLabel();
        searchTextField = new inventory.myClasses.MyTextField();
        searchLabel = new javax.swing.JLabel();
        backButton = new inventory.myClasses.MyButton();
        permitButton = new inventory.myClasses.MyButton();
        jSeparator1 = new javax.swing.JSeparator();
        dropButton = new inventory.myClasses.MyButton();

        userLabel.setText("User List");
        userLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userLabelMouseClicked(evt);
            }
        });

        userScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        userScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setAutoscrolls(true);

        userList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        userList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                userListValueChanged(evt);
            }
        });
        userScrollPane.setViewportView(userList);

        nameLabel.setText("Name");
        nameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nameLabelMouseClicked(evt);
            }
        });

        nameScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        nameScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        nameList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        nameList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                nameListValueChanged(evt);
            }
        });
        nameScrollPane.setViewportView(nameList);

        roleScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        roleScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        roleList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        roleList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roleListMouseClicked(evt);
            }
        });
        roleList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                roleListValueChanged(evt);
            }
        });
        roleScrollPane.setViewportView(roleList);

        contactScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        contactScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        contactList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        contactList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                contactListValueChanged(evt);
            }
        });
        contactScrollPane.setViewportView(contactList);

        roleLabel.setText("Role");
        roleLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roleLabelMouseClicked(evt);
            }
        });

        contactLabel.setText("Contact");
        contactLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactLabelMouseClicked(evt);
            }
        });

        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyReleased(evt);
            }
        });

        searchLabel.setText("Search By ID");

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        permitButton.setText("Permit");
        permitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                permitButtonActionPerformed(evt);
            }
        });

        dropButton.setText("UnPermit");
        dropButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(userScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(userLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roleScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(roleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(contactLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(searchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchTextField))
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(permitButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dropButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userLabel)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roleLabel)
                    .addComponent(contactLabel)
                    .addComponent(searchLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roleScrollPane)
                    .addComponent(userScrollPane)
                    .addComponent(nameScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(contactScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(permitButton)
                        .addGap(65, 65, 65)
                        .addComponent(dropButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void orderByLabelClick(java.awt.event.MouseEvent evt){
        if(evt.getClickCount()>1){
            this.order = "DESC";
        }else{
            this.order = "ASC";
        }
    }
    
    public void loadDataByID(String user) {
        this.ids = new ArrayList<Integer>();
        this.userArrayList = new ArrayList<String>();
        this.nameArrayList = new ArrayList<String>();
        this.roleArrayList =  new ArrayList<String>();
        this.contactArrayList =  new ArrayList<String>();
        
        try {
            ResultSet rs = null;
            
            String sql = "SELECT user.id, user.user, user.name, role.name as role, user.contact FROM inventory.user as user inner join inventory.role as role on user.role_id = role.id where user like '%"+user+"%' order by "+order_by+" "+order+";";
            
            if(sql.toString().contains("null")){
                sql = "SELECT user.id, user.user, user.name, role.name as role, user.contact FROM inventory.user as user inner join inventory.role as role on user.role_id = role.id where user like '%"+user+"%' order by user DESC;";
            }
            
            rs = inventory.core.DBConnection.excuteQuery(sql);  
            
            if(rs != null){
                while(rs.next()){
                    if(rs.getInt("id") == 4 || rs.getInt("id") == 1){
                        continue;
                    }
                    this.ids.add(rs.getInt("id"));
                    this.userArrayList.add(rs.getString("user"));
                    this.nameArrayList.add(rs.getString("name"));
                    this.roleArrayList.add(rs.getString("role"));
                    if(rs.getInt("contact") == 0){
                        this.contactArrayList.add("None");
                    }else{
                        this.contactArrayList.add(rs.getString("contact"));
                    }
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(CategoryManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.userList.setListData(userArrayList.toArray());
        this.nameList.setListData(this.nameArrayList.toArray());
        this.roleList.setListData(this.roleArrayList.toArray());
        this.contactList.setListData(this.contactArrayList.toArray());
    }
    
    private void userLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "user";
        this.loadDataByID(this.searchTextField.getText());
    }//GEN-LAST:event_userLabelMouseClicked

    private void userListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_userListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
            this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_userListValueChanged

    private void nameLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "name";
        this.loadDataByID(this.searchTextField.getText());
    }//GEN-LAST:event_nameLabelMouseClicked

    private void nameListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_nameListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
            this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_nameListValueChanged

    private void roleListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roleListMouseClicked
        // TODO add your handling code here:
        //System.out.println(evt.getClickCount());
    }//GEN-LAST:event_roleListMouseClicked

    private void roleListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_roleListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
            this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_roleListValueChanged

    private void contactListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_contactListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
            this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_contactListValueChanged

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        inventory.core.ProjectBOMStockMain.setPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("AdminMain"));
    }//GEN-LAST:event_backButtonActionPerformed

    private void roleLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roleLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "role";
        this.loadDataByID(this.searchTextField.getText());
    }//GEN-LAST:event_roleLabelMouseClicked

    private void contactLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "contact";
        this.loadDataByID(this.searchTextField.getText());
    }//GEN-LAST:event_contactLabelMouseClicked

    private void searchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyReleased
        // TODO add your handling code here:
        this.loadDataByID(this.searchTextField.getText());
        this.updateUI();
    }//GEN-LAST:event_searchTextFieldKeyReleased

    private void permitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_permitButtonActionPerformed
        // TODO add your handling code here:
        ChangePermit(2, "Permit");
    }//GEN-LAST:event_permitButtonActionPerformed

    private void dropButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropButtonActionPerformed
        // TODO add your handling code here:
        ChangePermit(4, "Drop");
    }//GEN-LAST:event_dropButtonActionPerformed

    private void ChangePermit(Integer role, String dropOrPermit){
        if(this.nameList.getSelectedIndex()<0){
            JOptionPane.showMessageDialog(this, "Please Select One User.","Alert",JOptionPane.OK_OPTION);
            return;
        }
        
        if(dropOrPermit.equals("Drop")){
            if(!this.roleList.getSelectedValue().toString().equals("User")){
                JOptionPane.showMessageDialog(this, "This User Already Dropped.","Alert",JOptionPane.OK_OPTION);
                return;
            }
        }else{
            if(this.roleList.getSelectedValue().toString().equals("User")){
                JOptionPane.showMessageDialog(this, "This User Already Permitted.","Alert",JOptionPane.OK_OPTION);
                return;
            }
        }
        
        if(JOptionPane.showConfirmDialog(this, "Do you really want to "+dropOrPermit+" this user : "+this.userList.getSelectedValue().toString()+"?!","Alert",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.CANCEL_OPTION){
            return;
        }
        String sql = "UPDATE `inventory`.`user` SET `role_id`='"+role+"' WHERE `id`='"+this.ids.get(this.nameList.getSelectedIndex())+"';";
        inventory.core.DBConnection.updateQuery(sql);
        
        String message = dropOrPermit.equals("Drop")?" was Dropped out.":" was Permitted.";
        JOptionPane.showMessageDialog(this, this.userList.getSelectedValue().toString()+message,"Confirm",JOptionPane.OK_OPTION);
        
        Integer id = this.ids.get(this.nameList.getSelectedIndex());
        
        this.loadDataByID(this.searchTextField.getText());
        this.listChanged(this.ids.indexOf(id));
    }
    
    private void listChanged(Integer index){
        if(index < 0)
            return;
        this.userList.setSelectedIndex(index);
        this.nameList.setSelectedIndex(index);
        this.roleList.setSelectedIndex(index);
        this.contactList.setSelectedIndex(index);
        
        this.userList.ensureIndexIsVisible(this.userList.getSelectedIndex());
        this.nameList.ensureIndexIsVisible(this.nameList.getSelectedIndex());
        this.roleList.ensureIndexIsVisible(this.roleList.getSelectedIndex());
        this.contactList.ensureIndexIsVisible(this.contactList.getSelectedIndex());
        
        this.updateUI();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel contactLabel;
    private javax.swing.JList contactList;
    private javax.swing.JScrollPane contactScrollPane;
    private javax.swing.JButton dropButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JList nameList;
    private javax.swing.JScrollPane nameScrollPane;
    private javax.swing.JButton permitButton;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JList roleList;
    private javax.swing.JScrollPane roleScrollPane;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JLabel userLabel;
    private javax.swing.JList userList;
    private javax.swing.JScrollPane userScrollPane;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void myInitComponents() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.initComponents();
    }

    @Override
    public void LoadData() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        loadDataByID("");
    }

    @Override
    public void setComponetsColor(Color transparent) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.backButton.setBackground(transparent);
        this.contactList.setBackground(transparent);
        this.contactScrollPane.setBackground(transparent);
        this.dropButton.setBackground(transparent);
        this.nameList.setBackground(transparent);
        this.nameScrollPane.setBackground(transparent);
        this.permitButton.setBackground(transparent);
        this.roleList.setBackground(transparent);
        this.roleScrollPane.setBackground(transparent);
        this.searchTextField.setBackground(transparent);
        this.userList.setBackground(transparent);
        this.userScrollPane.setBackground(transparent);
    }
}
