package com.main.java.concrete.bean;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.common.collect.Table;
import com.main.java.concrete.utility.Util;

public class Prodotto implements Serializable{
	
	private static final long serialVersionUID = 2526639282875550814L;
	
	private String nomeProdotto;
	private Vector<String> colori;
	private Map<String,Table> tabellePrezzi; // chiave: colore - valore: tabella
	private int numDimensioni; // le finestre e le portefinestre hanno 2 dimensioni (lxh) ma ad esempio il celletto ne ha solamente 1
	private Map<String, URL> pathImmagine;
	private double costoMontaggio;

	public Prodotto() {
		this.colori = new Vector();
		this.tabellePrezzi = new HashMap<String, Table>();
		this.numDimensioni = 2; // di default hanno 2 dimensioni
		this.pathImmagine = new HashMap<String, URL>();
	}

	public String getNomeProdotto() {
		return nomeProdotto;
	}
	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}
	
	public Vector getColori() {
		return colori;
	}
	public void setColori(Vector colori) {
		this.colori = colori;
	}
	
	public int getNumDimensioni() {
		return numDimensioni;
	}
	public void setNumDimensioni(int numDimensioni) {
		this.numDimensioni = numDimensioni;
	}

	public Map getPathImmagine() {
		return pathImmagine;
	}
	public void setPathImmagine(Map<String, URL> pathImmagine) {
		this.pathImmagine = pathImmagine;
	}

	public double getCostoMontaggio() {
		return costoMontaggio;
	}
	public void setCostoMontaggio(double costoMontaggio) {
		this.costoMontaggio = costoMontaggio;
	}
	
	public void aggiungiImmagine(String colore, String pathImmagine) {
		URL url = Prodotto.class.getResource(pathImmagine);
		this.pathImmagine.put(colore, url);
	}
	public void rimuoviImmagine(String colore) {
		this.pathImmagine.remove(colore);
	}
	
	public void aggiungiColore(String colore) {
		this.colori.add(colore);
	}
	public void rimuoviColore(String colore) {
		this.colori.remove(colore);
	}
	
	public Map getTabellePrezzi() {
		return tabellePrezzi;
	}
	public void setTabellePrezzi(Map tabellePrezzi) {
		this.tabellePrezzi = tabellePrezzi;
	}
	public void aggiungiTabellaPrezzi(String colore, Table tabellaPrezzi) {
		this.tabellePrezzi.put(colore, tabellaPrezzi);
	}
	public void rimuoviTabellaPrezzi(String colore) {
		this.tabellePrezzi.remove(colore);
	}
	
	public double calcolaPrezzo(String colore, double l, double a, double utileProdottiAttivita) {
		int larghezza = (int) l;
		int altezza = (int) a;
		
		if (larghezza % 100 != 0) {
			larghezza = 100 * ((larghezza / 100) + 1);
		}
		if (altezza % 100 != 0) {
			altezza = 100 * ((altezza / 100) + 1);
		}
		
		Table prezzi = (Table) getTabellePrezzi().get(colore);
		
		double prezzo = (double) prezzi.get(larghezza, altezza);
		
		prezzo = prezzo * (100+utileProdottiAttivita)/100;
		prezzo = Util.round(prezzo, 2);
		
		return prezzo;
	}

}
