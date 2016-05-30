package pl.edu.pwr.pp;

public class ImageConverter {

	/**
	 * Znaki odpowiadające kolejnym poziomom odcieni szarości - od czarnego (0)
	 * do białego (255).
	 */
	private static String INTENSITY_2_ASCII = "@%#*+=-:. ";
	private static String INTENSITY_1_ASCII = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";

	/**
	 * Metoda zwraca znak odpowiadający danemu odcieniowi szarości. Odcienie
	 * szarości mogą przyjmować wartości z zakresu [0,255]. Zakres jest dzielony
	 * na równe przedziały, liczba przedziałów jest równa ilości znaków w
	 * {@value #INTENSITY_2_ASCII}. Zwracany znak jest znakiem dla przedziału,
	 * do którego należy zadany odcień szarości.
	 * 
	 * 
	 * @param intensity
	 *            odcień szarości w zakresie od 0 do 255
	 * @return znak odpowiadający zadanemu odcieniowi szarości
	 */
	public static char intensityToAscii(int intensity, ImageQuality quality) {

		int step = quality.getStep();
		int limit = step;
		int n = 0;
		while (n < quality.getCharNumber()) {
			if (intensity <= limit)
			{
				if (quality == ImageQuality.Low)
					return INTENSITY_2_ASCII.charAt(n);
				else
					return INTENSITY_1_ASCII.charAt(n);
			}
			else {
				++n;
				limit += step;
				if (n % 2 != 0 || n % 50 == 0)
					++limit;
			}
		}
		return '@';
	}

	public static char intensityToAscii(int intensity) {
		return intensityToAscii(intensity, ImageQuality.Low);
	}

	/**
	 * Metoda zwraca dwuwymiarową tablicę znaków ASCII mając dwuwymiarową
	 * tablicę odcieni szarości. Metoda iteruje po elementach tablicy odcieni
	 * szarości i dla każdego elementu wywołuje {@ref #intensityToAscii(int)}.
	 * 
	 * @param intensities
	 *            tablica odcieni szarości obrazu
	 * @return tablica znaków ASCII
	 */
	public static char[][] intensitiesToAscii(int[][] intensities, ImageQuality quality) {

		char[][] result = new char[intensities.length][intensities[0].length];

		for (int i = 0; i < intensities.length; ++i)
			for (int j = 0; j < intensities[0].length; ++j)
				result[i][j] = intensityToAscii(intensities[i][j], quality);

		return result;
	}

}
