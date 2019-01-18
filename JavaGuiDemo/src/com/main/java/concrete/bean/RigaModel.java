package com.main.java.concrete.bean;

import java.net.URL;

public class RigaModel {
	
	private String nomeProdotto;
	private double larghezza;
	private double altezza;
	private String colore;
	private double altezzaManiglia;
	private int quantita;
	private String coloreManiglia;
	private String sensoApertura;
	private double sconto;
	private double prezzoUnitario;
	private double subTotale;
	
	private URL pathImmagine;
	
	public String getNomeProdotto() {
		return nomeProdotto;
	}
	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}
	public double getLarghezza() {
		return larghezza;
	}
	public void setLarghezza(double larghezza) {
		this.larghezza = larghezza;
	}
	public double getAltezza() {
		return altezza;
	}
	public void setAltezza(double altezza) {
		this.altezza = altezza;
	}
	public String getColore() {
		return colore;
	}
	public void setColore(String colore) {
		this.colore = colore;
	}
	public double getAltezzaManiglia() {
		return altezzaManiglia;
	}
	public void setAltezzaManiglia(double altezzaManiglia) {
		this.altezzaManiglia = altezzaManiglia;
	}
	public String getColoreManiglia() {
		return coloreManiglia;
	}
	public void setColoreManiglia(String coloreManiglia) {
		this.coloreManiglia = coloreManiglia;
	}
	public String getSensoApertura() {
		return sensoApertura;
	}
	public void setSensoApertura(String sensoApertura) {
		this.sensoApertura = sensoApertura;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	public double getSconto() {
		return sconto;
	}
	public void setSconto(double sconto) {
		this.sconto = sconto;
	}
	public double getPrezzoUnitario() {
		return prezzoUnitario;
	}
	public void setPrezzoUnitario(double prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}
	public double getSubTotale() {
		return subTotale;
	}
	public void setSubTotale(double subTotale) {
		this.subTotale = subTotale;
	}
	public URL getPathImmagine() {
		return pathImmagine;
	}
	public void setPathImmagine(URL pathImmagine) {
		this.pathImmagine = pathImmagine;
	}

}
