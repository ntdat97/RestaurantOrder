/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.awt.HeadlessException;
import java.sql.Connection;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

/**
 *
 * @author dell
 */
public class homepage1 extends javax.swing.JFrame implements ActionListener  {
    private Connection conn = null;
    private PreparedStatement ps = null; 
    private ResultSet rs = null; 
    private ResultSet rs1 = null;
    public String getname = null;
    private String namest;
    private JButton button;
    private JButton editst,addfo,lookup;
    private JPanel top;
    private JLabel stname;
    private JLabel test;
    private JPanel menutable;
    public homepage1(String getname) {
        this.getname = getname;
        setlayout();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public String getname1(){
        return getname;
    }
    public final void setlayout(){ 
        mysqlconnect b;
        b = new mysqlconnect();
        conn=b.getConnection();
        String sql1,sql;
        sql1 = "select returnname(?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql1)){
            stmt.setString(1, getname);
            rs1 = stmt.executeQuery();
            if(rs1.next()){
                namest = rs1.getString(1);
            }else{
                JOptionPane.showMessageDialog(null, "Sai tên hoặc mật khẩu");
            }
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        editst = new JButton("Chỉnh sửa NV");
        editst.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new editst(getname).setVisible(true);
                dispose();
            }
        
    });
        lookup = new JButton("Hiển thị");
        lookup.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new lookup(getname).setVisible(true);
                dispose();
            }
        
    });
        stname = new JLabel(namest);
        test = new JLabel("Tên nhân viên:   ");
        top = new JPanel();
        top.setLayout(new FlowLayout());
        menutable = new JPanel();
        top.setPreferredSize(new Dimension(300,35));
     setPreferredSize(new Dimension(600, 500));
       setLayout(new BorderLayout());
       top.add(test);
       top.add(stname);
       top.add(editst);
       top.add(lookup);
          String sqllook = "select position from staffid where staffid='"+getname+"'";
       try{
            ps=conn.prepareStatement(sqllook);
            rs=ps.executeQuery();
            while(rs.next()){
                if(!"quanly".equals(rs.getString(1)))
                     lookup.setVisible(false);
            }
       } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
       }
        String sqledit = "select position from staffid where staffid='"+getname+"'";
       try{
            ps=conn.prepareStatement(sqledit);
            rs=ps.executeQuery();
            while(rs.next()){
                if(!"quanly".equals(rs.getString(1)))
                     editst.setVisible(false);
                     //lookup.setVisible(false);
            }
       } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
       }
       add(top, BorderLayout.PAGE_START);
       add(menutable, BorderLayout.PAGE_END);
        setTitle("Home Page");
        pack();
        menutable.setLayout(new GridLayout(4,7,1,1));
        sql = "select * from dinnertable where del=1 order by tableid";
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
            final String t = rs.getString(1);
            final String st = rs.getString(3);
            button = new  JButton(rs.getString(2));
            if(rs.getInt(3) == 1){
                button.setBackground(Color.green);
            }
            if(rs.getInt(3) == 2){
                button.setBackground(Color.red);
            }
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                     new showdes(t,getname,st).setVisible(true); 
                     dispose();  
                }
            });
            button.setPreferredSize( new Dimension(100, 100) );
            menutable.add(button);
               // jPanel.add(new JButton(rs.getString(2)));
            }
            
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
        }
        /* -- */
        JButton edit = new JButton("Chỉnh sửa bàn");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new edittable(getname).setVisible(true);
                dispose();
            }
        });
        String sqledite = "select position from staffid where staffid='"+getname+"'";
       try{
            ps=conn.prepareStatement(sqledite);
            rs=ps.executeQuery();
            while(rs.next()){
                if(!"quanly".equals(rs.getString(1)))
                     edit.setVisible(false);
            }
       } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
       }
        edit.setPreferredSize( new Dimension(100, 100) );
        /* -- */
        addfo = new JButton("Chỉnh món");
        addfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new editfood(getname).setVisible(true);
                dispose();
            }
        });
        String sqladdfo = "select position from staffid where staffid='"+getname+"'";
       try{
            ps=conn.prepareStatement(sqladdfo);
            rs=ps.executeQuery();
            while(rs.next()){
                if(!"quanly".equals(rs.getString(1)))
                     addfo.setVisible(false);
            }
       } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
       }
       addfo.setPreferredSize( new Dimension(100, 100) );
        menutable.add(edit);
        menutable.add(addfo);
        add(menutable);
        pack();
    }
/*public void actionPerformed(ActionEvent e)
{
        new homepage().setVisible(true); 
        this.dispose();   
}*/
  /*  private void top(){
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(4,1,1,1));
        JLabel showname = new JLabel("adsdasd");
        JLabel abc = new JLabel("adsdasd");
        top.add(showname);
        top.add(abc);
        add(top);
        pack();
    }
*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */                    

    /**
     * This method is called from within the constructor to initialize the form.WARNING: Do NOT modify this code. The content of this method is always
 regenerated by the Form Editor.
     * @param args
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
                String aaa = null;
                new homepage1(aaa).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                                    

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
