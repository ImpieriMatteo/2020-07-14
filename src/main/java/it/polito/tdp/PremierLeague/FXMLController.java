/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.ElementoClassifica;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Simulatore;
import it.polito.tdp.PremierLeague.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private Simulatore simulatore;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	this.txtResult.clear();
 
    	Team teamScelto = this.cmbSquadra.getValue();
    	if(teamScelto==null) {
    		this.txtResult.appendText("Devi scegliere prima una squadra!!");
    		return;
    	}
    	
    	List<ElementoClassifica> teamMigliori = this.model.getTeamMigliori(teamScelto);
    	List<ElementoClassifica> teamPeggiori = this.model.getTeamPeggiori(teamScelto);
    	
    	Collections.sort(teamMigliori);
    	Collections.sort(teamPeggiori);
    	
    	this.txtResult.appendText("SQUADRE MIGLIORI: \n");
    	for(ElementoClassifica e : teamMigliori)
    		this.txtResult.appendText(e.toString()+"\n");
    	
    	this.txtResult.appendText("\n");
    	
    	this.txtResult.appendText("SQUADRE PEGGIORI: \n");
    	for(ElementoClassifica e : teamPeggiori)
    		this.txtResult.appendText(e.toString()+"\n");
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	String result = this.model.creaGrafo();
    	this.txtResult.appendText(result);
    	
    	this.btnClassifica.setDisable(false);
    	this.btnSimula.setDisable(false);
    	
    	List<Team> allTeams = new ArrayList<>(this.model.getTeams());
    	Collections.sort(allTeams);
    	
    	this.cmbSquadra.getItems().addAll(allTeams);
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer N;
    	Integer X;
    	
    	try {
    		N = Integer.parseInt(this.txtN.getText());
    		X = Integer.parseInt(this.txtX.getText());
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.appendText("Fromato dei dati inseriti (N e/o X) non corretto");
    		return;
    	}
    	
    	this.simulatore = new Simulatore();
    	this.simulatore.init(N, X, model);
    	this.simulatore.simula();
    	
    	this.txtResult.appendText("Numero di reporter medi per partita durante la simulazione: "+this.simulatore.getReporterMedi()+"\n\n");
    	this.txtResult.appendText("Numero di partite critiche (reporter totali minori di X): "+this.simulatore.getNumeroPartiteCritiche()+"\n\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
