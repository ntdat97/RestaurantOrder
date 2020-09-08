
package controller;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.util.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class lookup extends javax.swing.JFrame {
    private JPanel headerpa,center,staffpanel,billpanel,billpaneltrum,staffsearch;
    private JTable stafftb,searchstaff;
    private JScrollPane centersc;
    private JLabel showla,lookupla,lookfor/*,lbilldate,lfoname,lqty,lamount,ltt,ltbname,lstaffname,ldis
    ,lbilldate1,lfoname1,lqty1,lamount1,ltt1,lstaffname1,ldis1,lnamebill,lqtybill,lprice,brl*/;
    private JComboBox cb1;
    private JButton back;
   /* private JTextField lookuptx;
    private ButtonGroup bg,bg1;
    private JRadioButton r1,r2,r3,r4,r5,r6,r7;*/
    private String tbname,namebill,staffname;
    private int qtybill;
    private float price,dis,tt;
    private Date billdate;
    private String getname;
    //private int index =0;
    Connection conn = null;
    PreparedStatement ps = null; 
    ResultSet rs = null;
    public lookup(String getname){
        this.getname=getname;
        setPreferredSize(new Dimension(670, 500));
        setLayout(new BorderLayout());
        mysqlconnect b;
        b = new mysqlconnect();
        conn=b.getConnection();
        header();
        setTitle("Tìm kiếm");
         
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void header(){
        headerpa = new JPanel();
        headerpa.setPreferredSize(new Dimension(670, 70));
        headerpa.setLayout(new FlowLayout());
        showla = new JLabel("Hiển thị thông tin");
        cb1 =  new JComboBox();
        cb1.setName("cb1");
        cb1.setPreferredSize(new Dimension(100, 25));
        cb1.addItem("Unselected");
        cb1.addItem("Nhân viên");
        cb1.addItem("Món ăn");
        cb1.addItem("Hóa đơn");
        cb1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
               
                int index = 0;
                index = cb1.getSelectedIndex();
                showstaff(index);
                validate();
                repaint();
            }
            
        });
       /* lookupla = new JLabel("Tìm kiếm thông tin");
        lookuptx =  new JTextField();
        lookuptx.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
             search();  
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            search();              
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();  
            }
            
        });*/
        back = new JButton("Back");
        back.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
             new homepage1(getname).setVisible(true);
                dispose();
            }
        
    });
        
        /*r1 = new JRadioButton("Tất cả");
        r1.setBounds(100,50,100,30); 
        r2 = new JRadioButton("In use");
        r2.setBounds(100,50,100,30); 
        r3 = new JRadioButton("Đã xóa");
        r3.setBounds(100,50,100,30); 
        bg = new ButtonGroup();
        bg.add(r1);bg.add(r2);bg.add(r3);
        lookfor = new JLabel("Tìm kiếm trong: ");
        r4 = new JRadioButton("Tất cả");
        r4.setBounds(100,50,100,30); 
        r5 = new JRadioButton("Nhân viên");
        r5.setBounds(100,50,100,30); 
        r6 = new JRadioButton("Món ăn");
        r6.setBounds(100,50,100,30); 
        r7 = new JRadioButton("Hóa đơn");
        r7.setBounds(100,50,100,30); 
        bg1 = new ButtonGroup();
        bg1.add(r4);bg1.add(r5);bg1.add(r6);bg1.add(r7);
        JLabel br = new JLabel("|  ");
        JLabel br1 = new JLabel("|  ");
        br1.setFont(br1.getFont().deriveFont(18.0f));
        br.setFont(br.getFont().deriveFont(18.0f));*/
        /* -- */
       /* headerpa.add(lookupla);
        headerpa.add(lookuptx);
        headerpa.add(find);
        headerpa.add(br);*/headerpa.add(back);
        headerpa.add(showla);
        headerpa.add(cb1);
        /*headerpa.add(r1);
        headerpa.add(r2);
        headerpa.add(r3);
        headerpa.add(br1);
        headerpa.add(lookfor);
        headerpa.add(r4);
        headerpa.add(r5);
        headerpa.add(r6);
        headerpa.add(r7);*/
        add(headerpa, BorderLayout.PAGE_START);
        pack();
    }
    
    public void showstaff(int index){
        center = new JPanel();
        center.setLayout(new FlowLayout());
        //center.setMaximumSize(new Dimension(200,200));
         center.setPreferredSize(new Dimension(650, 2000));
        //center.setAlignmentY(Component.LEFT_ALIGNMENT);
       // center.FlowDirection = FlowDirection.TopDown;
    //center.WrapContents = false;
    //    center.AutoScroll = true;
        
        staffpanel = new JPanel();
        /* -- */
        stafftb = new JTable();
         try{
             String sqlst = "select * from staffid";
            ps=conn.prepareStatement(sqlst);
            rs=ps.executeQuery();
            stafftb.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
         staffpanel.add(stafftb);
        /* -- */
         billpaneltrum = new JPanel();
         billpaneltrum.setPreferredSize(new Dimension(650, 2000));
         int f=1; 
        String sqlbill,sqlbll1,sqlorder;
        sqlbll1 = "select count(distinct orderidh) from bill";
        try{
            ps=conn.prepareStatement(sqlbll1);
            rs=ps.executeQuery();
            if(rs.next()){
               f = rs.getInt(1);
            }
       } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
       }
        int[] order = new int[200];
        sqlorder = "select distinct orderidh from bill order by orderidh";
        try{
            ps=conn.prepareStatement(sqlorder);
            rs=ps.executeQuery();
            for(int r=0;r<=f;r++){
              if(rs.next()){
                  order[r]=rs.getInt(1);}
            }
       } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
       }
        //System.out.print(order[1]);
        //
        for(int i=0;i<=f;i++){
           int j=order[i];
        sqlbill = "select * from bill where orderidh='"+j+"'";
         try{
            ps=conn.prepareStatement(sqlbill);
            rs=ps.executeQuery();
            if(rs.next()){                  
                billdate = rs.getDate(4);
                tbname = rs.getString(9);
                billdate = rs.getDate(4);
                namebill = rs.getString(5);
                qtybill = rs.getInt(6);
                price = rs.getFloat(7);
                dis = rs.getFloat(11);
                tt = rs.getFloat(8);
                staffname = rs.getString(10);
          billpanel = new JPanel();
         billpanel.setLayout(new FlowLayout());
         billpanel.setPreferredSize(new Dimension(305,300));
         billpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
         JLabel ltbname = new JLabel(tbname);
         ltbname.setPreferredSize(new Dimension(200,20));
         JLabel lbilldate = new JLabel("Ngày in hóa đơn:");
         lbilldate.setPreferredSize(new Dimension(140,20));
         JLabel lbilldate1 = new JLabel(String.valueOf(billdate));
         lbilldate1.setPreferredSize(new Dimension(140,20));
          JLabel lnamebill = new JLabel("Tên món");
         lnamebill.setPreferredSize(new Dimension(125,20));
         JLabel lqtybill = new JLabel("Số lượng");
         lqtybill.setPreferredSize(new Dimension(55,20));
        JLabel  lprice = new JLabel("Giá tiền");
         lprice.setPreferredSize(new Dimension(90,20));
        JLabel lfoname = new JLabel(namebill);
         lfoname.setPreferredSize(new Dimension(125,20));
       JLabel  lqty = new JLabel(String.valueOf(qtybill));
         lqty.setPreferredSize(new Dimension(55,20));
        JLabel lamount = new JLabel(String.valueOf(price));
         lamount.setPreferredSize(new Dimension(90,20));
                billpanel.add(ltbname);billpanel.add(lbilldate);billpanel.add(lbilldate1);billpanel.add(lnamebill);
         billpanel.add(lqtybill);billpanel.add(lprice);billpanel.add(lfoname);billpanel.add(lqty);
         billpanel.add(lamount);
         while(rs.next()){
        namebill = rs.getString(5);
                qtybill = rs.getInt(6);
                price = rs.getFloat(7);
        JLabel lfoname2 = new JLabel(namebill);
         lfoname2.setPreferredSize(new Dimension(125,20));
       JLabel  lqty2 = new JLabel(String.valueOf(qtybill));
         lqty2.setPreferredSize(new Dimension(55,20));
        JLabel lamount2 = new JLabel(String.valueOf(price));
         lamount2.setPreferredSize(new Dimension(90,20));
         billpanel.add(lfoname2);billpanel.add(lqty2);
         billpanel.add(lamount2);
         }
        JLabel brl = new JLabel("----------------------------------------------------------------------");
         brl.setPreferredSize(new Dimension(300,20));
         JLabel ldis = new JLabel("Giảm giá");
         ldis.setPreferredSize(new Dimension(140,20));
         JLabel ldis1 = new JLabel(String.valueOf(dis));
         ldis1.setPreferredSize(new Dimension(140,20));
         JLabel ltt = new JLabel("Tổng hóa đơn");
         ltt.setPreferredSize(new Dimension(140,20));
         JLabel ltt1 = new JLabel(String.valueOf(tt));
         ltt1.setPreferredSize(new Dimension(140,20));
         JLabel lstaffname = new JLabel("Nhân viên");
         lstaffname.setPreferredSize(new Dimension(140,20));
         JLabel lstaffname1 = new JLabel(staffname);
         lstaffname1.setPreferredSize(new Dimension(140,20));
         billpanel.add(brl);billpanel.add(ldis);billpanel.add(ldis1);billpanel.add(ltt);
         billpanel.add(ltt1);billpanel.add(lstaffname);billpanel.add(lstaffname1); 
            }
         billpaneltrum.add(billpanel);
            
       } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
       }}
       
         /* -- */
        
        center.add(staffpanel);
        center.add(billpaneltrum);
       centersc = new JScrollPane();
       centersc.setViewportView(center);
       centersc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(centersc, BorderLayout.CENTER);
        staffpanel.setVisible(false);
        billpaneltrum.setVisible(false);
        if(index==1){
            staffpanel.setVisible(true);

        }
         if(index==3){
              staffpanel.setVisible(false);
            billpaneltrum.setVisible(true);
        }
         repaint();
        pack();
    }
   /* public void search(){
        System.out.print("a");
        staffsearch = new JPanel();
        String sqlsear;
        sqlsear = "select * from staffid where concat(staffid, '',staffname, '' , passwordd, '' ,datecreate, '' ,position,'',birthday,''\n" +
",gender,'',del) like ?";
        searchstaff = new JTable(); 
         try{
            ps=conn.prepareStatement(sqlsear);
            ps.setString(1,lookuptx.getText());
            rs=ps.executeQuery();
            searchstaff.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
         staffsearch.add(searchstaff);
         center.add(staffsearch);
    }*/
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
            java.util.logging.Logger.getLogger(homepage1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(homepage1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(homepage1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(homepage1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String a=null;
                new lookup(a).setVisible(true);
            }
        });}
}
