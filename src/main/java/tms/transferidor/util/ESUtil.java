package tms.transferidor.util;

import java.util.Scanner;

public class ESUtil {
	private static Scanner scanner = new Scanner(System.in);
	public static void print(String msg) {
		System.out.println(msg);
	}
	public static String input(String msg) {
		System.out.print(msg);
		return scanner.nextLine();
	}
}
