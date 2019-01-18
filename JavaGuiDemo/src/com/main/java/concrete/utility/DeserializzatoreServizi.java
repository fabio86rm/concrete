package com.main.java.concrete.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URI;
import java.util.Vector;

import com.main.java.concrete.bean.Servizio;

public class DeserializzatoreServizi {
	
	public Servizio deserializzaServizio(String classeSerializzata) throws IOException, ClassNotFoundException{
	      Servizio serv = null;
	      try {
	    	 InputStream uriServizio = DeserializzatoreServizi.class.getResourceAsStream("/serviziDeserializzati/"+classeSerializzata+".ser");
//	         FileInputStream fileIn = new FileInputStream("servizi/"+classeSerializzata+".ser");
//		     ObjectInputStream in = new ObjectInputStream(fileIn);
	         ObjectInputStream in = new ObjectInputStream(uriServizio);
	         serv = (Servizio) in.readObject();
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
	      
	      return serv;
	}

	
	// main creato esclusivamente per test
   public static void main(String [] args) {
	   DeserializzatoreServizi dp = new DeserializzatoreServizi();
	   try {
//	   	  String classeSerializzata = "Sopralluogo";
//		  String classeSerializzata = "Trasporto";
		  String classeSerializzata = "Dislocazione";
		  Servizio serv = dp.deserializzaServizio(classeSerializzata);
		      
	      System.out.println("Deserializzazione "+classeSerializzata+"...");
	      System.out.println("Nome: " + serv.getNomeServizio());
	      	      
	      Vector descrizioniServizio = serv.getDescrizioniServizio();
	      for(int i=0; i<descrizioniServizio.size(); i++) {
	    	  String descrizioneServizio = descrizioniServizio.get(i).toString();
	    	  System.out.println("Colore: " + descrizioneServizio);
	    	  double prezzo = serv.getTabellaPrezzi().get(descrizioneServizio);
		      System.out.println("Prezzo: "+prezzo);
	      }
	      
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
}
