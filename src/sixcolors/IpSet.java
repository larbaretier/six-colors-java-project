package sixcolors;

import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : IpSet
 * But : faire choisir a l'utilisateur l'ip avec laquelle il doit se connecter
 * 
 * 
 * */
public class IpSet {
	private int[] ipB = { 1, 2, 7, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
	public static String ipS = "";

	public void mainSet(boolean servor) {
		ipS = "";// reset
		int buttonH = Game.hh / 6;
		int buttonW = 3 * Game.wh / 4;
		Font font = new Font("Arial", Font.BOLD, buttonH / 2 - 2);
		Font font2 = new Font("Arial", Font.BOLD, buttonH / 4 - 2);
		boolean ok = false;
		while (!ok) {
			// afficher
			StdDraw.setFont(font2);
			StdDraw.clear(StdDraw.DARK_GRAY);
			StdDraw.filledRectangle(buttonW / 4, buttonH / 3, buttonW / 8, buttonH / 6);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(buttonW / 4, buttonH / 3 - 3, "Ok");
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setFont(font2);
			try {
				StdDraw.text(Game.wh / 2, Game.hh / 4, "Votre ip est :" + InetAddress.getLocalHost());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			StdDraw.setFont(font);
			for (int i = 0; i < this.ipB.length; i++) {
				StdDraw.text(Game.wh / 14 * (i + 1), Game.hh / 2, "" + this.ipB[i]);
				if ((i + 1) % 3 == 0 && i != 11) {// not last !
					StdDraw.text(Game.wh / 14 * (i + 1) + Game.wh / 28, Game.hh / 2, ".");
				}
			}
			StdDraw.show(0);
			double xn = StdDraw.mouseX();
			double yn = StdDraw.mouseY();
			// change number
			for (int i = 0; i < this.ipB.length; i++) {
				if (yn < Game.hh / 2 + Game.hh / 18 && yn > Game.hh / 2 - Game.hh / 18) {
					if (xn < Game.wh / 14 * (i + 1) + Game.wh / 28 && xn > Game.wh / 14 * (i + 1) - Game.wh / 28
							&& StdDraw.mousePressed()) {
						this.ipB[i]++;
						if (this.ipB[i] > 9) {
							this.ipB[i] = 0;
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			// confirm
			if (yn > buttonH / 3 - buttonH / 6 && yn < buttonH / 3 + buttonH / 6 && xn > buttonW / 4 - buttonW / 8
					&& xn < buttonW / 4 + buttonW / 8) {// menu button
				if (StdDraw.mousePressed()) {
					ok = true;

				}
			}

		}
		for (int i = 0; i < this.ipB.length; i++) {
			if (!(ipB[i] == 0 && (i + 1) % 3 == 1) && !(ipB[i] == 0 && (i + 1) % 3 == 2 && ipB[i - 1] == 0)) {
				IpSet.ipS += ipB[i];
				if ((i + 1) % 3 == 0 && i != 11) {// not last !
					IpSet.ipS += ".";
				}
			}

		}
		if (servor) {
			String[] ts = { "Nouvelle Partie", "Sauvegarde", "Carte Crée", "Retour" };
			int[] is = { 15, 9, 8, 1 };
			Menu aSolo = new Menu("Mode de Jeu", ts, is);
			aSolo.actionEffect(5);

		} else {
			String[] tss = { "Nouvelle Partie", "Sauvegarde", "Carte Crée", "Retour" };
			int[] iss = { 15, 9, 8, 1 };
			Menu aSolos = new Menu("Mode de Jeu", tss, iss);
			aSolos.actionEffect(99);
		}

	}
}
