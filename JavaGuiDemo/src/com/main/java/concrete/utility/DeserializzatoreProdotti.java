package com.main.java.concrete.utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

import com.google.common.collect.Table;
import com.main.java.concrete.bean.Prodotto;

public class DeserializzatoreProdotti {
	
	public Prodotto deserializzaProdotto(String classeSerializzata) throws IOException, ClassNotFoundException{
	      Prodotto prod = null;
	      try {
	    	 InputStream uriProdotto = DeserializzatoreServizi.class.getResourceAsStream("/prodottiDeserializzati/"+classeSerializzata+".ser");
//	         FileInputStream fileIn = new FileInputStream("prodotti/"+classeSerializzata+".ser");
//	         ObjectInputStream in = new ObjectInputStream(fileIn);
		     ObjectInputStream in = new ObjectInputStream(uriProdotto);
	         prod = (Prodotto) in.readObject();
	         in.close();
//	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         throw i;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Classe "+classeSerializzata+" non trovata");
	         c.printStackTrace();
	         throw c;
	      }
	      
	      return prod;
	}

	
	// main creato esclusivamente per test
   public static void main(String [] args) {
	   DeserializzatoreProdotti dp = new DeserializzatoreProdotti();
	   try {
	   	  String classeSerializzata = "Finestra1anta";
//		  String classeSerializzata = "Finestra2ante";
//		  String classeSerializzata = "Portafinestra1anta";
//		  String classeSerializzata = "Portafinestra2ante";
		  Prodotto prod = dp.deserializzaProdotto(classeSerializzata);
		      
	      System.out.println("Deserializzazione "+classeSerializzata+"...");
	      System.out.println("Nome: " + prod.getNomeProdotto());
	      
	      int larg = 787;
	      int alt = 1144;
	      if(larg%100!=0) {
	    	  larg = 100*((larg/100)+1);
	      }
	      if(alt%100!=0) {
	    	  alt = 100*((alt/100)+1);
	      }
	      	      
	      Vector colori = prod.getColori();
	      for(int i=0; i<colori.size(); i++) {
	    	  String colore = colori.get(i).toString();
	    	  System.out.println("Colore: " + colore);
	    	  Table prezzi = (Table) prod.getTabellePrezzi().get(colore);
		      double v = (double) prezzi.get(larg, alt);
		      System.out.println("Prezzo per "+larg+"x"+alt+": "+v);
	      }
	      
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
}