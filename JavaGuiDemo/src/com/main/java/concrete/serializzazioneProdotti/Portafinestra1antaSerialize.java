package com.main.java.concrete.serializzazioneProdotti;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.main.java.concrete.bean.Prodotto;
import com.main.java.concrete.utility.Constants;
import com.main.java.concrete.utility.Util;

public class Portafinestra1antaSerialize {
	
	public static void main(String[] args) {

//		String file = "./prodotti/Portafinestra1anta.ser";

		String file = "Portafinestra1anta.ser";
		final String dir = System.getProperty("user.dir");
		file = dir +"/src/prodottiDeserializzati/" + file;

		Prodotto prod = new Prodotto();
		prod.setNomeProdotto("Portafinestra 1 anta");
		   
		prod.setCostoMontaggio(45.0);

		Table<Integer, Integer, Double> tabellaPrezzi = HashBasedTable.create();

		tabellaPrezzi.put(700, 2200, 365.0);
		tabellaPrezzi.put(700, 2300, 372.0);
		tabellaPrezzi.put(700, 2400, 383.0);

		tabellaPrezzi.put(800, 2200, 381.0);
		tabellaPrezzi.put(800, 2300, 388.0);
		tabellaPrezzi.put(800, 2400, 398.0);

		tabellaPrezzi.put(900, 2200, 398.0);
		tabellaPrezzi.put(900, 2300, 405.0);
		tabellaPrezzi.put(900, 2400, 414.0);

		tabellaPrezzi.put(1000, 2200, 413.0);
		tabellaPrezzi.put(1000, 2300, 421.0);
		tabellaPrezzi.put(1000, 2400, 431.0);

		tabellaPrezzi.put(1100, 2200, 429.0);
		tabellaPrezzi.put(1100, 2300, 438.0);
		tabellaPrezzi.put(1100, 2400, 448.0);

		tabellaPrezzi = aggiungiScontoAttivita(tabellaPrezzi);
		
		String colore = "Bianco";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi);   
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 1 anta.jpg");

		colore = "Avorio";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 1 anta.jpg");
		   
		Table<Integer, Integer, Double> tabellaPrezzi2 = HashBasedTable.create();
		
		tabellaPrezzi2.put(700, 2200, 407.0);
		tabellaPrezzi2.put(700, 2300, 415.0);
		tabellaPrezzi2.put(700, 2400, 423.0);

		tabellaPrezzi2.put(800, 2200, 425.0);
		tabellaPrezzi2.put(800, 2300, 433.0);
		tabellaPrezzi2.put(800, 2400, 440.0);

		tabellaPrezzi2.put(900, 2200, 442.0);
		tabellaPrezzi2.put(900, 2300, 451.0);
		tabellaPrezzi2.put(900, 2400, 458.0);

		tabellaPrezzi2.put(1000, 2200, 459.0);
		tabellaPrezzi2.put(1000, 2300, 469.0);
		tabellaPrezzi2.put(1000, 2400, 476.0);

		tabellaPrezzi2.put(1100, 2200, 477.0);
		tabellaPrezzi2.put(1100, 2300, 488.0);
		tabellaPrezzi2.put(1100, 2400, 495.0);

		tabellaPrezzi2 = aggiungiScontoAttivita(tabellaPrezzi2);
		
		colore = "Effetto Legno Iroko";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 1 anta.jpg");

		colore = "Effetto Legno Noce";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 1 anta.jpg");

		colore = "Effetto Legno Rovere Sbiancato";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 1 anta.jpg");

		colore = "Altro Effetto Legno";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 1 anta.jpg");

		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(prod);
			out.close();
			fileOut.close();
			System.out.printf("Dati serializzati salvati in " + file);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public static Table aggiungiScontoAttivita(Table tabellaPrezzi) {
	   Map<Integer, Map<Integer, Double>> mappaPrezzi = tabellaPrezzi.rowMap();

	   for (Integer row : mappaPrezzi.keySet()) {
		   Map<Integer, Double> tmp = mappaPrezzi.get(row);
		   for (Map.Entry<Integer, Double> pair : tmp.entrySet()) {
			   System.out.println(row+" "+pair.getKey()+" "+pair.getValue());
			   double nuovoPrezzo = Util.round(pair.getValue() * ((100 - Constants.SCONTO_CONCRETE_A_ATTIVITA) / 100), 2);
		       pair.setValue(nuovoPrezzo);
		       System.out.println(row+" "+pair.getKey()+" "+pair.getValue());
		   }
	   }
	   
	   return tabellaPrezzi;
	}

}
