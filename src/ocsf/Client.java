package ocsf;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.event.AncestorEvent;

import com.lloseng.ocsf.client.ObservableClient;

public class Client extends ObservableClient {
	String message = "";
	Client client;

	public Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object message) {
		super.handleMessageFromServer(message);
		this.message = (String) message;
		System.out.println(message);
	}

	public void answer() {
		int answer = 0;
		int binary1 = 0;
		int binary2 = 0;
		String[] ans = message.split(" ");
		if (message.contains("What is")) {
			binary1 = Integer.parseInt(ans[2]);
			binary2 = Integer
					.parseInt(ans[4].substring(0, ans[4].length() - 1));
			switch (ans[3]) {
			case "^":
				answer = binary1 ^ binary2;
				break;
			case "|":
				answer = binary1 | binary2;
				break;
			case "&":
				answer = binary1 & binary2;
				break;
			default:
				break;
			}
		}
		try {
			client.sendToServer("" + answer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() throws IOException {
		openConnection();
	}

	public static void main(String[] args) {
		String host = "10.2.13.161";
		int port = 5001;
		Client client = new Client(host, port);
		Scanner scan = new Scanner(System.in);
		try {
			client.run();
			client.sendToServer("Login Map");
			while (client.isConnected()) {
				// client.sendToServer(scan.nextLine());
				client.answer();
			}
			client.closeConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
