/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package form;
import java.sql.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import controller.koneksi;

/**
 *
 * @author smk mandalahayu
 */
public class formSiswa extends javax.swing.JFrame {
private DefaultTableModel model;
Connection conn = koneksi.getKoneksi();
ResultSet rs = null;
PreparedStatement pst = null;
    /**
     * Creates new form formSiswa
     */
    public formSiswa() {
        initComponents();
        btnHapus.setEnabled(false);
        btnUbah.setEnabled(false);
        txtNo.setEnabled(false);
        btnTambah.setEnabled(false);
        loadTable();
        getData();
        clear();
    }
    public void clear()
    {
        txtNama.setText("");
        comboJenisKelamin.setSelectedIndex(0);
        comboKelas.setSelectedIndex(0);
        txtAlamat.setText("");
        txtNo.setText("");
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    public void loadTable() {
        model = new DefaultTableModel ( );
            tableSiswa.setModel(model);
            model.addColumn("No");
            model.addColumn("ID");
            model.addColumn("Nama");
            model.addColumn("Jenis Kelamin");
            model.addColumn("Kelas");
            model.addColumn("Alamat");

            getData();
    }
    private void cariData(String key)
    {
        try {
            model = new DefaultTableModel ( );
                tableSiswa.setModel(model);
                model.addColumn("No");
                model.addColumn("ID");
                model.addColumn("Nama");
                model.addColumn("Jenis Kelamin");
                model.addColumn("Kelas");
                model.addColumn("Alamat");
                model.getDataVector( ).removeAllElements( );
            String sql = "select * from siswa where nama LIKE '%"+key+"%' OR kelas LIKE '%"+key+"%'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            int noUrut = 1;
            while(rs.next ())
            {
                Object[] o = new Object[6];
                o[0] = noUrut; 
                o[1] = rs.getString("no");
                o[2] = rs.getString("nama");
                o[3] = rs.getString("jenis_kelamin");
                o[4] = rs.getString("kelas");
                o[5] = rs.getString("alamat");
                model.addRow(o);
                noUrut++;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void getData()
    {
        model.getDataVector( ).removeAllElements( );
        model.fireTableDataChanged( );
        try {
            String sql = "select * from siswa";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            int noUrut = 1;
            while(rs.next ())
            {
                Object[] o = new Object[6];
                o[0] = noUrut; 
                o[1] = rs.getString("no");
                o[2] = rs.getString("nama");
                o[3] = rs.getString("jenis_kelamin");
                o[4] = rs.getString("kelas");
                o[5] = rs.getString("alamat");
                model.addRow(o);
                noUrut++;
            }
        } catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err.getMessage() );
        }
    }
    public void tambahData()
    {
        String nama = txtNama.getText();
        String jk = comboJenisKelamin.getSelectedItem().toString();
        String kelas = comboKelas.getSelectedItem().toString();
        String alamat = txtAlamat.getText();
        String sql = "INSERT INTO siswa VALUES (?, ?, ?, ?, ?)";
        try {
            String val = "select * from siswa where nama = ? ";
            pst = conn.prepareStatement(val);
            pst.setString(1, nama);
            rs = pst.executeQuery();
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null, "Nama sudah tersedia");
            } else {
            pst = conn.prepareStatement(sql);
            pst.setString(1, null);
            pst.setString(2, nama);
            pst.setString(3, jk);
            pst.setString(4, kelas);
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
            int row = tableSiswa.getSelectedRow();
            String table_klik = (tableSiswa.getModel().getValueAt(row, 1).toString());
            java.sql.Statement pst = conn.createStatement();
            rs = pst.executeQuery("select * from siswa where no='"+table_klik+"'");
            if(rs.next())
            {
                String no = rs.getString("no");
                txtNo.setText(no);
                String nama = rs.getString("nama");
                txtNama.setText(nama);
                String jk = rs.getString("jenis_kelamin");
                comboJenisKelamin.setSelectedItem(jk);
                String kelas = rs.getString("kelas");
                comboKelas.setSelectedItem(kelas);
                String alamat = rs.getString("alamat");
                txtAlamat.setText(alamat);
            }
        } catch (Exception e) {
        }
    }
    public void updateData()
    {
        try {
            java.sql.Statement statSiswa = conn.createStatement();
            String sql = "update siswa set nama = '"+txtNama.getText()+"', jenis_kelamin = '"+comboJenisKelamin.getSelectedItem().toString()+"', kelas = '"+comboKelas.getSelectedItem().toString()+"', alamat = '"+txtAlamat.getText()+"' where no = '"+txtNo.getText()+"'";
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
        int tanya = JOptionPane.showConfirmDialog(null, "Yakin hapus data ini ?","Konfirmasi",JOptionPane.CANCEL_OPTION);
        if(tanya == JOptionPane.OK_OPTION)
        {
                try {
                java.sql.Statement statSiswa = conn.createStatement();
                String sql = "delete from siswa where no = '"+txtNo.getText()+"'";
                statSiswa.executeUpdate(sql);
                getData();
                clear();
                btnHapus.setEnabled(false);
                btnUbah.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
            } catch (Exception exc) {
                System.err.println(exc.getMessage());
            }
        }
    }
    // DISINI TERDAPAT VALIDASI DARI SEMUA FORM //
    public void FilterHuruf(KeyEvent a) {
        if (Character.isDigit(a.getKeyChar())) {
            a.consume();
            //Pesan Dialog Boleh Di Hapus Ini Hanya Sebagai Contoh
            JOptionPane.showMessageDialog(null, "Masukan Hanya Huruf", "Peringatan", JOptionPane.WARNING_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        comboJenisKelamin = new javax.swing.JComboBox();
        LabelKelas = new javax.swing.JLabel();
        comboKelas = new javax.swing.JComboBox();
        LabelKelas1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSiswa = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();
        txtNo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Siswa");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel2.setText("Search :");

        jLabel3.setText("Nama :");

        jLabel4.setText("Jenis Kelamin :");

        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNamaKeyTyped(evt);
            }
        });

        comboJenisKelamin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--", "L", "P" }));
        comboJenisKelamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboJenisKelaminActionPerformed(evt);
            }
        });

        LabelKelas.setText("Kelas :");

        comboKelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--", "12 RPL 1", "12 RPL 2", "12 RPL 3", " " }));

        LabelKelas1.setText("Alamat :");

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        tableSiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        tableSiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSiswaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableSiswa);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

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

        txtNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNo, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(txtNama)
                            .addComponent(comboJenisKelamin, 0, 100, Short.MAX_VALUE))
                        .addGap(67, 67, 67)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LabelKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LabelKelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 85, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jScrollPane2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUbah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKembali)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LabelKelas)
                        .addComponent(comboKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelKelas1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(comboJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal)
                    .addComponent(btnKembali))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void comboJenisKelaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboJenisKelaminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboJenisKelaminActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(txtNama.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (txtAlamat.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Alamat tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (comboJenisKelamin.getSelectedItem().equals("--"))
        {
            JOptionPane.showMessageDialog(null, "Jenis Kelamin tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (comboKelas.getSelectedItem().equals("--"))
        {
            JOptionPane.showMessageDialog(null, "Kelas tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            tambahData();
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        clear();
        btnTambah.setEnabled(false);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        new formHome().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void tableSiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSiswaMouseClicked
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
        mouseClick();
    }//GEN-LAST:event_tableSiswaMouseClicked

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        updateData();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void txtNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoActionPerformed
  
    }//GEN-LAST:event_txtNoActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String key = txtSearch.getText();
        System.out.println(key);
        if(key!=""){
            cariData(key);
        }else{
            getData();
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtNamaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyTyped
        FilterHuruf(evt);
        
    }//GEN-LAST:event_txtNamaKeyTyped

    private void txtNamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyReleased
        if(txtNama.getText().equals(""))
        {
            btnTambah.setEnabled(false);
        } else 
        {
            btnTambah.setEnabled(true);
        }
    }//GEN-LAST:event_txtNamaKeyReleased

    private void txtNamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyPressed
     
    }//GEN-LAST:event_txtNamaKeyPressed

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
            java.util.logging.Logger.getLogger(formSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formSiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelKelas;
    private javax.swing.JLabel LabelKelas1;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox comboJenisKelamin;
    private javax.swing.JComboBox comboKelas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableSiswa;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
