/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import WealthManagement.*;
import static GUI.MergeSort.mergeSort;
import java.awt.Color;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WealthManagerForm extends javax.swing.JFrame {
    Vector<Asset> assets;
    PaintPanel valuePaintPanel;
    PaintPanel debtPaintPanel;
    private double totalValue;
    private double totalDebt;
    private double netWorth;
    WealthManagerForm wmf;
    
    /**
     * Creates new form WealthManagerForm
     */
    public WealthManagerForm() {
        assets = new Vector<Asset>();
        
        initComponents();
        
        totalValue = 0;
        totalDebt = 0;
        netWorth = 0;
        wmf = this;
        
        valuePaintPanel = new PaintPanel(valuePanel.getBackground(), Color.GREEN, valuePanel.getSize().width, valuePanel.getSize().height);
        valuePaintPanel.setLocation(0, 0);
        valuePaintPanel.setSize(valuePanel.getSize());
        valuePanel.add(valuePaintPanel);
        
        debtPaintPanel = new PaintPanel(debtPanel.getBackground(), Color.RED, debtPanel.getSize().width, debtPanel.getSize().height);
        debtPaintPanel.setLocation(0, 0);
        debtPaintPanel.setSize(debtPanel.getSize());
        debtPanel.add(debtPaintPanel);
        
        JMenuItem item = this.newMenu.add("Bank Account");
        item.setActionCommand("Bank Account");
        item.addActionListener(new MenuActionListener());
        
        
        JMenuItem item1 = this.newMenu.add("Stock");
        item1.setActionCommand("Stock");
        item1.addActionListener(new MenuActionListener());
        
        JMenuItem item2 = this.newMenu.add("Property");
        item2.setActionCommand("Property");
        item2.addActionListener(new MenuActionListener());
        
        JMenuItem item3 = this.newMenu.add("Sort");
        item3.setActionCommand("Sort");
        item3.addActionListener(new MenuActionListener());

        this.AssetsList.setListData(assets);
        this.AssetsList.addListSelectionListener(new ListListener());
        this.AssetsList.addMouseListener(new DoubleClickListener());
        this.AssetsList.addKeyListener(new KeyPressListener(this));
        
    }
    
    public void UpdateValue(double v){
    totalValue += v;
    netWorth += v;
    
    valueLabel.setText(String.format("$%.2f", totalValue));
    netWorthValue.setText(String.format("$%.2f", netWorth));
    
    valuePaintPanel.setPercent(totalValue/(totalValue+totalDebt));
}
    
    public void UpdateDebt(double d){
    totalDebt += d;
    netWorth -= d;
    
    debtLabel.setText(String.format("-$%.2f", totalDebt));
    netWorthValue.setText(String.format("$%.2f", netWorth));
    
    debtPaintPanel.setPercent(totalDebt/(totalValue+totalDebt));
}
    
    public void updateBars(){
        valuePaintPanel.setPercent(totalValue/(totalValue+totalDebt));
        debtPaintPanel.setPercent(totalDebt/(totalValue+totalDebt));
    }
    
    
    public class MenuActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        { 
            switch (e.getActionCommand())
            {   
            case "Bank Account":
                BankAccountForm baf = new BankAccountForm(assets, AssetsList, wmf);
                baf.setVisible(true);
                break;
            case "Stock":
                StockForm sf = new StockForm(assets, AssetsList, wmf);
                sf.setVisible(true);
                break;
            case "Property":
                PropertyForm pf = new PropertyForm(assets, AssetsList, wmf);
                pf.setVisible(true);
                break;
            case "Sort":
                assets = mergeSort(assets);
                AssetsList.setListData(assets);
                break;
            }
        }
    }
    
    public class ListListener implements ListSelectionListener
    {

        @Override
        public void valueChanged(ListSelectionEvent e) {
           
            
            int selectedIndex = ((JList)e.getSource()).getSelectedIndex();
            
            if (selectedIndex < 0 || selectedIndex >= assets.size())
                return;
            
            Asset selectedAsset = assets.get(selectedIndex);
            
            detailTextArea.setText(selectedAsset.getDetailString());
        }
        
    }
    
    public class DoubleClickListener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            
            if (e.getClickCount() != 2 || assets.isEmpty()) // ignore single clicks
                return;
            
            int index = AssetsList.locationToIndex(e.getPoint());
            Asset clicked = assets.get(index);
            
            if (clicked instanceof BankAccount)
            {
                System.out.println("BankAccount");
                BankAccountForm baf = new BankAccountForm((BankAccount)clicked, detailTextArea, wmf);
                baf.setVisible(true);
            }
            else if (clicked instanceof Property)
            {
                String typeBox = "";
                if(clicked instanceof Car)
                    typeBox = "Car";
                else if(clicked instanceof House)
                    typeBox = "House";    
                System.out.println("Property");
                PropertyForm baf = new PropertyForm((Property)clicked, detailTextArea, typeBox, wmf);
                baf.setVisible(true);
                
            }
            else if (clicked instanceof Stock)
            {
                System.out.println("Stock");
                StockForm baf = new StockForm((Stock)clicked, detailTextArea, wmf);
                baf.setVisible(true);
            }
        }    
        
        

        // unused methods
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
    
    public class KeyPressListener implements KeyListener
    {
        JFrame parent;
        
        public KeyPressListener(JFrame parent)
        {
            this.parent = parent;
        }
        
        @Override
        public void keyReleased(KeyEvent e) 
        {
            if (e.getKeyChar() != 'd' || assets.isEmpty())
                return;

            int choice = JOptionPane.showConfirmDialog(
                parent,
                "Delete?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (choice == 0)
            {
                int selectedIndex = ((JList)e.getSource()).getSelectedIndex()-1;
                Asset toDelete = assets.get(selectedIndex);
                
                UpdateValue(-toDelete.getAssetValue());
                
                if (toDelete instanceof Property){
                    Property p = (Property)toDelete;
                    UpdateDebt(-p.getDebtAmount());
                }
                    
                
                assets.remove(selectedIndex);
                AssetsList.setListData(assets);
                detailTextArea.setText("");
            }
        }  
        
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {}  
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        assetLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AssetsList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        detailTextArea = new javax.swing.JTextArea();
        assetDetailLabel = new javax.swing.JLabel();
        valuePanel = new javax.swing.JPanel();
        valueLabel = new javax.swing.JLabel();
        netWorthLabel = new javax.swing.JLabel();
        netWorthValue = new javax.swing.JLabel();
        debtPanel = new javax.swing.JPanel();
        debtLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        newMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wealth Manager");

        assetLabel.setText("Assets");

        AssetsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(AssetsList);

        detailTextArea.setEditable(false);
        detailTextArea.setColumns(20);
        detailTextArea.setRows(5);
        jScrollPane2.setViewportView(detailTextArea);

        assetDetailLabel.setText("Asset Detail");

        valueLabel.setText("Value");

        javax.swing.GroupLayout valuePanelLayout = new javax.swing.GroupLayout(valuePanel);
        valuePanel.setLayout(valuePanelLayout);
        valuePanelLayout.setHorizontalGroup(
            valuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(valuePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(valueLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        valuePanelLayout.setVerticalGroup(
            valuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(valuePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(valueLabel)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        netWorthLabel.setText("Net Worth: ");

        netWorthValue.setText("$0.00");

        debtLabel.setText("Debt");

        javax.swing.GroupLayout debtPanelLayout = new javax.swing.GroupLayout(debtPanel);
        debtPanel.setLayout(debtPanelLayout);
        debtPanelLayout.setHorizontalGroup(
            debtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(debtPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(debtLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        debtPanelLayout.setVerticalGroup(
            debtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(debtPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(debtLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        newMenu.setText("New");
        menuBar.add(newMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assetLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(assetDetailLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(debtPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(valuePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(netWorthLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(netWorthValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(assetLabel)
                    .addComponent(assetDetailLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valuePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(debtPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(netWorthLabel)
                            .addComponent(netWorthValue))
                        .addGap(36, 123, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(WealthManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(WealthManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(WealthManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(WealthManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new WealthManagerForm().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList AssetsList;
    private javax.swing.JLabel assetDetailLabel;
    private javax.swing.JLabel assetLabel;
    private javax.swing.JLabel debtLabel;
    private javax.swing.JPanel debtPanel;
    private javax.swing.JTextArea detailTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel netWorthLabel;
    private javax.swing.JLabel netWorthValue;
    private javax.swing.JMenu newMenu;
    private javax.swing.JLabel valueLabel;
    private javax.swing.JPanel valuePanel;
    // End of variables declaration//GEN-END:variables
}
