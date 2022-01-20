package modele;
import controleur.Global;

import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * Gestion des murs
 *
 */
public class Mur extends Objet implements Global {	
	/**
	 * Constructeur
	 */
	public Mur() {
		// détermination de la position d'un mur
		Random rand = new Random();
		posX = rand.nextInt(LARGARN - LARGMUR);
		posY = rand.nextInt(HAUTARN - HAUTMUR);
		// instanciation du label pour affichage de l'image du mur
		jLabel = new JLabel();
		String chemin = "murs/mur.gif";
		URL resource = getClass().getClassLoader().getResource(chemin);
		jLabel.setIcon(new ImageIcon(resource));		
		jLabel.setBounds(posX, posY, LARGMUR, HAUTMUR);		
	}	
}
