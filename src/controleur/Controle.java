package controleur;

import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;
import outils.connexion.*;
import modele.Jeu;
import modele.JeuServeur;
import modele.JeuClient;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this) ;
		this.frmEntreeJeu.setVisible(true);
	}
	
	/**
	 * Déclenche la création d'un serveur ou le rejoignement d'un serveur existant
	 */
	public void evenementEntreeJeu(String info) {	
		if (info.equals("serveur")) {
			new ServeurSocket(this, PORT);
			this.leJeu = new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene(this, "serveur");
			((JeuServeur)this.leJeu).constructionMurs();
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
		this.frmChoix.dispose();
		this.frmArene.setVisible(true);
		((JeuClient)this.leJeu).envoi("pseudo" + SEP + pseudo + SEP + numPerso);	
	}
	
	/**
	 * Reçoit des informations de leJeu de type JeuServeur
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
		case ("ajout jlabel jeu") :
			this.frmArene.ajoutJLabelJeu((JLabel)info);
			break;
		case ("modif panel jeu") :
			this.leJeu.envoi((Connection)info, this.frmArene.getJpnJeu());
			break;
		case ("ajout phrase") :
			this.frmArene.ajoutTchat((String)info);
			((JeuServeur)leJeu).envoi(frmArene.getTxtChat());
			break;
		}
	}
	
	/**
	 * Reçoit des informations de leJeu de type JeuClient
	 * @param ordre
	 * @param info
	 */
	public void evenementJeuClient(String ordre, Object info) {
		switch (ordre) {
		case ("ajout panel mur") :
			this.frmArene.setJpnMurs((JPanel)info);
			break;
		case ("modif panel jeu") :
			this.frmArene.setJpnJeu((JPanel)info);
			break;
		case ("modif tchat") :
			this.frmArene.setTxtChat((String)info);
			break;
		}
	}
	
	/**
	 * Reçoit des informations de frmArene
	 */	
	public void evenementArene(String message) {
		((JeuClient)leJeu).envoi("tchat" + SEP + message);
	}
	
	
	/**
	 * 
	 * @param connection
	 * @param objet
	 */
	public void envoi(Connection connection, Object info) {
		connection.envoi(info);
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
				this.frmArene = new Arene(this, "client");
				this.frmChoix = new ChoixJoueur(this);
				this.frmChoix.setVisible(true);	
			}
			else {
				this.leJeu.connexion(connection);
			}
			break;
		case "réception" :
			this.leJeu.reception(connection, info);
			break;
		case "déconnexion" :			
			break;
		}			
	}
}
