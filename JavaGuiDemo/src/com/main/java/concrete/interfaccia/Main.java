package com.main.java.concrete.interfaccia;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.main.java.concrete.bean.Prodotto;
import com.main.java.concrete.bean.Servizio;
import com.main.java.concrete.utility.Constants;
import com.main.java.concrete.utility.DeserializzatoreProdotti;
import com.main.java.concrete.utility.DeserializzatoreServizi;
import com.main.java.concrete.utility.LettoreProperties;

public class Main {
	
	final static Logger logger = Logger.getLogger(LettoreProperties.class);
	private Properties prop;
	private static Vector<Prodotto> listaProdotti;
	private static Vector<Servizio> listaServizi;
	private static ImageIcon img;
	private static String titolo;
	
	public void inizializza() {
		LettoreProperties lp = new LettoreProperties();
		lp.leggiPropertiesLog4j();
		prop = lp.leggiPropertiesConfigurazione();
//		img = new ImageIcon(Constants.PATH_LOGO);
		URL url = Main.class.getResource(Constants.PATH_LOGO);
		img = new ImageIcon(url);
		titolo = Constants.TITOLO_PROGRAMMA;
	}
	
	public Vector<Prodotto> caricaProdotti() throws ClassNotFoundException, IOException {
		DeserializzatoreProdotti dp = new DeserializzatoreProdotti();
		
		String[] prodottiConfig = Constants.PRODOTTI.split(";");
		Vector<Prodotto> listaProdotti = new Vector<Prodotto>();
		for(int i=0; i<prodottiConfig.length; i++) {
			listaProdotti.add(dp.deserializzaProdotto(prodottiConfig[i]));
		}
		
		return listaProdotti;
	}
	
	public Vector<Servizio> caricaServizi() throws ClassNotFoundException, IOException {
		DeserializzatoreServizi ds = new DeserializzatoreServizi();
		
		String[] serviziConfig = Constants.SERVIZI.toString().split(";");
		Vector<Servizio> listaServizi = new Vector<Servizio>();
		for(int i=0; i<serviziConfig.length; i++) {
			listaServizi.add(ds.deserializzaServizio(serviziConfig[i]));
		}
		
		return listaServizi;
	}
	
	public static void main(String args[]) {
		// TODO importazione delle immagini trovato al link https://stackoverflow.com/questions/25635636/eclipse-exported-runnable-jar-not-showing-images
		// tramite la ricerca su google https://www.google.com/search?client=firefox-b-ab&ei=nz9AXM3hMKum_QbB0YCgDw&q=import+images+in+jar&oq=import+images+in+jar&gs_l=psy-ab.3...16831.18478..18834...0.0..0.154.302.0j2......0....1..gws-wiz.6mxrmEf7S54
		Main main = new Main();
		main.inizializza();
		try {
			listaProdotti = main.caricaProdotti();
			listaServizi = main.caricaServizi();
		} catch (ClassNotFoundException | IOException e1) {
			logger.error("Errore nel caricamento dei prodotti/servizi: "+e1.getMessage());
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					double utileProdottiAttivita = Double.parseDouble(main.prop.getProperty("utileProdottiAttivita"));
					double utileServiziAttivita = Double.parseDouble(main.prop.getProperty("utileServiziAttivita"));
					InterfacciaPreventivi window = new InterfacciaPreventivi(logger, listaProdotti, listaServizi, img, titolo, utileProdottiAttivita, utileServiziAttivita);
					window.rendiFrameVisibile(true);
				} catch (Exception e) {
					logger.error("Errore: "+e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
	
}
