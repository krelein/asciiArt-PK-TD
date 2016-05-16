package pl.edu.pwr.pp;

public class ImageConverter {

	/**
	 * Znaki odpowiadające kolejnym poziomom odcieni szarości - od czarnego (0)
	 * do białego (255).
	 */
	public static String INTENSITY_2_ASCII = "@%#*+=-:. ";

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
	public static char intensityToAscii(int intensity) {
		
		int n = 0;
		if(intensity<26)n=0;
		else if(intensity<52)n=1;
		else if(intensity<77)n=2;		
		else if(intensity<103)n=3;
		else if(intensity<128)n=4;
		else if(intensity<154)n=5;
		else if(intensity<180)n=6;
		else if(intensity<205)n=7;
		else if(intensity<231)n=8;
		else n=9;
		
		return INTENSITY_2_ASCII.charAt(n);
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
	public static char[][] intensitiesToAscii(int[][] intensities) {
	
		char[][] result = new char[intensities.length][intensities[0].length];
		
		for(int i=0; i< intensities.length; ++i)
			for(int j=0;j<intensities[0].length;++j)
				result[i][j] = intensityToAscii(intensities[i][j]);
		
		return result;
	}

}
