package modele;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Collection;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/**
 * Gestion des joueurs
 *
 */
public class Joueur extends Objet implements Global {
	/**
	 *  vie de départ pour tous les joueurs
	 */
	private static final int MAXVIE = 10 ;
	/**
	 *  gain de points de vie lors d'une attaque
	 */
	private static final int GAIN = 1 ; 
	/**
	 *  perte de points de vie lors d'une attaque
	 */
	private static final int PERTE = 2 ; 
	/**
	 *  pseudo saisi
	 */
	private String pseudo ;
	/**
	 *  n° correspondant au personnage (avatar) pour le fichier correspondant
	 */
	private int numPerso ; 
	/**
	 *  instance de JeuServeur pour communiquer avec lui
	 */
	private JeuServeur jeuServeur ;
	/**
	 *  numéro d'étape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape ;
	/**
	 *  la boule du joueur
	 */
	private Boule boule ;
	/**
	 *  vie restante du joueur
	 */
	private int vie ; 
	/**
	 *  tourné vers la gauche (0) ou vers la droite (1)
	 */
	private int orientation ;
	/**
	 *  label du message affiché sous le perso
	 */
	private JLabel message;
	
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * Retourne la direction dans laquelle le joueur est tourné
	 * @return orientation du joueur (0 gauche, 1 droite)
	 */
	public int getOrientation() {
		return orientation;
	}
	
	/**
	 * Constructeur
	 * @param jeuServeur
	 */
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		etape = 1;
		orientation = 1;
		vie = MAXVIE;
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position, affichage, création de la boule)
	 * @param pseudo
	 * @param numPerso
	 * @param lesJoueurs
	 * @param lesMurs
	 */
	public void initPerso(String pseudo, int numPerso, Collection lesJoueurs, Collection lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		System.out.println("Joueur "+pseudo+" - num perso "+numPerso+" créé");
		jLabel = new JLabel();	// label du personnage, appartient à la classe Objet
		message = new JLabel(); // label du message
		message.setHorizontalAlignment(JLabel.CENTER);
		message.setFont(new Font("Dialog", Font.PLAIN, 8));
		this.premierePosition(lesJoueurs, lesMurs);
		jeuServeur.ajoutJLabelJeuArene(jLabel);
		jeuServeur.ajoutJLabelJeuArene(message);
		boule = new Boule(jeuServeur);
		jeuServeur.ajoutJLabelJeuArene(boule.getJLabel());
		affiche("marche", etape);
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur)
	 * @param lesJoueurs
	 * @param lesMurs
	 */
	private void premierePosition(Collection lesJoueurs, Collection lesMurs) {
		jLabel.setBounds(0, 0, 33, 43);
		do {
			posX = (int) Math.round(Math.random() * (LARGARN - 33+10)) ;
			posY = (int) Math.round(Math.random() * (HAUTARN - 43+10)) ;		
		} while (toucheCollectionObjets(lesMurs)!=null || toucheCollectionObjets(lesJoueurs)!=null);		
	}
	
	/**
	 * Affiche le personnage et son message
	 * @param etatPerso peut être "marche", "touche" ou "mort"
	 * @param etape désigne l'étape dans cet état
	 */
	public void affiche(String etatPerso, int etape) {
		String chemin = "personnages/perso" + numPerso + etatPerso + etape + "d" + orientation + ".gif";
		URL resource = getClass().getClassLoader().getResource(chemin);
		jLabel.setIcon(new ImageIcon(resource));		
		jLabel.setBounds(posX, posY, LARGPERS, HAUTPERS);
		message.setText(pseudo + " : " + vie);
		message.setBounds(posX-10, posY+HAUTPERS, LARGPERS+20, 10);
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tir de boule...)
	 * @param typeAction action correspondant à la flèche actuellement appuyée
	 * @param lesJoueurs collection des joueurs
	 * @param lesMurs collections des murs du jeu
	 */
	public void action(int typeAction, Collection lesJoueurs, Collection lesMurs) {
		if (!estMort()) {
			if (typeAction == KeyEvent.VK_SPACE) {
				if (!boule.getJLabel().isVisible()) {
					this.boule.tireBoule(this, lesMurs);
				}			
			}
			else {
				etape++;
				if (etape == 4) {
					etape = 1;
				}
				if (typeAction == KeyEvent.VK_LEFT) {
					orientation = 0;
				}
				else if (typeAction == KeyEvent.VK_RIGHT) {
					orientation = 1;
				}
				deplace(typeAction, lesJoueurs, lesMurs);		
				affiche("marche", etape);
			}			
		}		
	}

	/**
	 * Gère le déplacement du personnage
	 * @param direction direction de déplacement
	 */
	private void deplace(int direction, Collection lesJoueurs, Collection lesMurs) {
		switch(direction) {
		case (KeyEvent.VK_LEFT) :
			posX -= PAS;
			if(posX < 0 || toucheCollectionObjets(lesJoueurs)!=null || toucheCollectionObjets(lesMurs)!=null) {
				posX += PAS;
			}			
			break;
		case (KeyEvent.VK_UP) :
			posY -= PAS;
			if(posY < 0 || toucheCollectionObjets(lesJoueurs)!=null || toucheCollectionObjets(lesMurs)!=null) {
				posY += PAS;
			}
			break;
		case (KeyEvent.VK_RIGHT) :
			posX += PAS;
			if(posX > LARGARN-this.jLabel.getWidth() || toucheCollectionObjets(lesJoueurs)!=null || toucheCollectionObjets(lesMurs)!=null) {
				posX -= PAS;
			}		
			break;
		case (KeyEvent.VK_DOWN) :
			posY += PAS;
			if(posY > HAUTARN-this.jLabel.getHeight() || toucheCollectionObjets(lesJoueurs)!=null || toucheCollectionObjets(lesMurs)!=null) {
				posY -= PAS;
			}
			break;	
		}
	}
	
	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
		vie += GAIN;
		vie = Math.min(vie, MAXVIE);
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie() {
		vie -= PERTE;
		vie = Math.max(vie, 0);
	}
	
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		if (vie == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
	}
	
}
