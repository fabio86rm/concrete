package com.main.java.concrete.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.common.collect.Table;

public class Servizio implements Serializable{
	
	private static final long serialVersionUID = -385853958437424707L;
	
	private String nomeServizio;
	private Vector descrizioniServizio;
	private Map<String, Double> tabellaPrezzi; // chiave: descrizioneServizione (singolo elemento) - valore: prezzo

	public Servizio() {
		this.descrizioniServizio = new Vector();
		this.tabellaPrezzi = new HashMap<String, Double>();
	}
	
	public String getNomeServizio() {
		return nomeServizio;
	}
	public void setNomeServizio(String nomeServizio) {
		this.nomeServizio = nomeServizio;
	}
	
	public Vector getDescrizioniServizio() {
		return descrizioniServizio;
	}
	public void setDescrizioneServizio(Vector descrizioniServizio) {
		this.descrizioniServizio = descrizioniServizio;
	}
	
	public Map<String, Double> getTabellaPrezzi() {
		return tabellaPrezzi;
	}
	public void setTabellaPrezzi(Map<String, Double> tabellaPrezzi) {
		this.tabellaPrezzi = tabellaPrezzi;
	}
	
	public void aggiungiDescrizioneServizio(String descrizione) {
		descrizioniServizio.add(descrizione);
	}
	public void rimuoviDescrizioneServizio(String descrizione) {
		descrizioniServizio.remove(descrizione);
	}
	
	public void aggiungiPrezzoServizio(String descrizioneServizio, double prezzo) {
		tabellaPrezzi.put(descrizioneServizio, prezzo);
	}
	public void rimuoviPrezzoServizio(String descrizioneServizio) {
		tabellaPrezzi.remove(descrizioneServizio);
	}
	
}
