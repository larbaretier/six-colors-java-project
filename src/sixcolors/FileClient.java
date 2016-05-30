package sixcolors;

import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : FileClient
 * But : recevoir un fichier pour le multijoueur de type board
 * 
 * 
 * */
public class FileClient {

	public static String ip = "127.0.0.1";

	public static void main(boolean realServor, boolean first) {
		FileClient.ip = IpSet.ipS;// get entered ip
		if (first) {
			int buttonH = Game.hh / 6;
			Font font2 = new Font("Arial", Font.BOLD, buttonH / 4 - 2);
			StdDraw.setFont(font2);
			StdDraw.clear(StdDraw.DARK_GRAY);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.text(Game.wh / 2, Game.hh / 2, "En attente d'un serveur...");
			try {
				StdDraw.text(Game.wh / 2, Game.hh / 4, "Votre ip est :" + InetAddress.getLocalHost());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			StdDraw.text(Game.wh / 2, Game.hh / 8, "Le serveur doit avoir pour ip:" + IpSet.ipS);
			StdDraw.show(0);
		}
		System.out.println(FileClient.ip);
		String aa = "bin/multiMap.object";
		if (realServor) {
			aa = "src/multiMap.object";
		}
		int a = 4444;
		if (realServor) {
			// le serveur ecoute le client
			a = 4445;
		}
		System.out.println("rec");
		Socket sock;
		try {
			sock = new Socket(FileClient.ip, a);
			byte[] mybytearray = new byte[1024 * 999];
			InputStream is = sock.getInputStream();
			FileOutputStream fos = new FileOutputStream(aa);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int bytesRead = is.read(mybytearray, 0, mybytearray.length);
			bos.write(mybytearray, 0, bytesRead);
			bos.close();
			sock.close();
			System.out.println("sock rec closed");

		} catch (IOException e) {
			FileClient.main(realServor, first);
		}

		System.out.println("done receiving");
	}
}