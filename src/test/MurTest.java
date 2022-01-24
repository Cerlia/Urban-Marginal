package test;

import modele.Mur;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MurTest {
	
	Mur mur1 = new Mur();
	Mur mur2 = new Mur();

	@Test
	void testToucheObjetGauche() {
		mur1.setPosX(40);
		mur1.setPosY(100);
		mur2.setPosX(30);
		mur2.setPosY(100);
		assertTrue(mur1.toucheObjet(mur2));
	}

	@Test
	void testToucheObjetDroite() {
		mur1.setPosX(40);
		mur1.setPosY(100);
		mur2.setPosX(47);
		mur2.setPosY(100);
		assertTrue(mur1.toucheObjet(mur2));
	}
	
	@Test
	void testToucheObjetHaut() {
		mur1.setPosX(40);
		mur1.setPosY(90);
		mur2.setPosX(40);
		mur2.setPosY(80);
		assertTrue(mur1.toucheObjet(mur2));
	}
	
	@Test
	void testToucheObjetBas() {
		mur1.setPosX(40);
		mur1.setPosY(90);
		mur2.setPosX(40);
		mur2.setPosY(100);
		assertTrue(mur1.toucheObjet(mur2));
	}
}
