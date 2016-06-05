package pl.edu.pwr.pp;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;

public class MainWindow {

	public JFrame frmAsciiArt;
	private JLabel picLabel = new JLabel();
	private JButton btnZapiszDoPliku;
	private JButton btnSaveAsImage = new JButton("Zapisz jako obrazek");
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
						btnSaveAsImage.setEnabled(true);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}
		});

		btnNewButton.setBounds(10, 11, 119, 23);
		frmAsciiArt.getContentPane().add(btnNewButton);

		btnZapiszDoPliku = new JButton("Zapisz do pliku");
		btnZapiszDoPliku.addActionListener(e -> {
			{
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
						JOptionPane.showMessageDialog(null, "Zapisano");
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}

			}
		});
		btnZapiszDoPliku.setEnabled(false);
		btnZapiszDoPliku.setBounds(10, 45, 119, 23);
		frmAsciiArt.getContentPane().add(btnZapiszDoPliku);
		btnSaveAsImage.setFont(new Font("Tahoma", Font.PLAIN, 9));

		btnSaveAsImage.setEnabled(false);
		btnSaveAsImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Font font = new Font("Serif", Font.PLAIN, 20);
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("png", "png");
				chooser.setFileFilter(filter);

				try {
					int returnVal = chooser.showSaveDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						image.convertToAscii(quality, scaleOption);
						BufferedImage img = image.textToImage(font);
						String path = chooser.getSelectedFile().getPath();

						if (!path.contains(".png"))
							path = path + ".png";

						File outputfile = new File(path);
						ImageIO.write(img, "png", outputfile);

						JOptionPane.showMessageDialog(null, "Zapisano");
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSaveAsImage.setBounds(10, 79, 119, 23);
		frmAsciiArt.getContentPane().add(btnSaveAsImage);

		JButton btnFunkcja_1 = new JButton("Funkcja 2");
		btnFunkcja_1.setEnabled(false);
		btnFunkcja_1.setBounds(10, 318, 119, 23);
		frmAsciiArt.getContentPane().add(btnFunkcja_1);

		picLabel.setBounds(155, 11, 313, 247);
		frmAsciiArt.getContentPane().add(picLabel);

		JLabel lblJakoKonwersji = new JLabel("Jakość konwersji:");
		lblJakoKonwersji.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblJakoKonwersji.setBounds(10, 109, 119, 23);
		frmAsciiArt.getContentPane().add(lblJakoKonwersji);

		JRadioButton rdbtnWysoka = new JRadioButton("Wysoka");
		rdbtnWysoka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnWysoka.isSelected())
					quality = ImageQuality.High;
			}
		});
		buttonGroup.add(rdbtnWysoka);
		rdbtnWysoka.setBounds(11, 134, 109, 23);
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
		rdbtnNiska.setBounds(11, 160, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnNiska);

		JLabel lblRozmiarObrazka = new JLabel("Rozmiar Obrazka");
		lblRozmiarObrazka.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRozmiarObrazka.setBounds(11, 190, 119, 14);
		frmAsciiArt.getContentPane().add(lblRozmiarObrazka);

		JRadioButton rdbtnZnakw = new JRadioButton("80 znaków");
		rdbtnZnakw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleOption = ScaleOption.Small;
			}
		});
		buttonGroup_1.add(rdbtnZnakw);
		rdbtnZnakw.setBounds(11, 211, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnZnakw);

		JRadioButton rdbtnZnakw_1 = new JRadioButton("160 znaków");
		rdbtnZnakw_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleOption = ScaleOption.Big;
			}
		});
		rdbtnZnakw_1.setSelected(true);
		buttonGroup_1.add(rdbtnZnakw_1);
		rdbtnZnakw_1.setBounds(11, 237, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnZnakw_1);

		JRadioButton rdbtnSzerokoEkranu = new JRadioButton("Szerokość ekranu");
		rdbtnSzerokoEkranu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleOption = ScaleOption.ScreenSize;
			}
		});
		buttonGroup_1.add(rdbtnSzerokoEkranu);
		rdbtnSzerokoEkranu.setBounds(11, 263, 109, 23);
		frmAsciiArt.getContentPane().add(rdbtnSzerokoEkranu);

		JRadioButton rdbtnOryginalna = new JRadioButton("Oryginalna");
		rdbtnOryginalna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleOption = ScaleOption.Normal;
			}
		});
		buttonGroup_1.add(rdbtnOryginalna);
		rdbtnOryginalna.setBounds(11, 289, 109, 23);
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
