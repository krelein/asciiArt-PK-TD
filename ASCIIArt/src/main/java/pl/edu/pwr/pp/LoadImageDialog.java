package pl.edu.pwr.pp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;

public class LoadImageDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField pathDirText;
	private JTextField pathUrlText;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rbDrive;
	private JRadioButton rbUrl;
	private boolean result=false;

	public boolean showDialog() {
		setVisible(true);
		return result;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoadImageDialog dialog = new LoadImageDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LoadImageDialog() {
		setTitle("Wczytaj obraz");
		setBounds(100, 100, 450, 229);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			rbDrive = new JRadioButton("Z dysku");
			buttonGroup.add(rbDrive);
			rbDrive.setSelected(true);
			rbDrive.setBounds(31, 27, 109, 23);
			contentPanel.add(rbDrive);
		}
		{
			rbUrl = new JRadioButton("Z adresu URL");
			buttonGroup.add(rbUrl);
			rbUrl.setBounds(31, 84, 109, 23);
			contentPanel.add(rbUrl);
		}
		{
			pathDirText = new JTextField();
			pathDirText.setEditable(false);
			pathDirText.setColumns(10);
			pathDirText.setBounds(60, 57, 340, 20);
			contentPanel.add(pathDirText);
		}
		{
			pathUrlText = new JTextField();
			pathUrlText.setColumns(10);
			pathUrlText.setBounds(60, 114, 340, 20);
			contentPanel.add(pathUrlText);
		}
		{
			JButton button = new JButton("Wybierz plik");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {// Wybieranie pliku
															// z dysku
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						pathDirText.setText(chooser.getSelectedFile().getPath());
					}
				}
			});
			button.setBounds(311, 27, 89, 23);
			contentPanel.add(button);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {// Zamykanie
																// okienka i
																// wpisywanie
																// ścieżki do
																// obrazka
						if (rbDrive.isSelected()) {
							PictureData.path = pathDirText.getText();
							PictureData.pictureType = PictureType.Dir;
						} else {
							PictureData.path = pathUrlText.getText();
							PictureData.pictureType = PictureType.Url;
						}
						try {
							PictureData.LoadImage();
							result=true;
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}			
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Anuluj");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
