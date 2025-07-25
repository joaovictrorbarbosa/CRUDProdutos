import java.sql.Connection;
import java.util.List;


public class Main2{
	public static void main(String[] args){
		try(Connection conexao = ConexaoDB.conectar()) {
			ProdutoDAO produtoDAO = new ProdutoDAO(conexao);

			//Listar todos produtos
			mostrarProdutos(produtoDAO);

			//Consulta por ID
			Produto produtoConsultado = produtoDAO.consultarPorId(1);
			if (produtoConsultado != null) {
				produtoConsultado.setNome("Lapiseira");
				System.out.println("Novo nome do produto: " + produtoConsultado.getNome());
				produtoDAO.atualizar(produtoConsultado);


				System.out.println("A base de dados ficou assim:");
				mostrarProdutos(produtoDAO);

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