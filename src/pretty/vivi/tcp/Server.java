package pretty.vivi.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import pretty.vivi.tcp.sever.handler.GeometryHandler;

public class Server {

	Logger log = (Logger) Logger.getLogger(getClass());

	private ServerSocket server;

	public enum TriangleType {
		KHONG_PHAI_TAM_GIAC, TAM_GIAC, TAM_GIAC_VUONG, TAM_GIAC_CAN, TAM_GIAC_DEU
	}

	private static Socket client;

	public Server() {
		try {
			server = new ServerSocket(1234);
			System.out.println("Server is running............!");

			while (true) {
				client = server.accept();
				handleClientConnection(client);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	int[] parseInput(String input) {
		int[] array = new int[input.length()];
		Scanner read = null;
		try {
			read = new Scanner(input);
			for (int i = 0; i < 6; i++) {
				array[i] = read.nextInt();
			}
		} finally {
			IOUtils.closeQuietly(read);
		}
		return array;
	}

	public void handleClientConnection(Socket client) {
		BufferedReader reader = null;
		PrintWriter writer = null;
		log.info("Handling client connection...");

		try {
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

			// 1. Read input
			String input = reader.readLine();
			log.info("Input: " + input);

			// 2. Get corresponding Geometry handler
			int[] points = parseInput(input);
			GeometryHandler handler = GeometryHandler.Factory.getHandler(points);

			// 3. Return result of handler to client
			String result = handler.check(points);
			log.info("Geometry check result: " + result);
			writer.write(result);

			writer.flush();
			log.info("Finish handling client connection");
		} catch (InputMismatchException e) {
			log.error("Failed to handle Client connection", e);
			if (writer != null) {
				writer.write("Error: Invalid data (input must contains 6 int numbers)");
				writer.flush();
			}
		} catch (Exception e) {
			log.error("Failed to handle Client connection", e);
			if (writer != null) {
				writer.write("Unknow error. PLease check log");
				writer.flush();
			}
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(client);
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}
