/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import model.Colecao;
import model.dao.ColecaoDaoJDBC;
import model.dao.DaoFactory;
import start.App;

public class PrincipalController implements Initializable {

    @FXML
    private Button btnIncluir;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnLimpar;
    @FXML
    private TextField txtFiltro;
    @FXML
    private TableView<Colecao> tblJogos;
    @FXML
    private TableColumn<Colecao, String> tblColNome;
    @FXML
    private TableColumn<Colecao, String> tblColGenero;
    @FXML
    private TableColumn<Colecao, String> tblColLancamento;
    @FXML
    private TableColumn<Colecao, String> tblColPlataforma;
    @FXML
    private TableColumn<Colecao, String> tblColDesenvolvedora;
    @FXML
    private TableColumn<Colecao, Boolean> tblColPossui;
    @FXML
    private Label lblNome;
    @FXML
    private Group grupoRadio;

    private Colecao colecaoSelecionada;
    private List<Colecao> listaJogos;
    private ObservableList<Colecao> observableListJogos;
    private String localImagem;
    @FXML
    private ImageView imgView;
    @FXML
    private Button btnEstatistica;
    @FXML
    private Button btnAdicionar;
    @FXML
    private CheckBox chkBxPossuir;
    @FXML
    private Rectangle pnView;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtGenero;
    @FXML
    private TextField txtPlataforma;
    @FXML
    private TextField txtDesenvolvedora;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGravar;
    @FXML
    private TextField txtLancamento;

    private static Colecao jogoSelecionado;
    private final String diretorioImagens = "src/main/resources/imagens";
    private String caminhoImagem;
    private List<Colecao> listaColecao;
    private ObservableList<Colecao> observableListColecao;
    @FXML
    private ImageView imgView1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cursorTratamento();
        carregarJogos("");
        
    }

    private void tblElementoOnAction(MouseEvent event) {
        limparCampos();
        colecaoSelecionada = tblJogos.selectionModelProperty().getValue().getSelectedItem();
        if (colecaoSelecionada != null) {
            txtNome.setText(jogoSelecionado.getNome());
            txtGenero.setText(jogoSelecionado.getGenero());
            txtPlataforma.setText(jogoSelecionado.getPlataforma());
            txtLancamento.setText(jogoSelecionado.getLancamento().toString());
            txtDesenvolvedora.setText(jogoSelecionado.getDesenvolvedora());
            caminhoImagem = colecaoSelecionada.getLocalImagem();
            if (caminhoImagem != null) {
                try {
                    Image image = new Image(new File(diretorioImagens, caminhoImagem).toURI().toString());
                    imgView.setImage(image);
                    pnView.setVisible(false);

                } catch (Exception e) {
                    Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                    alertErro.setTitle("Aviso");
                    alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
                    alertErro.showAndWait();
                }
            }
            if (colecaoSelecionada.isPossuir()) {
                chkBxPossuir.setSelected(true);
            } else {
                chkBxPossuir.setSelected(false);
            }

        }
    }

    @FXML
    private void tblOnAction(MouseEvent event) {
        colecaoSelecionada = tblJogos.selectionModelProperty().getValue().getSelectedItem();
        lblNome.setText(colecaoSelecionada.getNome());
        txtNome.setText(colecaoSelecionada.getNome());
        txtLancamento.setText(colecaoSelecionada.getLancamento());
        txtDesenvolvedora.setText(colecaoSelecionada.getDesenvolvedora());
        txtGenero.setText(colecaoSelecionada.getGenero());
        chkBxPossuir.setSelected(colecaoSelecionada.isPossuir());
        txtPlataforma.setText(colecaoSelecionada.getPlataforma());
        caminhoImagem = colecaoSelecionada.getLocalImagem();
        if (caminhoImagem != null) {
            Image image = new Image(new File(diretorioImagens, caminhoImagem).toURI().toString());
            imgView.setImage(image);

        }

    }
    
    

    @FXML
    private void btnIncluirOnAction(ActionEvent event) throws IOException {
        limparCampos();
        lblNome.setText("");
        colecaoSelecionada = null;
        carregarJogos(txtFiltro.getText());
    }

    @FXML
    private void btnExcluirOnAction(ActionEvent event) throws Exception {
        Colecao jogoSelecionado = tblJogos.getSelectionModel().getSelectedItem();
        ColecaoDaoJDBC dao = DaoFactory.novoColecaoDAO();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aviso");
        alert.setContentText("Confirma exclusão de " + jogoSelecionado.getNome() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                dao.excluir(jogoSelecionado);
                limparCampos();
            } catch (Exception e) {
                String mensagem = "Ocorreu um erro: " + e.getMessage();
                Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                alertErro.setTitle("Aviso");
                alertErro.setContentText(mensagem);
                alertErro.showAndWait();
            }
        }
        carregarJogos("");
    }

    @FXML
    private void btnFiltrarOnAction(ActionEvent event) {
        carregarJogos(txtFiltro.getText());
    }

    @FXML
    private void btnLimparOnAction(ActionEvent event) {
        txtFiltro.clear();
        limparCampos();
        lblNome.setText("");
        carregarJogos("");
    }

    public void carregarJogos(String param) {
        tblColNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tblColPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        tblColDesenvolvedora.setCellValueFactory(new PropertyValueFactory<>("desenvolvedora"));
        tblColPossui.setCellValueFactory(new PropertyValueFactory<>("possuir"));
        tblColLancamento.setCellValueFactory(new PropertyValueFactory<>("lancamento"));

        try {
            ColecaoDaoJDBC dao = DaoFactory.novoColecaoDAO();
            listaColecao = dao.listar(param);
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
            alertErro.showAndWait();
        }

        observableListColecao = FXCollections.observableArrayList(listaColecao);
        tblJogos.setItems(observableListColecao);
    }

    @FXML
    private void btnAdicionarOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        java.io.File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {

                File diretorioImagensFile = new File(diretorioImagens);
                if (!diretorioImagensFile.exists()) {
                    diretorioImagensFile.mkdirs();
                }

                Path destino = Paths.get(diretorioImagens + File.separator + file.getName());
                Files.copy(file.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                caminhoImagem = file.getName();
                Image image = new Image(new File(diretorioImagens, file.getName()).toURI().toString());
                if (image != null) {
                    imgView.setImage(image);
                    pnView.setVisible(false);
                }
            } catch (IOException e) {
                Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                alertErro.setTitle("Aviso");
                alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
                alertErro.showAndWait();
            }
        }
    }

    private void limparCampos() {
        txtNome.clear();
        chkBxPossuir.setSelected(false);
        txtGenero.clear();
        txtPlataforma.clear();
        txtDesenvolvedora.clear();
        txtLancamento.clear();
        imgView.setImage(null);
        caminhoImagem = null;
        pnView.setVisible(true);
        lblNome.setText("");
    }

    @FXML
    private void btnCancelarOnAction(ActionEvent event) {
        limparCampos();
        colecaoSelecionada = null;
        carregarJogos(txtFiltro.getText());
    }

    @FXML
    private void btnEstatisticaOnAction(ActionEvent event) {
        EstatisticaController.setListaColecao(listaColecao);

        try {
            App.setRoot("Estatistica");
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
            alertErro.showAndWait();
        }
    }

    @FXML
    private void btnGravarOnAction(ActionEvent event) {
        Colecao colecao = new Colecao();
        colecao.setNome(txtNome.getText());
        colecao.setGenero(txtGenero.getText());
        colecao.setPlataforma(txtPlataforma.getText());
        colecao.setLancamento(txtLancamento.getText());
        colecao.setDesenvolvedora(txtDesenvolvedora.getText());
        colecao.setLocalImagem(caminhoImagem);
        if (chkBxPossuir.isSelected()) {
            colecao.setPossuir(true);
        } else {
            colecao.setPossuir(false);
        }

        try {
            ColecaoDaoJDBC dao = DaoFactory.novoColecaoDAO();

            if (colecaoSelecionada == null) {
                dao.incluir(colecao);
            } else {
                colecao.setId(colecaoSelecionada.getId());
                dao.editar(colecao);
                colecaoSelecionada = null;
            }

            limparCampos();
            carregarJogos(txtFiltro.getText());
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
            alertErro.showAndWait();
        }
    }
    

    private void cursorTratamento() {
        txtNome.setTooltip(new Tooltip("Insira o nome do jogo para registro"));
        tblColGenero.setCellValueFactory(new PropertyValueFactory<>("Insira o genero do jogo"));
        tblColLancamento.setCellValueFactory(new PropertyValueFactory<>("Insira o ano de lançamento do jogo"));
        tblColPlataforma.setCellValueFactory(new PropertyValueFactory<>("Insira a plataforma em que você joga o jogo"));
        tblColDesenvolvedora.setCellValueFactory(new PropertyValueFactory<>("Insira a desenvolvedora do jogo"));
        btnAdicionar.setTooltip(new Tooltip("Selecione a imagem desejada para o jogo"));
        chkBxPossuir.setTooltip(new Tooltip("marque caso ja possua o jogo a ser registrada"));
    }
}
