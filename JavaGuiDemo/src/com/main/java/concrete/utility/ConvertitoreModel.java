package com.main.java.concrete.utility;

import java.net.URL;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.main.java.concrete.bean.Prodotto;
import com.main.java.concrete.bean.RigaModel;

public class ConvertitoreModel {
	
	private DefaultTableModel model;
	private Vector<Prodotto> listaProdotti;
	
	private double utileProdottiAttivita = 0.0;
	private double utileServiziAttivita = 0.0;
	
	public ConvertitoreModel(DefaultTableModel model, Vector<Prodotto> listaProdotti) {
		this.model = model;
		this.listaProdotti = listaProdotti;
	}
	
	public ConvertitoreModel(DefaultTableModel model, Vector<Prodotto> listaProdotti, double utileProdottiAttivita, double utileServiziAttivita) {
		this.model = model;
		this.listaProdotti = listaProdotti;
		this.utileProdottiAttivita = utileProdottiAttivita;
		this.utileServiziAttivita = utileServiziAttivita;
	}
	
	public Vector<RigaModel> fromModeltoVectorCopiaCliente() {
		/*
		 * tabella composta in questo modo: Prodotto, Larghezza, Altezza, Colore,
		 * Altezza Maniglia, Quantità, Sconto, Prezzo Unitario, Prezzo SubTotale
		 */
		
		Vector<RigaModel> vettoreRiga = new Vector<RigaModel>();
		
		for(int i=0; i<model.getRowCount(); i++) {
			RigaModel riga = new RigaModel();
			riga.setNomeProdotto(model.getValueAt(i, 0).toString());
			riga.setLarghezza(Double.parseDouble(model.getValueAt(i, 1).toString()));
			riga.setAltezza(Double.parseDouble(model.getValueAt(i, 2).toString()));
			riga.setColore(model.getValueAt(i, 3).toString());
			riga.setAltezzaManiglia(Double.parseDouble(model.getValueAt(i, 4).toString()));
			riga.setColoreManiglia(model.getValueAt(i, 5).toString());
			riga.setSensoApertura(model.getValueAt(i, 6).toString());
			riga.setQuantita(Integer.parseInt(model.getValueAt(i, 7).toString()));
			riga.setSconto(Double.parseDouble(model.getValueAt(i, 8).toString()));
			
			double prezzoUnitario = Double.parseDouble(model.getValueAt(i, 9).toString());
//			prezzoUnitario = prezzoUnitario / ((100 + utileProdottiAttivita)/100);
//			prezzoUnitario = Util.round(prezzoUnitario, 2);
			riga.setPrezzoUnitario(prezzoUnitario);
			double prezzoSubTotale = Double.parseDouble(model.getValueAt(i, 10).toString());
//			prezzoSubTotale = prezzoSubTotale / ((100 + utileProdottiAttivita)/100);
//			prezzoSubTotale = Util.round(prezzoSubTotale, 2);
			riga.setSubTotale(prezzoSubTotale);
			
			for(int j=0; j<listaProdotti.size(); j++) {
				if(listaProdotti.get(j).getNomeProdotto().equals(riga.getNomeProdotto())) {
					Map<String, URL> pathImmagine = listaProdotti.get(j).getPathImmagine();
					for(Map.Entry<String, URL> entry : pathImmagine.entrySet()) {
						if(entry.getKey().equals(riga.getColore())) {
							riga.setPathImmagine(entry.getValue());
							break;
						}
					}
					break;
				}
			}
			
			vettoreRiga.add(riga);
		}
		
		return vettoreRiga;
		
	}
	
	public Vector<RigaModel> fromModeltoVectorCopiaAttivita() {
		/*
		 * tabella composta in questo modo: Prodotto, Larghezza, Altezza, Colore,
		 * Altezza Maniglia, Quantità, Sconto, Prezzo Unitario, Prezzo SubTotale
		 */
		
		Vector<RigaModel> vettoreRiga = new Vector<RigaModel>();
		
		for(int i=0; i<model.getRowCount(); i++) {
			RigaModel riga = new RigaModel();
			riga.setNomeProdotto(model.getValueAt(i, 0).toString());
			riga.setLarghezza(Double.parseDouble(model.getValueAt(i, 1).toString()));
			riga.setAltezza(Double.parseDouble(model.getValueAt(i, 2).toString()));
			riga.setColore(model.getValueAt(i, 3).toString());
			riga.setAltezzaManiglia(Double.parseDouble(model.getValueAt(i, 4).toString()));
			riga.setColoreManiglia(model.getValueAt(i, 5).toString());
			riga.setSensoApertura(model.getValueAt(i, 6).toString());
			riga.setQuantita(Integer.parseInt(model.getValueAt(i, 7).toString()));
			riga.setSconto(0.0);	// sulla copia dell'attivita non si applica lo sconto
//			riga.setSconto(Double.parseDouble(model.getValueAt(i, 8).toString()));
			
//			double prezzoUnitario = Double.parseDouble(model.getValueAt(i, 9).toString());
//			prezzoUnitario = prezzoUnitario / ((100 + utileProdottiAttivita)/100);
//			prezzoUnitario = Util.round(prezzoUnitario, 2);
//			riga.setPrezzoUnitario(prezzoUnitario);
//			double prezzoSubTotale = Double.parseDouble(model.getValueAt(i, 10).toString());
//			prezzoSubTotale = prezzoSubTotale / ((100 + utileProdottiAttivita)/100);
//			prezzoSubTotale = Util.round(prezzoSubTotale, 2);
//			riga.setSubTotale(prezzoSubTotale);
			
			for(int j=0; j<listaProdotti.size(); j++) {
				if(listaProdotti.get(j).getNomeProdotto().equals(riga.getNomeProdotto())) {
					Map<String, URL> pathImmagine = listaProdotti.get(j).getPathImmagine();
					for(Map.Entry<String, URL> entry : pathImmagine.entrySet()) {
						if(entry.getKey().equals(riga.getColore())) {
							double prezzoUnitario = listaProdotti.get(j).calcolaPrezzo(entry.getKey(), riga.getLarghezza(), riga.getAltezza(), 0);
							riga.setPrezzoUnitario(prezzoUnitario);
							double prezzoSubTotale = prezzoUnitario * riga.getQuantita();
							prezzoSubTotale = Util.round(prezzoSubTotale, 2);
							riga.setSubTotale(prezzoSubTotale);
							riga.setPathImmagine(entry.getValue());
							break;
						}
					}
					break;
				}
			}
			
			vettoreRiga.add(riga);
		}
		
		return vettoreRiga;
		
	}

}
