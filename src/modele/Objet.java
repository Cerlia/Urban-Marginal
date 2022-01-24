package modele;

import java.util.Collection;

import javax.swing.JLabel;

/**
 * Informations communes à tous les objets (joueurs, murs, boules)
 * permet de mémoriser la position de l'objet et de gérer les  collisions
 *
 */
public abstract class Objet {
	// position X de l'objet
	protected Integer posX ;
	// position Y de l'objet
	protected Integer posY ;
	// label d'affichage de l'objet
	protected JLabel jLabel;
	
	/**
	 * getter sur jLabel
	 * @return
	 */
	public JLabel getJLabel() {
		return jLabel;
	}
	
	/**
	 * getter sur posX
	 * @return posX de l'objet
	 */
	public Integer getPosX() {
		return posX;
	}
	
	/**
	 * getter sur posY
	 * @return posY de l'objet
	 */
	public Integer getPosY() {
		return posY;
	}
	
	/**
	 * contrôle si l'objet actuel touche l'objet passé en paramètre
	 * @param objet contient l'objet à contrôler
	 * @return true si les 2 objets se touchent
	 */
	public Boolean toucheObjet(Objet objet) {
		if (this.jLabel==null || objet.jLabel==null) {
			return false ;
		} else {
			return(this.posX + this.jLabel.getWidth() > objet.posX &&
				this.posX < objet.posX + objet.jLabel.getWidth() && 
				this.posY + this.jLabel.getHeight() > objet.posY &&
				this.posY < objet.posY + objet.jLabel.getHeight()) ;
		}		
	}	
	
	/**
	 * contrôle si l'objet actuel touche un objet parmi la collection
	 * @param laCollection collection d'objets à vérifier
	 * @return true s'il existe une collision
	 */
	public Objet toucheCollectionObjets(Collection<Objet> lesObjets) {
		for(Objet unObjet : lesObjets) {
			if(!this.equals(unObjet)) {
				if(toucheObjet(unObjet)) {
					return unObjet;
				}
			}
		}
		return null;
	}
}