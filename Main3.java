import java.sql.Connection;
import java.util.List;


public class Main3{
	public static void main(String[] args){
		try(Connection conexao = ConexaoDB.conectar()) {
			ProdutoDAO produtoDAO = new ProdutoDAO(conexao);

			//Listar todos produtos
			mostrarProdutos(produtoDAO);

			//produtoDAO.excluir(4);

			produtoDAO.excluirTodos();

			mostrarProdutos(produtoDAO);
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