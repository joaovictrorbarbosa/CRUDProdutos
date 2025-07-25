import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class ProdutoGUI extends Application{
	private ProdutoDAO produtoDAO;
	private ObservableList<Produto> produtos;
	private TableView<Produto> tableView;
	private TextField nomeInput, quantidadeInput, precoInput;
	private ComboBox<String> statusComboBox;
	private Connection conexaoDB;


	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage palco) {
		conexaoDB= ConexaoDB.conectar();
		produtoDAO = new ProdutoDAO(conexaoDB);
		produtos = FXCollections.observableArrayList(produtoDAO.listarTodos()); // Carrega todos produtos do DB

		palco.setTitle("Gerenciamento de Estoque de Produtos");

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10,10,10,10));
		vBox.setSpacing(10);

		//Montando a interface Vertical

		HBox nomeProdutoBox = new HBox();
		nomeProdutoBox.setSpacing(10);
		Label nomeLabel = new Label("Produto:");
		nomeInput = new TextField();
		nomeProdutoBox.getChildren().addAll(nomeLabel, nomeInput);

		HBox quantidadeBox = new HBox();
		quantidadeBox.setSpacing(10);
		Label quantidadeLabel = new Label("Quantidade:");
		quantidadeInput = new TextField();
		quantidadeBox.getChildren().addAll(quantidadeLabel, quantidadeInput);

		HBox precoBox = new HBox();
		precoBox.setSpacing(10);
		Label precoLabel = new Label("Preço:");
		precoInput = new TextField();
		precoBox.getChildren().addAll(precoLabel, precoInput);

		HBox statusBox = new HBox();
		statusBox.setSpacing(10);
		Label statusLabel = new Label("Status:");
		statusComboBox = new ComboBox<>();
		statusComboBox.getItems().addAll("Estoque Normal", "Estoque Baixo");
		statusBox.getChildren().addAll(statusLabel, statusComboBox);

		//Botão adicionar Produto
		Button addButton = new Button("Adicionar");
		addButton.setOnAction(e -> {
			String preco = precoInput.getText().replace(',', '.');
			Produto product = new Produto(nomeInput.getText(), 
				Integer.parseInt(quantidadeInput.getText()), 
				Float.parseFloat(preco),
				statusComboBox.getValue());
		produtoDAO.inserir(product);
		produtos.setAll(produtoDAO.listarTodos()); 
		limparCampos();
		});

		//Botão atualizar produto
		Button attButton = new Button("Atualizar");
		attButton.setOnAction(e -> {
			Produto selectedProduto = tableView.getSelectionModel().getSelectedItem();
			if (selectedProduto != null) {
				selectedProduto.setNome(nomeInput.getText());
				selectedProduto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
				String precoatt = precoInput.getText().replace(',','.');
				selectedProduto.setPreco(Float.parseFloat(precoatt));
				selectedProduto.setStatus(statusComboBox.getValue());
				produtoDAO.atualizar(selectedProduto);
				produtos.setAll(produtoDAO.listarTodos());
				limparCampos();
			}
		});

		//Botao excluir produto
		Button excButton = new Button("Excluir");
		excButton.setOnAction(e -> {
			Produto selectedProduto = tableView.getSelectionModel().getSelectedItem();
			if (selectedProduto != null) {
				produtoDAO.excluir(selectedProduto.getId());
				produtos.setAll(produtoDAO.listarTodos());
				limparCampos();
			}
		});

		//Botao excluir Todos
		Button excAllButton = new Button("Limpar");
		excAllButton.setOnAction(e -> limparCampos());

		tableView = new TableView<>();
		tableView.setItems(produtos); //define a lista de produtos na tabela
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); //Ajusta tamanho das colunas
		List<TableColumn<Produto, ?>> columns = List.of(
			criarColuna("ID", "id"),
			criarColuna("Produto", "nome"),
			criarColuna("Quantidade", "quantidade"),
			criarColuna("Preço", "preco"),
			criarColuna("Status", "status")
			);
		tableView.getColumns().addAll(columns);

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				nomeInput.setText(newSelection.getNome());
				quantidadeInput.setText(String.valueOf(newSelection.getQuantidade()));
				precoInput.setText(String.valueOf(newSelection.getPreco()));
				statusComboBox.setValue(newSelection.getStatus());
			}

		});

		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(10);
		buttonBox.getChildren().addAll(addButton, attButton, excButton, excAllButton);

		vBox.getChildren().addAll(nomeProdutoBox, quantidadeBox, precoBox, statusBox, buttonBox, tableView);

		Scene scene = new Scene(vBox, 800, 600);
		scene.getStylesheets().add("styles-produtos.css"); // adiciona folha de estilos
		palco.setScene(scene);
		palco.show();
	
	}

	/**
	  * Metodo stop é automaticamente chamado qndo a aplicação javafx é encerrada.
	*/

	@Override
	public void stop(){
		try {
			conexaoDB.close(); // Fecha conexão com o banco de dados
		} catch( SQLException e) {
			System.err.println("Erro ao fechar conexao: " + e.getMessage());
		}
	}

	/** 
		* Limpa os campos de entrada do formulario
		* é chamado apos adicionar, atualizar ou excluir um produto
	*/

	private void limparCampos(){
		nomeInput.clear();
		quantidadeInput.clear();
		precoInput.clear();
		statusComboBox.setValue(null);
	}

	//Metodo criar colunas.
	private TableColumn<Produto, String> criarColuna(String title, String property) {
		TableColumn<Produto, String> col = new TableColumn<>(title);
		col.setCellValueFactory(new PropertyValueFactory(property)); // define a propriedade da coluna
		return col;
	}
}





