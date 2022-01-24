package modele;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;
import modele.Joueur;

/**
 * Gestion de la boule
 *
 */
public class Boule extends Objet implements Global, Runnable {

	/**
	 * instance de JeuServeur pour la communication
	 */
	private JeuServeur jeuServeur ;
	
	/**
	 * Collection de murs
	 */
	private Collection lesMurs;
	
	/**
	 * joueur qui lance la boule
	 */
	private Joueur attaquant ;
	
	/**
	 * Constructeur
	 * @param jeuServeur instance de JeuServeur à qui transmettre les infos
	 */
	public Boule(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		super.jLabel = new JLabel();
		String chemin = "boules/boule.gif";
		URL resource = getClass().getClassLoader().getResource(chemin);
		jLabel.setIcon(new ImageIcon(resource));
		jLabel.setVisible(false);
		jLabel.setBounds(0, 0, TAILLEBL, TAILLEBL);
	}
	
	/**
	 * Tire d'une boule
	 * @param leJoueur joueur à qui appartient cette boule
	 * @param lesMurs liste des murs du jeu
	 */
	public void tireBoule(Joueur attaquant, Collection<Mur> lesMurs) {
		this.lesMurs = lesMurs;
		this.attaquant = attaquant;
		if(attaquant.getOrientation() == 0) {
			posX = attaquant.getPosX()-TAILLEBL-1;
		}
		else {
			posX = attaquant.getPosX()+attaquant.jLabel.getWidth()+1;
		}
		posY = attaquant.getPosY()+(HAUTPERS-TAILLEBL)/2;
		new Thread(this).start();
	}
	
	public void run() {
		attaquant.affiche("marche", 1);
		this.jLabel.setVisible(true);
		jeuServeur.envoi(0);
		Joueur victime = null;
		int lePas;
		if(attaquant.getOrientation() == 0) {
			lePas = -PAS;
		}
		else {
			lePas = PAS;
		}
		do {
			posX += lePas;
			jLabel.setBounds(posX, posY, TAILLEBL, TAILLEBL);
			jeuServeur.envoiJeuATous();
			Collection lesJoueurs = jeuServeur.getLesJoueurs();
			victime = (Joueur)toucheCollectionObjets(lesJoueurs);
			pause(50, 0);
		} while ((posX>0 && posX<LARGARN-TAILLEBL) && victime==null && (toucheCollectionObjets(lesMurs) == null));
		// test s'il y a une victime
		if (victime!=null && victime.estMort()==false) {
			jeuServeur.envoi(1);
			victime.perteVie();
			attaquant.gainVie();
			for (int k = 1; k < 3; k++) {
				victime.affiche("touche", k);
				pause(80, 0);
			}
			if (victime.estMort()) {
				for (int k = 1; k < 3; k++) {
					victime.affiche("mort", k);
					pause(80, 0);
					jeuServeur.envoi(2);
				}
			} else {
				victime.affiche("marche", 1);
			}
		}
		// dans tous les cas, la boule devient invisible et le jeu est envoyé à tous
		jLabel.setVisible(false);
		jeuServeur.envoiJeuATous();
	}
	
	/**
	 * introduit une pause dans un thread
	 * @param mili millisecondes
	 * @param nano nanosecondes
	 */
	public void pause(long mili, int nano) {
		try {
			Thread.sleep(mili, nano);
		} catch (Exception e) {
			System.out.println("Erreur");
		}
	}
}
