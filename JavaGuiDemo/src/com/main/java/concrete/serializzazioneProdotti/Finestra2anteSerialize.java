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

public class Finestra2anteSerialize {
	
	public static void main(String[] args) {

//		String file = "./prodotti/Finestra2ante.ser";

		String file = "Finestra2ante.ser";
		final String dir = System.getProperty("user.dir");
		file = dir +"/src/prodottiDeserializzati/" + file;

		Prodotto prod = new Prodotto();
		prod.setNomeProdotto("Finestra 2 ante");
		   
		prod.setCostoMontaggio(35.0);

		Table<Integer, Integer, Double> tabellaPrezzi = HashBasedTable.create();

		tabellaPrezzi.put(1000, 700, 253.0);
		tabellaPrezzi.put(1000, 800, 263.0);
		tabellaPrezzi.put(1000, 900, 271.0);
		tabellaPrezzi.put(1000, 1000, 279.0);
		tabellaPrezzi.put(1000, 1100, 288.0);
		tabellaPrezzi.put(1000, 1200, 296.0);
		tabellaPrezzi.put(1000, 1300, 305.0);
		tabellaPrezzi.put(1000, 1400, 314.0);

		tabellaPrezzi.put(1100, 700, 261.0);
		tabellaPrezzi.put(1100, 800, 271.0);
		tabellaPrezzi.put(1100, 900, 279.0);
		tabellaPrezzi.put(1100, 1000, 288.0);
		tabellaPrezzi.put(1100, 1100, 296.0);
		tabellaPrezzi.put(1100, 1200, 305.0);
		tabellaPrezzi.put(1100, 1300, 314.0);
		tabellaPrezzi.put(1100, 1400, 324.0);

		tabellaPrezzi.put(1200, 700, 268.0);
		tabellaPrezzi.put(1200, 800, 279.0);
		tabellaPrezzi.put(1200, 900, 288.0);
		tabellaPrezzi.put(1200, 1000, 296.0);
		tabellaPrezzi.put(1200, 1100, 305.0);
		tabellaPrezzi.put(1200, 1200, 314.0);
		tabellaPrezzi.put(1200, 1300, 324.0);
		tabellaPrezzi.put(1200, 1400, 333.0);

		tabellaPrezzi.put(1300, 700, 276.0);
		tabellaPrezzi.put(1300, 800, 288.0);
		tabellaPrezzi.put(1300, 900, 296.0);
		tabellaPrezzi.put(1300, 1000, 305.0);
		tabellaPrezzi.put(1300, 1100, 314.0);
		tabellaPrezzi.put(1300, 1200, 324.0);
		tabellaPrezzi.put(1300, 1300, 333.0);
		tabellaPrezzi.put(1300, 1400, 343.0);

		tabellaPrezzi.put(1400, 700, 285.0);
		tabellaPrezzi.put(1400, 800, 296.0);
		tabellaPrezzi.put(1400, 900, 308.0);
		tabellaPrezzi.put(1400, 1000, 320.0);
		tabellaPrezzi.put(1400, 1100, 333.0);
		tabellaPrezzi.put(1400, 1200, 346.0);
		tabellaPrezzi.put(1400, 1300, 360.0);
		tabellaPrezzi.put(1400, 1400, 375.0);

		tabellaPrezzi.put(1500, 700, 293.0);
		tabellaPrezzi.put(1500, 800, 305.0);
		tabellaPrezzi.put(1500, 900, 317.0);
		tabellaPrezzi.put(1500, 1000, 330.0);
		tabellaPrezzi.put(1500, 1100, 343.0);
		tabellaPrezzi.put(1500, 1200, 357.0);
		tabellaPrezzi.put(1500, 1300, 371.0);
		tabellaPrezzi.put(1500, 1400, 386.0);

		tabellaPrezzi.put(1600, 700, 302.0);
		tabellaPrezzi.put(1600, 800, 314.0);
		tabellaPrezzi.put(1600, 900, 327.0);
		tabellaPrezzi.put(1600, 1000, 340.0);
		tabellaPrezzi.put(1600, 1100, 353.0);
		tabellaPrezzi.put(1600, 1200, 368.0);
		tabellaPrezzi.put(1600, 1300, 382.0);
		tabellaPrezzi.put(1600, 1400, 398.0);

		tabellaPrezzi.put(1700, 700, 311.0);
		tabellaPrezzi.put(1700, 800, 324.0);
		tabellaPrezzi.put(1700, 900, 337.0);
		tabellaPrezzi.put(1700, 1000, 350.0);
		tabellaPrezzi.put(1700, 1100, 364.0);
		tabellaPrezzi.put(1700, 1200, 379.0);
		tabellaPrezzi.put(1700, 1300, 394.0);
		tabellaPrezzi.put(1700, 1400, 409.0);

		tabellaPrezzi = aggiungiScontoAttivita(tabellaPrezzi);
		
		String colore = "Bianco";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 2 ante.jpg");

		colore = "Avorio";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 2 ante.jpg");
		
		Table<Integer, Integer, Double> tabellaPrezzi2 = HashBasedTable.create();
		
		tabellaPrezzi2.put(1000, 700, 285.0);
		tabellaPrezzi2.put(1000, 800, 296.0);
		tabellaPrezzi2.put(1000, 900, 305.0);
		tabellaPrezzi2.put(1000, 1000, 314.0);
		tabellaPrezzi2.put(1000, 1100, 324.0);
		tabellaPrezzi2.put(1000, 1200, 334.0);
		tabellaPrezzi2.put(1000, 1300, 344.0);
		tabellaPrezzi2.put(1000, 1400, 354.0);

		tabellaPrezzi2.put(1100, 700, 294.0);
		tabellaPrezzi2.put(1100, 800, 305.0);
		tabellaPrezzi2.put(1100, 900, 314.0);
		tabellaPrezzi2.put(1100, 1000, 324.0);
		tabellaPrezzi2.put(1100, 1100, 334.0);
		tabellaPrezzi2.put(1100, 1200, 344.0);
		tabellaPrezzi2.put(1100, 1300, 354.0);
		tabellaPrezzi2.put(1100, 1400, 365.0);

		tabellaPrezzi2.put(1200, 700, 302.0);
		tabellaPrezzi2.put(1200, 800, 314.0);
		tabellaPrezzi2.put(1200, 900, 324.0);
		tabellaPrezzi2.put(1200, 1000, 334.0);
		tabellaPrezzi2.put(1200, 1100, 344.0);
		tabellaPrezzi2.put(1200, 1200, 354.0);
		tabellaPrezzi2.put(1200, 1300, 365.0);
		tabellaPrezzi2.put(1200, 1400, 375.0);

		tabellaPrezzi2.put(1300, 700, 311.0);
		tabellaPrezzi2.put(1300, 800, 324.0);
		tabellaPrezzi2.put(1300, 900, 334.0);
		tabellaPrezzi2.put(1300, 1000, 344.0);
		tabellaPrezzi2.put(1300, 1100, 354.0);
		tabellaPrezzi2.put(1300, 1200, 365.0);
		tabellaPrezzi2.put(1300, 1300, 375.0);
		tabellaPrezzi2.put(1300, 1400, 387.0);

		tabellaPrezzi2.put(1400, 700, 321.0);
		tabellaPrezzi2.put(1400, 800, 334.0);
		tabellaPrezzi2.put(1400, 900, 347.0);
		tabellaPrezzi2.put(1400, 1000, 361.0);
		tabellaPrezzi2.put(1400, 1100, 375.0);
		tabellaPrezzi2.put(1400, 1200, 390.0);
		tabellaPrezzi2.put(1400, 1300, 406.0);
		tabellaPrezzi2.put(1400, 1400, 422.0);

		tabellaPrezzi2.put(1500, 700, 330.0);
		tabellaPrezzi2.put(1500, 800, 344.0);
		tabellaPrezzi2.put(1500, 900, 357.0);
		tabellaPrezzi2.put(1500, 1000, 372.0);
		tabellaPrezzi2.put(1500, 1100, 387.0);
		tabellaPrezzi2.put(1500, 1200, 402.0);
		tabellaPrezzi2.put(1500, 1300, 418.0);
		tabellaPrezzi2.put(1500, 1400, 435.0);

		tabellaPrezzi2.put(1600, 700, 340.0);
		tabellaPrezzi2.put(1600, 800, 354.0);
		tabellaPrezzi2.put(1600, 900, 368.0);
		tabellaPrezzi2.put(1600, 1000, 383.0);
		tabellaPrezzi2.put(1600, 1100, 398.0);
		tabellaPrezzi2.put(1600, 1200, 414.0);
		tabellaPrezzi2.put(1600, 1300, 431.0);
		tabellaPrezzi2.put(1600, 1400, 448.0);

		tabellaPrezzi2.put(1700, 700, 351.0);
		tabellaPrezzi2.put(1700, 800, 365.0);
		tabellaPrezzi2.put(1700, 900, 379.0);
		tabellaPrezzi2.put(1700, 1000, 394.0);
		tabellaPrezzi2.put(1700, 1100, 410.0);
		tabellaPrezzi2.put(1700, 1200, 426.0);
		tabellaPrezzi2.put(1700, 1300, 444.0);
		tabellaPrezzi2.put(1700, 1400, 461.0);

		tabellaPrezzi2 = aggiungiScontoAttivita(tabellaPrezzi2);
		
		colore = "Effetto Legno Iroko";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 2 ante.jpg");

		colore = "Effetto Legno Noce";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 2 ante.jpg");

		colore = "Effetto Legno Rovere Sbiancato";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 2 ante.jpg");

		colore = "Altro Effetto Legno";
		prod.aggiungiColore(colore);
		prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
		   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 2 ante.jpg");

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
		       double nuovoPrezzo = Util.round(pair.getValue() * (100 / (100 + Constants.SCONTO_CONCRETE_A_ATTIVITA)), 2);
		       pair.setValue(nuovoPrezzo);
		       System.out.println(row+" "+pair.getKey()+" "+pair.getValue());
		   }
	   }
	   
	   return tabellaPrezzi;
	}

}
