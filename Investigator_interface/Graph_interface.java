package Investigator_interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Graph_interface extends JFrame implements ActionListener, KeyListener {
    //˫ƴ
    JLabel doublePin;
    JTextField inputDp;
    //��ѯ��ť
    JButton query;
    //�����ļ�
    JButton start;
    //�������
    JLabel output;
    //��־���������
    JList log;
    //ÿ��һ������
    DefaultListModel<String> lstModel;
    //������
    JScrollPane scrollPane;
    //��ѯ��¼����
    int count=0;
    public Graph_interface(){
        super("���귴����");
        init();
        setVisible(true);
        setBounds(550,200,700,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        int row=0;
        //˫ƴ
        doublePin = new JLabel("�����뺺��");
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(doublePin, constraints);

        inputDp = new JTextField(40);
        constraints.gridx = 1;
        constraints.gridy = row;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 0;
        inputDp.addKeyListener(this);
        add(inputDp, constraints);
        //��ѯ
        query = new JButton("��ѯ");
        constraints.gridx = 2;
        constraints.gridy = row;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        query.addActionListener(this);
        add(query, constraints);
        row++;
        //���
        output = new JLabel("��ѯ���");
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(output, constraints);
        row++;
        //�б������
        log = new JList();
        //log.setMultipleMode(true);
        //������
        lstModel = new DefaultListModel<>();
        log = new JList<>(lstModel);
        scrollPane = new JScrollPane(log);
        ////////////////////
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridwidth = 9;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add(scrollPane, constraints);

        pack();
    }
    public static void main(String[] args) {
        new Graph_interface();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == query) {
            if ((inputDp.getText()).equals("")){
                new Warn_dialog(this);
            }else {
                showResult();
            }
        }
    }
    private void scrollListToBottom() {
        Rectangle lastCell = log.getCellBounds(lstModel.getSize()-1,lstModel.getSize()-1);
        scrollPane.getViewport().scrollRectToVisible(lastCell);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((inputDp.getText().equals(""))){
            Warn_dialog wd = new Warn_dialog(this);
            wd.setVisible(true);
        }else{
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                showResult();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void showResult(){
        String character=null,line1=null,line2=null,line3=null,line4=null;
        if ((character=inputDp.getText())!=null){
            count++;
            Dict_list dl1 = new Dict_list();
            line1=dl1.readDictFile("C:\\Users\\Daniel James\\AppData\\Roaming\\Rime\\pinyin_simp.dict.yaml",character);
            lstModel.addElement("��"+count+"��:");
            lstModel.addElement("'"+character+"'"+"��ȫƴ��"+line1);
            Dict_list dl4 = new Dict_list();
            line4=dl4.readDictFile("D:\\qpAddSo.dict.yaml",character);
            lstModel.addElement("'"+character+"'"+"��ȫƴ+������"+line4);
            Dict_list dl2 = new Dict_list();
            line2=dl2.readDictFile("D:\\dpAddSh.dict.yaml",character);
            lstModel.addElement("'"+character+"'"+"��˫ƴ+˫�Σ�"+line2);
            Dict_list dl3 = new Dict_list();
            line3=dl3.readDictFile("D:\\dpAddSo.dict.yaml",character);
            lstModel.addElement("'"+character+"'"+"��˫ƴ+������"+line3);
            lstModel.addElement("--------------------------------------------");
        }
        inputDp.setText("");
        scrollListToBottom();
    }
}

 