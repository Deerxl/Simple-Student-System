import javax.swing.JDialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class EditDialog extends JDialog{
    JDialog editDialog;
    JLabel nameLabel, idLabel, classLabel, course1Label, course2Label, course3Label;
    JTextField idText, nameText, classText, course1Text, course2Text, course3Text;
    JPanel panel1, panel2, panel3;
    JButton saveBtn;
    StuFunc stuFunc;
    private boolean addWay = false;

    public EditDialog(String title, boolean add, Vector getRowData) {
        if(add)
            addWay = true;  //设置 add

        editDialog = new JDialog();
        editDialog.setSize(480, 320);
        editDialog.setTitle(title);

        nameLabel = new JLabel("姓名");
        idLabel = new JLabel("学号");
        classLabel = new JLabel("班级");
        course1Label = new JLabel("课程1");
        course2Label = new JLabel("课程2");
        course3Label = new JLabel("课程3");

        idText = new JTextField(20);
        nameText = new JTextField(20);
        classText = new JTextField(20);
        course1Text = new JTextField(20);
        course2Text = new JTextField(20);
        course3Text = new JTextField(20);

        saveBtn = new JButton("保存");
        saveBtn.addActionListener(new saveBtnAddListener());

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(6, 1));
        panel1.add(idLabel);
        panel1.add(nameLabel);
        panel1.add(classLabel);
        panel1.add(course1Label);
        panel1.add(course2Label);
        panel1.add(course3Label);

        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(6, 1));
        panel2.add(idText);
        panel2.add(nameText);
        panel2.add(classText);
        panel2.add(course1Text);
        panel2.add(course2Text);
        panel2.add(course3Text);

        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1, 2));
        panel3.add(saveBtn);

        editDialog.add(panel1, BorderLayout.WEST);
        editDialog.add(panel2, BorderLayout.CENTER);
        editDialog.add(panel3, BorderLayout.SOUTH);

        //如果是修改则 textfield显示
        if(!addWay){
            try{
                idText.setText((String)getRowData.get(0));
                nameText.setText((String)getRowData.get(1));
                classText.setText((String)getRowData.get(2));
                course1Text.setText((String)getRowData.get(3));
                course2Text.setText((String)getRowData.get(4));
                course3Text.setText((String)getRowData.get(5));

                idText.setEditable(false);   //修改时设置 id 不可改
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }

       // editDialog.pack();
        editDialog.setModal(true);
        editDialog.setVisible(true);
    }

    class saveBtnAddListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (addWay) {
                try {
                    stuFunc = new StuFunc();
                    boolean temp = stuFunc.idRegex(idText.getText(),nameText.getText().trim(), classText.getText().trim());
                    if(temp){
                        stuFunc.Add(Long.parseUnsignedLong(idText.getText().trim()), nameText.getText().trim(), classText.getText().trim(),
                                course1Text.getText().trim(), course2Text.getText().trim(), course3Text.getText().trim());
                        editDialog.dispose();
                    }else
                        JOptionPane.showMessageDialog(null,"请输入正确的学号/姓名/班级");

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            } else {
                try {
                    stuFunc = new StuFunc();
                    boolean temp = stuFunc.idRegex(idText.getText(),nameText.getText().trim(), classText.getText().trim());
                    if(temp){
                        stuFunc.Update(Long.parseUnsignedLong(idText.getText()), nameText.getText().trim(), classText.getText().trim(),
                                course1Text.getText().trim(), course2Text.getText().trim(), course3Text.getText().trim());
                        editDialog.dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(null,"请输入正确的学号/姓名/班级");
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }
    }

}

