/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class showdes extends javax.swing.JFrame implements ActionListener {
    Connection conn = null;
    Connection conn1 = null;
    PreparedStatement ps = null; 
    ResultSet rs = null;
    PreparedStatement ps1 = null; 
    ResultSet rs1 = null;
    private String getname = null;
    private final String idtable,stat;
    private JTabbedPane tab;
    private JButton button,confirmbt,chorn,ttbt,backbt;
    private JPanel foodit,drinkit,selectedit,other,footer,header,all;
    private JLabel stfn,sear,qtylabel,labelname,labelqty,stf,ttprn,ttpr,ttcn,ttc,brl,tabn,brl2,jlabel;
    private JTextField searc;
    private JScrollPane sr,selec,srf,srd,sro;
    public showdes(String idtable, String getname,String stat){
        this.getname = getname;
        this.idtable=idtable;
        this.stat=stat;
        setSize(750,500);
      //  setPreferredSize(new Dimension(600, 500));
        this.setLocationRelativeTo(null);
        setname();
        setTitle("Gọi món");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        }
    public void selectedit(){
        selectedit = new JPanel();
        selectedit.setPreferredSize(new Dimension(200,1300));
        selectedit.setLayout(new FlowLayout()); 
        labelname = new JLabel("Tên món     ");
        labelqty = new JLabel("Số lượng");
        selectedit.add(labelname);
        selectedit.add(labelqty);
        selectedit.repaint();
         selec = new  JScrollPane();
         selec.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       selec.setViewportView(selectedit);
    }
    public void fetchorder(){
        if("2".equals(stat)){
            mysqlconnect a;
        a = new mysqlconnect();
        Connection conn=a.getConnection();
        String sqlfe;
        sqlfe ="select itemid,itemqty from orderdetail where tableid='"+idtable+"'";
        String foodname = null;
         try{
            ps=conn.prepareStatement(sqlfe);
            rs=ps.executeQuery();
            while(rs.next()){
                 String sqlfew="select returnamefo(?)";
                          try(PreparedStatement stmtfe = conn.prepareStatement(sqlfew)){
                                    stmtfe.setString(1, rs.getString(1));
                                    ResultSet rsfe = stmtfe.executeQuery();
                                    while(rsfe.next()){
                                        foodname=rsfe.getString(1);
                                    }
                                } catch (SQLException ex) {
                            Logger.getLogger(showdes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                final String foodid = rs.getString(1);
                button = new  JButton("<html><center>"+foodname+"</center></html>");
                button.setName(foodid);
                button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    deletebt(foodid);
                    ttclb();
                    updateprice();
                    validate();
                }
            });
                button.setPreferredSize( new Dimension(100, 50) );
                selectedit.add(button);
                jlabel = new JLabel();
                jlabel.setName(foodid);
                jlabel.setText(String.valueOf(rs.getInt(2)));
                selectedit.add(jlabel);
            }
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
         ttclb();
         updateprice();
        }
    }
    public void updateprice(){
        float temp=0;
        mysqlconnect a;
        a = new mysqlconnect();
        Connection conn=a.getConnection();
        String sql; 
         int len = selectedit.getComponentCount(); 
         for(int i=1;i<len;i++){
             Component comp = selectedit.getComponent(i);
             if(len==2){
                 int lenqty1 = footer.getComponentCount();
                 for(int j=0;j<lenqty1;j++){
                     Component compqty = footer.getComponent(j);
                     if(compqty instanceof JLabel){
                         JLabel c = (JLabel) compqty;
                         String ttcc = "ttpr";
                         if(ttcc.equals(c.getName())){
                             c.setText("0");
                             validate();
                         }
                     }
                 }
             }
             if(comp instanceof JButton){
                 JButton b = (JButton) comp;
                 String currentname = b.getName();
                 float currentprice=0;
                 sql = "select * from menuitem where itemid='"+currentname+"'";
                  try{
                      ps=conn.prepareStatement(sql);
                      rs=ps.executeQuery();
                      while(rs.next()){
                          currentprice=rs.getFloat(4);
                        }
                        } catch(SQLException | HeadlessException e){
                            JOptionPane.showMessageDialog(null, e);
                          }
                  JLabel l = (JLabel) selectedit.getComponent(i+1);
                  String currentqty = l.getText();
                 int currentqtyint = Integer.parseInt(currentqty);
                 temp=temp+(currentqtyint*currentprice);
             }
             if(i==(len-1) && len!=2){
                
                 int lenqty = footer.getComponentCount();
                 for(int j=0;j<lenqty;j++){
                     Component compqty = footer.getComponent(j);
                     if(compqty instanceof JLabel){
                         JLabel c = (JLabel) compqty;
                         String ttcc = "ttpr";
                         if(ttcc.equals(c.getName())){ 
                             c.setText(String.valueOf(temp));
                             validate();
                         }
                     }
                 }
             }
             
         }
    }
    public void ttclb(){
        int temp=0;
         int len = selectedit.getComponentCount(); 
         int lenqty1 = footer.getComponentCount();
                 for(int j=0;j<lenqty1;j++){
                     Component compqty = footer.getComponent(j);
                     if(compqty instanceof JLabel){
                         JLabel c = (JLabel) compqty;
                         String ttcc = "ttc";
                         if(ttcc.equals(c.getName())){
                             c.setText("0");
                             validate();
                         }
                     }
                 }
         for(int i=2;i<len;i++){
             Component comp = selectedit.getComponent(i);
             if(comp instanceof JLabel){
                 JLabel b = (JLabel) comp;
                 String currentcount = b.getText();
                 int currentcountint = Integer.parseInt(currentcount);
                 temp=temp+currentcountint;
                 
             }
             if(i==(len-1) && len!=2){
                
                 int lenqty = footer.getComponentCount();
                 for(int j=0;j<lenqty;j++){
                     Component compqty = footer.getComponent(j);
                     if(compqty instanceof JLabel){
                         JLabel c = (JLabel) compqty;
                         String ttcc = "ttc";
                         if(ttcc.equals(c.getName())){ 
                             c.setText(String.valueOf(temp));
                             validate();
                         }
                     }
                 }
             }
         }
    }
    public void deletebt(String idde){
        int len = selectedit.getComponentCount(); 
          for(int i=2;i<len;i++){
              Component comp = selectedit.getComponent(i);
              if(comp instanceof JLabel){
                  JLabel b = (JLabel) comp;
                  if(idde.equals(b.getName())){
                      String currentcount = b.getText();
                      int currentcountint = Integer.parseInt(currentcount);
                      if(currentcountint==1){
                          JButton bt = (JButton) selectedit.getComponent(i-1);
                          selectedit.remove(bt);
                          selectedit.remove(b);
                          
                          break;
                      } else {
                          currentcountint=--currentcountint;
                          b.setText(String.valueOf(currentcountint));
                          break;
                      }
                  }
              }
          }
          updateprice();
          ttclb();
          selectedit.repaint();
    }
    public void orch(String idf){
         mysqlconnect a;
        a = new mysqlconnect();
        Connection conn=a.getConnection();
        String sql; 
        sql = "select * from menuitem where itemid='"+idf+"'";
        int len = selectedit.getComponentCount(); 
        for(int i=0;i<len;i++){
          Component comp = selectedit.getComponent(i);
          if(comp instanceof JButton){
            JButton b = (JButton) comp;
                if(idf.equals(b.getName())){
                    break;
                }}  
                if(i==(len-1) && len!=2) {
                    try{
                      ps=conn.prepareStatement(sql);
                      rs=ps.executeQuery();
                      while(rs.next()){
                          chorn = new  JButton("<html><center>"+rs.getString(2)+"</center></html>");
                          chorn.setName(idf);
                          chorn.addActionListener(new ActionListener() {
                          @Override
                          public void actionPerformed(ActionEvent e) { 
                              deletebt(idf);
                              ttclb();
                              updateprice();
                          }
                      });
                          chorn.setPreferredSize( new Dimension(100, 50) );
                          selectedit.add(chorn);
                        }
                        } catch(SQLException | HeadlessException e){
                            JOptionPane.showMessageDialog(null, e);
                          } 
                break;
            }
          if(len==2 && i!=0){
              try{
                      ps=conn.prepareStatement(sql);
                      rs=ps.executeQuery();
                      while(rs.next()){
                          chorn = new  JButton("<html><center>"+rs.getString(2)+"</center></html>");
                          chorn.setName(idf);
                          chorn.addActionListener(new ActionListener() {
                          @Override
                          public void actionPerformed(ActionEvent e) { 
                              deletebt(idf);
                              ttclb();
                              updateprice();
                          }
                      });
                          chorn.setPreferredSize( new Dimension(100, 50) );
                          selectedit.add(chorn);
                      }
                  } catch(SQLException | HeadlessException e){
                      JOptionPane.showMessageDialog(null, e);
                  } 
              break;
          }
          
     }
        selectedit.addComponentListener(null);
    }
    public void qty(String qtyy){
       // Component[] ch = selectedit.getComponents();
        int len = selectedit.getComponentCount(); 
        for(int i=0;i<len;i++){
        Component comp = selectedit.getComponent(i);
        if(comp instanceof JLabel){
            JLabel b = (JLabel) comp;
            if(qtyy.equals(b.getName())){
                String currentcount = b.getText();
                int currentcountint = Integer.parseInt(currentcount);
                currentcountint=++currentcountint;
                b.setText(String.valueOf(currentcountint));
                break;
            }} 
            if(i==(len-1) && len!=3) {
                qtylabel = new JLabel("1");
                qtylabel.setName(qtyy);
                selectedit.add(qtylabel);
            }
            if(len==3 && i!=1 && i!=0){
                qtylabel = new JLabel("1");
                qtylabel.setName(qtyy);
                selectedit.add(qtylabel);
            }
        }
    }
    private void setall(){
        mysqlconnect a;
        a = new mysqlconnect();
        Connection conn=a.getConnection();
        String sql; 
        sql = "select * from menuitem where del=1 order by itemname";
        all = new JPanel();
        all.setPreferredSize(new Dimension(430,1000));
        all.setLayout(new FlowLayout());
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                final String idal = rs.getString(1);
                button = new  JButton("<html><center>"+rs.getString(2)+"</center></html>");
                button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    //JButton source = (JButton) e.getSource();
                    //source.setEnabled(false);
                    //source.setBackground(Color.RED);
                    orch(idal);
                    qty(idal);
                    ttclb();
                    updateprice();
                    validate();
                }
            });
                button.setPreferredSize( new Dimension(100, 100) );
                all.add(button);
            }
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
       sr = new  JScrollPane();
       sr.setViewportView(all);
     //  sr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      // sr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    private void setfood(){
        mysqlconnect a;
        a = new mysqlconnect();
        Connection conn=a.getConnection();
        String sqlf; 
        sqlf = "select * from menuitem where classid=1 order by itemname";
         foodit = new JPanel();
        foodit.setPreferredSize(new Dimension(450,1000));
        foodit.setLayout(new FlowLayout());
        try{
            ps=conn.prepareStatement(sqlf);
            rs=ps.executeQuery();
            while(rs.next()){
                final String idal = rs.getString(1);
                button = new  JButton("<html><center>"+rs.getString(2)+"</center></html>");
                button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  
                    orch(idal);
                    qty(idal);
                    ttclb();
                    updateprice();
                    validate();
                }
            });
                button.setPreferredSize( new Dimension(100, 100) );
                foodit.add(button);
            }
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
        srf = new JScrollPane();
       srf.setViewportView(foodit);
    }
    private void setdrink(){
        mysqlconnect a;
        a = new mysqlconnect();
        Connection conn=a.getConnection();
        String sqlf; 
        sqlf = "select * from menuitem where classid=2 order by itemname";
         drinkit = new JPanel();
        drinkit.setPreferredSize(new Dimension(430,1000));
        drinkit.setLayout(new FlowLayout());
        try{
            ps=conn.prepareStatement(sqlf);
            rs=ps.executeQuery();
            while(rs.next()){
                final String idal = rs.getString(1);
                button = new  JButton("<html><center>"+rs.getString(2)+"</center></html>");
                button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    orch(idal);
                    qty(idal);
                    ttclb();
                    updateprice();
                    validate();
                }
            });
                button.setPreferredSize( new Dimension(100, 100) );
                drinkit.add(button);
            }
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
        srd = new JScrollPane();
       srd.setViewportView(drinkit);
    }
    private void setother(){
         mysqlconnect a;
        a = new mysqlconnect();
        Connection conn=a.getConnection();
        String sqlf; 
        sqlf = "select * from menuitem where classid=3 order by itemname";
         other = new JPanel();
        other.setPreferredSize(new Dimension(430,1000));
        other.setLayout(new FlowLayout());
        try{
            ps=conn.prepareStatement(sqlf);
            rs=ps.executeQuery();
            while(rs.next()){
                final String idal = rs.getString(1);
                button = new  JButton("<html><center>"+rs.getString(2)+"</center></html>");
                button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  
                    orch(idal);
                    qty(idal);
                    ttclb();
                    updateprice();
                    validate();
                }
            });
                button.setPreferredSize( new Dimension(100, 100) );
                other.add(button);
            }
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
        sro = new JScrollPane();
       sro.setViewportView(other);
    }
    private void footer(){
         mysqlconnect a;
             a = new mysqlconnect();
        Connection conn=a.getConnection();
        String sqlf = "{call deleteft(?)}";
        footer = new JPanel();
         footer.setPreferredSize(new Dimension(600,30));
        footer.setLayout(new FlowLayout());
        confirmbt  = new JButton("Xác nhận ");
        confirmbt.addActionListener(new ActionListener(){  
         @Override
                public void actionPerformed(ActionEvent e) {
                    if(selectedit.getComponentCount()==2){
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn món");
                    } else {
                    try(CallableStatement stmt = conn.prepareCall(sqlf)){
                        stmt.setString(1, idtable);
                        stmt.execute();
                    } catch (SQLException ex) {
                 Logger.getLogger(showdes.class.getName()).log(Level.SEVERE, null, ex);
             }
                    for( int i = 2; i < selectedit.getComponentCount(); i++ ){
                        String itemid;
                        int qty;
                        float amount=0;
                       Component comp = selectedit.getComponent(i);
                       if(comp instanceof JButton){
                          JButton b = (JButton) comp;
                          itemid=b.getName();
                          JLabel l = (JLabel) selectedit.getComponent(i+1);
                          String currentqty = l.getText();
                          qty = Integer.parseInt(currentqty);
                          String sqlb="select priceofone(?,?)";
                          try(PreparedStatement stmtb = conn.prepareStatement(sqlb)){
                                    stmtb.setString(1, itemid);
                                    stmtb.setInt(2, qty);
                                    ResultSet rs = stmtb.executeQuery();
                                    while(rs.next()){
                                        amount=rs.getFloat(1);
                                    }
                                } catch (SQLException ex) {
                            Logger.getLogger(showdes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                          String sqlc="{call installval(?,?,?,?,?)}";
                          try(CallableStatement stmtc = conn.prepareCall(sqlc)){
                                    stmtc.setString(1, itemid);
                                    stmtc.setInt(2, qty);
                                    stmtc.setFloat(3, amount);
                                    stmtc.setString(4, idtable);
                                    stmtc.setString(5, getname);
                                    stmtc.execute();
                                } catch (SQLException ex) {
                            Logger.getLogger(showdes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       }
                       
                    } 
                    String sqls="{call setstatus(?)}";
                          try(PreparedStatement stmts = conn.prepareStatement(sqls)){
                              stmts.setString(1, idtable);
                              stmts.execute();
                          } catch (SQLException ex) {
                            Logger.getLogger(showdes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    new homepage1(getname).setVisible(true);
                    dispose();
                }
                }   
        });
        ttcn = new JLabel("Tổng số món: ");
        ttc = new JLabel("0");
        ttc.setName("ttc");
        ttprn = new JLabel("Tổng giá tiền: ");
        ttpr = new JLabel("0");    
        ttpr.setName("ttpr");
        brl2 = new JLabel("|");
        ttbt = new JButton("Tính Tiền");
        ttbt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  
                mysqlconnect a;
                a = new mysqlconnect();
                Connection conn=a.getConnection();
                String sqlf = "{call ttpd(?)}";
                if(selectedit.getComponentCount()==2){
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn món");
                    }
                else {
                try(PreparedStatement stmts = conn.prepareStatement(sqlf)){
                              stmts.setString(1, idtable);
                              stmts.execute();
                          } catch (SQLException ex) {
                            Logger.getLogger(showdes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                new homepage1(getname).setVisible(true);
                    dispose();
                }}
            });
        footer.add(ttprn);
        footer.add(ttpr);
        footer.add(brl2);
        footer.add(ttcn);
        footer.add(ttc);
        footer.add(confirmbt);
        footer.add(ttbt);
    }
    private void header(){
        header = new JPanel();
         header.setPreferredSize(new Dimension(450,35));
        header.setLayout(new FlowLayout());
        backbt = new JButton("Quay lại");
        backbt.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new homepage1(getname).setVisible(true);
                    dispose();
            }
        
    });
         stfn = new JLabel("Nhân viên: ");
         sear = new JLabel("Tìm kiếm: ");
         searc = new JTextField(10);
          String sqlhe;
          mysqlconnect a;
        a = new mysqlconnect();
          String namest = null;
          Connection conn1 = a.getConnection();
        sqlhe = "select returnname(?)";
        try(PreparedStatement stmt4 = conn1.prepareStatement(sqlhe)){
            stmt4.setString(1, getname);
            rs1 = stmt4.executeQuery();
            if(rs1.next()){
                namest = rs1.getString(1);
            }else{
                JOptionPane.showMessageDialog(null, "Sai tên hoặc mật khẩu");
            }
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            
        }
         stf = new JLabel(namest+"  ");
          brl = new JLabel("|");
          header.add(backbt);
          header.add(stfn);
        header.add(stf);
        header.add(brl);
        header.add(sear);
        header.add(searc);
        conn=a.getConnection();
        String sqlf; 
        sqlf = "select tablename from dinnertable where tableid='"+idtable+"'";
        try{
            ps=conn.prepareStatement(sqlf);
            rs=ps.executeQuery();
            tabn = new JLabel();
            if(rs.next()){
                tabn.setText(rs.getString(1));
            }        header.add(tabn);
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    
    private void setname(){
        setall();
        setfood();
        setdrink();
        setother();
        footer();
        selectedit();
        header();
        fetchorder();
        tab = new JTabbedPane();
        setLayout(new BorderLayout());
        tab.addTab("Tất cả",null, sr,null);
        tab.addTab("Thức ăn",null, srf,null);
        tab.addTab("Đồ uống",null, drinkit,null);
        tab.addTab("Khác",null, other,null);
        add(header, BorderLayout.PAGE_START);
        add(tab, BorderLayout.CENTER);
        add(selec, BorderLayout.LINE_END);
        add(footer, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] arg){
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String id = null;
                String id1 = null;
                String id2 = null;
                showdes showdes = new showdes(id,id1,id2);
            }
        });
    }

}
