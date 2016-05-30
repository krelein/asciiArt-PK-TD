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
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class MainWindow {

	public JFrame frmAsciiArt;
	private JLabel picLabel = new JLabel();
	private JButton btnZapiszDoPliku;
	private Image image = new Image();
	private ImageQuality quality = ImageQuality.Low;
	private ScaleOption scaleOption = ScaleOption.Big;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

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
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAsciiArt = new JFrame();
		frmAsciiArt.setTitle("Ascii Art");
		frmAsciiArt.setBounds(100, 100, 773, 441);
		frmAsciiArt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAsciiArt.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Wczytaj obraz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadImageDialog dia = new LoadImageDialog();
				dia.setModalityType(ModalityType.APPLICATION_MODAL);
				ImagePath imagePath = dia.showDialog();
				if (imagePath.path != null) {

					try {
						image.loadImage(imagePath);
						setImage();
						btnZapiszDoPliku.setEnabled(true);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}
		});

		btnNewButton.setBounds(10, 11, 119, 23);
		frmAsciiArt.getContentPane().add(btnNewButton);

		btnZapiszDoPliku = new JButton("Zapisz do pliku");
		btnZapiszDoPliku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ImageFileWriter imageFileWriter = new ImageFileWriter();
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");
					chooser.setFileFilter(filter);

					int returnVal = chooser.showSaveDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						image.convertToAscii(quality, scaleOption);
						String path = chooser.getSelectedFile().getPath();
						if (!path.contains(".txt"))
							path = path + ".txt";
						imageFileWriter.saveToTxtFile(image.getAsciiImage(), path);
					}
					JOptionPane.showMessageDialog(null, "Zapisano");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}

			}
		});
		btnZapiszDoPliku.setEnabled(false);
		btnZapiszDoPliku.setBounds(10, 45, 119, 23);
		frmAsciiArt.getContentPane().add(btnZapiszDoPliku);

		JButton btnFunkcja = new JButton("Funkcja 1");
		btnFunkcja.setEnabled(false);
		btnFunkcja.setBounds(10, 284, 119, 23);
		frmAsciiArt.getContentPane().add(btnFunkcja);

		JButton btnFunkcja_1 = new JButton("Funkcja 2");
		btnFunkcja_1.setEnabled(false);
		btnFunkcja_1.setBounds(10, 318, 119, 23);
		frmAsciiArt.getContentPane().add(btnFunkcja_1);

		picLabel.setBounds(155, 11, 313, 247);
		frmAsciiArt.getContentPane().add(picLabel);

		JLabel lblJakoKonwersji = new JLabel("Jakość konwersji:");
		lblJakoKonwersji.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblJakoKonwersji.setBounds(9, 74, 119, 23);
		frmAsciiArt.getContentPane().add(lblJakoKonwersji);

		JRadioButton rdbtnWysoka = new JRadioButton("Wysoka");
		rdbtnWysoka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnWysoka.isSelected())
					quality = ImageQuality.High;
			}
		});
		buttonGroup.add(rdbtnWysoka);
		rdbtnWysoka.setBounds(10, 99, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnWysoka);

		JRadioButton rdbtnNiska = new JRadioButton("Niska");
		rdbtnNiska.setSelected(true);
		rdbtnNiska.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNiska.isSelected())
					quality = ImageQuality.Low;
			}
		});
		buttonGroup.add(rdbtnNiska);
		rdbtnNiska.setBounds(10, 125, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnNiska);

		JLabel lblRozmiarObrazka = new JLabel("Rozmiar Obrazka");
		lblRozmiarObrazka.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRozmiarObrazka.setBounds(10, 155, 119, 14);
		frmAsciiArt.getContentPane().add(lblRozmiarObrazka);

		JRadioButton rdbtnZnakw = new JRadioButton("80 znaków");
		rdbtnZnakw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleOption = ScaleOption.Small;
			}
		});
		buttonGroup_1.add(rdbtnZnakw);
		rdbtnZnakw.setBounds(10, 176, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnZnakw);

		JRadioButton rdbtnZnakw_1 = new JRadioButton("160 znaków");
		rdbtnZnakw_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleOption = ScaleOption.Big;
			}
		});
		rdbtnZnakw_1.setSelected(true);
		buttonGroup_1.add(rdbtnZnakw_1);
		rdbtnZnakw_1.setBounds(10, 202, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnZnakw_1);

		JRadioButton rdbtnSzerokoEkranu = new JRadioButton("Szerokość ekranu");
		rdbtnSzerokoEkranu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleOption = ScaleOption.ScreenSize;
			}
		});
		buttonGroup_1.add(rdbtnSzerokoEkranu);
		rdbtnSzerokoEkranu.setBounds(10, 228, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnSzerokoEkranu);

		JRadioButton rdbtnOryginalna = new JRadioButton("Oryginalna");
		rdbtnOryginalna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleOption = ScaleOption.Normal;
			}
		});
		buttonGroup_1.add(rdbtnOryginalna);
		rdbtnOryginalna.setBounds(10, 254, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnOryginalna);
	}

	/**
	 * Ustawia obrazek w formie
	 */
	public void setImage() {
		ImageIcon icon = new ImageIcon(image.getImage());
		picLabel.setBounds(155, 11, icon.getIconWidth(), icon.getIconHeight());
		picLabel.setIcon(icon);

	}
}
