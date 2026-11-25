package cadastropessoa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private Integer id;
    private LocalDateTime dataVenda;
    private PessoaFisica cliente;
    private List<ItemVenda> itens;

    public Venda(PessoaFisica cliente, List<ItemVenda> itens) {
        this.cliente = cliente;
        this.itens = itens;
        this.dataVenda = LocalDateTime.now();
        this.id = 0;
    }

    public Venda(){
        this.itens = new ArrayList<>();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public PessoaFisica getCliente() {
        return cliente;
    }

    public void setCliente(PessoaFisica cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "Data: "    + this.dataVenda + "\n" +
               "Cliente: " + (this.cliente != null ? this.cliente.getNome() : "") + "\n" +
               "Itens: "   + (this.itens != null ? this.itens.size() : 0);
    }
}