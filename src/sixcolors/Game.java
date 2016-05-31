/**
* @author Louis
* Demarage du jeu
*/
package sixcolors;

import java.awt.Font;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : Game
 * But : fichier racine du jeu, choix de mode d'affichage et intro si en mode graphique
 * 
 * 
 * */
public class Game {
	static boolean cake=false;
	static int song=0;
	static int wh = 640 + 100;// window width in pixel
	static int hh = 640;
	public static void main(String args[]) {
		int wh = Game.wh;// window width in pixel
		int hh = Game.hh;// window height in pixel
		System.out.println("Want to play ?");

		System.out.println("lets go !");
		playTerminal();
		
		StdDraw.setCanvasSize(wh,hh);
		intro();
		String[] t = { "Start" };
		int[] i = { 1 };
		Menu m = new Menu("", t, i);
		m.showMenu();

	}
	
	public static void playTerminal(){
		boolean graphic=true;
		boolean choice=false;
		Scanner scan = new Scanner(System.in);
		while(!choice){
			System.out.println("Jouer en mode 0: Graphique 1: Terminal");
			String c = scan.next();
			if(c.equals("0")){
				return;
			}else if(c.equals("1")){
				choice=true;
			}
		}
		Gamu.graph=false;
		choice=false;
		System.out.println("Ok, mode terminal");
		System.out.println("(dommage car la version graphique est vraiment pas mal...)");
		String ca;
		while(!choice){
			System.out.println("Regle de capture 0:Oui 1:Non");
			ca = scan.next();
			if(ca.equals("0")){
				choice=true;
			}else if(ca.equals("1")){
				choice=true;
			}
		}
		choice=false;
		String nb="";
		System.out.println("Ok");
		while(!choice){
			System.out.println("Nombre de joueurs 0:deux 1:quatre");
			nb = scan.next();
			if(nb.equals("0") ||nb.equals("1")){
				choice=true;
			}
		}
		choice=false;
		System.out.println("Ok");
		String fo="";
		while(!choice){
			System.out.println("Forme 0:carre 1:hexagone");
			fo = scan.next();
			if(fo.equals("0") ||fo.equals("1")){
				choice=true;
			}
		}
		choice=false;
		System.out.println("Ok");
		int w=7;
		while(!choice){
			System.out.println("Largeur entre 7 et 29");
			String hg = scan.next();
			w=Integer.parseInt(hg);
			if(w>6 && w<30){
				choice=true;
				if(w%2==0){
					w--;
				}
			}
		}
		choice=false;
		System.out.println("Ok");
		int h=7;
		while(!choice){
			System.out.println("Hauteur entre 7 et 29");
			String gh = scan.next();
			h=Integer.parseInt(gh);
			if(h>6 && h<30){
				choice=true;
				if(h%2==0){
					h--;
				}
			}
		}
		choice=false;
		System.out.println("Ok");
		String p1 = "";
		while(!choice){
			System.out.println("Joueur 1 : 0:humain 1:ia idiote 2:ia agressive 3:ia sournoise 4:ia douee");
			p1 = scan.next();
			if(p1.equals("0") ||p1.equals("1")||p1.equals("2")||p1.equals("3")||p1.equals("4")){
				choice=true;
			}
		}
		choice=false;
		System.out.println("Ok");
		String p2 = "";
		while(!choice){
			System.out.println("Joueur 2 : 0:humain 1:ia idiote 2:ia agressive 3:ia sournoise 4:ia douee");
			p2 = scan.next();
			if(p2.equals("0") ||p2.equals("1")||p2.equals("2")||p2.equals("3")||p2.equals("4")){
				choice=true;
			}
		}
		choice=false;
		System.out.println("Ok");
		String p3="-1";
		String p4="-1";
		if(nb.equals("1")){//4 players
			while(!choice){
				System.out.println("Joueur 3 : 0:humain 1:ia idiote 2:ia agressive 3:ia sournoise 4:ia douee");
				p3 = scan.next();
				if(p3.equals("0") ||p3.equals("1")||p3.equals("2")||p3.equals("3")||p3.equals("4")){
					choice=true;
				}
			}
			choice=false;
			System.out.println("Ok");
			while(!choice){
				System.out.println("Joueur 4 : 0:humain 1:ia idiote 2:ia agressive 3:ia sournoise 4:ia douee");
				p4 = scan.next();
				if(p4.equals("0") ||p4.equals("1")||p4.equals("2")||p4.equals("3")||p4.equals("4")){
					choice=true;
				}
			}
			choice=false;
			System.out.println("Ok");
		}
		choice=false;
		System.out.println("Ok, bonne partie !");
		//traduction des choix
		
		int [] being={Integer.parseInt(p1),Integer.parseInt(p2),Integer.parseInt(p3),Integer.parseInt(p4)};
		int [] hat={0,0,0,0};
		Gamu gss1 = new Gamu(hat);
		int ff;
		if(fo.equals("0")){ff=1;}else{ff=3;}
		gss1.setForme(ff);
		gss1.startiaGen(w, h, being, false, false);
		scan.close();
		
	}
	
	public static void maincopy() {
		int wh = 640 + 100;// window width in pixel
		int hh = 640;// window height in pixel
		System.out.println("Whant to play ?");
		System.out.println("lets go !");
		intro();
		String[] t = { "Start" };
		int[] i = { 1 };
		Menu m = new Menu("", t, i);
		m.showMenu();
	}
	public static void intro(){
		//put background
		String[] texture = { "images/back_texture.png", "images/button_texture.png", "images/button_texture2.png" };
		String[] tScreen={"images/logod1.png", "images/logod2.png", "images/logod3.png",
				"images/logod4.png", "images/logod5.png", "images/logod6.png"};
		int wh = 640 + 100;// window width in pixel
		int hh = 640;// window height in pixel
		StdDraw.setXscale(0,wh);
		StdDraw.setYscale(0,hh);
		int time=0;
		int scene=0;//1
		//heads
		double x1;
		double x2;
		boolean up1;
		boolean up2;
		//title
		int step=0;//0
		double xm=wh/2;
		double ym=hh+hh*0.5;
		boolean running=true;
		while(running){
			if(scene==0){//black screen
				StdDraw.clear(StdDraw.BLACK);
				step++;
				if(step>100){
					step=0;
					scene=1;
				}
			}else if(scene==1){//java logo with sound
				StdDraw.clear(StdDraw.BLACK);
				StdDraw.picture(wh/2, hh/2, "images/javalogo.png",wh/3,hh/4);
				StdDraw.show(0);
				MakeSound m=new MakeSound();
				m.playSound("images/javasegasound.wav",0);//apparition jusqua fin du son
				step=301;
				if(step>300){
					step=0;
					scene=2;
				}
			}else if(scene==2){//isep project
				StdDraw.clear(StdDraw.BLACK);
				StdDraw.setPenColor(StdDraw.WHITE);
				Font fontTf = new Font("Arial", Font.BOLD, (int) (hh*0.04));
				StdDraw.setFont(fontTf);
				StdDraw.text(wh/2, hh/2,"An ISEP school project");
				step++;
				if(step>300){
					step=0;
					scene=5;
				}
			}else if(scene==3){
				
			}else if(scene==4){
			
			}else if(scene==5){
				step++;
				if(step>10){
					step=0;
					scene=6;
					Game.song=1;
					Thread t1 = new Thread(new Runnable() {
					    public void run() {//met la musique en background
					    	MakeSound m=new MakeSound();
					    	m.playSound("images/titlesix.wav",1);
					    }
					});
					Thread t = new Thread(new Runnable() {
					    public void run() {//met la musique en background
					    	MakeSound m=new MakeSound();
					    	if(Game.song==1){
								m.playSound("images/javam.wav",1);
								if(Game.song==1){
									Game.song=0;
									maincopy();
								}
					    	}else if(Game.song==2){
					    		m.playSound("images/FIRST.wav",2);
					    	}
							run();//en boucle
					    }
					});
					t1.start();
					t.start();
					//sleep
					try {
						Thread.sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}else if(scene==6){//title animation
				StdDraw.picture(wh/2, hh/2, texture[0],wh,hh);//background
				if(step==0){
					ym=ym-hh*0.05;
					StdDraw.picture(xm,ym, tScreen[0],wh/2,wh/4);
					if(ym<=hh*0.5+hh/3){
						xm=wh*0.6;
						ym=hh*0.5+hh/3;
						step=1;
					}
				}else if(step==1){
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[0],wh/2,wh/4);
					xm=xm-wh*0.1;
					StdDraw.picture(wh/2-xm,ym, tScreen[2],wh/2,wh/4);
					StdDraw.picture(wh/2+xm,ym, tScreen[3],wh/2,wh/4);
					if(xm<=0){
						xm=wh/2-1;
						ym=hh*0.5+hh/3;
						step=3;
					}
				}else if(step==3){
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[0],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[1],wh/2-xm,wh/4-xm/2);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[2],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[3],wh/2,wh/4);
					xm=xm-wh*0.06;
					if(xm<=0){
						xm=wh*0.6;
						ym=hh*0.5+hh/3;
						step=4;
					}
					
				}else if(step==4){
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[0],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[1],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[2],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[3],wh/2,wh/4);
					xm=xm-wh*0.1;
					StdDraw.picture(wh/2+xm,hh*0.5+hh/3, tScreen[5],wh/2,wh/4);
					if(xm<=0){
						xm=wh*0.5;
						ym=hh*0.5+hh/3;
						step=5;
					}
				}else if(step==5){
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[0],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[1],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[2],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[3],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[4],wh/2,wh/4);
					StdDraw.picture(wh/2,hh*0.5+hh/3, tScreen[5],wh/2,wh/4);
					xm=xm-wh*0.1;
					if(xm<=0){
						xm=wh*0.5;
						ym=hh*0.5+hh/3;
						running=false;
					}
				}
			}
			StdDraw.show(0);
		}
		
		
	}

}
