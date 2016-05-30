package sixcolors;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : FileServor
 * But : Envoyer un fichier pour le multijoueur de type board
 * 
 * 
 * */
public class FileServor {
	public static ServerSocket servsock;

	@SuppressWarnings("resource")

	public static void main(boolean first, boolean realServor) throws IOException {
		if (first) {
			int buttonH = Game.hh / 6;
			Font font2 = new Font("Arial", Font.BOLD, buttonH / 4 - 2);
			StdDraw.setFont(font2);
			StdDraw.clear(StdDraw.DARK_GRAY);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.text(Game.wh / 2, Game.hh / 2, "En attente d'un client...");
			try {
				StdDraw.text(Game.wh / 2, Game.hh / 4, "Votre ip est :" + InetAddress.getLocalHost());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StdDraw.text(Game.wh / 2, Game.hh / 8, "Le client doit avoir pour ip:" + IpSet.ipS);
			StdDraw.show(0);
		}
		String aa = "bin/multiMap.object";
		if (realServor) {
			aa = "src/multiMap.object";
		}
		int a = 4445;
		if (realServor) {
			a = 4444;
		}
		System.out.println("send");
		FileServor.servsock = new ServerSocket(a);
		File myFile = new File(aa);
		while (!FileServor.servsock.isClosed()) {
			Socket sock = servsock.accept();
			byte[] mybytearray = new byte[(int) myFile.length()];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
			bis.read(mybytearray, 0, mybytearray.length);
			OutputStream os = sock.getOutputStream();
			os.write(mybytearray, 0, mybytearray.length);
			os.flush();
			sock.close();
			System.out.println("sock send closed");
			System.out.println("okok");
			if (first) {
				System.out.println("first");
				int[] hat = { 3, 1, 1, 2 };
				Gamu gss2 = new Gamu(hat);
				int[] being = { 0, 2, 3, 3 };
				gss2.startiaMult(9, 9, being, false, false, true, true, true, null, null);
			} else {
				return;
			}
		}
	}
}