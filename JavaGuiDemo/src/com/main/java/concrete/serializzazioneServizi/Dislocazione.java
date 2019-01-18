package com.main.java.concrete.serializzazioneServizi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.main.java.concrete.bean.Servizio;

public class Dislocazione {
	
	public static void main(String args[]) {
		   
//		   String file = "./servizi/Dislocazione.ser";

		   String file = "Dislocazione.ser";
		   final String dir = System.getProperty("user.dir");
		   file = dir +"/src/serviziDeserializzati/" + file;
		   
		   Servizio serv = new Servizio();
		   serv.setNomeServizio("Dislocazione su piano");
		   
		   serv.aggiungiDescrizioneServizio("No Dislocazione");
		   serv.aggiungiDescrizioneServizio("Fino al 2° piano");
		   serv.aggiungiDescrizioneServizio("Oltre il 2° piano");
		   
		   serv.aggiungiPrezzoServizio(serv.getDescrizioniServizio().get(0).toString(), 0.0);
		   serv.aggiungiPrezzoServizio(serv.getDescrizioniServizio().get(1).toString(), 100.0);
		   serv.aggiungiPrezzoServizio(serv.getDescrizioniServizio().get(2).toString(), 150.0);
		   
		   try {
			   FileOutputStream fileOut =
					   new FileOutputStream(file);
			   ObjectOutputStream out = new ObjectOutputStream(fileOut);
			   out.writeObject(serv);
			   out.close();
			   fileOut.close();
			   System.out.printf("Dati serializzati salvati in "+file);
		   } catch (IOException i) {
			   i.printStackTrace();
		   }
		
	}

}
