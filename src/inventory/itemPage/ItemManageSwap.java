
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.itemPage;

import inventory.myClasses.MyExpireDateCellRenderer;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.Position;

/**
 *
 * @author paul
 */
public class ItemManageSwap extends inventory.myClasses.MyJPanel {
    private ArrayList<Integer> item_ids;
    private ArrayList<Integer> variety_id;
    private ArrayList<String> varietyNameArrayList;
    private ArrayList<Integer> category_id;
    private ArrayList<String> categoryNameArrayList;
    private ArrayList<String> categoryCodeArrayList;
    
    private ArrayList<String> modelNameArrayList;
    private ArrayList<Integer> packageArrayList;
    private ArrayList<Float> priceArrayList;
    public static ArrayList<Integer> currentArrayList;
    private ArrayList<Float> totalPriceArrayList;
    private ArrayList<Date> expireDateArrayList;
    private ArrayList<String> descriptionArrayList;
    private ArrayList<String> nationArrayList;
    
    private String order_by = "categoryname";
    private String order = "ASC";
    
    private String searchSubject = "item.name";
    
    private String pastName = "";
    
    public static Color expired = null;
    public static Color oneWeek = null;
    public static Color twoWeek = null;
    public static Color fourWeek = null;
    public static Color afterFourWeek = null;
    
    public static Color remainZero = null;
    
    /**
     * Creates new form ItemManageSwap
     */
    public ItemManageSwap() {
        super();
        //loadDataByName("");
        setDefaultExpireDateColor();
    }

    private void setDefaultExpireDateColor(){
        try {
            ResultSet rs = inventory.core.DBConnection.executeQuery("SELECT * FROM inventory.weekAndColor;");
            while(rs.next()){
                switch(rs.getInt("week")){
                    case 0:
                        expired = new Color(rs.getInt("colorRed"),rs.getInt("colorGreen"),rs.getInt("colorBlue"));
                        break;
                    case 1:
                        oneWeek = new Color(rs.getInt("colorRed"),rs.getInt("colorGreen"),rs.getInt("colorBlue"));
                        break;
                    case 2:
                        twoWeek = new Color(rs.getInt("colorRed"),rs.getInt("colorGreen"),rs.getInt("colorBlue"));
                        break;
                    case 3:
                        fourWeek = new Color(rs.getInt("colorRed"),rs.getInt("colorGreen"),rs.getInt("colorBlue"));
                        break;
                    case 4:
                        afterFourWeek = new Color(rs.getInt("colorRed"),rs.getInt("colorGreen"),rs.getInt("colorBlue"));
                        break;
                    case 5:
                        remainZero = new Color(rs.getInt("colorRed"),rs.getInt("colorGreen"),rs.getInt("colorBlue"));
                        break;
                }
            }
            this.expiredColorButton.setBackground(expired);
            this.oneWeekColorButton.setBackground(oneWeek);
            this.twoWeekColorButton.setBackground(twoWeek);
            this.fourWeekColorButton.setBackground(fourWeek);
            this.afterFourWeekColorButton.setBackground(afterFourWeek);
            this.remainZeroColorButton.setBackground(remainZero);
        } catch (SQLException ex) {
            Logger.getLogger(ItemManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void loadDataByName(String name) {
        try {
            this.category_id = new ArrayList<Integer>();
            this.categoryNameArrayList = new ArrayList<String>();
            
            String sql = "SELECT * FROM inventory.category;";
            
            ResultSet rs = inventory.core.DBConnection.executeQuery(sql);
            
            while(rs.next()){
                if(rs.getInt("disable_id") != 1){
                    continue;
                }
                this.category_id.add(rs.getInt("id"));
                this.categoryNameArrayList.add(rs.getString("name"));
            }
            this.categoryList.setListData(this.categoryNameArrayList.toArray());
            this.categoryList.clearSelection();
    //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ItemManageSwap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public final void reLoadDataByName() {
        this.item_ids = new ArrayList<>();
        this.modelNameArrayList =  new ArrayList<>();
        this.packageArrayList =  new ArrayList<>();
        this.priceArrayList = new ArrayList<>();
        this.currentArrayList =  new ArrayList<>();
        this.expireDateArrayList = new ArrayList<>();
        this.descriptionArrayList = new ArrayList<>();
        this.nationArrayList = new ArrayList<>();
        this.categoryCodeArrayList = new ArrayList<>();
        //this.modelTextPane.setText("");
        
        ArrayList<String> priceOnListArrayList = new ArrayList<>();
        
        if(this.varietyNameList.getSelectedIndex() < 0){
            return;
        }
        Integer id = this.variety_id.get(varietyNameList.getSelectedIndex());
        
        //SELECT item.id,item.name as itemname, category.name as categoryname, model.name as modelname, package.count, item.price, nation.name as nationname, item.current, item.price*item.current as total, item.expiredate, item.description 
        //FROM inventory.item as item inner join inventory.nation as nation inner join inventory.package as package inner join inventory.model as model inner join inventory.category as category 
        //ON item.category_id = nation.id and item.model_id = model.id and item.package_id = package.id and item.category_id = category.id where item.name like '%%' ;
        
        //SELECT item.id,item.name as itemname, category.name as categoryname, model.name as modelname, package.count, item.price, nation.name as nationname, item.current, item.price*item.current as total, item.expiredate, item.description FROM inventory.item as item inner join inventory.nation as nation inner join inventory.package as package inner join inventory.model as model inner join inventory.category as category ON item.category_id = nation.id and item.model_id = model.id and item.package_id = package.id and item.category_id = category.id where item.name like '%%' order by item.name;
        
        String sql = "SELECT item.id,item.name as itemname, CONCAT(category.code, LPAD(variety.varietyNumber,2,'0'), LPAD(item.itemNumber,3,'0')) as wcode, category.name as categoryname, model.name as modelname, package.count, item.price, nation.name as nationname, item.current, item.price*item.current as total, item.expiredate, item.description, item.disable_id FROM inventory.item as item join inventory.nation as nation join inventory.package as package join inventory.model as model join inventory.category as category join inventory.variety ON item.nation_id = nation.id and item.model_id = model.id and item.package_id = package.id and inventory.item.category_id = inventory.category.id and item.variety_id = variety.id and item.variety_id = "+id+" and item.disable_id = 1 where "+this.searchSubject+" like '%"+this.pastName+"%' order by "+order_by+" "+order+";";
        
        try {
            ResultSet rs = inventory.core.DBConnection.executeQuery(sql);  
            
            if(rs != null){
                while(rs.next()){
                    if(rs.getInt("disable_id") != 1){
                        continue;
                    }
                    this.item_ids.add(rs.getInt("id"));
                    
                    this.categoryCodeArrayList.add(rs.getString("wcode"));
                    
                    this.modelNameArrayList.add(rs.getString("modelname"));
                    this.packageArrayList.add(rs.getInt("count"));
                    this.priceArrayList.add(rs.getFloat("price"));
                    this.nationArrayList.add(rs.getString("nationname"));
                    String priceAndNation = null;
                    switch (this.nationArrayList.get(this.nationArrayList.size()-1)) {
                        case "South Korea":
                            priceAndNation = (this.priceArrayList.get(this.priceArrayList.size()-1))+" W";
                            break;
                        case "Malawi":
                            priceAndNation = (this.priceArrayList.get(this.priceArrayList.size()-1))+" MK";
                            break;
                    }
                    priceOnListArrayList.add(priceAndNation);
                    
                    this.currentArrayList.add(rs.getInt("current"));
                    this.expireDateArrayList.add(rs.getDate("expiredate"));
                    this.descriptionArrayList.add(rs.getString("description"));
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(CategoryManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.modelScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
        this.packageScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
        this.priceScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
        this.currentScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
//        this.totalPriceScrollPane.getVerticalScrollBar().setModel(this.nameScrollPane.getVerticalScrollBar().getModel());
        this.expireDateScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
        this.descriptionScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
        this.codeScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
        
        this.descriptionScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                ItemManageSwap.this.updateUI();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        this.modelList.setListData(this.modelNameArrayList.toArray());
        this.packageList.setListData(this.packageArrayList.toArray());
        this.priceList.setListData(priceOnListArrayList.toArray());
        this.currentList.setListData(this.currentArrayList.toArray());
//        this.totalPriceList.setListData(totalPriceOnListArrayList.toArray());
        this.expireDateList.setListData(this.expireDateArrayList.toArray());
        this.descriptionList.setListData(this.descriptionArrayList.toArray());
        this.codeList.setListData(this.categoryCodeArrayList.toArray());
        
        this.expireDateList.setCellRenderer(new MyExpireDateCellRenderer());
    }
    
    
    public void setSelectedListItem(String name){
        //System.out.println(this.itemNameList.getNextMatch(name, 0, Position.Bias.Forward));
        listChanged(this.varietyNameList.getNextMatch(name, 0, Position.Bias.Forward));
    }
    
    private void listChanged(Integer index){
        if(index < 0)
            return;
        this.modelList.setSelectedIndex(index);
        this.packageList.setSelectedIndex(index);
        this.priceList.setSelectedIndex(index);
        this.currentList.setSelectedIndex(index);
//        this.totalPriceList.setSelectedIndex(index);
        this.expireDateList.setSelectedIndex(index);
        this.descriptionList.setSelectedIndex(index);
        this.codeList.setSelectedIndex(index);
        
        this.modelList.ensureIndexIsVisible(this.modelList.getSelectedIndex());
        this.packageList.ensureIndexIsVisible(this.packageList.getSelectedIndex());
        this.priceList.ensureIndexIsVisible(this.priceList.getSelectedIndex());
        this.currentList.ensureIndexIsVisible(this.currentList.getSelectedIndex());
//        this.totalPriceList.ensureIndexIsVisible(this.totalPriceList.getSelectedIndex());
        this.expireDateList.ensureIndexIsVisible(this.expireDateList.getSelectedIndex());
        this.descriptionList.ensureIndexIsVisible(this.descriptionList.getSelectedIndex());
        this.codeList.ensureIndexIsVisible(this.codeList.getSelectedIndex());
        
        this.updateUI();
    }
    
    
    @Override
    protected void myInitComponents() {
        this.initComponents();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void LoadData() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.loadDataByName("");
    }

    @Override
    public void setComponetsColor(Color transparent) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.addButton.setBackground(transparent);
        this.backButton.setBackground(transparent);
        this.categoryList.setBackground(transparent);
        this.categoryScrollPane.setBackground(transparent);
        this.currentList.setBackground(transparent);
        this.currentScrollPane.setBackground(transparent);
        this.deductButton.setBackground(transparent);
        this.descriptionList.setBackground(transparent);
        this.descriptionScrollPane.setBackground(transparent);
        this.dropButton.setBackground(transparent);
        this.editButton.setBackground(transparent);
        this.expireDateList.setBackground(transparent);
        this.expireDateScrollPane.setBackground(transparent);
        this.modelList.setBackground(transparent);
        this.modelScrollPane.setBackground(transparent);
        this.varietyNameList.setBackground(transparent);
        this.varietyNameScrollPane.setBackground(transparent);
        this.packageList.setBackground(transparent);
        this.packageScrollPane.setBackground(transparent);
        this.priceList.setBackground(transparent);
        this.packageScrollPane.setBackground(transparent);
        this.priceList.setBackground(transparent);
        this.priceScrollPane.setBackground(transparent);
        this.registerButton.setBackground(transparent);
        this.swapButton.setBackground(transparent);
//        this.totalPriceList.setBackground(transparent);
//        this.totalPriceScrollPane.setBackground(transparent);
    }
    
    private void doubleClickOnListExceptModelAndCategory(java.awt.event.MouseEvent evt){
        if(evt.getClickCount()==2){
            if(this.varietyNameList.getSelectedIndex()>=0){
                if(inventory.core.MainFrame.role == inventory.core.ProjectBOMStockMain.roles.indexOf("Admin")){
                    editPerform();
                }else if(inventory.core.MainFrame.role == inventory.core.ProjectBOMStockMain.roles.indexOf("User")){
                    deductPerformed();
                }
            }
        }
    }
    
    private void editPerform(){
        if(this.modelList.getSelectedIndex() >= 0){
            inventory.itemPage.ItemUpdate p = new inventory.itemPage.ItemUpdate();
            p.setElements(this.item_ids.get(this.modelList.getSelectedIndex()),this.variety_id.get(this.varietyNameList.getSelectedIndex()),this.categoryList.getSelectedValue().toString(),this.modelList.getSelectedValue().toString(),this.nationArrayList.get(this.priceList.getSelectedIndex()),this.packageList.getSelectedValue().toString());
            
            if(inventory.core.ProjectBOMStockMain.display != null){
                inventory.core.ProjectBOMStockMain.display.dispose();
            }
            inventory.core.ProjectBOMStockMain.display = new inventory.core.ShowingFrame(p, "Edit");
            inventory.core.ProjectBOMStockMain.display.setVisible(true);
        }
    }
    
    private void deductPerformed(){
        if(this.varietyNameList.getSelectedIndex() >= 0){
            inventory.itemPage.ItemChange p = new inventory.itemPage.ItemChange();
            p.setElements(this.item_ids.get(this.varietyNameList.getSelectedIndex()),this.categoryList.getSelectedValue().toString(),this.modelList.getSelectedValue().toString(),this.nationArrayList.get(this.priceList.getSelectedIndex()),this.packageList.getSelectedValue().toString(),"Deduct");
            if(inventory.core.ProjectBOMStockMain.display != null){
                inventory.core.ProjectBOMStockMain.display.dispose();
            }        
            inventory.core.ProjectBOMStockMain.display = new inventory.core.ShowingFrame(p, "Deduct");
            inventory.core.ProjectBOMStockMain.display.setVisible(true);
        }
    }
    
    private void orderByLabelClick(java.awt.event.MouseEvent evt){
        if(evt.getClickCount()>1){
            this.order = "DESC";
        }else{
            this.order = "ASC";
        }
    }
    
    private void categorySingleClicked(java.awt.event.MouseEvent evt){
        if(evt.getSource() instanceof javax.swing.JList){
            try {
                if(((javax.swing.JList)evt.getSource()).getSelectedIndex() < 0){
                    return;
                }
                Integer id = this.category_id.get(((javax.swing.JList)evt.getSource()).getSelectedIndex());
                
                this.variety_id = new ArrayList<Integer>();
                this.varietyNameArrayList = new ArrayList<String>();
                
                String sql = null;
                
                sql = "SELECT * FROM inventory.variety where category_id = "+id+";";
                
                ResultSet rs = inventory.core.DBConnection.executeQuery(sql);
                
                while(rs.next()){
                    if(rs.getInt("disable_id") != 1){
                        continue;
                    }
                    
                    this.variety_id.add(rs.getInt("id"));
                    String str = rs.getString("name");
                    this.varietyNameArrayList.add(str);
                }
                
                this.varietyNameList.setListData(this.varietyNameArrayList.toArray());
    //            this.category_id.get(((javax.swing.JList)evt.getSource()).getSelectedIndex());
            } catch (SQLException ex) {
                Logger.getLogger(ItemManageSwap.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private void varietyNameSingleClicked(){
        if(true){
            try {
                this.item_ids = new ArrayList<>();
                this.modelNameArrayList =  new ArrayList<>();
                this.packageArrayList =  new ArrayList<>();
                this.priceArrayList = new ArrayList<>();
                this.currentArrayList =  new ArrayList<>();
                this.expireDateArrayList = new ArrayList<>();
                this.descriptionArrayList = new ArrayList<>();
                this.nationArrayList = new ArrayList<>();
                this.categoryCodeArrayList = new ArrayList<>();
                
                if(this.varietyNameList.getSelectedIndex() < 0){
                    return;
                }
                Integer id = this.variety_id.get(varietyNameList.getSelectedIndex());
                
                this.item_ids = new ArrayList<Integer>();
                ArrayList<String> priceOnListArrayList = new ArrayList<>();
        
                String sql = null;
                
                sql = "SELECT item.id,item.name as itemname, CONCAT(category.code, LPAD(variety.varietyNumber,2,'0'), LPAD(item.itemNumber,3,'0')) as wcode, category.name as categoryname, model.name as modelname, package.count, item.price, nation.name as nationname, item.current, item.price*item.current as total, item.expiredate, item.description, item.disable_id FROM inventory.item as item join inventory.nation as nation join inventory.package as package join inventory.model as model join inventory.category as category join inventory.variety ON item.nation_id = nation.id and item.model_id = model.id and item.package_id = package.id and inventory.item.category_id = inventory.category.id and item.variety_id = variety.id and item.variety_id"
                        + " = "+id+" and item.disable_id = 1 where "+this.searchSubject+" like '%"+this.pastName+"%' order by "+order_by+" "+order+";";
                
                ResultSet rs = inventory.core.DBConnection.executeQuery(sql);
                
                if(rs != null){
                    while(rs.next()){
                        if(rs.getInt("disable_id") != 1){
                            continue;
                        }
                        this.item_ids.add(rs.getInt("id"));
                        this.categoryCodeArrayList.add(rs.getString("wcode"));
                        this.modelNameArrayList.add(rs.getString("modelname"));
                        this.packageArrayList.add(rs.getInt("count"));
                        this.priceArrayList.add(rs.getFloat("price"));
                        this.nationArrayList.add(rs.getString("nationname"));
                        String priceAndNation = null;
                        switch (this.nationArrayList.get(this.nationArrayList.size()-1)) {
                            case "South Korea":
                                priceAndNation = (this.priceArrayList.get(this.priceArrayList.size()-1))+" W";
                                break;
                            case "Malawi":
                                priceAndNation = (this.priceArrayList.get(this.priceArrayList.size()-1))+" MK";
                                break;
                        }
                        priceOnListArrayList.add(priceAndNation);
                        
                        this.currentArrayList.add(rs.getInt("current"));
                        this.expireDateArrayList.add(rs.getDate("expiredate"));
                        this.descriptionArrayList.add(rs.getString("description"));
                    }
                }
                
                this.modelScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
                this.packageScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
                this.priceScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
                this.currentScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
        //        this.totalPriceScrollPane.getVerticalScrollBar().setModel(this.nameScrollPane.getVerticalScrollBar().getModel());
                this.expireDateScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
                this.descriptionScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
                this.codeScrollPane.getVerticalScrollBar().setModel(this.descriptionScrollPane.getVerticalScrollBar().getModel());
                
                this.descriptionScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener() {

                    @Override
                    public void adjustmentValueChanged(AdjustmentEvent e) {
                        ItemManageSwap.this.updateUI();
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });

                this.modelList.setListData(this.modelNameArrayList.toArray());
                this.packageList.setListData(this.packageArrayList.toArray());
                this.priceList.setListData(priceOnListArrayList.toArray());
                this.currentList.setListData(this.currentArrayList.toArray());
        //        this.totalPriceList.setListData(totalPriceOnListArrayList.toArray());
                this.expireDateList.setListData(this.expireDateArrayList.toArray());
                this.descriptionList.setListData(this.descriptionArrayList.toArray());
                this.codeList.setListData(this.categoryCodeArrayList.toArray());

                this.expireDateList.setCellRenderer(new MyExpireDateCellRenderer());
            } catch (SQLException ex) {
                Logger.getLogger(ItemManageSwap.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private void changeCategory(){
        String empty[] = {};
        this.modelList.setListData(empty);
        this.packageList.setListData(empty);
        this.priceList.setListData(empty);
        this.currentList.setListData(empty);
        this.expireDateList.setListData(empty);
        this.descriptionList.setListData(empty);
        this.codeList.setListData(empty);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        varietyNameScrollPane = new javax.swing.JScrollPane();
        varietyNameList = new inventory.myClasses.MyList();
        modelScrollPane = new javax.swing.JScrollPane();
        modelList = new javax.swing.JList();
        nameLabel = new javax.swing.JLabel();
        modelLabel = new javax.swing.JLabel();
        packageScrollPane = new javax.swing.JScrollPane();
        packageList = new javax.swing.JList();
        packageLabel = new javax.swing.JLabel();
        priceScrollPane = new javax.swing.JScrollPane();
        priceList = new javax.swing.JList();
        priceLabel = new javax.swing.JLabel();
        currentScrollPane = new javax.swing.JScrollPane();
        currentList = new javax.swing.JList();
        currentLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        dropButton = new inventory.myClasses.MyButton();
        editButton = new inventory.myClasses.MyButton();
        registerButton = new inventory.myClasses.MyButton();
        expireDateScrollPane = new javax.swing.JScrollPane();
        expireDateList = new javax.swing.JList();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionList = new javax.swing.JList();
        descriptionLabel = new javax.swing.JLabel();
        expireDateLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        categoryScrollPane = new javax.swing.JScrollPane();
        categoryList = new inventory.myClasses.MyList();
        categoryLabel = new javax.swing.JLabel();
        addButton = new inventory.myClasses.MyButton();
        deductButton = new inventory.myClasses.MyButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        oneWeekColorLabel = new javax.swing.JLabel();
        twoWeekColorLabel = new javax.swing.JLabel();
        fourWeekColorLabel = new javax.swing.JLabel();
        expiredColorLabel = new javax.swing.JLabel();
        oneWeekColorButton = new inventory.myClasses.MyColorChooserButton();
        twoWeekColorButton = new inventory.myClasses.MyColorChooserButton();
        fourWeekColorButton = new inventory.myClasses.MyColorChooserButton();
        expiredColorButton = new inventory.myClasses.MyColorChooserButton();
        afterFourWeekColorLabel = new javax.swing.JLabel();
        afterFourWeekColorButton = new inventory.myClasses.MyColorChooserButton();
        remainZeroColorButton = new inventory.myClasses.MyColorChooserButton();
        remainZeroColorLabel = new javax.swing.JLabel();
        swapButton = new inventory.myClasses.MyButton();
        modeLabel = new javax.swing.JLabel();
        codeScrollPane = new javax.swing.JScrollPane();
        codeList = new inventory.myClasses.MyList();
        codeLabel = new javax.swing.JLabel();

        varietyNameScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        varietyNameScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        varietyNameScrollPane.setAutoscrolls(true);

        varietyNameList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                varietyNameListMouseClicked(evt);
            }
        });
        varietyNameList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                varietyNameListValueChanged(evt);
            }
        });
        varietyNameScrollPane.setViewportView(varietyNameList);

        modelScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        modelScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        modelList.setCellRenderer(new inventory.myClasses.MyItemManageSwapCellRenderer());
        modelList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modelListMouseClicked(evt);
            }
        });
        modelList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                modelListValueChanged(evt);
            }
        });
        modelScrollPane.setViewportView(modelList);

        nameLabel.setText("Name List");
        nameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nameLabelMouseClicked(evt);
            }
        });

        modelLabel.setText("Model");
        modelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modelLabelMouseClicked(evt);
            }
        });

        packageScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        packageScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        packageList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        packageList.setCellRenderer(new inventory.myClasses.MyItemManageSwapCellRenderer());
        packageList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                packageListMouseClicked(evt);
            }
        });
        packageList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                packageListValueChanged(evt);
            }
        });
        packageScrollPane.setViewportView(packageList);

        packageLabel.setText("Package");
        packageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                packageLabelMouseClicked(evt);
            }
        });

        priceScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        priceScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        priceList.setCellRenderer(new inventory.myClasses.MyItemManageSwapCellRenderer());
        priceList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                priceListMouseClicked(evt);
            }
        });
        priceList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                priceListValueChanged(evt);
            }
        });
        priceScrollPane.setViewportView(priceList);

        priceLabel.setText("Price");
        priceLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                priceLabelMouseClicked(evt);
            }
        });

        currentScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        currentScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        currentList.setCellRenderer(new inventory.myClasses.MyItemManageSwapCellRenderer());
        currentList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                currentListMouseClicked(evt);
            }
        });
        currentList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                currentListValueChanged(evt);
            }
        });
        currentScrollPane.setViewportView(currentList);

        currentLabel.setText("Remain");
        currentLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                currentLabelMouseClicked(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        backButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                backButtonKeyTyped(evt);
            }
        });

        dropButton.setText("Drop");
        dropButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropButtonActionPerformed(evt);
            }
        });
        dropButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dropButtonKeyTyped(evt);
            }
        });

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        editButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                editButtonKeyTyped(evt);
            }
        });

        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });
        registerButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                registerButtonKeyTyped(evt);
            }
        });

        expireDateScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        expireDateScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        expireDateList.setCellRenderer(new inventory.myClasses.MyItemManageSwapCellRenderer());
        expireDateList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expireDateListMouseClicked(evt);
            }
        });
        expireDateList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                expireDateListValueChanged(evt);
            }
        });
        expireDateScrollPane.setViewportView(expireDateList);

        descriptionScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        descriptionScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        descriptionList.setCellRenderer(new inventory.myClasses.MyItemManageCellRenderer());
        descriptionList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                descriptionListMouseClicked(evt);
            }
        });
        descriptionList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                descriptionListValueChanged(evt);
            }
        });
        descriptionScrollPane.setViewportView(descriptionList);

        descriptionLabel.setText("Description");

        expireDateLabel.setText("ExpireDate");
        expireDateLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expireDateLabelMouseClicked(evt);
            }
        });

        categoryScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        categoryScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        categoryList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                categoryListMouseClicked(evt);
            }
        });
        categoryList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                categoryListValueChanged(evt);
            }
        });
        categoryScrollPane.setViewportView(categoryList);

        categoryLabel.setText("Category");
        categoryLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                categoryLabelMouseClicked(evt);
            }
        });

        addButton.setText("Add Current");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        deductButton.setText("Deduct Current");
        deductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deductButtonActionPerformed(evt);
            }
        });

        oneWeekColorLabel.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        oneWeekColorLabel.setText("1 Week");

        twoWeekColorLabel.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        twoWeekColorLabel.setText("2 Week");

        fourWeekColorLabel.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        fourWeekColorLabel.setText("4  Week");

        expiredColorLabel.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        expiredColorLabel.setText("Expired");

        oneWeekColorButton.setBackground(java.awt.Color.RED);
        oneWeekColorButton.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        oneWeekColorButton.setToolTipText("oneWeek");

        twoWeekColorButton.setBackground(java.awt.Color.RED);
        twoWeekColorButton.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        twoWeekColorButton.setToolTipText("twoWeek");

        fourWeekColorButton.setBackground(java.awt.Color.RED);
        fourWeekColorButton.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        fourWeekColorButton.setToolTipText("fourWeek");

        expiredColorButton.setBackground(java.awt.Color.RED);
        expiredColorButton.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        expiredColorButton.setToolTipText("expired");

        afterFourWeekColorLabel.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        afterFourWeekColorLabel.setText("~4  Week");

        afterFourWeekColorButton.setBackground(java.awt.Color.RED);
        afterFourWeekColorButton.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        afterFourWeekColorButton.setToolTipText("afterFourWeek");

        remainZeroColorButton.setBackground(java.awt.Color.RED);
        remainZeroColorButton.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        remainZeroColorButton.setToolTipText("remainZero");

        remainZeroColorLabel.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        remainZeroColorLabel.setText("Reamin Zero");

        swapButton.setText("Swap Mode");
        swapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                swapButtonActionPerformed(evt);
            }
        });

        modeLabel.setText("Category Mode");

        codeScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        codeScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        codeList.setCellRenderer(new inventory.myClasses.MyItemManageSwapCellRenderer());
        codeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                codeListMouseClicked(evt);
            }
        });
        codeList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                codeListValueChanged(evt);
            }
        });
        codeScrollPane.setViewportView(codeList);

        codeLabel.setText("Code");
        codeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                codeLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(categoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(categoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(varietyNameScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modelScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(packageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(packageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(priceScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(currentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(currentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(expireDateScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(expireDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(deductButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(editButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(registerButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(twoWeekColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(afterFourWeekColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(oneWeekColorLabel)
                                                .addComponent(oneWeekColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(twoWeekColorLabel))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(afterFourWeekColorLabel)
                                                .addComponent(expiredColorLabel)
                                                .addComponent(expiredColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(fourWeekColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(fourWeekColorLabel))
                                            .addGap(12, 12, 12)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(remainZeroColorLabel)
                                                .addComponent(remainZeroColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(dropButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(modeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(swapButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel)
                            .addComponent(codeLabel)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(swapButton)
                            .addComponent(modeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(packageLabel)
                            .addComponent(modelLabel)
                            .addComponent(categoryLabel)
                            .addComponent(currentLabel)
                            .addComponent(expireDateLabel)
                            .addComponent(descriptionLabel))))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dropButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(oneWeekColorLabel)
                                    .addComponent(expiredColorLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(oneWeekColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(expiredColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(twoWeekColorLabel)
                                    .addComponent(afterFourWeekColorLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(twoWeekColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(afterFourWeekColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(remainZeroColorLabel)
                                    .addComponent(fourWeekColorLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fourWeekColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(remainZeroColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(currentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(priceScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(packageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(modelScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(expireDateScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(varietyNameScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(categoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(codeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void varietyNameListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_varietyNameListMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1){
            this.varietyNameSingleClicked();
        }
    }//GEN-LAST:event_varietyNameListMouseClicked

    private void varietyNameListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_varietyNameListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList){
            this.updateUI();
            this.varietyNameSingleClicked();
            //this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
        }
    }//GEN-LAST:event_varietyNameListValueChanged

    private void modelListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modelListMouseClicked
        // TODO add your handling code here:
        //System.out.println(evt.getClickCount());
        if(evt.getClickCount()==2){
            try {
                //this.categoryList.getSelectedValue().toString()
                String sql = "SELECT name, contact FROM inventory.model where name = '"+this.modelList.getSelectedValue().toString()+"';";
                ResultSet rs = inventory.core.DBConnection.executeQuery(sql);

                if(rs.next()){
                    inventory.modelPage.ModelChange p = new inventory.modelPage.ModelChange();
                    p.setChangeConfig(0, rs.getString("name"), rs.getInt("contact"), "");
                    p.showMode(true);

                    inventory.core.ProjectBOMStockMain.display = new inventory.core.ShowingFrame(p, "Model");
                    inventory.core.ProjectBOMStockMain.display.setVisible(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ItemManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_modelListMouseClicked

    private void modelListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_modelListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
        this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_modelListValueChanged

    private void nameLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameLabelMouseClicked
        // TODO add your handling code here:
//        orderByLabelClick(evt);
//        this.order_by = "itemname";
//        this.reLoadDataByName();
    }//GEN-LAST:event_nameLabelMouseClicked

    private void modelLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modelLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "modelname";
        this.reLoadDataByName();
    }//GEN-LAST:event_modelLabelMouseClicked

    private void packageListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_packageListMouseClicked
        // TODO add your handling code here:
        doubleClickOnListExceptModelAndCategory(evt);
    }//GEN-LAST:event_packageListMouseClicked

    private void packageListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_packageListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
        this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_packageListValueChanged

    private void packageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_packageLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "count";
        this.reLoadDataByName();
    }//GEN-LAST:event_packageLabelMouseClicked

    private void priceListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_priceListMouseClicked
        // TODO add your handling code here:
        doubleClickOnListExceptModelAndCategory(evt);
    }//GEN-LAST:event_priceListMouseClicked

    private void priceListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_priceListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
        this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_priceListValueChanged

    private void priceLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_priceLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "price";
        this.reLoadDataByName();
    }//GEN-LAST:event_priceLabelMouseClicked

    private void currentListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_currentListMouseClicked
        // TODO add your handling code here:
        doubleClickOnListExceptModelAndCategory(evt);
    }//GEN-LAST:event_currentListMouseClicked

    private void currentListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_currentListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
        this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_currentListValueChanged

    private void currentLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_currentLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "current";
        this.reLoadDataByName();
    }//GEN-LAST:event_currentLabelMouseClicked

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        if(inventory.core.MainFrame.role == 1){
            inventory.core.ProjectBOMStockMain.setPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("AdminMain"));
        }else{
            inventory.core.ProjectBOMStockMain.setPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("Login"));
        }
        if(inventory.core.ProjectBOMStockMain.display != null){
            inventory.core.ProjectBOMStockMain.display.dispose();
        }
    }//GEN-LAST:event_backButtonActionPerformed

    private void backButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_backButtonKeyTyped
        // TODO add your handling code here:
        if(evt.getKeyChar()=='\n'){
            this.backButtonActionPerformed(null);
        }
    }//GEN-LAST:event_backButtonKeyTyped

    private void dropButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropButtonActionPerformed
        // TODO add your handling code here:
        if(this.varietyNameList.getSelectedIndex()>=0){
            if(JOptionPane.showConfirmDialog(this, "This will be Deleted!!!. Are you Sure?!","Confirm",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
                String name = this.varietyNameList.getSelectedValue().toString();

                String s = JOptionPane.showInputDialog(this, "Please Type a Reason", "Drop",JOptionPane.OK_CANCEL_OPTION);

                if(s != null && !s.trim().equals("")){
                    try {
                        //INSERT INTO `inventory`.`disable` (`description`, `user_id`, `table_id`, `table_type`) VALUES ('desc', 'user_id', 'tabld_id', 'table_type');
                        String sql = "INSERT INTO `inventory`.`disable` (`description`, `user_id`, `table_id`, `table_type`) VALUES ('"+s+"', '"+inventory.core.MainFrame.user_id+"', '"+this.item_ids.get(this.varietyNameList.getSelectedIndex())+"', '"+inventory.core.ProjectBOMStockMain.table_type.indexOf("Item")+"');";
                        ResultSet rs = inventory.core.DBConnection.updateQueryGetID(sql);

                        if(rs.next()){
                            sql = "UPDATE `inventory`.`item` SET `disable_id`='"+rs.getLong(1)+"' WHERE `id`='"+this.item_ids.get(this.varietyNameList.getSelectedIndex())+"';";
                            inventory.core.DBConnection.updateQuery(sql);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ItemManage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //inventory.core.DBConnection.updateQuery("DELETE FROM `inventory`.`item` WHERE `id`='"+this.id.get(this.itemNameList.getSelectedIndex())+"';");
                this.loadDataByName("");
                JOptionPane.showMessageDialog(this, name + " was Deleted.","Alert",JOptionPane.OK_OPTION);
            }
        }
    }//GEN-LAST:event_dropButtonActionPerformed

    private void dropButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dropButtonKeyTyped
        // TODO add your handling code here:
        if(evt.getKeyChar()=='\n'){
            this.dropButtonActionPerformed(null);
        }
    }//GEN-LAST:event_dropButtonKeyTyped

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
        editPerform();
    }//GEN-LAST:event_editButtonActionPerformed

    private void editButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editButtonKeyTyped
        // TODO add your handling code here:
        if(evt.getKeyChar()=='\n'){
            this.editButtonActionPerformed(null);
        }
    }//GEN-LAST:event_editButtonKeyTyped

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        // TODO add your handling code here:
        if(this.varietyNameList.getSelectedIndex() < 0){
            JOptionPane.showMessageDialog(this, "Please Select Category and Variety", "Alert", JOptionPane.OK_OPTION);
            return;
        }
        inventory.itemPage.ItemUpdate p = new inventory.itemPage.ItemUpdate();
        p.setElements(0,this.variety_id.get(this.varietyNameList.getSelectedIndex()),"","","","");

        if(inventory.core.ProjectBOMStockMain.display != null)
        inventory.core.ProjectBOMStockMain.display.dispose();
        inventory.core.ProjectBOMStockMain.display = new inventory.core.ShowingFrame(p, "ItemUpdate");
        inventory.core.ProjectBOMStockMain.display.setVisible(true);
        //((inventory.itemPage.ItemUpdate)inventory.core.ProjectBOMStockMain.getPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("ItemUpdate"))).setElements(0,"","","","");
        //inventory.core.ProjectBOMStockMain.setPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("ItemUpdate"));
        //((inventory.itemPage.ItemUpdate)inventory.core.ProjectBOMStockMain.getPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("ItemUpdate"))).setChangeConfig(null,"",null,"Register");
    }//GEN-LAST:event_registerButtonActionPerformed

    private void registerButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_registerButtonKeyTyped
        // TODO add your handling code here:
        if(evt.getKeyChar()=='\n')
        this.registerButtonActionPerformed(null);
    }//GEN-LAST:event_registerButtonKeyTyped

    private void expireDateListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expireDateListMouseClicked
        // TODO add your handling code here:
        doubleClickOnListExceptModelAndCategory(evt);
    }//GEN-LAST:event_expireDateListMouseClicked

    private void expireDateListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_expireDateListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
        this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_expireDateListValueChanged

    private void descriptionListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_descriptionListMouseClicked
        // TODO add your handling code here:
        doubleClickOnListExceptModelAndCategory(evt);
    }//GEN-LAST:event_descriptionListMouseClicked

    private void descriptionListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_descriptionListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
        this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_descriptionListValueChanged

    private void expireDateLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expireDateLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "expiredate";
        this.reLoadDataByName();
    }//GEN-LAST:event_expireDateLabelMouseClicked

    private void categoryListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_categoryListMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1){
            this.categorySingleClicked(evt);
        }else if(evt.getClickCount()==2){
            try {
                //this.categoryList.getSelectedValue().toString()
                String sql = "SELECT name, description FROM inventory.category where name = '"+this.categoryList.getSelectedValue().toString()+"';";
                ResultSet rs = inventory.core.DBConnection.executeQuery(sql);

                if(rs.next()){
                    inventory.categoryPage.CategoryEdit p = new inventory.categoryPage.CategoryEdit();
                    p.setEditConfig(0, rs.getString("name"), rs.getString("description"),rs.getString("code"));
                    p.showMode(true);

                    inventory.core.ProjectBOMStockMain.display = new inventory.core.ShowingFrame(p, "Category");
                    inventory.core.ProjectBOMStockMain.display.setVisible(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ItemManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_categoryListMouseClicked

    private void categoryListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_categoryListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList){
            this.updateUI();
            changeCategory();
            
            //this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
        }
    }//GEN-LAST:event_categoryListValueChanged

    private void categoryLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_categoryLabelMouseClicked
        // TODO add your handling code here:
//        orderByLabelClick(evt);
//        this.order_by = "categoryname";
//        this.reLoadDataByName();
    }//GEN-LAST:event_categoryLabelMouseClicked

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        if(this.varietyNameList.getSelectedIndex() >= 0){
            inventory.itemPage.ItemChange p = new inventory.itemPage.ItemChange();
            p.setElements(this.item_ids.get(this.varietyNameList.getSelectedIndex()),this.categoryList.getSelectedValue().toString(),this.modelList.getSelectedValue().toString(),this.nationArrayList.get(this.priceList.getSelectedIndex()),this.packageList.getSelectedValue().toString(),"Add");
            if(inventory.core.ProjectBOMStockMain.display != null){
                inventory.core.ProjectBOMStockMain.display.dispose();
            }
            inventory.core.ProjectBOMStockMain.display = new inventory.core.ShowingFrame(p, "Add");
            inventory.core.ProjectBOMStockMain.display.setVisible(true);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void deductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deductButtonActionPerformed
        // TODO add your handling code here:
        deductPerformed();
    }//GEN-LAST:event_deductButtonActionPerformed

    private void swapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swapButtonActionPerformed
        // TODO add your handling code here:
        ((inventory.itemPage.ItemManage)inventory.core.ProjectBOMStockMain.getPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("ItemManage"))).LoadData();
        
        inventory.core.ProjectBOMStockMain.setPage(inventory.core.ProjectBOMStockMain.PageList.indexOf("ItemManage"));
        
        if(inventory.core.ProjectBOMStockMain.display != null){
            inventory.core.ProjectBOMStockMain.display.dispose();
        }
    }//GEN-LAST:event_swapButtonActionPerformed

    private void codeListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_codeListValueChanged
        // TODO add your handling code here:
        if(evt.getSource() instanceof javax.swing.JList)
        this.listChanged(((javax.swing.JList)evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_codeListValueChanged

    private void codeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_codeLabelMouseClicked
        // TODO add your handling code here:
        orderByLabelClick(evt);
        this.order_by = "wcode";
        this.reLoadDataByName();
    }//GEN-LAST:event_codeLabelMouseClicked

    private void codeListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_codeListMouseClicked
        // TODO add your handling code here:
        doubleClickOnListExceptModelAndCategory(evt);
    }//GEN-LAST:event_codeListMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton afterFourWeekColorButton;
    private javax.swing.JLabel afterFourWeekColorLabel;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JList categoryList;
    private javax.swing.JScrollPane categoryScrollPane;
    private javax.swing.JLabel codeLabel;
    private javax.swing.JList codeList;
    private javax.swing.JScrollPane codeScrollPane;
    private javax.swing.JLabel currentLabel;
    private javax.swing.JList currentList;
    private javax.swing.JScrollPane currentScrollPane;
    private javax.swing.JButton deductButton;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JList descriptionList;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JButton dropButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel expireDateLabel;
    private javax.swing.JList expireDateList;
    private javax.swing.JScrollPane expireDateScrollPane;
    private javax.swing.JButton expiredColorButton;
    private javax.swing.JLabel expiredColorLabel;
    private javax.swing.JButton fourWeekColorButton;
    private javax.swing.JLabel fourWeekColorLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel modeLabel;
    private javax.swing.JLabel modelLabel;
    private javax.swing.JList modelList;
    private javax.swing.JScrollPane modelScrollPane;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton oneWeekColorButton;
    private javax.swing.JLabel oneWeekColorLabel;
    private javax.swing.JLabel packageLabel;
    private javax.swing.JList packageList;
    private javax.swing.JScrollPane packageScrollPane;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JList priceList;
    private javax.swing.JScrollPane priceScrollPane;
    private javax.swing.JButton registerButton;
    private javax.swing.JButton remainZeroColorButton;
    private javax.swing.JLabel remainZeroColorLabel;
    private javax.swing.JButton swapButton;
    private javax.swing.JButton twoWeekColorButton;
    private javax.swing.JLabel twoWeekColorLabel;
    private javax.swing.JList varietyNameList;
    private javax.swing.JScrollPane varietyNameScrollPane;
    // End of variables declaration//GEN-END:variables
}
