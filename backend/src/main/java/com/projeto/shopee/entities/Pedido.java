package com.projeto.shopee.entities;

import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDate;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    private Usuario usuario;

    private Double valorTotal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PedidoItens> pedidoItens;

    private LocalDate dataPedido;

    public Pedido() {
    }

    public Pedido(Long idPedido, Usuario usuario, Double valorTotal, List<PedidoItens> pedidoItens) {
        this.idPedido = idPedido;
        this.usuario = usuario;
        this.valorTotal = valorTotal;
        this.pedidoItens = pedidoItens;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<PedidoItens> getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(List<PedidoItens> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }
}