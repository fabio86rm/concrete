package com.main.java.concrete.serializzazioneServizi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.main.java.concrete.bean.Servizio;

public class Trasporto {
	
	public static void main(String args[]) {
		   
//		   String file = "./servizi/Trasporto.ser";

		   String file = "Trasporto.ser";
		   final String dir = System.getProperty("user.dir");
		   file = dir +"/src/serviziDeserializzati/" + file;
		   
		   Servizio serv = new Servizio();
		   serv.setNomeServizio("Trasporto");
		   
		   serv.aggiungiDescrizioneServizio("No Trasporto");
		   serv.aggiungiDescrizioneServizio("Entro 25 km");
		   serv.aggiungiDescrizioneServizio("Da 25 km a 60 km");
		   serv.aggiungiDescrizioneServizio("Oltre 60 km");
		   
		   serv.aggiungiPrezzoServizio(serv.getDescrizioniServizio().get(0).toString(), 0.0);
		   serv.aggiungiPrezzoServizio(serv.getDescrizioniServizio().get(1).toString(), 150.0);
		   serv.aggiungiPrezzoServizio(serv.getDescrizioniServizio().get(2).toString(), 200.0);
		   serv.aggiungiPrezzoServizio(serv.getDescrizioniServizio().get(3).toString(), -1);
		   
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
