package pl.edu.pwr.pp;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class mainWindow {

	public JFrame frmAsciiArt;
	private JLabel picLabel = new JLabel();
	private JButton btnZapiszDoPliku;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception e) {
					}
					mainWindow window = new mainWindow();
					window.frmAsciiArt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAsciiArt = new JFrame();
		frmAsciiArt.setTitle("Ascii Art");
		frmAsciiArt.setBounds(100, 100, 509, 337);
		frmAsciiArt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAsciiArt.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Wczytaj obraz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadImageDialog dia = new LoadImageDialog();
				dia.setModalityType(ModalityType.APPLICATION_MODAL);
				if (dia.showDialog()) {
					setImage();
					if (PictureData.canBeConverted)
						btnZapiszDoPliku.setEnabled(true);
					else
						btnZapiszDoPliku.setEnabled(false);
				}
			}
		});

		btnNewButton.setBounds(10, 11, 119, 23);
		frmAsciiArt.getContentPane().add(btnNewButton);

		JButton btnOpcja = new JButton("Opcja 1");
		btnOpcja.setEnabled(false);
		btnOpcja.setBounds(10, 42, 119, 23);
		frmAsciiArt.getContentPane().add(btnOpcja);

		JButton btnOpcja_1 = new JButton("Opcja 2");
		btnOpcja_1.setEnabled(false);
		btnOpcja_1.setBounds(10, 76, 119, 23);
		frmAsciiArt.getContentPane().add(btnOpcja_1);

		btnZapiszDoPliku = new JButton("Zapisz do pliku");
		btnZapiszDoPliku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageFileReader imageFileReader = new ImageFileReader();
				ImageFileWriter imageFileWriter = new ImageFileWriter();
				try {

					int[][] intensities = imageFileReader.readPgmFile(PictureData.path);
					char[][] ascii = ImageConverter.intensitiesToAscii(intensities);

					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("txt","txt");
					chooser.setFileFilter(filter);
					int returnVal = chooser.showSaveDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						imageFileWriter.saveToTxtFile(ascii, chooser.getSelectedFile().getPath()+".txt");
					}

					JOptionPane.showMessageDialog(null, "Zapisano");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnZapiszDoPliku.setEnabled(false);
		btnZapiszDoPliku.setBounds(10, 110, 119, 23);
		frmAsciiArt.getContentPane().add(btnZapiszDoPliku);

		JButton btnFunkcja = new JButton("Funkcja 1");
		btnFunkcja.setEnabled(false);
		btnFunkcja.setBounds(10, 144, 119, 23);
		frmAsciiArt.getContentPane().add(btnFunkcja);

		JButton btnFunkcja_1 = new JButton("Funkcja 2");
		btnFunkcja_1.setEnabled(false);
		btnFunkcja_1.setBounds(10, 178, 119, 23);
		frmAsciiArt.getContentPane().add(btnFunkcja_1);

		picLabel.setBounds(155, 11, 313, 247);
		frmAsciiArt.getContentPane().add(picLabel);
	}

	/**
	 * Ustawia obrazek w formie
	 */
	public void setImage() {
		ImageIcon icon = new ImageIcon(PictureData.image);
		picLabel.setBounds(155, 11, icon.getIconWidth(), icon.getIconHeight());
		picLabel.setIcon(icon);
	}
}
