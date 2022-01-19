package controleur;

import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;
import outils.connexion.*;
import modele.Jeu;
import modele.JeuServeur;
import modele.JeuClient;
import controleur.Global;


/**
 * Contrôleur et point d'entrée de l'applicaton 
 * @author emds
 */
public class Controle implements AsyncResponse, Global {
	private EntreeJeu frmEntreeJeu ;
	private Arene frmArene;
	private ChoixJoueur frmChoix;
	private Jeu leJeu;

	/**
	 * Méthode de démarrage
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		new Controle();
	}
	
	/**
	 * Déclenche la création d'un serveur ou le rejoignement d'un serveur existant
	 */
	public void evenementEntreeJeu(String info) {	
		if (info.equals("serveur")) {
			new ServeurSocket(this, PORT);
			leJeu = new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene();
			this.frmArene.setVisible(true);
		}
		else {
			new ClientSocket(this, info, PORT);
		}
	}
	
	/**
	 * Ouverture de la zone de jeu après sélection du personnage et pseudo
	 * @param pseudo transmis par la frame ChoixJoueur
	 * @param numPerso transmis par la frame ChoixJoueur
	 */
	public void evenementChoixJoueur(String pseudo, int numPerso) {
		((JeuClient)leJeu).envoi("pseudo" + SEP + pseudo + SEP + numPerso);	
		this.frmChoix.dispose();
		this.frmArene.setVisible(true);
	}
	
	public void envoi(Connection connection, Object objet) {
		connection.envoi(objet);
	}
	
	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this) ;
		this.frmEntreeJeu.setVisible(true);
	}

	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch(ordre) {
		case "connexion" :
			if(!(this.leJeu instanceof JeuServeur)) {
				leJeu = new JeuClient(this);
				leJeu.connexion(connection);
				this.frmEntreeJeu.dispose();
				this.frmArene = new Arene();
				this.frmChoix = new ChoixJoueur(this);
				this.frmChoix.setVisible(true);	
			}
			else {
				leJeu.connexion(connection);
			}
		break;
		case "réception" :
			leJeu.reception(connection, info);
		break;
		case "déconnexion" :
			
		break;
		}			
	}
}
