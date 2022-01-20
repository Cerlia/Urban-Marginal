package controleur;

import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;
import outils.connexion.*;
import modele.Jeu;
import modele.JeuServeur;
import modele.JeuClient;

import javax.swing.JPanel;

import controleur.Global;


/**
 * Contr�leur et point d'entr�e de l'applicaton 
 * @author emds
 */
public class Controle implements AsyncResponse, Global {
	private EntreeJeu frmEntreeJeu ;
	private Arene frmArene;
	private ChoixJoueur frmChoix;
	private Jeu leJeu;

	/**
	 * M�thode de d�marrage
	 * @param args non utilis�
	 */
	public static void main(String[] args) {
		new Controle();
	}
	
	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this) ;
		this.frmEntreeJeu.setVisible(true);
	}
	
	/**
	 * D�clenche la cr�ation d'un serveur ou le rejoignement d'un serveur existant
	 */
	public void evenementEntreeJeu(String info) {	
		if (info.equals("serveur")) {
			new ServeurSocket(this, PORT);
			leJeu = new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene();
			((JeuServeur)leJeu).constructionMurs();
			this.frmArene.setVisible(true);
		}
		else {
			new ClientSocket(this, info, PORT);
		}
	}
	
	/**
	 * Ouverture de la zone de jeu apr�s s�lection du personnage et pseudo
	 * @param pseudo transmis par la frame ChoixJoueur
	 * @param numPerso transmis par la frame ChoixJoueur
	 */
	public void evenementChoixJoueur(String pseudo, int numPerso) {
		((JeuClient)leJeu).envoi("pseudo" + SEP + pseudo + SEP + numPerso);	
		this.frmChoix.dispose();
		this.frmArene.setVisible(true);
	}
	
	/**
	 * 
	 * @param connection
	 * @param objet
	 */
	public void envoi(Connection connection, Object objet) {
		connection.envoi(objet);
	}
	
	/**
	 * 
	 */
	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch(ordre) {
		case "connexion" :
			if(!(this.leJeu instanceof JeuServeur)) {
				this.leJeu = new JeuClient(this);
				this.leJeu.connexion(connection);
				this.frmEntreeJeu.dispose();
				this.frmArene = new Arene();
				this.frmChoix = new ChoixJoueur(this);
				this.frmChoix.setVisible(true);	
			}
			else {
				this.leJeu.connexion(connection);
			}
		break;
		case "r�ception" :
			leJeu.reception(connection, info);
		break;
		case "d�connexion" :
			
		break;
		}			
	}
	
	/**
	 * 
	 * @param ordre
	 * @param info
	 */
	public void evenementJeuServeur(String ordre, Object info) {
		switch (ordre) {
		case ("ajout mur") :
			frmArene.ajoutMurs(info);
			break;
		case ("ajout panel mur") :
			this.leJeu.envoi((Connection)info, this.frmArene.getJpnMurs());
			break;
		}
	}
	
	/**
	 * 
	 * @param ordre
	 * @param info
	 */
	public void evenementJeuClient(String ordre, Object info) {
		switch (ordre) {
		case ("ajout panel mur") :
			this.frmArene.setJpnMurs((JPanel)info);
			break;
		}
	}
}
