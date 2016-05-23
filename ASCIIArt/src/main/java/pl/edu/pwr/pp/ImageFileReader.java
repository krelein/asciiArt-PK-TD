package pl.edu.pwr.pp;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageFileReader {

	/**
	 * Metoda czyta plik pgm i zwraca tablicę odcieni szarości.
	 * 
	 * @param fileName
	 *            nazwa pliku pgm
	 * @return tablica odcieni szarości odczytanych z pliku
	 * @throws URISyntaxException
	 *             jeżeli plik nie istnieje
	 */
	public int[][] readPgmFile(String fileName) throws URISyntaxException {
		int columns = 0;
		int rows = 0;
		int[][] intensities = null;

		Path path = Paths.get(fileName);

		try (BufferedReader reader = Files.newBufferedReader(path)) {
			// w kolejnych liniach kodu można / należy skorzystać z metody
			// reader.readLine()

			// pierwsza linijka pliku pgm powinna zawierać P2
			if (!reader.readLine().equals("P2"))
				return null;
			// druga linijka pliku pgm powinna zawierać komentarz rozpoczynający
			// się od #
			reader.readLine();

			// trzecia linijka pliku pgm powinna zawierać dwie liczby - liczbę
			// kolumn i liczbę wierszy (w tej kolejności). Te wartości należy
			// przypisać do zmiennych columns i rows.
			String[] data = reader.readLine().split(" ");
			columns = Integer.parseInt(data[0]);
			rows = Integer.parseInt(data[1]);

			// czwarta linijka pliku pgm powinna zawierać 255 - najwyższą
			// wartość odcienia szarości w pliku
			reader.readLine();

			// inicjalizacja tablicy na odcienie szarości
			intensities = new int[rows][];

			for (int i = 0; i < rows; i++) {
				intensities[i] = new int[columns];
			}

			// kolejne linijki pliku pgm zawierają odcienie szarości kolejnych
			// pikseli rozdzielone spacjami
			String line = null;
			int currentRow = 0;
			int currentColumn = 0;
			while ((line = reader.readLine()) != null) {
				String[] elements = line.split(" ");
				// ** Nasz kod wczytujący dane z pliku **
				for (int i = 0; i < elements.length; i++) {
					intensities[currentRow][currentColumn] = Integer.parseInt(elements[i]);
					++currentColumn;
					if (currentColumn % columns == 0) {
						++currentRow;
						currentColumn = 0;
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return intensities;
	}

}
