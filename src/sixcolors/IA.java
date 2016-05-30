/**
* @author Louis
* intelligence artificielle
*/
package sixcolors;

import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : IA
 * But : faire reflechir l'ordi et le faire jouer
 * 
 * 
 * */
public class IA extends Player {
	private int level = 1;
	private Board ori;
	private Animation ano=new Animation();

	public IA(Board b, int lv,int c,int [] h) {
		this.level = lv-1;//décalage avec lv donné a cause du choix "humain" qui n'est pas une IA
		this.ori = new Board(b);
		this.setType(0);
		this.setColor(c);
		this.ano.setPlayerHat(h);
	}
	
	public int testcolor(int c) {
		this.ori.save();
		int score = 0;
		Board b1 = new Board(ori);
		b1=b1.readit(true);
		Board b2 = new Board(ori);
		b2=b2.readit(true);
		b1.setColor1(c);
		b1.setPlayTurn(1);
		b1.updateBoard();
		b1.upscore(1);
		b1.setPlayTurn(2);
		b2.setColor2(c);
		b2.setPlayTurn(2);
		b2.updateBoard();
		b2.upscore(2);
		b2.setPlayTurn(1);
		int a = 1;
		double b = 0.75;// their score is not as important as ours

		// IA power level
		if (this.level == 1) {
			b = 0;
		} else if (this.level == 2) {
			a = 0;
			b = 1;
		}
		int e = 0;
		// if 4 players
		Board b3 = new Board(ori);
		Board b4 = new Board(ori);
		if (this.ori.getNbPlayer() == 4) {
			e = 1;
			b3=b3.readit(true);
			b4=b4.readit(true);
			b3.setColor3(c);
			b3.setPlayTurn(3);
			b3.updateBoard();
			b3.upscore(3);
			b3.setPlayTurn(4);
			b4.setColor1(c);
			b4.setPlayTurn(4);
			b4.updateBoard();
			b4.upscore(4);
			b4.setPlayTurn(1);
		}
		if (ori.getPlayTurn() == 1) {
			score = a * b1.getScores()[0] + (int) b * b2.getScores()[1]
					+ e * ((int) b * b3.getScores()[2] + (int) b * b4.getScores()[3]);
			System.out.println("scores");
			System.out.println(b1.getScores()[0]);
			System.out.println(b2.getScores()[1]);
		} else if (ori.getPlayTurn() == 2) {
			score = (int) a * b2.getScores()[1] + a * b1.getScores()[0]
					+ e * ((int) b * b3.getScores()[2] + (int) b * b2.getScores()[3]);
			System.out.println("scores");
			System.out.println(b1.getScores()[0]);
			System.out.println(b2.getScores()[1]);
		} else if (ori.getPlayTurn() == 3) {
			score = (int) b * b2.getScores()[1] + a * b3.getScores()[2]
					+ e * ((int) b * b1.getScores()[0] + (int) b * b4.getScores()[3]);
		} else if (ori.getPlayTurn() == 4) {
			score = (int) b * b3.getScores()[2] + a * b4.getScores()[3]
					+ e * ((int) b * b2.getScores()[1] + (int) b * b1.getScores()[0]);
		}

		return score;
	}

	public int decision() {
		int c = -1;
		int highs = -1;
		if (this.level != 0) {// intelligent choice from IA
			for (int i = 0; i < 6; i++) {
				if ((this.ori.getNbPlayer() == 2 && i != ori.getColor1() && i != ori.getColor2())
						|| (this.ori.getNbPlayer() == 4 && i != ori.getColor1() && i != ori.getColor2()
								&& i != ori.getColor3() && i != ori.getColor4())) {
					if (this.testcolor(i) > highs) {
						highs = this.testcolor(i);
						c = i;
					}
				}
			}
		} else {// random IA choice
			while (c == -1 || c == ori.getColor1() || c == ori.getColor2()
					|| (this.ori.getNbPlayer() == 4 && c == ori.getColor3())
					|| (this.ori.getNbPlayer() == 4 && c == ori.getColor4())) {
				Random rand = new Random();
				c = rand.nextInt((5) + 1);
			}
		}
		return c;
	}
	
	public int play(Board b, int ent,int ang, boolean lef, int fp, int im, int nbp, int playerNb) {
		if(Gamu.graph){
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledRectangle(b.getWidth() * 6 + 6 + 6 + 1, 5, 6, 3);
			StdDraw.setPenColor(StdDraw.BLACK);
			if(Gamu.graph)
			this.ano.drawPlayer(playerNb,0, this.ori,true);
			if (nbp == 4) {
				drawchoice(b.getColor1(), b.getColor2(), b.getColor3(), b.getColor4(), b);
			} else {
				drawchoice(b.getColor1(), b.getColor2(), 19, 19, b);
			}
		}
		this.ori=b;
		ent = this.decision();
		// 1.5sec d'attente pour pas de clicks non voulus
				try {
					Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
			}
		return ent;
	}

}
