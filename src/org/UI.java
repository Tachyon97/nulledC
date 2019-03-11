package org;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import tasks.Attack;

public class UI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField txtGuard;
	private JButton addTask;
	private JButton startScript;
	DefaultListModel model = new DefaultListModel();
	private JList taskList;
	private JLabel addTaskLabel;
	FlowLayout flow = new FlowLayout();
	private JPanel contentPane;

	@SuppressWarnings("unchecked")
	public UI() {
		super("Nulled combat 1.0");
		setResizable(false);
		setBackground(Color.BLACK);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		getContentPane().setBounds(100, 100, 100, 100);
		taskList = new JList(model);
		taskList.setBounds(45, 115, 281, 87);
		taskList.setBorder(new TitledBorder(null, "Tasks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		taskList.setVisibleRowCount(4);
		txtGuard = new JTextField();
		txtGuard.setBounds(28, 88, 86, 20);
		txtGuard.setColumns(10);
		getContentPane().add(txtGuard);
		textField = new JTextField();
		textField.setBounds(28, 88, 86, 20);
		textField.setBounds(28, 88, 86, 20);
		textField.setColumns(10);
		getContentPane().add(textField);
		startScript = new JButton("Start");
		startScript.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				FirstScript.uiWait = false;
			}
		});
		addTask = new JButton("Add task");
		addTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FirstScript.cache.add(new Attack(txtGuard.getText(), Integer.parseInt(textField.getText())));
				model.addElement(txtGuard.getText() + " Task: " + textField.getText());
			}
		});
		getContentPane().add(addTask);
		getContentPane().add(startScript);
		addTaskLabel = new JLabel("Added Tasks");
		addTaskLabel.setBounds(75, 77, 67, 14);
		getContentPane().add(addTaskLabel);
		getContentPane().add(taskList);
		pack();
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}
