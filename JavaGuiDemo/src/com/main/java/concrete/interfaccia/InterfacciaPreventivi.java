package com.main.java.concrete.interfaccia;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.main.java.concrete.bean.Prodotto;
import com.main.java.concrete.bean.RigaModel;
import com.main.java.concrete.bean.Servizio;
import com.main.java.concrete.stampa.ScrittorePDF;
import com.main.java.concrete.utility.Constants;
import com.main.java.concrete.utility.ConvertitoreModel;
import com.main.java.concrete.utility.Util;
import java.awt.Color;
import javax.swing.SwingConstants;

public class InterfacciaPreventivi {
	
	//TODO in questo video viene illustrato come fare l'inserimento, la cancellazione e la modifica di una riga in una tabella JTable: https://www.youtube.com/watch?v=22MBsRYuM4Q
	
	private JFrame frame;
	
	private JLabel lblDimensioni;
	private JLabel lblColoreManiglia;
	
	private JTextField textFieldLarghezza;
	private JTextField textFieldAltezza;
	private JTextField textFieldAltezzaManiglia;
	private JTextField textFieldQuantita;
	private JTextField textFieldSconto;
	private JTable tabella;
	
	private JComboBox comboBoxProdotto;
	private JComboBox comboBoxColore;
	private JComboBox comboBoxColoreManiglia;
	private JComboBox comboBoxSensoApertura;
	
	private JButton btnAggiungiElemento;
	private JButton btnEliminaElemento;
	
	private Vector<Prodotto> listaProdotti;
	private Vector<Servizio> listaServizi;
	
	private Logger logger;
	private JButton btnModificaElemento;
	private JTextField textFieldTotaleProdotti;
	private JLabel lblTotaleProdotti;
	private JTextField textFieldTotaleProdottiIVA;
	private JLabel lblTotaleProdottiIVA;
	private JTextField textFieldIVA;
	private JLabel lblIVAProdotti;
	private JLabel lblPercIVA;
	private JButton btnStampaCopiaCliente;
	private JButton btnStampaCopiaAttivita;
	private JLabel lblDatiCliente;
	private JLabel lblNome;
	private JLabel lblCitta;
	private JTextField textFieldNome;
	private JTextField textFieldCitta;
	private JLabel lblProvincia;
	private JTextField textFieldProvincia;
	private JLabel lblIndirizzo;
	private JTextField textFieldIndirizzo;
	private JLabel lblPartitaIva;
	private JLabel lblTelefono;
	private JLabel lblMail;
	private JTextField textFieldPartitaIva;
	private JTextField textFieldTelefono;
	private JTextField textFieldMail;
	private JSeparator separator_1;
	private JLabel lblServizi;
	private JLabel lblSopralluogo;
	private JLabel lblTrasporto;
	private JLabel lblMontaggio;
	private JLabel lblDislocazionePiano;

	private int indiceServizio;
	private JComboBox[] comboBoxServizi;
	private boolean servizioAttivato[];
	
	private JComboBox comboBoxSopralluogo;
	private JComboBox comboBoxTrasporto;
	private JComboBox comboBoxDislocazionePiano;
	private JComboBox comboBoxMontaggio;
	private JLabel lblCap;
	private JTextField textFieldCap;
	
	private boolean sopralluogoSelezionato;
	private boolean trasportoSelezionato;
	private boolean dislocazioneSelezionato;
	private boolean montaggioSelezionato;

	private double costoSopralluogo = 0.0;
	private double costoTrasporto = 0.0;
	private double costoDislocazione = 0.0;
	private double costoMontaggio = 0.0;
	
	private String[] messaggioServizi; // messaggio che poi deve essere riportato nella stampa del PDF, in quanto indica se il prezzo non è completo (come ad esempio nel caso di un sopralluogo oltre i 60km)
	
	private double utileProdottiAttivita;
	private double utileServiziAttivita;
	
	private double iva;
	private JTextField textFieldTotaleServizi;
	private JTextField textFieldTotaleServiziIVA;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					InterfacciaPreventivi window = new InterfacciaPreventivi();
//					window.rendiFrameVisibile(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	public InterfacciaPreventivi() {
//		initialize();
//	}

	/**
	 * Create the application.
	 */
	public InterfacciaPreventivi(Logger logger, Vector<Prodotto> listaProdotti, Vector<Servizio> listaServizi, ImageIcon img, String titolo, double utileProdottiAttivita, double utileServiziAttivita) {
		this.logger = logger;
		this.listaProdotti = listaProdotti;
		this.listaServizi = listaServizi;
		this.messaggioServizi = new String[3];
		this.messaggioServizi[0] = ""; // sopralluogo
		this.messaggioServizi[1] = ""; // trasporto
		this.messaggioServizi[2] = ""; // dislocazione

		this.costoSopralluogo = 0.0;
		this.costoTrasporto = 0.0;
		this.costoDislocazione = 0.0;
		
		this.utileProdottiAttivita = utileProdottiAttivita;
		this.utileServiziAttivita = utileServiziAttivita;
		
		this.iva = 22.0;
		
		initialize(img, titolo);
	}
	
	public void rendiFrameVisibile(boolean visible){
		frame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ImageIcon img, String titolo) {
		logger.info("Inizializzazione - START");
		
		frame = new JFrame(titolo);
		frame.setBounds(100, 100, 1124, 759);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(img.getImage());
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		JLabel lblSelezionareIlProdotto = new JLabel("Selezionare il prodotto");
		lblSelezionareIlProdotto.setBounds(10, 226, 150, 14);
		frame.getContentPane().add(lblSelezionareIlProdotto);
		
		comboBoxProdotto = new JComboBox();
		comboBoxProdotto.setBounds(209, 226, 199, 20);
		inizializzaListaProdotti(comboBoxProdotto);
		comboBoxProdotto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inizializzaListaColori(comboBoxColore, comboBoxProdotto.getSelectedItem().toString());
				Prodotto prod = getProdottoSelezionato();
				if(prod.getNumDimensioni()<2) {
					lblDimensioni.setText("Indicare la dimensione");
					textFieldAltezza.setEnabled(false);
					textFieldAltezzaManiglia.setEnabled(false);
				} else {
					lblDimensioni.setText("Indicare le dimensioni (l x h)");
					textFieldAltezza.setEnabled(true);
					textFieldAltezzaManiglia.setEnabled(true);
				}
			}
		});
		frame.getContentPane().add(comboBoxProdotto);
		
		lblColoreManiglia = new JLabel();
		lblColoreManiglia.setText("Colore della maniglia");
		lblColoreManiglia.setBounds(10, 263, 189, 14);
		frame.getContentPane().add(lblColoreManiglia);

		comboBoxColoreManiglia = new JComboBox();
		comboBoxColoreManiglia.setBounds(209, 263, 199, 20);
		Prodotto prod = getProdottoSelezionato();
		if(prod.getNumDimensioni()<2) {
			comboBoxColoreManiglia.setEnabled(false);
		} else {
			comboBoxColoreManiglia.setEnabled(true);
		}
		inizializzaListaColoriManiglia(comboBoxColoreManiglia);
		frame.getContentPane().add(comboBoxColoreManiglia);
		
		lblDimensioni = new JLabel();
		prod = getProdottoSelezionato();
		if(prod.getNumDimensioni()<2) {
			lblDimensioni.setText("Indicare la dimensione");
		} else {
			lblDimensioni.setText("Indicare le dimensioni (l x h)");
		}
		lblDimensioni.setBounds(10, 296, 189, 14);
		frame.getContentPane().add(lblDimensioni);
		
		textFieldLarghezza = new JTextField();
		textFieldLarghezza.setBounds(209, 293, 66, 20);
		frame.getContentPane().add(textFieldLarghezza);
		textFieldLarghezza.setColumns(10);
		
		textFieldAltezza = new JTextField();
		textFieldAltezza.setBounds(285, 293, 66, 20);
		if(prod.getNumDimensioni()<2) {
			textFieldAltezza.setEnabled(false);
		} else {
			textFieldAltezza.setEnabled(true);
		}
		frame.getContentPane().add(textFieldAltezza);
		textFieldAltezza.setColumns(10);
		
		JLabel lblMillimetriDimensioni = new JLabel("mm");
		lblMillimetriDimensioni.setBounds(362, 296, 46, 14);
		frame.getContentPane().add(lblMillimetriDimensioni);
		
		JLabel lblColore = new JLabel("Colore del prodotto");
		lblColore.setBounds(10, 332, 150, 14);
		frame.getContentPane().add(lblColore);
		
		comboBoxColore = new JComboBox();
		comboBoxColore.setBounds(209, 332, 199, 20);
		inizializzaListaColori(comboBoxColore, comboBoxProdotto.getSelectedItem().toString());
		frame.getContentPane().add(comboBoxColore);
		
		JLabel lblAltezzaManiglia = new JLabel("Altezza maniglia");
		lblAltezzaManiglia.setBounds(529, 229, 150, 14);
		frame.getContentPane().add(lblAltezzaManiglia);
		
		textFieldAltezzaManiglia = new JTextField();
		textFieldAltezzaManiglia.setBounds(704, 226, 114, 20);
		if(prod.getNumDimensioni()<2) {
			textFieldAltezzaManiglia.setEnabled(false);
		} else {
			textFieldAltezzaManiglia.setEnabled(true);
		}
		frame.getContentPane().add(textFieldAltezzaManiglia);
		textFieldAltezzaManiglia.setColumns(10);
		
		JLabel lblAperturaManiglia = new JLabel("Senso di apertura");
		lblAperturaManiglia.setBounds(529, 263, 150, 14);
		frame.getContentPane().add(lblAperturaManiglia);
		
		comboBoxSensoApertura = new JComboBox();
		comboBoxSensoApertura.setBounds(704, 263, 114, 20);
		if(prod.getNumDimensioni()<2) {
			comboBoxSensoApertura.setEnabled(false);
		} else {
			comboBoxSensoApertura.setEnabled(true);
		}
		inizializzaListaSensoApertura(comboBoxSensoApertura);
		frame.getContentPane().add(comboBoxSensoApertura);
		
		JLabel lblMillimetriAltezzaManiglia = new JLabel("mm");
		lblMillimetriAltezzaManiglia.setBounds(828, 229, 46, 14);
		frame.getContentPane().add(lblMillimetriAltezzaManiglia);
		
		JLabel lblQuantita = new JLabel("Quantit\u00E0");
		lblQuantita.setBounds(529, 296, 150, 14);
		frame.getContentPane().add(lblQuantita);
		
		textFieldQuantita = new JTextField();
		textFieldQuantita.setBounds(704, 293, 114, 20);
		textFieldQuantita.setText("1");
		frame.getContentPane().add(textFieldQuantita);
		textFieldQuantita.setColumns(10);
		
		JLabel lblSconto = new JLabel("Sconto");
		lblSconto.setBounds(529, 335, 150, 14);
		frame.getContentPane().add(lblSconto);
		
		textFieldSconto = new JTextField();
		textFieldSconto.setBounds(704, 332, 114, 20);
		textFieldSconto.setText("0.0");
		frame.getContentPane().add(textFieldSconto);
		textFieldSconto.setColumns(10);
		
		JLabel lblPercentuale = new JLabel("%");
		lblPercentuale.setBounds(828, 335, 46, 14);
		frame.getContentPane().add(lblPercentuale);
		
		JScrollPane scrollPaneTabella = new JScrollPane();
		scrollPaneTabella.setBounds(33, 388, 1041, 191);
		frame.getContentPane().add(scrollPaneTabella);
		
		//headers for the table
        Vector colonneTabella = new Vector();
        colonneTabella.add("Prodotto");
        colonneTabella.add("Larghezza");
        colonneTabella.add("Altezza");
        colonneTabella.add("Colore");
        colonneTabella.add("Altezza Maniglia");
        colonneTabella.add("Colore Maniglia");
        colonneTabella.add("Senso Apertura");
        colonneTabella.add("Quantità");
        colonneTabella.add("Sconto");
        colonneTabella.add("Prezzo Unitario");
        colonneTabella.add("Sub Totale");
        
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
               //le celle della tabella non sono editabili
               return false;
            }
        };
        model.setColumnIdentifiers(colonneTabella);
        tabella = new JTable();
        tabella.setModel(model);
        tabella.setRowHeight(30);
        
        tabella.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		btnEliminaElemento.setEnabled(true);
        		btnModificaElemento.setEnabled(true);
        		
        		int rigaSelezionata = tabella.getSelectedRow();
        		comboBoxProdotto.setSelectedItem(model.getValueAt(rigaSelezionata, 0));
        		textFieldLarghezza.setText((String) model.getValueAt(rigaSelezionata, 1));
        		textFieldAltezza.setText((String) model.getValueAt(rigaSelezionata, 2));
        		comboBoxColore.setSelectedItem(model.getValueAt(rigaSelezionata, 3));
        		textFieldAltezzaManiglia.setText((String) model.getValueAt(rigaSelezionata, 4));
        		comboBoxColoreManiglia.setSelectedItem(model.getValueAt(rigaSelezionata, 5));
        		comboBoxSensoApertura.setSelectedItem(model.getValueAt(rigaSelezionata, 6));
        		textFieldQuantita.setText((String) model.getValueAt(rigaSelezionata, 7));
        		textFieldSconto.setText((String) model.getValueAt(rigaSelezionata, 8));
        	}
		});
		
		scrollPaneTabella.setViewportView(tabella);
		
		
		btnAggiungiElemento = new JButton("Aggiungi");
		btnAggiungiElemento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnEliminaElemento.setEnabled(false);
        		btnModificaElemento.setEnabled(false);
				
				if(checkValoriInseritiPerInserimentoTabella(textFieldLarghezza, textFieldAltezza, textFieldAltezzaManiglia, textFieldQuantita, textFieldSconto)) {
				
					String prodotto = comboBoxProdotto.getSelectedItem().toString();
					String larghezza = textFieldLarghezza.getText();
					String altezza = textFieldAltezza.getText();
					String colore = comboBoxColore.getSelectedItem().toString();
					String altezzaManiglia = textFieldAltezzaManiglia.getText();
					String coloreManiglia = comboBoxColoreManiglia.getSelectedItem().toString();
					String sensoAperturaManiglia = comboBoxSensoApertura.getSelectedItem().toString();
					String quantita = textFieldQuantita.getText();
					String sconto = textFieldSconto.getText();
					
					Vector riga = aggiungiElemento(prodotto, larghezza, altezza, colore, altezzaManiglia, coloreManiglia, sensoAperturaManiglia, quantita, sconto, comboBoxMontaggio.getSelectedItem().toString());
					
	    			// se il prezzo è 0, vuol dire che è un fuori misura
		    		if((double) riga.lastElement()>0.0) {
		    			model.addRow(riga);
		    		}
			        resettaValori(textFieldLarghezza, textFieldAltezza, textFieldAltezzaManiglia, textFieldQuantita, textFieldSconto);
			        
			        // calcola di nuovo il totale
//			        calcolaTotaleProdotti(model, true);
					double totale = calcolaTotaleProdotti(model);
					textFieldTotaleProdotti.setText(Double.toString(totale));
					double totaleIVA = calcolaTotaleIVA(totale, iva);
					textFieldTotaleProdottiIVA.setText(Double.toString(totaleIVA));
					
					// calcola i servizi in base al montaggio
					String montaggio = comboBoxMontaggio.getSelectedItem().toString();
					// aggiorna il totale
					double totaleServizi = calcolaMontaggioProdotti(model, montaggio, costoMontaggio);
					textFieldTotaleServizi.setText(Double.toString(totaleServizi));
					double totaleServiziIVA = calcolaTotaleIVA(totaleServizi, Constants.IVA_SERVIZI);
					textFieldTotaleServiziIVA.setText(Double.toString(totaleServiziIVA));
					if("NO".equals(comboBoxMontaggio.getSelectedItem().toString())) {
						costoMontaggio = 0;
					}
				} else {
					JOptionPane.showMessageDialog(null, "E' necessario valorizzare tutti i campi prima di inserire una nuova riga", "Attenzione", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAggiungiElemento.setBounds(899, 202, 175, 43);
		frame.getContentPane().add(btnAggiungiElemento);
		
		btnEliminaElemento = new JButton("Elimina");
		btnEliminaElemento.setBounds(899, 307, 175, 43);
		btnEliminaElemento.setEnabled(false);
		btnEliminaElemento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int rigaSelezionata = tabella.getSelectedRow();
		        if(rigaSelezionata>=0) {
		        	model.removeRow(rigaSelezionata);
			        
			        // calcola di nuovo il totale
//		        	calcolaTotaleProdotti(model, true);
					double totale = calcolaTotaleProdotti(model);
					textFieldTotaleProdotti.setText(Double.toString(totale));
					double totaleIVA = calcolaTotaleIVA(totale, iva);
					textFieldTotaleProdottiIVA.setText(Double.toString(totaleIVA));
					
					// calcola i servizi in base al montaggio
					String montaggio = comboBoxMontaggio.getSelectedItem().toString();
					// aggiorna il totale
					double totaleServizi = calcolaMontaggioProdotti(model, montaggio, costoMontaggio);
					textFieldTotaleServizi.setText(Double.toString(totaleServizi));
					double totaleServiziIVA = calcolaTotaleIVA(totaleServizi, Constants.IVA_SERVIZI);
					textFieldTotaleServiziIVA.setText(Double.toString(totaleServiziIVA));
					if("NO".equals(comboBoxMontaggio.getSelectedItem().toString())) {
						costoMontaggio = 0;
					}
		        } else {
		        	logger.warn("Non è stata selezionata alcuna riga");
			        JOptionPane.showMessageDialog(null, "Non è stata selezionata alcuna riga", "Attenzione", JOptionPane.WARNING_MESSAGE);
		        }
			}
		});
		frame.getContentPane().add(btnEliminaElemento);
		
		btnModificaElemento = new JButton("Modifica");
		btnModificaElemento.setEnabled(false);
		btnModificaElemento.setBounds(899, 254, 175, 43);
		btnModificaElemento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int rigaSelezionata = tabella.getSelectedRow();
		        if(rigaSelezionata>=0) {
		        	String colore = comboBoxColore.getSelectedItem().toString();
		        	String dimLarg = textFieldLarghezza.getText();
		        	String dimAlt = textFieldAltezza.getText();
		        	String quantita = textFieldQuantita.getText();
		        	String sconto = textFieldSconto.getText();
		        	
					String coloreManiglia = comboBoxColoreManiglia.getSelectedItem().toString();
					String sensoAperturaManiglia = comboBoxSensoApertura.getSelectedItem().toString();
		    		
		    		double prezzoUnitario = determinaPrezzo(colore, dimLarg, dimAlt, sconto, comboBoxMontaggio.getSelectedItem().toString());
		    		double prezzoRigaSubTotale = calcolaSubTotale(prezzoUnitario, quantita);

	    			// se il prezzo è 0, vuol dire che è un fuori misura
		    		if(prezzoUnitario>0.0) {
			        	model.setValueAt(comboBoxProdotto.getSelectedItem(), rigaSelezionata, 0);
			        	model.setValueAt(dimLarg, rigaSelezionata, 1);
			        	model.setValueAt(dimAlt, rigaSelezionata, 2);
			        	model.setValueAt(colore, rigaSelezionata, 3);
			        	model.setValueAt(textFieldAltezzaManiglia.getText(), rigaSelezionata, 4);
			        	model.setValueAt(coloreManiglia, rigaSelezionata, 5);
			        	model.setValueAt(sensoAperturaManiglia, rigaSelezionata, 6);
			        	model.setValueAt(quantita, rigaSelezionata, 7);
			        	model.setValueAt(sconto, rigaSelezionata, 8);
			        	model.setValueAt(prezzoUnitario, rigaSelezionata, 9);
			        	model.setValueAt(prezzoRigaSubTotale, rigaSelezionata, 10);
		    		}
			        
			        // calcola di nuovo il totale
//			        calcolaTotaleProdotti(model, true);
					double totale = calcolaTotaleProdotti(model);
					textFieldTotaleProdotti.setText(Double.toString(totale));
					double totaleIVA = calcolaTotaleIVA(totale, iva);
					textFieldTotaleProdottiIVA.setText(Double.toString(totaleIVA));
					
					// calcola i servizi in base al montaggio
					String montaggio = comboBoxMontaggio.getSelectedItem().toString();
					// aggiorna il totale
					double totaleServizi = calcolaMontaggioProdotti(model, montaggio, costoMontaggio);
					textFieldTotaleServizi.setText(Double.toString(totaleServizi));
					double totaleServiziIVA = calcolaTotaleIVA(totaleServizi, Constants.IVA_SERVIZI);
					textFieldTotaleServiziIVA.setText(Double.toString(totaleServiziIVA));
					if("NO".equals(comboBoxMontaggio.getSelectedItem().toString())) {
						costoMontaggio = 0;
					}
		        } else {
		        	logger.warn("Non è stata selezionata alcuna riga");
			        JOptionPane.showMessageDialog(null, "Non è stata selezionata alcuna riga", "Attenzione", JOptionPane.WARNING_MESSAGE);
		        }
			}
		});
		frame.getContentPane().add(btnModificaElemento);
		
		lblIVAProdotti = new JLabel("IVA");
		Font font = new Font("Tahoma", Font.BOLD, 11);
		lblIVAProdotti.setFont(font);
		lblIVAProdotti.setBounds(585, 663, 50, 14);
		frame.getContentPane().add(lblIVAProdotti);
		
		textFieldIVA = new JTextField();
		textFieldIVA.setBounds(618, 660, 48, 20);
		textFieldIVA.setText(Double.toString(iva));
		textFieldIVA.setColumns(10);
//		textFieldIVA.addMouseListener(new MouseAdapter() {
//        	public void mouseClicked(MouseEvent e) {
//        		JOptionPane.showMessageDialog(null, "Stai per modificare manualmente il prezzo totale!\nUna volta modificato, non è possibile tornare indietro", "Attenzione", JOptionPane.WARNING_MESSAGE);
//        	}
//		});
		textFieldIVA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldIVA.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "E' necessario definire l'iva", "Attenzione", JOptionPane.WARNING_MESSAGE);
				} else {
					iva = Double.parseDouble(textFieldIVA.getText());
					double totale = Double.parseDouble(textFieldTotaleProdotti.getText());
					double totaleIVA = calcolaTotaleIVA(totale, iva);
					textFieldTotaleProdottiIVA.setText(Double.toString(totaleIVA));
				}
			}
		});
		frame.getContentPane().add(textFieldIVA);

		lblPercIVA = new JLabel("%");
		lblPercIVA.setFont(font);
		lblPercIVA.setBounds(678, 663, 20, 14);
		frame.getContentPane().add(lblPercIVA);
		
		lblTotaleProdotti = new JLabel("Totale");
		lblTotaleProdotti.setFont(font);
		lblTotaleProdotti.setBounds(756, 633, 46, 14);
		frame.getContentPane().add(lblTotaleProdotti);
		
		textFieldTotaleProdotti = new JTextField();
		textFieldTotaleProdotti.setBounds(816, 630, 78, 20);
		textFieldTotaleProdotti.setText("0.0");
		textFieldTotaleProdotti.setColumns(10);
        textFieldTotaleProdotti.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		JOptionPane.showMessageDialog(null, "Stai per modificare manualmente il prezzo totale!\nUna volta modificato, non è possibile tornare indietro", "Attenzione", JOptionPane.WARNING_MESSAGE);
        	}
		});
        textFieldTotaleProdotti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double totale = Double.parseDouble(textFieldTotaleProdotti.getText());
				double totaleIVA = calcolaTotaleIVA(totale, iva);
				textFieldTotaleProdottiIVA.setText(Double.toString(totaleIVA));
			}
		});
		frame.getContentPane().add(textFieldTotaleProdotti);
		
		lblTotaleProdottiIVA = new JLabel("Totale + IVA");
		lblTotaleProdottiIVA.setFont(font);
		lblTotaleProdottiIVA.setBounds(720, 663, 80, 14);
		frame.getContentPane().add(lblTotaleProdottiIVA);
		
		textFieldTotaleProdottiIVA = new JTextField();
		textFieldTotaleProdottiIVA.setBounds(816, 660, 78, 20);
		textFieldTotaleProdottiIVA.setText("0.0");
		textFieldTotaleProdottiIVA.setColumns(10);
		textFieldTotaleProdottiIVA.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		JOptionPane.showMessageDialog(null, "Stai per modificare manualmente il prezzo totale!\nUna volta modificato, non è possibile tornare indietro", "Attenzione", JOptionPane.WARNING_MESSAGE);
        	}
		});
		textFieldTotaleProdottiIVA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double totale = Double.parseDouble(textFieldTotaleProdottiIVA.getText());
//				calcolaTotaleProdotti(model, false);
				totale = calcolaTotaleEscludendoIVA(totale, iva);
				textFieldTotaleProdotti.setText(Double.toString(totale));
			}
		});
		frame.getContentPane().add(textFieldTotaleProdottiIVA);
		
		btnStampaCopiaCliente = new JButton("Copia Cliente");
		btnStampaCopiaCliente.setBounds(20, 620, 199, 58);
		btnStampaCopiaCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ConvertitoreModel convertitore = new ConvertitoreModel(model, listaProdotti);
				Vector<RigaModel> modelVettore = convertitore.fromModeltoVectorCopiaCliente();
				
				double totaleProdotti = Double.parseDouble(textFieldTotaleProdotti.getText());
				double totaleProdottiIva = Double.parseDouble(textFieldTotaleProdottiIVA.getText());
				
				double totaleServizi = Double.parseDouble(textFieldTotaleServizi.getText());
				double totaleServiziIva = Double.parseDouble(textFieldTotaleServiziIVA.getText());
				
				String[] servizi = new String[4];
				Servizio sopralluogo = getServizio("Sopralluogo");
				Servizio trasporto = getServizio("Trasporto");
				Servizio dislocazione = getServizio("Dislocazione su piano");
				servizi[0] = getDescrizioneServizioSelezionato(sopralluogo, comboBoxSopralluogo);
				servizi[1] = getDescrizioneServizioSelezionato(trasporto, comboBoxTrasporto);
				servizi[2] = getDescrizioneServizioSelezionato(dislocazione, comboBoxDislocazionePiano);
				servizi[3] = comboBoxMontaggio.getSelectedItem().toString();

				String[] prezziServizi = new String[4];
				prezziServizi[0] = Double.toString(costoSopralluogo);
				prezziServizi[1] = Double.toString(costoTrasporto);
				prezziServizi[2] = Double.toString(costoDislocazione);
				prezziServizi[3] = "-";
				
				String[] datiCliente = new String[8];
				datiCliente[0] = textFieldNome.getText();
				datiCliente[1] = textFieldIndirizzo.getText();
				datiCliente[2] = textFieldCap.getText();
				datiCliente[3] = textFieldCitta.getText();
				datiCliente[4] = textFieldProvincia.getText();
				datiCliente[5] = textFieldPartitaIva.getText();
				datiCliente[6] = textFieldTelefono.getText();
				datiCliente[7] = textFieldMail.getText();
				
				Prodotto prod = getProdottoSelezionato();
				String coloreManiglia = "";
				String sensoApertura = "";
				if(prod.getNumDimensioni()>=2) {
					coloreManiglia = comboBoxColoreManiglia.getSelectedItem().toString();
					sensoApertura = comboBoxSensoApertura.getSelectedItem().toString();
				}
				
				boolean stampa = true;
				for(int i=0; i<datiCliente.length; i++) {
					if(i==5)
						continue;
					if(datiCliente[i]==null || datiCliente[i].length()==0) {
						stampa = false;
					}
				}
				
				if(stampa && modelVettore.size()>0) {
					ScrittorePDF scrittore = new ScrittorePDF(modelVettore, totaleProdotti, totaleProdottiIva, totaleServizi, totaleServiziIva, iva, servizi, prezziServizi, datiCliente, coloreManiglia, sensoApertura, logger, true);
					try {
						scrittore.creaPreventivi();
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					}
				} else if(!stampa) {
					JOptionPane.showMessageDialog(null, "Per procedere con la stampa è necessario definire tutti i dati del cliente.", "Attenzione", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Non è stato inserito alcun prodotto su cui poter fare un preventivo.", "Attenzione", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnStampaCopiaCliente);

		btnStampaCopiaAttivita = new JButton("Copia Bricofer");
		btnStampaCopiaAttivita.setBounds(264, 620, 199, 58);
		btnStampaCopiaAttivita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ConvertitoreModel convertitore = new ConvertitoreModel(model, listaProdotti, utileProdottiAttivita, utileServiziAttivita);
				Vector<RigaModel> modelVettore = convertitore.fromModeltoVectorCopiaAttivita();
				
				double totaleProdotti = Double.parseDouble(textFieldTotaleProdotti.getText());
				double totaleProdottiIva = Double.parseDouble(textFieldTotaleProdottiIVA.getText());
				
				double totaleServizi = Double.parseDouble(textFieldTotaleServizi.getText());
				double totaleServiziIva = Double.parseDouble(textFieldTotaleServiziIVA.getText());
				
				String[] servizi = new String[4];
				Servizio sopralluogo = getServizio("Sopralluogo");
				Servizio trasporto = getServizio("Trasporto");
				Servizio dislocazione = getServizio("Dislocazione su piano");
				servizi[0] = getDescrizioneServizioSelezionato(sopralluogo, comboBoxSopralluogo);
				servizi[1] = getDescrizioneServizioSelezionato(trasporto, comboBoxTrasporto);
				servizi[2] = getDescrizioneServizioSelezionato(dislocazione, comboBoxDislocazionePiano);
				servizi[3] = comboBoxMontaggio.getSelectedItem().toString();

				String[] prezziServizi = new String[4];
				prezziServizi[0] = Double.toString(costoSopralluogo);
				prezziServizi[1] = Double.toString(costoTrasporto);
				prezziServizi[2] = Double.toString(costoDislocazione);
				prezziServizi[3] = "-";
				
				String[] datiCliente = new String[8];
				datiCliente[0] = textFieldNome.getText();
				datiCliente[1] = textFieldIndirizzo.getText();
				datiCliente[2] = textFieldCap.getText();
				datiCliente[3] = textFieldCitta.getText();
				datiCliente[4] = textFieldProvincia.getText();
				datiCliente[5] = textFieldPartitaIva.getText();
				datiCliente[6] = textFieldTelefono.getText();
				datiCliente[7] = textFieldMail.getText();
				
				boolean stampa = true;
				for(int i=0; i<datiCliente.length; i++) {
					if(i==5)
						continue;
					if(datiCliente[i]==null || datiCliente[i].length()==0) {
						stampa = false;
					}
				}
				
				Prodotto prod = getProdottoSelezionato();
				String coloreManiglia = "";
				String sensoApertura = "";
				if(prod.getNumDimensioni()>=2) {
					coloreManiglia = comboBoxColoreManiglia.getSelectedItem().toString();
					sensoApertura = comboBoxSensoApertura.getSelectedItem().toString();
				}
				
				if(stampa && modelVettore.size()>0) {
//					ScrittorePDF scrittore = new ScrittorePDF(modelVettore, totale, totaleIva, iva, servizi, prezziServizi, datiCliente, coloreManiglia, sensoApertura, logger, false);
					ScrittorePDF scrittore = new ScrittorePDF(modelVettore, totaleProdotti, totaleProdottiIva, totaleServizi, totaleServiziIva, Constants.IVA_CONCRETE_A_ATTIVITA, servizi, prezziServizi, datiCliente, coloreManiglia, sensoApertura, logger, false);
					try {
						scrittore.creaPreventivi();
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					}
				} else if(!stampa) {
					JOptionPane.showMessageDialog(null, "Per procedere con la stampa è necessario definire tutti i dati del cliente.", "Attenzione", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Non è stato inserito alcun prodotto su cui poter fare un preventivo.", "Attenzione", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnStampaCopiaAttivita);
		
		lblDatiCliente = new JLabel("DATI CLIENTE");
		lblDatiCliente.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDatiCliente.setBounds(228, 11, 123, 14);
		frame.getContentPane().add(lblDatiCliente);
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 49, 96, 14);
		frame.getContentPane().add(lblNome);
		
		lblCitta = new JLabel("Citt\u00E0");
		lblCitta.setBounds(10, 74, 96, 14);
		frame.getContentPane().add(lblCitta);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(114, 43, 150, 20);
		frame.getContentPane().add(textFieldNome);
		textFieldNome.setColumns(10);
		
		textFieldCitta = new JTextField();
		textFieldCitta.setBounds(114, 68, 150, 20);
		frame.getContentPane().add(textFieldCitta);
		textFieldCitta.setColumns(10);
		
		lblProvincia = new JLabel("Provincia");
		lblProvincia.setBounds(10, 99, 96, 14);
		frame.getContentPane().add(lblProvincia);
		
		textFieldProvincia = new JTextField();
		textFieldProvincia.setBounds(114, 93, 150, 20);
		frame.getContentPane().add(textFieldProvincia);
		textFieldProvincia.setColumns(10);
		
		lblIndirizzo = new JLabel("Indirizzo");
		lblIndirizzo.setBounds(10, 124, 96, 14);
		frame.getContentPane().add(lblIndirizzo);
		
		textFieldIndirizzo = new JTextField();
		textFieldIndirizzo.setBounds(114, 118, 150, 20);
		frame.getContentPane().add(textFieldIndirizzo);
		textFieldIndirizzo.setColumns(10);
		
		lblCap = new JLabel("CAP");
		lblCap.setBounds(305, 49, 96, 14);
		frame.getContentPane().add(lblCap);
		
		textFieldCap = new JTextField();
		textFieldCap.setBounds(407, 43, 150, 20);
		frame.getContentPane().add(textFieldCap);
		textFieldCap.setColumns(10);
		
		lblPartitaIva = new JLabel("Partita IVA");
		lblPartitaIva.setBounds(305, 74, 96, 14);
		frame.getContentPane().add(lblPartitaIva);
		
		lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(305, 99, 96, 14);
		frame.getContentPane().add(lblTelefono);
		
		lblMail = new JLabel("E-mail");
		lblMail.setBounds(305, 124, 96, 14);
		frame.getContentPane().add(lblMail);
		
		textFieldPartitaIva = new JTextField();
		textFieldPartitaIva.setBounds(407, 68, 150, 20);
		frame.getContentPane().add(textFieldPartitaIva);
		textFieldPartitaIva.setColumns(10);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(407, 93, 150, 20);
		frame.getContentPane().add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		textFieldMail = new JTextField();
		textFieldMail.setBounds(407, 118, 150, 20);
		frame.getContentPane().add(textFieldMail);
		textFieldMail.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(100, 175, 900, 2);
		frame.getContentPane().add(separator);
		
		lblServizi = new JLabel("SERVIZI EXTRA");
		lblServizi.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblServizi.setBounds(828, 12, 123, 14);
		frame.getContentPane().add(lblServizi);
		
		lblSopralluogo = new JLabel("Sopralluogo");
		lblSopralluogo.setBounds(730, 46, 132, 14);
		frame.getContentPane().add(lblSopralluogo);
		
		lblTrasporto = new JLabel("Trasporto");
		lblTrasporto.setBounds(730, 71, 132, 14);
		frame.getContentPane().add(lblTrasporto);
		
		lblMontaggio = new JLabel("Montaggio");
		lblMontaggio.setBounds(730, 121, 132, 14);
		frame.getContentPane().add(lblMontaggio);
		
		lblDislocazionePiano = new JLabel("Dislocazione su piano");
		lblDislocazionePiano.setBounds(730, 96, 132, 14);
		frame.getContentPane().add(lblDislocazionePiano);
		
//		comboBoxServizi = new JComboBox[listaServizi.size()];
//		comboBoxServizi[0] = new JComboBox();
//		comboBoxServizi[0].setBounds(882, 46, 192, 20); // sopralluogo
//		comboBoxServizi[1] = new JComboBox();
//		comboBoxServizi[1].setBounds(882, 71, 192, 20); // trasporto
//		comboBoxServizi[2] = new JComboBox();
//		comboBoxServizi[2].setBounds(882, 96, 192, 20); // dislocazione
//		servizioAttivato = new boolean[listaServizi.size()];
//		for(indiceServizio=0; indiceServizio<comboBoxServizi.length; indiceServizio++) {
//			inizializzaListaServizio(comboBoxServizi[indiceServizio], listaServizi.get(indiceServizio).getNomeServizio());
//			comboBoxServizi[indiceServizio].addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					Servizio serv= getServizioSelezionato("Sopralluogo");
//					for (Map.Entry<String, Double> singoloElementoMappa : serv.getTabellaPrezzi().entrySet()) {
//						if((singoloElementoMappa.getValue()==-1)  && (comboBoxServizi[indiceServizio].getSelectedItem().equals(singoloElementoMappa.getKey()))) {
//							messaggioServizi[0] = "Prezzo mancante del calcolo del sopralluogo";
//							JOptionPane.showMessageDialog(null, "Il prezzo per un sopralluogo "+singoloElementoMappa.getKey()+" è da definire. Contattare Concrete", "Attenzione", JOptionPane.WARNING_MESSAGE);
//						} else {
//							messaggioServizi[0] = "";
//							// aggiorna il totale
//							aggiornaTotaleSopralluogo(model, comboBoxServizi[indiceServizio].getSelectedItem().toString());
//							if(listaServizi.get(indiceServizio).getDescrizioniServizio().get(0).equals(comboBoxServizi[indiceServizio].getSelectedItem().toString())) {
//								servizioAttivato[indiceServizio] = false;
//							} else {
//								servizioAttivato[indiceServizio] = true;
//							}
//						}
//					}
//				}
//			});
//			frame.getContentPane().add(comboBoxServizi[indiceServizio]);
//		}
		
		comboBoxSopralluogo = new JComboBox();
		comboBoxSopralluogo.setBounds(882, 46, 192, 20);
		String nomeServizio = "Sopralluogo";
		inizializzaListaServizio(comboBoxSopralluogo, nomeServizio);
		comboBoxSopralluogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nomeServizio = "Sopralluogo";
				
				Servizio serv= getServizio(nomeServizio);
				boolean effettuaAggiornamento = true;
				for (Map.Entry<String, Double> singoloElementoMappa : serv.getTabellaPrezzi().entrySet()) {
					if((singoloElementoMappa.getValue()==-1)  && (comboBoxSopralluogo.getSelectedItem().equals(singoloElementoMappa.getKey()))) {
						messaggioServizi[1] = "Prezzo mancante del calcolo del "+nomeServizio.toLowerCase();
						JOptionPane.showMessageDialog(null, "Il prezzo per un "+nomeServizio.toLowerCase() + " "+singoloElementoMappa.getKey()+" è da definire. Contattare Concrete", "Attenzione", JOptionPane.WARNING_MESSAGE);
						effettuaAggiornamento = false;
						break;
					}
				}
				if(effettuaAggiornamento) {
					messaggioServizi[1] = "";
					// aggiorna il totale
					Servizio servizio = getServizio(nomeServizio);
					double prezzoServizio = getPrezzoServizioSelezionato(servizio, comboBoxSopralluogo);
					prezzoServizio = prezzoServizio * ((100 + utileServiziAttivita)/100);
					String descrizioneServizio = comboBoxSopralluogo.getSelectedItem().toString();
					String primoElementoServizio = "No " + nomeServizio;
					double totaleServizi = calcolaTotaleServizi(sopralluogoSelezionato, servizio, costoSopralluogo, prezzoServizio, descrizioneServizio, primoElementoServizio);
					textFieldTotaleServizi.setText(Double.toString(totaleServizi));
					double totaleServiziIVA = calcolaTotaleIVA(totaleServizi, Constants.IVA_CONCRETE_A_ATTIVITA);
					textFieldTotaleServiziIVA.setText(Double.toString(totaleServiziIVA));
					if(primoElementoServizio.equals(comboBoxSopralluogo.getSelectedItem().toString())) {
						costoSopralluogo = 0;
						sopralluogoSelezionato = false;
					} else {
						costoSopralluogo = prezzoServizio;
						sopralluogoSelezionato = true;
					}
				}
			}
		});
		frame.getContentPane().add(comboBoxSopralluogo);
		
		comboBoxTrasporto = new JComboBox();
		comboBoxTrasporto.setBounds(882, 71, 192, 20);
		nomeServizio = "Trasporto";
		inizializzaListaServizio(comboBoxTrasporto, nomeServizio);
		comboBoxTrasporto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomeServizio = "Trasporto";
				
				Servizio serv= getServizio(nomeServizio);
				boolean effettuaAggiornamento = true;
				for (Map.Entry<String, Double> singoloElementoMappa : serv.getTabellaPrezzi().entrySet()) {
					if((singoloElementoMappa.getValue()==-1)  && (comboBoxTrasporto.getSelectedItem().equals(singoloElementoMappa.getKey()))) {
						messaggioServizi[1] = "Prezzo mancante del calcolo del "+nomeServizio.toLowerCase();
						JOptionPane.showMessageDialog(null, "Il prezzo per un "+nomeServizio.toLowerCase() + " "+singoloElementoMappa.getKey()+" è da definire. Contattare Concrete", "Attenzione", JOptionPane.WARNING_MESSAGE);
						effettuaAggiornamento = false;
						break;
					}
				}
				if(effettuaAggiornamento) {
					messaggioServizi[1] = "";
					// aggiorna il totale
					Servizio servizio = getServizio(nomeServizio);
					double prezzoServizio = getPrezzoServizioSelezionato(servizio, comboBoxTrasporto);
					prezzoServizio = prezzoServizio * ((100 + utileServiziAttivita)/100);
					String descrizioneServizio = comboBoxTrasporto.getSelectedItem().toString();
					String primoElementoServizio = "No " + nomeServizio;
					double totaleServizi = calcolaTotaleServizi(trasportoSelezionato, servizio, costoTrasporto, prezzoServizio, descrizioneServizio, primoElementoServizio);
					textFieldTotaleServizi.setText(Double.toString(totaleServizi));
					double totaleServiziIVA = calcolaTotaleIVA(totaleServizi, Constants.IVA_CONCRETE_A_ATTIVITA);
					textFieldTotaleServiziIVA.setText(Double.toString(totaleServiziIVA));
					if(primoElementoServizio.equals(comboBoxTrasporto.getSelectedItem().toString())) {
						costoTrasporto = 0;
						trasportoSelezionato = false;
					} else {
						costoTrasporto = prezzoServizio;
						trasportoSelezionato = true;
					}
				}
			}
		});
		frame.getContentPane().add(comboBoxTrasporto);
		
		comboBoxDislocazionePiano = new JComboBox();
		comboBoxDislocazionePiano.setBounds(882, 96, 192, 20);
		nomeServizio = "Dislocazione su piano";
		inizializzaListaServizio(comboBoxDislocazionePiano, nomeServizio);
		comboBoxDislocazionePiano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nomeServizio = "Dislocazione su piano";
				
				Servizio serv= getServizio(nomeServizio);
				boolean effettuaAggiornamento = true;
				for (Map.Entry<String, Double> singoloElementoMappa : serv.getTabellaPrezzi().entrySet()) {
					if((singoloElementoMappa.getValue()==-1)  && (comboBoxDislocazionePiano.getSelectedItem().equals(singoloElementoMappa.getKey()))) {
						messaggioServizi[1] = "Prezzo mancante del calcolo del "+nomeServizio.toLowerCase();
						JOptionPane.showMessageDialog(null, "Il prezzo per un "+nomeServizio.toLowerCase() + " "+singoloElementoMappa.getKey()+" è da definire. Contattare Concrete", "Attenzione", JOptionPane.WARNING_MESSAGE);
						effettuaAggiornamento = false;
						break;
					}
				}
				if(effettuaAggiornamento) {
					messaggioServizi[1] = "";
					// aggiorna il totale
					Servizio servizio = getServizio(nomeServizio);
					double prezzoServizio = getPrezzoServizioSelezionato(servizio, comboBoxDislocazionePiano);
					prezzoServizio = prezzoServizio * ((100 + utileServiziAttivita)/100);
					String descrizioneServizio = comboBoxDislocazionePiano.getSelectedItem().toString();
					String primoElementoServizio = "No " + nomeServizio;
					double totaleServizi = calcolaTotaleServizi(dislocazioneSelezionato, servizio, costoDislocazione, prezzoServizio, descrizioneServizio, primoElementoServizio);
					textFieldTotaleServizi.setText(Double.toString(totaleServizi));
					double totaleServiziIVA = calcolaTotaleIVA(totaleServizi, Constants.IVA_CONCRETE_A_ATTIVITA);
					textFieldTotaleServiziIVA.setText(Double.toString(totaleServiziIVA));
					if(primoElementoServizio.equals(comboBoxDislocazionePiano.getSelectedItem().toString())) {
						costoDislocazione = 0;
						dislocazioneSelezionato = false;
					} else {
						costoDislocazione = prezzoServizio;
						dislocazioneSelezionato = true;
					}
				}
			
			}
		});
		frame.getContentPane().add(comboBoxDislocazionePiano);
		
		comboBoxMontaggio = new JComboBox();
		comboBoxMontaggio.setBounds(882, 121, 192, 20);
		frame.getContentPane().add(comboBoxMontaggio);
		comboBoxMontaggio.addItem("NO");
		comboBoxMontaggio.addItem("SI");
		comboBoxMontaggio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String montaggio = comboBoxMontaggio.getSelectedItem().toString();
				// aggiorna il totale
				double totaleServizi = calcolaMontaggioProdotti(model, montaggio, costoMontaggio);
				textFieldTotaleServizi.setText(Double.toString(totaleServizi));
				double totaleServiziIVA = calcolaTotaleIVA(totaleServizi, Constants.IVA_CONCRETE_A_ATTIVITA);
				textFieldTotaleServiziIVA.setText(Double.toString(totaleServiziIVA));
				if("NO".equals(comboBoxMontaggio.getSelectedItem().toString())) {
					costoMontaggio = 0;
				}
			}
		});
//		comboBoxMontaggio.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				// aggiorna tutti i prezzi della tabella
//				aggiornaPrezzoUnitarioESubTotale(model, comboBoxMontaggio.getSelectedItem().toString());
//				if("SI".equals(comboBoxMontaggio.getSelectedItem().toString())) {
//					montaggioSelezionato = true;
//				} else {
//					montaggioSelezionato = false;
//				}
//
//		        // calcola di nuovo il totale
////		        calcolaTotaleProdotti(model, true);
//				double totale = calcolaTotaleProdotti(model);
//				textFieldTotaleProdotti.setText(Double.toString(totale));
//				textFieldTotaleProdottiIVA.setText(Double.toString(calcolaTotaleIVA(totale, iva)));
//				
//			}
//		});
		
		JLabel lblNewLabel = new JLabel("Totale");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(960, 633, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblTotaleIva = new JLabel("Totale + IVA");
		lblTotaleIva.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotaleIva.setBounds(924, 663, 80, 14);
		frame.getContentPane().add(lblTotaleIva);
		
		textFieldTotaleServizi = new JTextField();
		textFieldTotaleServizi.setText("0.0");
		textFieldTotaleServizi.setEditable(false);
		textFieldTotaleServizi.setBounds(1012, 630, 70, 20);
		frame.getContentPane().add(textFieldTotaleServizi);
		textFieldTotaleServizi.setColumns(10);
		
		textFieldTotaleServiziIVA = new JTextField();
		textFieldTotaleServiziIVA.setText("0.0");
		textFieldTotaleServiziIVA.setEditable(false);
		textFieldTotaleServiziIVA.setBounds(1012, 660, 70, 20);
		frame.getContentPane().add(textFieldTotaleServiziIVA);
		textFieldTotaleServiziIVA.setColumns(10);
		
		JLabel lblProdotti = new JLabel("PRODOTTI");
		lblProdotti.setForeground(Color.GRAY);
		lblProdotti.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblProdotti.setBounds(704, 600, 100, 14);
		frame.getContentPane().add(lblProdotti);
		
		JLabel lblServizi_1 = new JLabel("SERVIZI");
		lblServizi_1.setForeground(Color.GRAY);
		lblServizi_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblServizi_1.setBounds(970, 600, 100, 14);
		frame.getContentPane().add(lblServizi_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(909, 601, 1, 100);
		frame.getContentPane().add(separator_2);
        
	}

	private void inizializzaListaServizio(JComboBox comboBoxServizio, String servizio) {
		logger.info("Inizializzazione lista servizio '"+servizio+"'");
		for(int i=0; i<listaServizi.size(); i++) {
			if(servizio.equals(listaServizi.get(i).getNomeServizio())) {
				Vector<String> descrizioniServizio = listaServizi.get(i).getDescrizioniServizio();
				for(int j=0; j<descrizioniServizio.size(); j++) {
					comboBoxServizio.addItem(descrizioniServizio.get(j));
				}
			}
		}
	}

	private void inizializzaListaProdotti(JComboBox comboBoxProdotti) {
		logger.info("Inizializzazione lista prodotti");
		for(int i=0; i<listaProdotti.size(); i++) {
			comboBoxProdotti.addItem((listaProdotti.get(i)).getNomeProdotto());
		}
	}

	private void inizializzaListaColori(JComboBox comboBoxColori, String prodotto) {
		logger.info("Inizializzazione lista colori del prodotto selezionato: "+prodotto);
		comboBoxColori.removeAllItems();
		
		Prodotto prod = getProdottoSelezionato();
		for(int i=0; i<prod.getColori().size(); i++) {
			comboBoxColori.addItem(prod.getColori().get(i));
		}
	}

	private void inizializzaListaColoriManiglia(JComboBox comboBoxColoriManiglia) {
		logger.info("Inizializzazione lista colori della maniglia");
		comboBoxColoriManiglia.removeAllItems();

		String[] coloriManiglia = Constants.COLORI_MANIGLIA.split(";");
		for(int i=0; i<coloriManiglia.length; i++) {
			comboBoxColoriManiglia.addItem(coloriManiglia[i]);
		}
	}

	private void inizializzaListaSensoApertura(JComboBox comboBoxSensoApertura) {
		logger.info("Inizializzazione lista colori della maniglia");
		comboBoxSensoApertura.removeAllItems();

		String[] sensiApertura = {"Destra","Sinistra"};
		for(int i=0; i<sensiApertura.length; i++) {
			comboBoxSensoApertura.addItem(sensiApertura[i]);
		}
	}
	
	private boolean checkValoriInseritiPerInserimentoTabella(JTextField larghezza, JTextField altezza, JTextField maniglia, JTextField quantita, JTextField sconto) {
		logger.info("Controllo valori inseriti");
		if(larghezza.getText().length()==0 || quantita.getText().length()==0 || sconto.getText().length()==0) {
			logger.warn("Non sono stati inseriti tutti i valori");
			return false;
		}
		// l'altezza va controllata solamente se il prodotto prevede 2 dimensioni
		Prodotto prod = getProdottoSelezionato();
		if(prod.getNumDimensioni()==2 && (altezza.getText().length()==0 || maniglia.getText().length()==0)) {
			logger.warn("Non sono stati inseriti tutti i valori");
			return false;
		}
		return true;
	}
	
	private Vector aggiungiElemento(String prodotto, String dimLarg, String dimAlt, String colore, String altManiglia, String coloreManiglia, String sensoAperturaManiglia, String quantita, String sconto, String montaggio) {
		logger.info("Aggiunta nuova riga");
		Vector riga = new Vector();
		riga.add(prodotto);
		riga.add(dimLarg);
		riga.add(dimAlt);
		riga.add(colore);
		riga.add(altManiglia);
		riga.add(coloreManiglia);
		riga.add(sensoAperturaManiglia);
		riga.add(quantita);
		riga.add(sconto);
		
		double prezzoUnitario = determinaPrezzo(colore, dimLarg, dimAlt, sconto, montaggio);
		
		double prezzoSubTotale = calcolaSubTotale(prezzoUnitario, quantita);
		
		riga.add(prezzoUnitario);
		riga.add(prezzoSubTotale);
		return riga;
	}
	
	private void resettaValori(JTextField larghezza, JTextField altezza, JTextField maniglia, JTextField quantita, JTextField sconto) {
		logger.info("Reset valori interfaccia");
		larghezza.setText("");
		altezza.setText("");
		maniglia.setText("");
		quantita.setText("1");
//		sconto.setText("0.0"); // commentato perché probabilmente se viene applicato uno sconto su alcune finestre, viene applicato su tutte
	}
	
	private double determinaPrezzo(String colore, String dimLarg, String dimAlt, String sconto, String montaggio) {
		
		Prodotto prod = getProdottoSelezionato();
		dimLarg = dimLarg.replace(",", ".");
		dimAlt = dimAlt.replace(",", ".");
		if(dimAlt==null || dimAlt.length()==0) {
			dimAlt = "0.0";
		}
		double prezzoUnitario = 0.0;
		try {
			prezzoUnitario = prod.calcolaPrezzo(colore, Double.parseDouble(dimLarg), Double.parseDouble(dimAlt), utileProdottiAttivita);
			prezzoUnitario = prezzoUnitario * (100 / (100 + Double.parseDouble(sconto)));
//			prezzoUnitario = calcolaPrezzoConMontaggio(prod.getNomeProdotto(), prezzoUnitario, montaggio);
			prezzoUnitario = Util.round(prezzoUnitario, 2);
		} catch(NullPointerException e) {
			logger.error("Errore: non è presente il prezzo per le dimensioni indicate\n"+e.getMessage());
	        JOptionPane.showMessageDialog(null, prod.getNomeProdotto()+" fuori dimensioni. Contattare Concrete", "Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		
		return prezzoUnitario;
		
	}
	
	private Prodotto getProdottoSelezionato() {
		String prodotto = comboBoxProdotto.getSelectedItem().toString();
		Prodotto prod = new Prodotto();
		for(int i=0; i<listaProdotti.size(); i++) {
			if(listaProdotti.get(i).getNomeProdotto().equals(prodotto)) {
				prod = listaProdotti.get(i);
				break;
			}
		}
		return prod;
	}
	
	private Servizio getServizio(String nomeServizio) {
		Servizio serv = new Servizio();
		for(int i=0; i<listaServizi.size(); i++) {
			if(listaServizi.get(i).getNomeServizio().equals(nomeServizio)) {
				serv = listaServizi.get(i);
				break;
			}
		}
		return serv;
	}

	private String getDescrizioneServizioSelezionato(Servizio servizio, JComboBox comboBoxServizio) {
		for (Map.Entry<String, Double> singoloElementoMappa : servizio.getTabellaPrezzi().entrySet()) {
			if(singoloElementoMappa.getKey().equals(comboBoxServizio.getSelectedItem())) {
				return singoloElementoMappa.getKey();
			}
		}
		return null;
	}

	private double getPrezzoServizioSelezionato(Servizio servizio, JComboBox comboBoxServizio) {
		for (Map.Entry<String, Double> singoloElementoMappa : servizio.getTabellaPrezzi().entrySet()) {
			if(singoloElementoMappa.getKey().equals(comboBoxServizio.getSelectedItem())) {
				return singoloElementoMappa.getValue();
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * calcola il subtotale sulla riga, determinato dal prezzo unitario * quantita
	 * @param prezzoUnitario
	 * @param sconto
	 * @return
	 */
	public double calcolaSubTotale(double prezzoUnitario,String quantita) {
		double subTotale = Util.round(prezzoUnitario*Integer.parseInt(quantita), 2);
		return subTotale;
	}
	
	/**
	 * 
	 * aggiorna il prezzo unitario e il subtotale, in base al montaggio se è stato selezionato o meno
	 * @param model
	 * @param montaggio
	 */
	public void aggiornaPrezzoUnitarioESubTotale(DefaultTableModel model, String montaggio) {
		if(!montaggioSelezionato && "SI".equals(montaggio)) {
			for(int i=0; i<model.getRowCount(); i++) {
				for(int j=0; j<listaProdotti.size(); j++) {
					if(model.getValueAt(i, 0).toString().equals(listaProdotti.get(j).getNomeProdotto())) {
						double prezzoUnitario = Double.parseDouble(model.getValueAt(i, 9).toString());
						double costoMontaggioProdotto = listaProdotti.get(j).getCostoMontaggio();
						costoMontaggioProdotto = costoMontaggioProdotto * ((100 + utileServiziAttivita) / 100);
						prezzoUnitario = prezzoUnitario + costoMontaggioProdotto;
						model.setValueAt(prezzoUnitario, i, 9);
						String quantita = model.getValueAt(i, 7).toString();
						model.setValueAt(calcolaSubTotale(prezzoUnitario, quantita), i, 10);
						break;
					}
				}
			}
		} else if(montaggioSelezionato && "NO".equals(montaggio)) {
			for(int i=0; i<model.getRowCount(); i++) {
				for(int j=0; j<listaProdotti.size(); j++) {
					if(model.getValueAt(i, 0).toString().equals(listaProdotti.get(j).getNomeProdotto())) {
						double prezzoUnitario = Double.parseDouble(model.getValueAt(i, 9).toString());
						double costoMontaggioProdotto = listaProdotti.get(j).getCostoMontaggio();
						costoMontaggioProdotto = costoMontaggioProdotto * ((100 + utileServiziAttivita) / 100);
						prezzoUnitario = prezzoUnitario - costoMontaggioProdotto;
						model.setValueAt(prezzoUnitario, i, 9);
						String quantita = model.getValueAt(i, 7).toString();
						model.setValueAt(calcolaSubTotale(prezzoUnitario, quantita), i, 10);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * calcola il prezzo unitario in base al montaggio se è stato selezionato o meno
	 * @param model
	 * @param montaggio
	 */
//	public double calcolaPrezzoConMontaggio(String prodotto, double prezzoUnitario, String montaggio) {
//		if("SI".equals(montaggio)) {
//			for(int j=0; j<listaProdotti.size(); j++) {
//				if(prodotto.equals(listaProdotti.get(j).getNomeProdotto())) {
//					double costoMontaggioProdotto = listaProdotti.get(j).getCostoMontaggio();
//					prezzoUnitario = prezzoUnitario + costoMontaggioProdotto;
//					break;
//				}
//			}
//		}
//		return prezzoUnitario;
//	}
	
	/**
	 * calcola il prezzo totale quando viene aggiunta una nuova riga
	 * @param model
	 * @return
	 */
	public double calcolaTotaleProdotti(DefaultTableModel model) {
		double totale = 0.0;
		for(int i=0; i<model.getDataVector().size(); i++) {
			double prezzoRiga = Double.parseDouble(model.getValueAt(i, model.getColumnCount()-1).toString());
			totale = totale + prezzoRiga;
		}
		
		totale = Util.round(totale, 2);
		
		return totale;
	}
	
	/**
	 * calcola il prezzo del servizio di montaggio quando viene aggiunta una nuova riga
	 * @param model
	 * @return
	 */
	public double calcolaMontaggioProdotti(DefaultTableModel model, String montaggio, double costoMontaggioPrecedente) {
		double totaleServizi = Double.parseDouble(textFieldTotaleServizi.getText());
		totaleServizi = totaleServizi-costoMontaggioPrecedente;
		if("SI".equals(montaggio)) {
			costoMontaggio = 0.0;
			for(int i=0; i<model.getDataVector().size(); i++) {
				String prodotto = model.getValueAt(i, 0).toString();
				for(int j=0; j<listaProdotti.size(); j++) {
					if(prodotto.equals(listaProdotti.get(j).getNomeProdotto())) {
						double costoMontaggioProdotto = listaProdotti.get(j).getCostoMontaggio();
						costoMontaggioProdotto = costoMontaggioProdotto * ((100 + utileServiziAttivita)/100);
						costoMontaggio += costoMontaggioProdotto;
						totaleServizi = totaleServizi + costoMontaggioProdotto;
						break;
					}
				}
			}
		}
		
		totaleServizi = Util.round(totaleServizi, 2);
		
		return totaleServizi;
	}
	
	public double calcolaTotaleIVA(double totale, double iva) {
		double totaleIVA = Util.round(totale * ((100 + iva) / 100), 2);
		return totaleIVA;
	}
	
	/**
	 * Metodo che permette di calcolare il totaleNoIVA a partire dal prezzo totaleIVA.
	 * Viene utilizzato nel caso in cui si modifica il prezzo totaleIVA, pertanto è
	 * necessario ricalcolare il prezzo totaleNoIVA
	 * 
	 * @param totale
	 * @param iva
	 * @return
	 */
	public double calcolaTotaleEscludendoIVA(double totale, double iva) {
		double totaleIVA = Util.round(totale * (100 / (100 + iva)), 2);
		return totaleIVA;
	}

	public double calcolaTotaleServizi(boolean servizioSelezionato, Servizio servizio, double prezzoServizioSelezionatoPrecedente, double prezzoServizioSelezionato, String descrizioneServizioSelezionato, String primoElementoServizio) {
		double totale = Double.parseDouble(textFieldTotaleServizi.getText());
		totale = totale-prezzoServizioSelezionatoPrecedente;
		if(!primoElementoServizio.equals(descrizioneServizioSelezionato)) {
			totale = totale+prezzoServizioSelezionato;
		}
		
		totale = Util.round(totale, 2);
		
		return totale;
	}
}
