/*
 *	@author Stepanov Dmitry 18.03.2019
 *
 *	This application divides numbers and prints table with result into console 
*/

package ua.com.foxminded;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter devidend: ");
		int dividend = Math.abs(scanner.nextInt());

		System.out.print("Enter devider: ");
		int divider = Math.abs(scanner.nextInt());
		scanner.close();

		Division division = new Division();
		division.divide(dividend, divider);
		DivisionFormatter formatter = new DivisionFormatter();
		System.out.println(formatter.format(division.divide(dividend, divider)));
	}
}