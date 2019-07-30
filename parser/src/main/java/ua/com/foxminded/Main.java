/*
 *	@author Stepanov Dmitry 30.06.2019
 *
 *	This application reads data from 3 files, decode abbreveations, order lines by time and 
 prints report that shows top 15 racers and the rest after underline into console
*/

package ua.com.foxminded;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		RacerReader reader = new RacerReader();
		Formatter formatter = new Formatter();
		System.out.println(formatter.format(reader.readRacers("start.log", "end.log", "abbreviations.txt"), 15));
	}
}