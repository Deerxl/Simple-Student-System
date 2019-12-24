import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class StuFrame {
    JFrame stuFrame;
    JTextField textField;
    JButton searchBtn, addBtn, deleteBtn, updateBtn;
    JTable stuTable;
    JPanel panel1;
    JScrollPane panel2;
    StuFunc stuFunc;
    Table table;
    String getText = "";
    int selectedRow;
    Vector selectRowData;

    public static void main(String[] args){
        StuFrame frame = new StuFrame("");
    }

    //temp 若为 "" 则 table会显示全部的学生信息，反之，则显示 选中部分学生的信息
    public StuFrame(String temp){
        stuFrame = new JFrame("学生课程管理系统");

        //设置窗口
        stuFrame.setSize(800,600);
        stuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel1 = new JPanel(new GridLayout(1,6));
        panel2 = new JScrollPane();

        textField = new JTextField(15);
        searchBtn = new JButton("查询");
        searchBtn.addActionListener(new btnListener());
        addBtn = new JButton("添加");
        this.addBtn.addActionListener(new btnListener());
        deleteBtn = new JButton("删除");
        deleteBtn.addActionListener(new btnListener());
        updateBtn = new JButton("修改");
        updateBtn.addActionListener(new btnListener());

        //创建表格
        table = new Table();
        stuTable = new JTable(table.rowData(temp), table.columnsData());

        panel1.add(textField);
        panel1.add(searchBtn);
        panel1.add(addBtn);
        panel1.add(deleteBtn);
        panel1.add(updateBtn);
        panel2.setViewportView(stuTable);
        stuFrame.add(panel1, BorderLayout.NORTH);
        stuFrame.add(panel2, BorderLayout.SOUTH);

        //显示窗口
        stuFrame.pack();
        stuFrame.setVisible(true);
    }

    class btnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==addBtn){    //增
                EditDialog addDialog = new EditDialog("添加",true,selectRowData);
                //刷新
                String[][] data = stuFunc.Select(getText);
                table.updateTable(data,table.columnsData());
                stuTable.setModel(table);

            }else if(e.getSource().equals(deleteBtn)){       //删
                stuFunc = new StuFunc();
                selectedRow = stuTable.getSelectedRow();
                if(-1 == selectedRow){
                    JOptionPane.showMessageDialog(null,"请选中一行！");
                }else{
                    String id = (String) (stuTable.getValueAt(selectedRow, 0));
                    stuFunc.DeleteById(id);
                    JOptionPane.showMessageDialog(null,"删除成功！");
                }

                String[][] data = stuFunc.Select(getText);
                table.updateTable(data,table.columnsData());
                stuTable.setModel(table);
            }else if(e.getSource().equals(updateBtn)) {         //改
                selectedRow = stuTable.getSelectedRow();
                selectRowData = getSelectedRowData();
                EditDialog updateDialog = new EditDialog("修改", false, selectRowData);

                String[][] data = stuFunc.Select(getText);
                table.updateTable(data,table.columnsData());
                stuTable.setModel(table);
            }

            else if(e.getSource().equals(searchBtn)){        //查
                    if(textField.getText().equals("")){
                        getText = textField.getText();
                    }else{
                        getText = textField.getText().trim();
                    }
                    stuFunc = new StuFunc();               //刷新表格
                    String[][] data = stuFunc.Select(getText);
                    table.updateTable(data,table.columnsData());
                    stuTable.setModel(table);

            }
        }
    }
    //返回 选中行的数据
    public Vector getSelectedRowData(){
        Vector rowData = new Vector();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(null, "请选中一行");
            return null;
        }
        else {
            for(int i = 0; i< 6;i++){
                rowData.add(stuTable.getValueAt(selectedRow, i));
            }
            return rowData;
        }
    }

    public void OutPut(){

    }
}


