package cadastropessoa;

public class ItemVenda {
    private Integer id;
    private Produto produto;
    private int quantidade;
    private double valorUnitario;

    public ItemVenda(Produto produto, int quantidade, double valorUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.id = 0;
    }

    public ItemVenda(){}

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return "Produto: "        + (this.produto != null ? this.produto.getNome() : "") + "\n" +
               "Quantidade: "     + this.quantidade    + "\n" +
               "Valor unitário: " + this.valorUnitario;
    }
}