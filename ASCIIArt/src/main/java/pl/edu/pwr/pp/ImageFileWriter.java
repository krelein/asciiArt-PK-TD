package pl.edu.pwr.pp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class ImageFileWriter {

	public void saveToTxtFile(char[][] ascii, String fileName){
		// np. korzystając z java.io.PrintWriter
		// TODO Wasz kod
		File file  = new File(fileName);
		if(file.exists()){
			file.delete();
		}
		else{
			try{
			file.createNewFile();
			}
			catch(IOException e){
				
			}
		}
		
		try{
		PrintWriter writer = new PrintWriter(fileName);
	    
		 for(int i=0;i<ascii.length;++i){
			 for(int j=0;j<ascii[0].length; ++j){
				 writer.print(ascii[i][j]); 
			 }
			 writer.print('\n');
		 }
		 
		 writer.close();
		}
		catch(FileNotFoundException e){
			
		}
	}
}
	
