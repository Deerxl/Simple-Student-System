import javax.swing.table.AbstractTableModel;

public class Table extends AbstractTableModel {
    String[][]rowData;
    String[] columns = {"学号", "姓名", "班级", "课程1", "课程2", "课程3"};
    StuFunc stuFunc;

    public Table(){}

    public Table(String temp){
        //如果 temp 是 "" 则显示全部的值，反之，则查询 关键词 temp 的学生信息
        try {
            rowData = rowData(temp);
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }
    public int getRowCount(){
        return this.rowData.length;
    }
    public int getColumnCount(){
        return this.columns.length;
    }
    public String getValueAt(int row, int column){
        return this.rowData[row][column];
    }

    public void updateTable(String[][] rowData, String[] columns) {
        this.rowData = rowData;
        this.columns = columns;
        this.fireTableDataChanged();
    }

    public String[] columnsData(){
        return columns;
    }

    public String[][] rowData(String temp){
        String[][] rowData;
        stuFunc = new StuFunc();
        rowData = stuFunc.Select(temp);
        return rowData;
    }

}
