/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import controller.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SMKMANDALAHAYU
 */
public class formPengguna extends javax.swing.JFrame {
private DefaultTableModel model;
Connection conn = koneksi.getKoneksi();
ResultSet rs = null;
PreparedStatement pst = null;

    public formPengguna() {
        initComponents();
        btnHapus.setEnabled(false);
        btnUbah.setEnabled(false);
        btnTambah.setEnabled(false);
        loadTable();
        getData();
        clear();
    }
    public void clear()
    {
        txtNamapengguna.setText("");
        txtAlamat.setText("");
        txtPassword.setText("");
        txtUsername.setText("");
        txtNo.setText("");
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    public void loadTable() {
        model = new DefaultTableModel ( );
            tablePengguna.setModel(model);
            model.addColumn("No");
            model.addColumn("ID");
            model.addColumn("Nama Pengguna");
            model.addColumn("Username");
            model.addColumn("Password");
            model.addColumn("Alamat");

            getData();
    }
    public void getData()
    {
        model.getDataVector( ).removeAllElements( );
        model.fireTableDataChanged( );
        try {
            String sql = "select * from pengguna";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            int noUrut = 1;
            while(rs.next ())
            {
                Object[] o = new Object[6];
                o[0] = noUrut; 
                o[1] = rs.getString("no");
                o[2] = rs.getString("nama_pengguna");
                o[3] = rs.getString("username");
                o[4] = rs.getString("password");
                o[5] = rs.getString("alamat");
                model.addRow(o);
                noUrut++;
            }
        } catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err.getMessage() );
        }
    }
    private void cariData(String key)
    {
        try {
            model = new DefaultTableModel ( );
                tablePengguna.setModel(model);
                model.addColumn("No");
                model.addColumn("ID");
                model.addColumn("Nama Pengguna");
                model.addColumn("Username");
                model.addColumn("Password");
                model.addColumn("Alamat");
                model.getDataVector( ).removeAllElements( );
            String sql = "select * from pengguna where nama_pengguna LIKE '%"+key+"%' OR username LIKE '%"+key+"%'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            int noUrut = 1;
            while(rs.next ())
            {
                Object[] o = new Object[6];
                o[0] = noUrut; 
                o[1] = rs.getString("no");
                o[2] = rs.getString("nama_pengguna");
                o[3] = rs.getString("username");
                o[4] = rs.getString("password");
                o[5] = rs.getString("alamat");
                model.addRow(o);
                noUrut++;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void tambahData()
    {
        String nama_pengguna = txtNamapengguna.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String alamat = txtAlamat.getText();
        String sql = "INSERT INTO pengguna VALUES (?, ?, ?, ?, ?)";
        try {
            String val = "select * from pengguna where username = ? ";
            pst = conn.prepareStatement(val);
            pst.setString(1, username);
            rs = pst.executeQuery();
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null, "Nama sudah tersedia");
            } else {
            pst = conn.prepareStatement(sql);
            pst.setString(1, null);
            pst.setString(2, nama_pengguna);
            pst.setString(3, username);
            pst.setString(4, password);
            pst.setString(5, alamat);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
            getData();
            clear();
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Data gagal ditambahkan");
        }
    }
    public void mouseClick()
    {
        try {
            int row = tablePengguna.getSelectedRow();
            String table_klik = (tablePengguna.getModel().getValueAt(row, 1).toString());
            java.sql.Statement pst = conn.createStatement();
            rs = pst.executeQuery("select * from pengguna where no='"+table_klik+"'");
            if(rs.next())
            {
                String no = rs.getString("no");
                txtNo.setText(no);
                String nama = rs.getString("nama_pengguna");
                txtNamapengguna.setText(nama);
                String username = rs.getString("username");
                txtUsername.setText(username);
                String password = rs.getString("password");
                txtPassword.setText(password);
                String alamat = rs.getString("alamat");
                txtAlamat.setText(alamat);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void updateData()
    {
        try {
            java.sql.Statement statSiswa = conn.createStatement();
            String sql = "update pengguna set nama_pengguna = '"+txtNamapengguna.getText()+"', username = '"+txtUsername.getText()+"', password = '"+txtPassword.getText()+"', alamat = '"+txtAlamat.getText()+"' where no = '"+txtNo.getText()+"'";
            statSiswa.executeUpdate(sql);
            getData();
            clear();
            btnUbah.setEnabled(false);
            btnHapus.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Data berhasil di edit");
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }
    public void hapusData()
    {
        try {
            java.sql.Statement statSiswa = conn.createStatement();
            String sql = "delete from pengguna where no = '"+txtNo.getText()+"'";
            statSiswa.executeUpdate(sql);
            getData();
            clear();
            btnHapus.setEnabled(false);
            btnUbah.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
        } catch (Exception exc) {
//            System.err.println(exc.getMessage());
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNamapengguna = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePengguna = new javax.swing.JTable();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        txtNo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Pengguna");

        jLabel2.setText("Nama Pengguna :");

        txtNamapengguna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamapenggunaKeyReleased(evt);
            }
        });

        jLabel3.setText("Username :");

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jLabel4.setText("Password :");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        jLabel5.setText("Alamat :");

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        tablePengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablePengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePenggunaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablePengguna);

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        txtNo.setEditable(false);

        jLabel6.setText("Search :");

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNo, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(txtNamapengguna)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUbah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKembali)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNamapengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal)
                    .addComponent(btnKembali)
                    .addComponent(btnTambah))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        updateData();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(txtNamapengguna.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (txtAlamat.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Alamat tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (txtUsername.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Jenis Kelamin tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (txtPassword.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Kelas tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            tambahData();
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtNamapenggunaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamapenggunaKeyReleased
        if(txtNamapengguna.getText().equals(""))
        {
            btnTambah.setEnabled(false);
        } else 
        {
            btnTambah.setEnabled(true);
        }
    }//GEN-LAST:event_txtNamapenggunaKeyReleased

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        clear();
        btnTambah.setEnabled(false);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void tablePenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePenggunaMouseClicked
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
        mouseClick();
    }//GEN-LAST:event_tablePenggunaMouseClicked

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        String key = txtCari.getText();
        System.out.println(key);
        if(key!=""){
            cariData(key);
        }else{
            getData();
        }
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        formHome fh = new formHome();
        fh.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

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
            java.util.logging.Logger.getLogger(formPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formPengguna().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablePengguna;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtNamapengguna;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
