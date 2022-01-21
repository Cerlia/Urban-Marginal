package modele;

import java.awt.Font;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/**
 * Gestion des joueurs
 *
 */
public class Joueur extends Objet implements Global{
	// vie de départ pour tous les joueurs
	private static final int MAXVIE = 10 ;
	// gain de points de vie lors d'une attaque
	private static final int GAIN = 1 ; 
	// perte de points de vie lors d'une attaque
	private static final int PERTE = 2 ; 
	// pseudo saisi
	private String pseudo ;
	// n° correspondant au personnage (avatar) pour le fichier correspondant
	private int numPerso ; 
	// instance de JeuServeur pour communiquer avec lui
	private JeuServeur jeuServeur ;
	// numéro d'étape dans l'animation (de la marche, touché ou mort)
	private int etape ;
	// la boule du joueur
	private Boule boule ;
	// vie restante du joueur
	private int vie ; 
	// tourné vers la gauche (0) ou vers la droite (1)
	private int orientation ;
	// label du message affiché sous le perso
	private JLabel message;
	
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * Constructeur
	 */
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		etape = 1;
		orientation = 1;
		vie = MAXVIE;
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position, affichage, création de la boule)
	 */
	public void initPerso(String pseudo, int numPerso, Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
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
		affiche("marche", etape);
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur)
	 */
	private void premierePosition(Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		jLabel.setBounds(0, 0, 33, 43);
		do {
			posX = (int) Math.round(Math.random() * (LARGARN - 33+10)) ;
			posY = (int) Math.round(Math.random() * (HAUTARN - 43+10)) ;		
		} while (this.toucheMur(lesMurs) == true || this.toucheJoueur(lesJoueurs) == true);		
	}
	
	/**
	 * Affiche le personnage et son message
	 * @param etatPerso peut être "marche", "touche" ou "mort"
	 * @param etape désigne l'étape dans cet état
	 */
	public void affiche(String etatPerso, int etape) {
		String chemin = "personnages/perso" + numPerso + "marche" + etape + "d" + orientation + ".gif";
		URL resource = getClass().getClassLoader().getResource(chemin);
		jLabel.setIcon(new ImageIcon(resource));		
		jLabel.setBounds(posX, posY, jLabel.getWidth(), jLabel.getHeight());
		message.setText(pseudo + " : " + vie);
		message.setBounds(posX-10, posY+jLabel.getHeight(), jLabel.getWidth()+20, 10);
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 */
	public void action() {
	}

	/**
	 * Gère le déplacement du personnage
	 */
	private void deplace() { 
	}

	/**
	 * Contrôle si le joueur touche un des autres joueurs
	 * @return true si deux joueurs se touchent
	 */
	private Boolean toucheJoueur(Collection<Joueur> lesJoueurs) {
		for(Joueur unJoueur : lesJoueurs) {
			if(!this.equals(unJoueur)) {
				if(super.toucheObjet(unJoueur)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	* Contrôle si le joueur touche un des murs
	 * @return true si un joueur touche un mur
	 */
	private Boolean toucheMur(ArrayList<Mur> lesMurs) {
		for (Mur unMur : lesMurs) {
			if (this.toucheObjet(unMur)) {
				return true;
			}
		}
		return false;			
	}
	
	
	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie() {
	}
	
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return null;
	}
	
	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
	}
	
}
