package com.main.java.concrete.stampa;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.main.java.concrete.bean.RigaModel;
import com.main.java.concrete.utility.Constants;
import com.main.java.concrete.utility.LettoreProperties;
import com.main.java.concrete.utility.Util;


public class ScrittorePDF {

	private static Properties prop;
	
	private static String FILE;
	
	private static Font bigFont 	= new Font(Font.FontFamily.TIMES_ROMAN, 18,		Font.BOLD);
	private static Font redFont 	= new Font(Font.FontFamily.TIMES_ROMAN, 12,		Font.NORMAL, BaseColor.RED);
	private static Font subFont 	= new Font(Font.FontFamily.TIMES_ROMAN, 16,		Font.BOLD);
	private static Font smallBold 	= new Font(Font.FontFamily.TIMES_ROMAN, 12,		Font.BOLD);
	
	// font per le tabelle
	private static Font boldFontDati = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
	private static Font boldFontRighe = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
	private static Font fontRighe = new Font(Font.FontFamily.HELVETICA, 11);
	private static Font boldFontEtichetteTabProdotto = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLDITALIC);
	
	private static Vector<RigaModel> righeTabella;
	private static double totaleProdotti;
	private static double totaleProdottiIva;
	private static double iva;
	private static double totaleServizi;
	private static double totaleServiziIva;
	
	private static String[] servizi;
	private static String[] prezziServizi;
	
	private static String[] datiAttivita;
	private static String[] datiCliente;
	
	private static String coloreManiglia;
	private static String sensoAperturaManiglia;
	
	private static boolean copiaCliente;
	
	private static Logger logger;
	
	public ScrittorePDF(Vector<RigaModel> righeTabella, double totaleProdotti, double totaleProdottiIva, double totaleServizi, double totaleServiziIva, double iva, String[] servizi, String[] prezziServizi, String[] datiCliente, String coloreManiglia, String sensoAperturaManiglia, Logger logger, boolean copiaCliente) {
		this.righeTabella = righeTabella;
		this.totaleProdotti = totaleProdotti;
		this.totaleProdottiIva = totaleProdottiIva;
		this.iva = iva;
		this.totaleServizi = totaleServizi;
		this.totaleServiziIva = totaleServiziIva;
		this.servizi = servizi;
		this.prezziServizi = prezziServizi;
		this.datiCliente = datiCliente;
		this.copiaCliente = copiaCliente;
		this.coloreManiglia = coloreManiglia;
		this.sensoAperturaManiglia = sensoAperturaManiglia;
		this.logger = logger;
		inizializza();
		inizializzaDatiAttivita();
	}
	
	public void inizializza() {
		LettoreProperties lp = new LettoreProperties();
		prop = lp.leggiPropertiesConfigurazione();
		FILE = Constants.PATH_STAMPE;
		Date date = new Date();
		String currentDate= new SimpleDateFormat("yyyyMMddhhmm").format(date);
		if(copiaCliente) {
			FILE = FILE+currentDate+"CopiaCliente.pdf"; // Ë sbagliato, perchÈ dopo la data dovrei aggiungere l'estensione "pdf"
		} else {
			FILE = FILE+currentDate+"CopiaPersonale.pdf"; // Ë sbagliato, perchÈ dopo la data dovrei aggiungere l'estensione "pdf"
		}
	}
	
	public void inizializzaDatiAttivita() {
		
		datiAttivita = new String[8];

		datiAttivita[0] = prop.getProperty("nomeAttivita");
		datiAttivita[1] = prop.getProperty("indirizzoAttivita");
		datiAttivita[2] = prop.getProperty("capAttivita");
		datiAttivita[3] = prop.getProperty("cittaAttivita");
		datiAttivita[4] = prop.getProperty("provinciaAttivita");
		datiAttivita[5] = prop.getProperty("partitaIvaAttivita");
		datiAttivita[6] = prop.getProperty("telefonoAttivita");
		datiAttivita[7] = prop.getProperty("emailAttivita");
		
	}
	
	public static void creaPreventivi() throws FileNotFoundException, Exception {
		try {
			Document document = new Document();
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(FILE));
			if(copiaCliente) {
				HeaderFooterPageEvent event = new HeaderFooterPageEvent(); // permette di aggiungere un header e un footer a tutte le pagine del documento
				pdfWriter.setPageEvent(event);
			}
			document.open();
			aggiungiMetaDati(document);
			aggiungiPrefazione(document);
			aggiungiContenuto(document);
			document.close();
			apriPdf(FILE);
		} catch(FileNotFoundException e) {
			String messaggio = "Errore: assicurarsi che il file non sia gi‡ aperto.";
			logger.error("Errore: assicurarsi che il file non sia gi‡ aperto.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new FileNotFoundException(messaggio);
		} catch (Exception e) {
			String messaggio = "Errore nella creazione del file PDF.";
			logger.error("Errore nella creazione del file PDF.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new Exception(messaggio);
		}
	}
	
	private static void apriPdf(String file) throws Exception {
		if (Desktop.isDesktopSupported()) {
		    try {
		        File myFile = new File(file);
		        Desktop.getDesktop().open(myFile);
		    } catch(Exception e) {
		    	logger.error("Errore nell'apertura del file PDF: "+e.getStackTrace());
		    	throw new Exception("Errore nell'apertura del file PDF.");
		    }
		}
	}
	
	/* iText permette di aggiungere metadati al pdf che possono essere 
	 * visualizzati in Adobe Reader da File -> Propriet√† */
	private static void aggiungiMetaDati(Document document) {
		Date date = new Date();
		String currentDate= new SimpleDateFormat("yyyyMMdd").format(date);
		document.addTitle("Preventivo "+currentDate);
		document.addSubject("Preventivo");
		document.addKeywords("Preventivo "+prop.getProperty("nomeAttivita"));
		document.addAuthor(prop.getProperty("nomeAttivita"));
		document.addCreator(prop.getProperty("nomeAttivita"));
	}

	private static void aggiungiPrefazione(Document document)	throws DocumentException {
		Paragraph prefazione = new Paragraph();
		
		// Aggiungiamo una linea vuota
		aggiungiLineaVuota(prefazione, 1);
		
		// Aggiungiamo il titolo
		prefazione.add(new Paragraph("Preventivo del " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()), bigFont));

		aggiungiLineaVuota(prefazione, 1);

		// Aggiunta al documento
		document.add(prefazione);
	}

	private static void aggiungiContenuto(Document document) throws DocumentException, Exception {
		
		Paragraph sectionParagraph = new Paragraph();

		creaPrimaPagina(sectionParagraph);
		document.add(sectionParagraph);
		
		// Aggiungiamo la nuova pagina con il preventivo effettivo
		creaNuovaPagina(document);

//		creaTabellaPreventivo(sectionParagraph2);
		for(int i=0; i<righeTabella.size(); i++) {
			Paragraph sectionTmp = new Paragraph();
			aggiungiLineaVuota(sectionTmp, 3);
			PdfPTable tabellaProdotti = creaTabellaProdotto(righeTabella.get(i));
			sectionTmp.add(tabellaProdotti);
			if(i<righeTabella.size()-1 && i%2!=0) {
				document.add(sectionTmp);
				// aggiungo lo spazio per l'ultima tabella dei prezzi
				creaNuovaPagina(document);
			} else {
				aggiungiLineaVuota(sectionTmp, 1);
				document.add(sectionTmp);
			}
		}
		
		PdfPTable tabellaTotale = creaTabellaTotale();
		Paragraph sectionTotale = new Paragraph();
		aggiungiLineaVuota(sectionTotale, 2);
		sectionTotale.add(tabellaTotale);
		document.add(sectionTotale);
		
		PdfPTable tabellaFirma = creaTabellaFirma();
		Paragraph sectionFirma = new Paragraph();
		aggiungiLineaVuota(sectionFirma, 1);
		sectionFirma.add(tabellaFirma);
		document.add(sectionFirma);
		
	}
	
	private static void creaPrimaPagina(Paragraph sectionParagraph) throws Exception {
		PdfPTable tabellaLoghi = null;
		if(copiaCliente) {
			tabellaLoghi = creaTabellaLoghiCopiaCliente();
		} else {
			tabellaLoghi = creaTabellaLoghiCopiaAttivita();
		}
		PdfPTable tabellaDatiAttivita = creaTabellaDatiAnagrafici();
		PdfPTable tabellaServizi = creaTabellaServizi();
		
//		section.add(tabellaLoghi);
		sectionParagraph.add(tabellaLoghi);
		aggiungiLineaVuota(sectionParagraph, 4);
		sectionParagraph.add(tabellaDatiAttivita);
		aggiungiLineaVuota(sectionParagraph, 6);
		sectionParagraph.add(tabellaServizi);
		aggiungiLineaVuota(sectionParagraph, 4);
		sectionParagraph.add("N.B. La consegna entro 20 giorni lavorativi Ë garantita esclusivamente per i colori bianco, avorio, effetto legno iroko, effetto legno noce ed effetto legno rovere sbiancato.");
	}
	
	private static void aggiungiLineaVuota(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	private static void creaNuovaPagina(Document document) {
		// Nuova pagina
		document.newPage();
	}
	
	private static PdfPTable creaTabellaLoghiCopiaCliente() throws DocumentException, MalformedURLException, IOException, BadElementException {
		
		PdfPTable tabella = new PdfPTable(3);
		tabella.setWidthPercentage(100);
		// imposto la dimensione delle colonne
		try {
			float[] p = new float[3];
			p[0] = (float) 1.5;
			p[1] = 2;
			p[2] = 1;
			tabella.setWidths(p);
		} catch (DocumentException e) {
			logger.error("Errore nell'impostazione delle dimensioni delle colonne della tabella dei dati: "+e.getStackTrace());
			e.printStackTrace();
			throw new DocumentException("Errore nell'impostazione delle dimensioni delle colonne della tabella dei dati.");
		}
		
		String logoAttivita = Constants.LOGO_ATTIVITA_PER_STAMPA;
		Image imageAttivita;
		try {
//			URL urlLogo = ScrittorePDF.class.getResource(logoAttivita);
			PdfPCell cImage = new PdfPCell();
			imageAttivita = Image.getInstance(logoAttivita);
			cImage.addElement(imageAttivita);
			cImage.setBorder(Rectangle.NO_BORDER);
			cImage.setVerticalAlignment(Element.ALIGN_TOP);
			tabella.addCell(cImage);
		} catch (MalformedURLException e) {
			logger.error("Errore nella creazione della cella del logo dell'attivit‡. URL composto in modo errato.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new MalformedURLException("Errore nella creazione della cella del logo dell'attivit‡. URL composto in modo errato.");
		} catch (IOException e) {
			logger.error("Errore nella creazione della cella del logo dell'attivit‡. Errore IO.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new IOException("Errore nella creazione della cella del logo dell'attivit‡. Errore IO.");
		} catch (BadElementException e) {
			logger.error("Errore nella creazione della cella del logo dell'attivit‡. Eccezione nell'elemento.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new BadElementException("Errore nella creazione della cella del logo dell'attivit‡. Eccezione nell'elemento.");
		}

		String logoConcrete = Constants.LOGO_CONCRETE_PER_STAMPA;
		Image imageConcrete;
		try {
			URL urlLogo = ScrittorePDF.class.getResource(logoConcrete);
			PdfPCell cImage = new PdfPCell();
			imageConcrete = Image.getInstance(urlLogo);
			cImage.addElement(imageConcrete);
			cImage.setBorder(Rectangle.NO_BORDER);
			cImage.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tabella.addCell(cImage);
		} catch (MalformedURLException e) {
			logger.error("Errore nella creazione della cella del logo di Concrete. URL composto in modo errato.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new MalformedURLException("Errore nella creazione della cella del logo di Concrete. URL composto in modo errato.");
		} catch (IOException e) {
			logger.error("Errore nella creazione della cella del logo di Concrete. Errore IO.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new IOException("Errore nella creazione della cella del logo di Concrete. Errore IO.");
		} catch (BadElementException e) {
			logger.error("Errore nella creazione della cella del logo di Concrete. Eccezione nell'elemento.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new BadElementException("Errore nella creazione della cella del logo di Concrete. Eccezione nell'elemento.");
		}

		String logoltalplastick = Constants.LOGO_ITALPLASTIC_PER_STAMPA;
		Image imageItalplastick;
		try {
			URL urlLogo = ScrittorePDF.class.getResource(logoltalplastick);
			PdfPCell cImage = new PdfPCell();
			imageItalplastick = Image.getInstance(urlLogo);
			cImage.addElement(imageItalplastick);
			cImage.setBorder(Rectangle.NO_BORDER);
			cImage.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tabella.addCell(cImage);
		} catch (MalformedURLException e) {
			logger.error("Errore nella creazione della cella del logo di ItalPlastick. URL composto in modo errato.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new MalformedURLException("Errore nella creazione della cella del logo di ItalPlastick. URL composto in modo errato.");
		} catch (IOException e) {
			logger.error("Errore nella creazione della cella del logo di ItalPlastick. Errore IO.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new IOException("Errore nella creazione della cella del logo di ItalPlastick. Errore IO.");
		} catch (BadElementException e) {
			logger.error("Errore nella creazione della cella del logo di ItalPlastick. Eccezione nell'elemento.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new BadElementException("Errore nella creazione della cella del logo di ItalPlastick. Eccezione nell'elemento.");
		}
		
		return tabella;
	}
	
	private static PdfPTable creaTabellaLoghiCopiaAttivita() throws DocumentException, MalformedURLException, IOException, BadElementException {
		
		PdfPTable tabella = new PdfPTable(1);
		tabella.setWidthPercentage(80);
		
		String logoAttivita = Constants.LOGO_ATTIVITA_PER_STAMPA;
		Image imageAttivita;
		try {
			PdfPCell cImage = new PdfPCell();
			imageAttivita = Image.getInstance(logoAttivita);
			cImage.addElement(imageAttivita);
			cImage.setBorder(Rectangle.NO_BORDER);
			cImage.setVerticalAlignment(Element.ALIGN_TOP);
			tabella.addCell(cImage);
		} catch (MalformedURLException e) {
			logger.error("Errore nella creazione della cella del logo dell'attivit‡. URL composto in modo errato.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new MalformedURLException("Errore nella creazione della cella del logo dell'attivit‡. URL composto in modo errato.");
		} catch (IOException e) {
			logger.error("Errore nella creazione della cella del logo dell'attivit‡. Errore IO.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new IOException("Errore nella creazione della cella del logo dell'attivit‡. Errore IO.");
		} catch (BadElementException e) {
			logger.error("Errore nella creazione della cella del logo dell'attivit‡. Eccezione nell'elemento.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new BadElementException("Errore nella creazione della cella del logo dell'attivit‡. Eccezione nell'elemento.");
		}
		
		return tabella;
	}
	
	private static PdfPTable creaTabellaDatiAnagrafici() throws DocumentException {
		
		PdfPTable tabella = new PdfPTable(4);
		tabella.setWidthPercentage(100);
		// imposto la dimensione delle colonne
		try {
			int[] p = new int[4];
			p[0] = 1;
			p[1] = 2;
			p[2] = 1;
			p[3] = 2;
			tabella.setWidths(p);
		} catch (DocumentException e) {
			logger.error("Errore nell'impostazione delle dimensioni delle colonne della tabella dei dati: "+e.getStackTrace());
			e.printStackTrace();
			throw new DocumentException("Errore nell'impostazione delle dimensioni delle colonne della tabella dei dati.");
		}
		
		

		Phrase phraseDatiAttivita = new Phrase(Constants.ETICHETTA_DATI_ATT, boldFontDati );
		PdfPCell cella = new PdfPCell(phraseDatiAttivita);
		cella.setColspan(2);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		Phrase phraseDatiCliente = new Phrase(Constants.ETICHETTA_DATI_CL, boldFontDati );
		cella = new PdfPCell(phraseDatiCliente);
		cella.setColspan(2);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		tabella.addCell(new Phrase(Constants.ETICHETTA_RAG_SOC_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiAttivita[0], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_NOME_CL, boldFontRighe));
		tabella.addCell(new Phrase(datiCliente[0], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_INDIRIZZO_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiAttivita[1], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_INDIRIZZO_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiCliente[1], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_CAP_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiAttivita[2], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_CAP_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiCliente[2], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_CITTA_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiAttivita[3], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_CITTA_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiCliente[3], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_PROVINCIA_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiAttivita[4], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_PROVINCIA_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiCliente[4], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_PIVA_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiAttivita[5], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_PIVA_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiCliente[5], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_TELEFONO_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiAttivita[6], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_TELEFONO_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiCliente[6], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_MAIL_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiAttivita[7], fontRighe));
		tabella.addCell(new Phrase(Constants.ETICHETTA_MAIL_ATT, boldFontRighe));
		tabella.addCell(new Phrase(datiCliente[7], fontRighe));
		
		
		return tabella;
	}
	
	private static PdfPTable creaTabellaServizi() throws DocumentException {
		
		PdfPTable tabella = new PdfPTable(3);
		tabella.setWidthPercentage(100);
		// imposto la dimensione delle colonne
		try {
			float[] p = new float[3];
			p[0] = 2;
			p[1] = (float) 1.5;
			p[2] = (float) 0.5;
			tabella.setWidths(p);
		} catch (DocumentException e) {
			logger.error("Errore nell'impostazione delle dimensioni delle colonne della tabella dei servizi: "+e.getStackTrace());
			e.printStackTrace();
			throw new DocumentException("Errore nell'impostazione delle dimensioni delle colonne della tabella dei servizi.");
		}

		PdfPCell cella = new PdfPCell();
		cella.setBorder(Rectangle.NO_BORDER);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		Phrase titoloDescrizioneServizio = new Phrase("Descrizione", boldFontDati );
		cella = new PdfPCell(titoloDescrizioneServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		Phrase titoloPrezzoServizio = new Phrase("Prezzo", boldFontDati );
		cella = new PdfPCell(titoloPrezzoServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		tabella.addCell(new Phrase("Sopralluogo", boldFontRighe));
		Phrase descrizioneServizio = new Phrase(servizi[0], fontRighe);
		cella = new PdfPCell(descrizioneServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		Phrase prezzoServizio = new Phrase(prezziServizi[0]+" Ä", fontRighe);
		cella = new PdfPCell(prezzoServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		tabella.addCell(new Phrase("Trasporto", boldFontRighe));
		descrizioneServizio = new Phrase(servizi[1], fontRighe);
		cella = new PdfPCell(descrizioneServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		prezzoServizio = new Phrase(prezziServizi[1]+" Ä", fontRighe);
		cella = new PdfPCell(prezzoServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		tabella.addCell(new Phrase("Dislocazione su piano n∞", boldFontRighe));
		descrizioneServizio = new Phrase(servizi[2], fontRighe);
		cella = new PdfPCell(descrizioneServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		prezzoServizio = new Phrase(prezziServizi[2]+" Ä", fontRighe);
		cella = new PdfPCell(prezzoServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		tabella.addCell(new Phrase("Montaggio", boldFontRighe));
		descrizioneServizio = new Phrase(servizi[3], fontRighe);
		cella = new PdfPCell(descrizioneServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		prezzoServizio = new Phrase(prezziServizi[3], fontRighe);
		cella = new PdfPCell(prezzoServizio);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		return tabella;
	}
	
	public static PdfPTable creaTabellaProdotto(RigaModel riga) throws DocumentException, MalformedURLException, IOException, BadElementException {
		
		PdfPTable tabella = new PdfPTable(5);
		tabella.setWidthPercentage(100);
		// imposto la dimensione delle colonne
		try {
			int[] p = new int[5];
			p[0] = 2;
			p[1] = 2;
			p[2] = 1;
			p[3] = 1;
			p[4] = 1;
			tabella.setWidths(p);
		} catch (DocumentException e) {
			logger.error("Errore nell'impostazione delle dimensioni delle colonne della tabella del prodotto: "+e.getStackTrace());
			e.printStackTrace();
			throw new DocumentException("Errore nell'impostazione delle dimensioni delle colonne della tabella del prodotto: ");
		}
		
		PdfPCell c1 = new PdfPCell(new Phrase(Constants.ETICHETTA_PRODOTTO, boldFontDati));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//		c1.setGrayFill(0.8f);
		tabella.addCell(c1);
		c1 = new PdfPCell(new Phrase(Constants.ETICHETTA_DETTAGLI, boldFontDati));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//		c1.setGrayFill(0.8f);
		c1.setColspan(4);
		tabella.addCell(c1);
		
		try {
			URL immagineProdotto = riga.getPathImmagine();
			Image image;
			image = Image.getInstance(immagineProdotto);
			image.setAlignment(Image.ALIGN_CENTER);
			PdfPCell cImage = new PdfPCell();
			cImage.setRowspan(14);
			cImage.addElement(image);
			cImage.setFixedHeight(100);
			tabella.addCell(cImage);
		} catch (MalformedURLException e) {
			logger.error("Errore nella creazione della cella del logo del prodotto. URL composto in modo errato.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new MalformedURLException("Errore nella creazione della cella del logo del prodotto. URL composto in modo errato.");
		} catch (IOException e) {
			logger.error("Errore nella creazione della cella del logo del prodotto. Errore IO.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new IOException("Errore nella creazione della cella del logo del prodotto. Errore IO.");
		} catch (BadElementException e) {
			logger.error("Errore nella creazione della cella del logo del prodotto. Eccezione nell'elemento.\n"+e.getStackTrace());
			e.printStackTrace();
			throw new BadElementException("Errore nella creazione della cella del logo del prodotto. Eccezione nell'elemento.");
		}
		
		Phrase phraseEticTipoProdotto = new Phrase(Constants.TIPOLOGIA_PRODOTTO, boldFontEtichetteTabProdotto );
		PdfPCell cella = new PdfPCell(phraseEticTipoProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_LEFT);
		tabella.addCell(cella);
		Phrase phraseTipoProdotto = new Phrase(riga.getNomeProdotto(), fontRighe );
		cella = new PdfPCell(phraseTipoProdotto);
		cella.setColspan(3);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		Phrase phraseEticDimensioniProdotto = new Phrase(Constants.DIMENSIONI_2_DIMENSIONI, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticDimensioniProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_LEFT);
		tabella.addCell(cella);
		String dimensioni = riga.getLarghezza() + " x "+riga.getAltezza() + " mm";
		Phrase phraseDimensioniProdotto = new Phrase(dimensioni, fontRighe );
		cella = new PdfPCell(phraseDimensioniProdotto);
		cella.setColspan(3);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		Phrase phraseEticColoreProdotto = new Phrase(Constants.COLORE, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticColoreProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_LEFT);
		tabella.addCell(cella);
		Phrase phraseColoreProdotto = new Phrase(riga.getColore(), fontRighe );
		cella = new PdfPCell(phraseColoreProdotto);
		cella.setColspan(3);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		Phrase phraseEticAltezzaManigliaProdotto = new Phrase(Constants.ALTEZZA_MANIGLIA, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticAltezzaManigliaProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_LEFT);
		tabella.addCell(cella);
		String altezzaManiglia = Double.toString(riga.getAltezzaManiglia()) + " mm";
		Phrase phraseAltezzaManigliaProdotto = new Phrase(altezzaManiglia, fontRighe );
		cella = new PdfPCell(phraseAltezzaManigliaProdotto);
		cella.setColspan(3);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		Phrase phraseEticColoreManiglia = new Phrase(Constants.COLORE_MANIGLIA, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticColoreManiglia);
		cella.setHorizontalAlignment(Element.ALIGN_LEFT);
		tabella.addCell(cella);
		Phrase phraseColoreManiglia = new Phrase(coloreManiglia, fontRighe );
		cella = new PdfPCell(phraseColoreManiglia);
		cella.setColspan(3);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);
		
		Phrase phraseEticSensoAperturaManiglia = new Phrase(Constants.SENSO_APERTURA_MANIGLIA, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticSensoAperturaManiglia);
		cella.setHorizontalAlignment(Element.ALIGN_LEFT);
		tabella.addCell(cella);
		Phrase phraseSensoAperturaManiglia = new Phrase(sensoAperturaManiglia, fontRighe );
		cella = new PdfPCell(phraseSensoAperturaManiglia);
		cella.setColspan(3);
		cella.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(cella);

		Phrase phraseNote = new Phrase("\n\n\n\n\n\n\n\n", boldFontDati );
		cella = new PdfPCell(phraseNote);
		cella.setColspan(4);
		cella.setRowspan(6);
		tabella.addCell(cella);
		
		Phrase phraseEticPrezzoUnitarioProdotto = new Phrase(Constants.PREZZO_UNITARIO, boldFontDati );
		cella = new PdfPCell(phraseEticPrezzoUnitarioProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		Phrase phraseEticQuantitaProdotto = new Phrase(Constants.QUANTITA, boldFontDati );
		cella = new PdfPCell(phraseEticQuantitaProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		Phrase phraseEticScontoProdotto = new Phrase(Constants.SCONTO, boldFontDati );
		cella = new PdfPCell(phraseEticScontoProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		Phrase phraseEticSubtotaleProdotto = new Phrase(Constants.SUBTOTALE, boldFontDati );
		cella = new PdfPCell(phraseEticSubtotaleProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
		String prezzoUnitario = "Ä " + riga.getPrezzoUnitario();
		Phrase phrasePrezzoUnitarioProdotto = new Phrase(prezzoUnitario, fontRighe );
		cella = new PdfPCell(phrasePrezzoUnitarioProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		Phrase phraseQuantitaProdotto = new Phrase(Integer.toString(riga.getQuantita()), fontRighe );
		cella = new PdfPCell(phraseQuantitaProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		String sconto = riga.getSconto() + " %";
		Phrase phraseScontoProdotto = new Phrase(sconto, fontRighe );
		cella = new PdfPCell(phraseScontoProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		String subtotale = "Ä " + riga.getSubTotale();
		Phrase phraseSubtotaleProdotto = new Phrase(subtotale, fontRighe );
		cella = new PdfPCell(phraseSubtotaleProdotto);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
		
		
		return tabella;
	}
	
//	public static PdfPTable creaTabellaTotale() throws DocumentException {
//		
//		PdfPTable tabella = new PdfPTable(6);
//		tabella.setWidthPercentage(100);
//		// imposto la dimensione delle colonne
//		try {
//			int[] p = new int[6];
//			p[0] = 2;
//			p[1] = 1;
//			p[2] = 2;
//			p[3] = 2;
//			p[4] = 1;
//			p[5] = 2;
//			tabella.setWidths(p);
//		} catch (DocumentException e) {
//			logger.error("Errore nell'impostazione delle dimensioni delle colonne della tabella del prodotto: "+e.getStackTrace());
//			e.printStackTrace();
//			throw new DocumentException("Errore nell'impostazione delle dimensioni delle colonne della tabella del prodotto.");
//		}
//		
//		PdfPCell c1 = new PdfPCell(new Phrase(Constants.FIRMA_PER_ACCETTAZIONE, boldFontDati));
//		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
////		c1.setGrayFill(0.8f);
//		tabella.addCell(c1);
//
//		c1 = new PdfPCell();
//		c1.setBorder(Rectangle.NO_BORDER);
//		c1.setColspan(3);
//		tabella.addCell(c1);
//		
//		c1 = new PdfPCell(new Phrase("", boldFontDati));
//		c1.setRowspan(2);
//		tabella.addCell(c1);
//		
//		Phrase phraseEticTotaleIvaEsclusaProdotto = new Phrase(Constants.TOTALE_IVA_ESCLUSA, boldFontEtichetteTabProdotto );
//		PdfPCell cella = new PdfPCell(phraseEticTotaleIvaEsclusaProdotto);
//		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		tabella.addCell(cella);
//		Phrase phraseEticIVAProdotto = new Phrase(Constants.IVA, boldFontEtichetteTabProdotto );
//		cella = new PdfPCell(phraseEticIVAProdotto);
//		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		tabella.addCell(cella);
//		Phrase phraseEticTotaleProdotto = new Phrase(Constants.TOTALE, boldFontEtichetteTabProdotto );
//		cella = new PdfPCell(phraseEticTotaleProdotto);
//		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		tabella.addCell(cella);
//		
////		DecimalFormat df2 = new DecimalFormat(".##");
//		
////		double iva = Double.parseDouble(prop.getProperty("iva"));
//		
//		double totalePerStampa = calcolaTotalePerStampa();
////		double totNoIva = totalePerStampa*100/(100 + iva);
////		String totaleNoIva = "Ä " + df2.format(totNoIva);
//		String totaleNoIva = "Ä " + totalePerStampa;
//		Phrase phraseTotaleNoIva = new Phrase(totaleNoIva, fontRighe );
//		cella = new PdfPCell(phraseTotaleNoIva);
//		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		tabella.addCell(cella);
//		
//		Phrase phraseIVA = new Phrase(iva + " %", fontRighe );
//		cella = new PdfPCell(phraseIVA);
//		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		tabella.addCell(cella);
//		
//		double totaleIvaPerStampa = calcolaTotaleIvaPerStampa();
//		String totaleIva = "Ä " + totaleIvaPerStampa;
////		double totaleIva = totale * ((100 + iva) / 100);
////		totaleIva = Util.round(totaleIva, 2);
////		String totaleIvaStr = "Ä " + totaleIva;
//		Phrase phraseTotaleIVA = new Phrase(totaleIva, fontRighe );
//		cella = new PdfPCell(phraseTotaleIVA);
//		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		tabella.addCell(cella);
//		
//		return tabella;
//	}
	
	public static PdfPTable creaTabellaTotale() throws DocumentException {
		
		PdfPTable tabella = new PdfPTable(6);
		tabella.setWidthPercentage(100);
		// imposto la dimensione delle colonne
		try {
			int[] p = new int[6];
			p[0] = 3;
			p[1] = 1;
			p[2] = 2;
			p[3] = 3;
			p[4] = 1;
			p[5] = 2;
			tabella.setWidths(p);
		} catch (DocumentException e) {
			logger.error("Errore nell'impostazione delle dimensioni delle colonne della tabella del prodotto: "+e.getStackTrace());
			e.printStackTrace();
			throw new DocumentException("Errore nell'impostazione delle dimensioni delle colonne della tabella del prodotto.");
		}
		
		PdfPCell c1 = new PdfPCell(new Phrase(Constants.ETICHETTA_PRODOTTI, boldFontDati));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setColspan(3);
		tabella.addCell(c1);
		
		PdfPCell c2 = new PdfPCell(new Phrase(Constants.ETICHETTA_SERVIZI, boldFontDati));
		c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		c2.setColspan(3);
		tabella.addCell(c2);

//		c1 = new PdfPCell();
//		c1.setBorder(Rectangle.NO_BORDER);
//		c1.setColspan(3);
//		tabella.addCell(c1);
		
//		c1 = new PdfPCell(new Phrase("", boldFontDati));
//		c1.setRowspan(2);
//		tabella.addCell(c1);
		
		// prodotti
		Phrase phraseEticTotaleIvaEsclusaProdotti = new Phrase(Constants.TOTALE_IVA_ESCLUSA, boldFontEtichetteTabProdotto );
		PdfPCell cella = new PdfPCell(phraseEticTotaleIvaEsclusaProdotti);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		Phrase phraseEticIVAProdotti = new Phrase(Constants.IVA, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticIVAProdotti);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		Phrase phraseEticTotaleProdotti = new Phrase(Constants.TOTALE, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticTotaleProdotti);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
		// servizi
		Phrase phraseEticTotaleIvaEsclusaServizi = new Phrase(Constants.TOTALE_IVA_ESCLUSA, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticTotaleIvaEsclusaServizi);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		Phrase phraseEticIVAServizi = new Phrase(Constants.IVA, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticIVAServizi);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		Phrase phraseEticTotaleServizi = new Phrase(Constants.TOTALE, boldFontEtichetteTabProdotto );
		cella = new PdfPCell(phraseEticTotaleServizi);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
		// ------ prodotti
		double totaleProdottiPerStampa = calcolaTotalePerStampa();
		String totaleProdottiNoIva = "Ä " + totaleProdottiPerStampa;
		Phrase phraseTotaleProdottiNoIva = new Phrase(totaleProdottiNoIva, fontRighe );
		cella = new PdfPCell(phraseTotaleProdottiNoIva);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
		Phrase phraseProdottiIVA = new Phrase(iva + " %", fontRighe );
		cella = new PdfPCell(phraseProdottiIVA);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
		double totaleProdottiIvaPerStampa = calcolaTotaleIvaPerStampa();
		String totaleProdottiIva = "Ä " + totaleProdottiIvaPerStampa;
		Phrase phraseTotaleProdottiIVA = new Phrase(totaleProdottiIva, fontRighe );
		cella = new PdfPCell(phraseTotaleProdottiIVA);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
		// ------ servizi
		double totaleServiziPerStampa = totaleServizi;
		String totaleServiziNoIva = "Ä " + totaleServiziPerStampa;
		Phrase phraseTotaleServiziNoIva = new Phrase(totaleServiziNoIva, fontRighe );
		cella = new PdfPCell(phraseTotaleServiziNoIva);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
		String ivaServizi = Constants.IVA_SERVIZI + " %";
		Phrase phraseServiziIVA = new Phrase(ivaServizi, fontRighe );
		cella = new PdfPCell(phraseServiziIVA);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
		double totaleServiziIvaPerStampa = totaleServiziIva;
		String totaleServiziIva = "Ä " + totaleServiziIvaPerStampa;
		Phrase phraseTotaleServiziIVA = new Phrase(totaleServiziIva, fontRighe );
		cella = new PdfPCell(phraseTotaleServiziIVA);
		cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(cella);
		
//		PdfPCell c4 = new PdfPCell();
//		c1.setBorder(Rectangle.NO_BORDER);
//		c4.setColspan(6);
//		tabella.addCell(c4);
//		
//		PdfPCell c3 = new PdfPCell(new Phrase(Constants.FIRMA_PER_ACCETTAZIONE, boldFontDati));
//		c3.setHorizontalAlignment(Element.ALIGN_CENTER);
//		c3.setColspan(2);
//		tabella.addCell(c3);
		
		return tabella;
	}
	
	private static PdfPTable creaTabellaFirma() throws DocumentException {
		
		PdfPTable tabella = new PdfPTable(4);
		tabella.setWidthPercentage(100);
		// imposto la dimensione delle colonne
		try {
			int[] p = new int[4];
			p[0] = 3;
			p[1] = 3;
			p[2] = 2;
			p[3] = 1;
			tabella.setWidths(p);
		} catch (DocumentException e) {
			logger.error("Errore nell'impostazione delle dimensioni delle colonne della tabella del prodotto: "+e.getStackTrace());
			e.printStackTrace();
			throw new DocumentException("Errore nell'impostazione delle dimensioni delle colonne della tabella del prodotto.");
		}
		
		PdfPCell c1 = new PdfPCell(new Phrase(Constants.FIRMA_PER_ACCETTAZIONE, boldFontDati));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(c1);
		
		c1 = new PdfPCell();
		c1.setBorder(Rectangle.NO_BORDER);
		tabella.addCell(c1);

		PdfPCell c2 = new PdfPCell(new Phrase(Constants.TOTALE.toUpperCase(), boldFontDati));
		c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabella.addCell(c2);
		double totaleIvaPerStampa = calcolaTotaleIvaPerStampa();
		if(copiaCliente) {
			totaleIvaPerStampa = totaleIvaPerStampa + totaleServiziIva;
		}
		String totaleIva = "Ä " + Util.round(totaleIvaPerStampa,2);
		Phrase phraseTotaleServiziIVA = new Phrase(totaleIva, fontRighe );
		c2 = new PdfPCell(phraseTotaleServiziIVA);
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabella.addCell(c2);
		
		for(int i=0; i<4; i++) {
			c1 = new PdfPCell(new Phrase(" "));
			c1.setBorder(Rectangle.NO_BORDER);
			tabella.addCell(c1);
		}

		c1 = new PdfPCell(new Phrase(" "));
		c1.setBorder(Rectangle.BOTTOM);
//		c1.setRowspan(2);
		tabella.addCell(c1);
		
		for(int i=0; i<3; i++) {
			c1 = new PdfPCell(new Phrase(" "));
			c1.setBorder(Rectangle.NO_BORDER);
			tabella.addCell(c1);
		}
//		c1 = new PdfPCell();
//		c1.setColspan(3);
//		c1.setBorder(Rectangle.NO_BORDER);
//		tabella.addCell(c1);
		
		return tabella;
	}
	
	private static double calcolaTotalePerStampa() {
		if(copiaCliente) {
			return Util.round(totaleProdotti, 2);
		}
		double totaleAttivita = 0.0;
		for(int i=0; i<righeTabella.size(); i++) {
			totaleAttivita = totaleAttivita + righeTabella.get(i).getSubTotale();
		}
		totaleAttivita = Util.round(totaleAttivita, 2);
		
		return totaleAttivita;
	}
	
	private static double calcolaTotaleIvaPerStampa() {
		if(copiaCliente) {
			return Util.round(totaleProdottiIva, 2);
		}
		double totaleAttivita = 0.0;
		for(int i=0; i<righeTabella.size(); i++) {
			totaleAttivita = totaleAttivita + righeTabella.get(i).getSubTotale();
		}
		totaleAttivita = totaleAttivita * ((100 + iva) / 100);
		totaleAttivita = Util.round(totaleAttivita, 2);
		
		return totaleAttivita;
	}
	
}