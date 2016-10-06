package clueGame;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BadConfigFormatException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadConfigFormatException() {
		super("Error: Bad Config Format");
	}
	
	public BadConfigFormatException(String err) throws FileNotFoundException{
		super(err);
		
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter("log.log", true));
			out.println(err);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
	}
}

