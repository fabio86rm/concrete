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

public class Portafinestra2anteSerialize {
	
	public static void main(String[] args) {

//		String file = "./prodotti/Portafinestra2ante.ser";

		String file = "Portafinestra2ante.ser";
		final String dir = System.getProperty("user.dir");
		file = dir +"/src/prodottiDeserializzati/" + file;

		Prodotto prod = new Prodotto();
		prod.setNomeProdotto("Portafinestra 2 ante");
		
		prod.setCostoMontaggio(45.0);

		Table<Integer, Integer, Double> tabellaPrezzi = HashBasedTable.create();

		tabellaPrezzi.put(800, 2200, 444.0);
		tabellaPrezzi.put(800, 2300, 454.0);
		tabellaPrezzi.put(800, 2400, 463.0);

		tabellaPrezzi.put(900, 2200, 459.0);
		tabellaPrezzi.put(900, 2300, 469.0);
		tabellaPrezzi.put(900, 2400, 477.0);

		tabellaPrezzi.put(1000, 2200, 475.0);
		tabellaPrezzi.put(1000, 2300, 486.0);
		tabellaPrezzi.put(1000, 2400, 491.0);

		tabellaPrezzi.put(1100, 2200, 492.0);
		tabellaPrezzi.put(1100, 2300, 503.0);
		tabellaPrezzi.put(1100, 2400, 506.0);

		tabellaPrezzi.put(1200, 2200, 511.0);
		tabellaPrezzi.put(1200, 2300, 518.0);
		tabellaPrezzi.put(1200, 2400, 523.0);

		tabellaPrezzi.put(1300, 2200, 526.0);
		tabellaPrezzi.put(1300, 2300, 534.0);
		tabellaPrezzi.put(1300, 2400, 539.0);

		tabellaPrezzi.put(1400, 2200, 542.0);
		tabellaPrezzi.put(1400, 2300, 550.0);
		tabellaPrezzi.put(1400, 2400, 557.0);

		tabellaPrezzi = aggiungiScontoAttivita(tabellaPrezzi);
		
		String colore = "Bianco";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 2 ante.jpg");

		colore = "Avorio";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 2 ante.jpg");
		
		
		Table<Integer, Integer, Double> tabellaPrezzi2 = HashBasedTable.create();
		
		tabellaPrezzi2.put(800, 2200, 509.0);
		tabellaPrezzi2.put(800, 2300, 522.0);
		tabellaPrezzi2.put(800, 2400, 532.0);

		tabellaPrezzi2.put(900, 2200, 526.0);
		tabellaPrezzi2.put(900, 2300, 539.0);
		tabellaPrezzi2.put(900, 2400, 548.0);

		tabellaPrezzi2.put(1000, 2200, 544.0);
		tabellaPrezzi2.put(1000, 2300, 557.0);
		tabellaPrezzi2.put(1000, 2400, 565.0);

		tabellaPrezzi2.put(1100, 2200, 560.0);
		tabellaPrezzi2.put(1100, 2300, 573.0);
		tabellaPrezzi2.put(1100, 2400, 582.0);

		tabellaPrezzi2.put(1200, 2200, 577.0);
		tabellaPrezzi2.put(1200, 2300, 590.0);
		tabellaPrezzi2.put(1200, 2400, 599.0);

		tabellaPrezzi2.put(1300, 2200, 594.0);
		tabellaPrezzi2.put(1300, 2300, 608.0);
		tabellaPrezzi2.put(1300, 2400, 617.0);

		tabellaPrezzi2.put(1400, 2200, 612.0);
		tabellaPrezzi2.put(1400, 2300, 626.0);
		tabellaPrezzi2.put(1400, 2400, 636.0);

		tabellaPrezzi2 = aggiungiScontoAttivita(tabellaPrezzi2);
		
		colore = "Effetto legno Iroko";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 2 ante.jpg");

		colore = "Effetto legno Noce";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 2 ante.jpg");

		colore = "Effetto legno Rovere Sbiancato";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 2 ante.jpg");

		colore = "Altro Effetto legno";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"portafinestra 2 ante.jpg");

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
