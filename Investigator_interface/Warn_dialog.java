package Investigator_interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Warn_dialog extends JDialog implements ActionListener {
    JButton btSure = new JButton("确定");
    JButton btCancel = new JButton("取消");
    public Warn_dialog(JFrame jFrame) {
        super(jFrame,"Warning",true);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel tips = new JLabel("文本框内容不能为空");
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(40,50,30,50);
        add(tips,constraints);

        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(btSure,constraints);
        btSure.addActionListener(this);

        //setLocationRelativeTo(null);
        setBounds(750,300,300,300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source== btSure) {
            this.dispose();
        }
    }
}
