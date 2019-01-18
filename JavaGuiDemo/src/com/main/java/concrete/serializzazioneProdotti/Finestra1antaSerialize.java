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

public class Finestra1antaSerialize {

	public static void main(String [] args) {
	   
//	   String file = "./prodotti/Finestra1anta.ser";

	   String file = "Finestra1anta.ser";
	   final String dir = System.getProperty("user.dir");
	   file = dir +"/src/prodottiDeserializzati/" + file;
	   
	   Prodotto prod = new Prodotto();
	   prod.setNomeProdotto("Finestra 1 anta");
	   
	   prod.setCostoMontaggio(35.0);
	   
	   Table<Integer, Integer, Double> tabellaPrezzi = HashBasedTable.create();
	   
	   tabellaPrezzi.put(500, 700, 183.0);
	   tabellaPrezzi.put(500, 800, 186.0);
	   tabellaPrezzi.put(500, 900, 189.0);
	   tabellaPrezzi.put(500, 1000, 204.0);
	   tabellaPrezzi.put(500, 1100, 206.0);
	   tabellaPrezzi.put(500, 1200, 208.0);
	   tabellaPrezzi.put(500, 1300, 220.0);
	   tabellaPrezzi.put(500, 1400, 225.0);
	   
	   tabellaPrezzi.put(600, 700, 205.0);
	   tabellaPrezzi.put(600, 800, 211.0);
	   tabellaPrezzi.put(600, 900, 214.0);
	   tabellaPrezzi.put(600, 1000, 217.0);
	   tabellaPrezzi.put(600, 1100, 220.0);
	   tabellaPrezzi.put(600, 1200, 226.0);
	   tabellaPrezzi.put(600, 1300, 230.0);
	   tabellaPrezzi.put(600, 1400, 238.0);
	   
	   tabellaPrezzi.put(700, 700, 209.0);
	   tabellaPrezzi.put(700, 800, 213.0);
	   tabellaPrezzi.put(700, 900, 217.0);
	   tabellaPrezzi.put(700, 1000, 221.0);
	   tabellaPrezzi.put(700, 1100, 225.0);
	   tabellaPrezzi.put(700, 1200, 229.0);
	   tabellaPrezzi.put(700, 1300, 234.0);
	   tabellaPrezzi.put(700, 1400, 239.0);
	   
	   tabellaPrezzi.put(800, 700, 214.00);
	   tabellaPrezzi.put(800, 800, 220.42);
	   tabellaPrezzi.put(800, 900, 227.03);
	   tabellaPrezzi.put(800, 1000, 233.84);
	   tabellaPrezzi.put(800, 1100, 240.86);
	   tabellaPrezzi.put(800, 1200, 248.08);
	   tabellaPrezzi.put(800, 1300, 255.53);
	   tabellaPrezzi.put(800, 1400, 263.19);

	   tabellaPrezzi.put(900, 700, 220.00);
	   tabellaPrezzi.put(900, 800, 228.80);
	   tabellaPrezzi.put(900, 900, 237.95);
	   tabellaPrezzi.put(900, 1000, 247.47);
	   tabellaPrezzi.put(900, 1100, 257.37);
	   tabellaPrezzi.put(900, 1200, 267.66);
	   tabellaPrezzi.put(900, 1300, 278.37);
	   tabellaPrezzi.put(900, 1400, 289.50);

	   tabellaPrezzi.put(1000, 700, 231.00);
	   tabellaPrezzi.put(1000, 800, 240.24);
	   tabellaPrezzi.put(1000, 900, 249.85);
	   tabellaPrezzi.put(1000, 1000, 259.84);
	   tabellaPrezzi.put(1000, 1100, 270.24);
	   tabellaPrezzi.put(1000, 1200, 281.05);
	   tabellaPrezzi.put(1000, 1300, 292.29);
	   tabellaPrezzi.put(1000, 1400, 303.98);

	   tabellaPrezzi.put(1100, 700, 239.00);
	   tabellaPrezzi.put(1100, 800, 248.56);
	   tabellaPrezzi.put(1100, 900, 258.50);
	   tabellaPrezzi.put(1100, 1000, 268.84);
	   tabellaPrezzi.put(1100, 1100, 279.60);
	   tabellaPrezzi.put(1100, 1200, 290.78);
	   tabellaPrezzi.put(1100, 1300, 302.41);
	   tabellaPrezzi.put(1100, 1400, 314.51);

	   tabellaPrezzi.put(1200, 700, 241.00);
	   tabellaPrezzi.put(1200, 800, 250.64);
	   tabellaPrezzi.put(1200, 900, 260.67);
	   tabellaPrezzi.put(1200, 1000, 271.09);
	   tabellaPrezzi.put(1200, 1100, 281.94);
	   tabellaPrezzi.put(1200, 1200, 293.21);
	   tabellaPrezzi.put(1200, 1300, 304.94);
	   tabellaPrezzi.put(1200, 1400, 317.14);
	   
	   tabellaPrezzi = aggiungiScontoAttivita(tabellaPrezzi);

	   String colore = "Bianco";
	   prod.aggiungiColore(colore);
	   prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi);
	   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 1 anta.jpg");

	   colore = "Avorio";
	   prod.aggiungiColore(colore);
	   prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi);
	   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 1 anta.jpg");
	   
	   
	   Table<Integer, Integer, Double> tabellaPrezzi2 = HashBasedTable.create();
	   
	   tabellaPrezzi2.put(500, 700, 206.0);
	   tabellaPrezzi2.put(500, 800, 214.0);
	   tabellaPrezzi2.put(500, 900, 221.0);
	   tabellaPrezzi2.put(500, 1000, 227.0);
	   tabellaPrezzi2.put(500, 1100, 234.0);
	   tabellaPrezzi2.put(500, 1200, 241.0);
	   tabellaPrezzi2.put(500, 1300, 248.0);
	   tabellaPrezzi2.put(500, 1400, 256.0);

	   tabellaPrezzi2.put(600, 700, 211.0);
	   tabellaPrezzi2.put(600, 800, 219.0);
	   tabellaPrezzi2.put(600, 900, 226.0);
	   tabellaPrezzi2.put(600, 1000, 233.0);
	   tabellaPrezzi2.put(600, 1100, 240.0);
	   tabellaPrezzi2.put(600, 1200, 247.0);
	   tabellaPrezzi2.put(600, 1300, 254.0);
	   tabellaPrezzi2.put(600, 1400, 262.0);

	   tabellaPrezzi2.put(700, 700, 217.0);
	   tabellaPrezzi2.put(700, 800, 226.0);
	   tabellaPrezzi2.put(700, 900, 232.0);
	   tabellaPrezzi2.put(700, 1000, 239.0);
	   tabellaPrezzi2.put(700, 1100, 247.0);
	   tabellaPrezzi2.put(700, 1200, 254.0);
	   tabellaPrezzi2.put(700, 1300, 262.0);
	   tabellaPrezzi2.put(700, 1400, 269.0);

	   tabellaPrezzi2.put(800, 700, 223.0);
	   tabellaPrezzi2.put(800, 800, 232.0);
	   tabellaPrezzi2.put(800, 900, 239.0);
	   tabellaPrezzi2.put(800, 1000, 246.0);
	   tabellaPrezzi2.put(800, 1100, 253.0);
	   tabellaPrezzi2.put(800, 1200, 261.0);
	   tabellaPrezzi2.put(800, 1300, 269.0);
	   tabellaPrezzi2.put(800, 1400, 277.0);

	   tabellaPrezzi2.put(900, 700, 233.0);
	   tabellaPrezzi2.put(900, 800, 242.0);
	   tabellaPrezzi2.put(900, 900, 252.0);
	   tabellaPrezzi2.put(900, 1000, 262.0);
	   tabellaPrezzi2.put(900, 1100, 273.0);
	   tabellaPrezzi2.put(900, 1200, 283.0);
	   tabellaPrezzi2.put(900, 1300, 295.0);
	   tabellaPrezzi2.put(900, 1400, 307.0);

	   tabellaPrezzi2.put(1000, 700, 242.0);
	   tabellaPrezzi2.put(1000, 800, 252.0);
	   tabellaPrezzi2.put(1000, 900, 262.0);
	   tabellaPrezzi2.put(1000, 1000, 272.0);
	   tabellaPrezzi2.put(1000, 1100, 283.0);
	   tabellaPrezzi2.put(1000, 1200, 294.0);
	   tabellaPrezzi2.put(1000, 1300, 306.0);
	   tabellaPrezzi2.put(1000, 1400, 318.0);

	   tabellaPrezzi2.put(1100, 700, 249.0);
	   tabellaPrezzi2.put(1100, 800, 259.0);
	   tabellaPrezzi2.put(1100, 900, 269.0);
	   tabellaPrezzi2.put(1100, 1000, 280.0);
	   tabellaPrezzi2.put(1100, 1100, 291.0);
	   tabellaPrezzi2.put(1100, 1200, 303.0);
	   tabellaPrezzi2.put(1100, 1300, 315.0);
	   tabellaPrezzi2.put(1100, 1400, 328.0);

	   tabellaPrezzi2.put(1200, 700, 257.0);
	   tabellaPrezzi2.put(1200, 800, 267.0);
	   tabellaPrezzi2.put(1200, 900, 278.0);
	   tabellaPrezzi2.put(1200, 1000, 289.0);
	   tabellaPrezzi2.put(1200, 1100, 301.0);
	   tabellaPrezzi2.put(1200, 1200, 313.0);
	   tabellaPrezzi2.put(1200, 1300, 325.0);
	   tabellaPrezzi2.put(1200, 1400, 338.0);
	   
	   tabellaPrezzi2 = aggiungiScontoAttivita(tabellaPrezzi2);

	   colore = "Effetto Legno Iroko";
	   prod.aggiungiColore(colore);
	   prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
	   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 1 anta.jpg");

	   colore = "Effetto Legno Noce";
	   prod.aggiungiColore(colore);
	   prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
	   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 1 anta.jpg");

	   colore = "Effetto Legno Rovere Sbiancato";
	   prod.aggiungiColore(colore);
	   prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
	   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 1 anta.jpg");

	   colore = "Altro Effetto Legno";
	   prod.aggiungiColore(colore);
	   prod.aggiungiTabellaPrezzi(colore, tabellaPrezzi2);
	   prod.aggiungiImmagine(colore,Constants.FOLDER_IMMAGINI_PRODOTTI+"finestra 1 anta.jpg");
	   
	   try {
		   FileOutputStream fileOut =
				   new FileOutputStream(file);
		   ObjectOutputStream out = new ObjectOutputStream(fileOut);
		   out.writeObject(prod);
		   out.close();
		   fileOut.close();
		   System.out.printf("Dati serializzati salvati in "+file);
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