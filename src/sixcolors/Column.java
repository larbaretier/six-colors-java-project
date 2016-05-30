/**
* @author Louis
* Colonnes de Cases (pour le plateau de jeu)
*/
package sixcolors;

import java.io.Serializable;

/* Classe : Column
 * But : Gérer les colonnes de cases dont est constitué le tableau 'board'
 * 
 * 
 * */
public class Column implements Serializable {
	private int idcol = 0;// set from 1 to 13 (width)
	private Hexa[] box;
	private int forme = 3;// 1:square,2:diamond,3:hexa

	public int getid() {
		return this.idcol;
	}

	// clone
	public Column(Column tocopy) {
		this.idcol = tocopy.idcol;
		this.box = new Hexa[tocopy.box.length];
		this.box = tocopy.box;
		this.forme = tocopy.forme;
		for (int i = 0; i < this.box.length; i++) {
			this.box[i] = new Hexa(tocopy.box[i], this, this.forme);
		}
	}

	Column(int nb, int idd, int width, int f, int nbP) {
		this.idcol = idd;
		this.box = new Hexa[nb];
		this.forme = f;
		for (int i = 0; i < nb; i++) {
			if (idd == 1 && i == 0) {
				this.box[i] = new Hexa(i, this, 1, 1, this.forme);
			} // high left first player
			else if (idd == width && i == nb - 1) {
				this.box[i] = new Hexa(i, this, 2, 2, this.forme);
			} // down right second player
			else {
				this.box[i] = new Hexa(i, this, this.forme);
			} // other Hexa generated randomly
		}
	}

	public void drawcol() {// couleurs sur term
		int le = this.box.length;
		System.out.print("[");
		for (int k = 0; k < le; k++) {
			System.out.print(this.box[k].getColor());
			System.out.print(" ; ");
		}
		System.out.println("]");
	}

	public void drawcolp() {// proprietaire sur term
		int le = this.box.length;
		System.out.print("[");
		for (int k = 0; k < le; k++) {
			System.out.print(this.box[k].getProp());
			System.out.print("  ");
		}
		System.out.println("]");
	}

	public int getIdcol() {
		return idcol;
	}

	public void setIdcol(int idcol) {
		this.idcol = idcol;
	}

	public Hexa[] getBox() {
		return box;
	}

	public void setBox(Hexa[] box) {
		this.box = box;
	}

}