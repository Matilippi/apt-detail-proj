package com.detail.app.mydetail.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.detail.app.mydetail.controller.SurgeriesController;
import com.detail.app.mydetail.model.Surgery;
import com.detail.app.mydetail.view.SurgeryView;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SurgeryScreen extends JFrame implements SurgeryView{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtPatientName;
	private JList<Surgery> listSurgeries;
	private DefaultListModel<Surgery> listSurgeriesModel;
	private JLabel lblErrorMessage;
	private SurgeriesController surgeriesController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SurgeryScreen frame = new SurgeryScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public DefaultListModel<Surgery> getListSurgeriesModel(){
		return listSurgeriesModel;
	}
	public void setSurgeriesController(SurgeriesController surgeriesController) {
		this.surgeriesController = surgeriesController;
	}
	
	/**
	 * Create the frame.
	 */
	public SurgeryScreen() {
		listSurgeriesModel = new DefaultListModel<>();
		listSurgeries = new JList<>(listSurgeriesModel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{39, 12, 130, 22, 57, 130, 0};
		gbl_contentPane.rowHeights = new int[]{26, 29, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("id");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		txtId = new JTextField();
		txtId.setName("TextBoxField");
		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtId.insets = new Insets(0, 0, 5, 5);
		gbc_txtId.gridx = 2;
		gbc_txtId.gridy = 0;
		contentPane.add(txtId, gbc_txtId);
		txtId.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("patient Name");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 0;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtPatientName = new JTextField();
		txtPatientName.setName("PatientNameBoxField");
		GridBagConstraints gbc_txtPatientName = new GridBagConstraints();
		gbc_txtPatientName.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtPatientName.insets = new Insets(0, 0, 5, 0);
		gbc_txtPatientName.gridx = 5;
		gbc_txtPatientName.gridy = 0;
		contentPane.add(txtPatientName, gbc_txtPatientName);
		txtPatientName.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(
			e -> surgeriesController.newSurgery(new Surgery(txtId.getText(),txtPatientName.getText()))
		);
		txtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnAdd.setEnabled(
				!txtId.getText().trim().isEmpty()&&
				!txtPatientName.getText().trim().isEmpty()
			);}
		});
		txtPatientName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnAdd.setEnabled(
				!txtId.getText().trim().isEmpty()&&
				!txtPatientName.getText().trim().isEmpty()
			);}
		});
		btnAdd.setEnabled(false);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridwidth = 2;
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 1;
		contentPane.add(btnAdd, gbc_btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setEnabled(false);
		btnRemove.addActionListener((e)-> {
			surgeriesController.deleteSurgery(listSurgeries.getSelectedValue());
		});
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnRemove.gridwidth = 2;
		gbc_btnRemove.gridx = 4;
		gbc_btnRemove.gridy = 1;
		contentPane.add(btnRemove, gbc_btnRemove);
		
		listSurgeries.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnRemove.setEnabled(listSurgeries.getSelectedIndex()!= -1);
			}
		});
		listSurgeries.setName("SurgeriesList");
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 2;
		gbc_list.gridy = 3;
		contentPane.add(listSurgeries, gbc_list);
		lblErrorMessage = new JLabel(" ");
		lblErrorMessage.setName("error");
		GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
		gbc_lblErrorMessage.gridx = 5;
		gbc_lblErrorMessage.gridy = 3;
		contentPane.add(lblErrorMessage, gbc_lblErrorMessage);
	}
	
	@Override
	public void showAllSurgeries(List<Surgery> surgeries) {
		surgeries.stream().forEach(listSurgeriesModel::addElement);
	}

	@Override
	public void showError(String message, Surgery surgery) {
		lblErrorMessage.setText(message + surgery);
		
	}
	private void resetErrorLabel() {
		lblErrorMessage.setText("");
	}

	@Override
	public void surgeryAdded(Surgery surgery) {
		listSurgeriesModel.addElement(surgery);
		resetErrorLabel();
		
	}

	@Override
	public void surgeryRemoved(Surgery surgery) {
		listSurgeriesModel.removeElement(surgery);
		resetErrorLabel();
		
	}


}
