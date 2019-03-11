package org;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import tasks.Attack;

public class sad extends JFrame {

	private JPanel contentPane;
	DefaultListModel model = new DefaultListModel();
	private JTextField txtNpclist;
	private JTextField txtTaskamount;
	private JCheckBox checkHeal;
	private JCheckBox checkSlayer;

	public sad() {
		super("Nulled combat 1.0");
		setForeground(Color.BLACK);
		getContentPane().setLayout(null);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JList<?> list = new JList(model);
		list.setBorder(new TitledBorder(null, "Tasks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(4);
		list.setBounds(10, 42, 281, 87);
		contentPane.add(list);
		txtNpclist = new JTextField();
		txtNpclist.setBounds(10, 11, 86, 20);
		contentPane.add(txtNpclist);
		txtNpclist.setColumns(10);

		txtTaskamount = new JTextField();
		txtTaskamount.setColumns(10);
		txtTaskamount.setBounds(106, 11, 86, 20);
		contentPane.add(txtTaskamount);

		JButton btnAddTask = new JButton("Add task");
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkSlayer.isSelected()) {
					FirstScript.checkSlayer();
					FirstScript.cache.add(new Attack(txtNpclist.getText(), FirstScript.slayer));
					model.addElement(txtNpclist.getText() + " Task: Until slayer task finished");
				}
				FirstScript.cache.add(new Attack(txtNpclist.getText(), Integer.parseInt(txtTaskamount.getText())));
				model.addElement(txtNpclist.getText() + " Task: " + txtTaskamount.getText());
			}
		});
		btnAddTask.setBounds(202, 10, 89, 23);
		contentPane.add(btnAddTask);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				FirstScript.uiWait = false;
			}
		});
		btnStart.setBounds(301, 10, 89, 23);
		contentPane.add(btnStart);

		checkHeal = new JCheckBox("heal");
		checkHeal.setBounds(297, 43, 97, 23);
		contentPane.add(checkHeal);

		checkSlayer = new JCheckBox("Slayer task");
		checkSlayer.setBounds(297, 69, 97, 23);
		contentPane.add(checkSlayer);

	}

}
