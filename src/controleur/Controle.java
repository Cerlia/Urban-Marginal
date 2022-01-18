package controleur;

import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;
import outils.connexion.*;


/**
 * Contrôleur et point d'entrée de l'applicaton 
 * @author emds
 *
 */
public class Controle implements AsyncResponse {

	private EntreeJeu frmEntreeJeu ;
	private Arene frmArene;
	private ChoixJoueur frmChoix;
	private String typeJeu;
	private final int PORT = 6666;

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
			this.typeJeu = "serveur";
			new ServeurSocket(this, PORT);			
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene();
			this.frmArene.setVisible(true);
		}
		else {
			this.typeJeu = "client";
			new ClientSocket(this, info, PORT);
		}
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
			if(this.typeJeu.equals("client")) {
				this.frmEntreeJeu.dispose();
				this.frmArene = new Arene();
				this.frmChoix = new ChoixJoueur();
				this.frmChoix.setVisible(true);	
			}
		break;
		case "réception" :
			
		break;
		case "déconnexion" :
			
		break;
		}			
	}
}
