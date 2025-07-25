import java.sql.Connection;
import java.util.List;


public class Main{
	public static void main(String[] args){
		try(Connection conexao = ConexaoDB.conectar()) {
			ProdutoDAO produtoDAO = new ProdutoDAO(conexao);

			//Listar todos produtos
			mostrarProdutos(produtoDAO);

			//Exemplo inserção de produtos
			Produto novoProduto1 = new Produto("Lapis", 50, 1.50, "Em estoque");
			Produto novoProduto2 = new Produto("SmartPhone", 10, 1499.99, "Baixo estoque");
			Produto novoProduto3 = new Produto("Tablet", 1, 899.99, "Baixo estoque");

			produtoDAO.inserir(novoProduto1);
			produtoDAO.inserir(novoProduto2);
			produtoDAO.inserir(novoProduto3);

			//Lista todos produtos novamente, para ver se foram incluidos
			mostrarProdutos(produtoDAO);

			//Consulta por ID
			Produto produtoConsultado = produtoDAO.consultarPorId(1);
			if (produtoConsultado != null) {
				System.out.println("Produto encontrado: " + produtoConsultado.getNome());
			} else {
				System.out.println("Produto não encontrado.");
			}
		} catch (Exception e) {
			System.err.println("Erro geral: " + e.getMessage());
		}
	}
	//Metodo para listar produtos	
	private static void mostrarProdutos(ProdutoDAO produtoDAO) {
	 	List<Produto> todosProdutos = produtoDAO.listarTodos();
	 	if (todosProdutos.isEmpty()) {
	 		System.out.println("Nenhum produto encontrado.");
	 	} else {
	 		System.out.println("Lista de Produtos: ");
	 		for (Produto p : todosProdutos) {
	 			System.out.println(p.getId() + ": " + p.getNome() + " - " + p.getPreco());
	 		}
	 	}

	}
	

}