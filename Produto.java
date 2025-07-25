public class Produto  {
	private int id;
	private String nome;
	private int quantidade;
	private float preco;
	private String status;


	public Produto(String nome, int quantidade, float preco, String status) {
		this.nome = nome;
		this.quantidade = quantidade;
		this.preco = preco;
		this.status = status;
	}

	public Produto() {

	}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getNome(){
		return nome; 
	}

	public void setNome(String nome){
		this.nome = nome;
	}

	public int getQuantidade(){
		return quantidade;
	}

	public void setQuantidade(int quantidade){
		this.quantidade = quantidade;
	}

	public float getPreco(){
		return preco;
	}

	public void setPreco(float preco){
		this.preco = preco;
	}

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status = status;
	}
}
