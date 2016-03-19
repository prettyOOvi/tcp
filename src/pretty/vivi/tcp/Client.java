package pretty.vivi.tcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class Client {

	public static String inputFromKeyboard() throws IOException {
		String input = "";
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("(x1 y1  x2  y2  x3  y3 )");
		input = keyboard.readLine();

		return input;
	}

	private static Socket client;

	public static void calculator(String input) {
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			client = new Socket("localhost", 1234);

			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

			writer.write(input);
			writer.write('\n');
			writer.flush();

			String response = null;
			do {
				if (reader.ready()) {
					response = reader.readLine();
					System.out.println("Server response: " + response);
				}

				Thread.sleep(100);
			} while (response == null || response.equals(""));
		} catch (Exception ex) {
			throw new RuntimeException("Failed to calculate", ex);
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(client);
		}
	}

	public static void main(String[] args) {
		String ch = "";

		do {
			try {
				String input;
				BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Enter choice: (1 - Data input from keyboard || 2 - Data input from file): ");
				ch = keyboard.readLine();
				System.out.println("");

				if (ch.equalsIgnoreCase("1")) {
					input = inputFromKeyboard();
					System.out.println("");
					System.out.println("----------------------------------------");
					System.out.println("Input: " + input);
					calculator(input);
					System.out.println("----------------------------------------");
				} else if (ch.equalsIgnoreCase("2")) {
					BufferedReader fileReader = null;
					try {
						fileReader = new BufferedReader(new FileReader("input.txt"));
						while ((input = fileReader.readLine()) != null) {

							if (input.equalsIgnoreCase("stop")) {
								break;
							}

							System.out.println("----------------------------------------");
							System.out.println("Input: " + input);
							calculator(input);
							System.out.println("----------------------------------------");
						} 
					} finally {
						IOUtils.closeQuietly(fileReader);
					}
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} while (!ch.endsWith("quit"));
	}

}
