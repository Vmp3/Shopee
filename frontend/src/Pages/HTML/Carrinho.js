import React, { useState } from 'react';
import axios from 'axios';
import ProdutosList from '../../Components/Carrinho/ProdutosList';
import Header from '../../Components/Menu/Items/Header/Header';
import ResumoCompra from '../../Components/Carrinho/ResumoCompra';
import '../CSS/Carrinho.css';
import { toast } from 'react-toastify';

const Carrinho = () => {
  const [carrinho, setCarrinho] = useState(
    JSON.parse(localStorage.getItem('carrinho'))?.map(produto => ({
      ...produto,
      quantidade: produto.quantidade || 1
    })) || []
  );

  const aumentarQuantidade = (id) => {
    const novoCarrinho = carrinho.map(produto =>
      produto.id === id ? { ...produto, quantidade: produto.quantidade + 1 } : produto
    );
    setCarrinho(novoCarrinho);
    localStorage.setItem('carrinho', JSON.stringify(novoCarrinho));
  };

  const diminuirQuantidade = (id) => {
    const produtoRemovido = carrinho.find(produto => produto.id === id && produto.quantidade === 1);
    const novoCarrinho = carrinho.map(produto =>
      produto.id === id ? { ...produto, quantidade: produto.quantidade - 1 } : produto
    ).filter(produto => produto.quantidade > 0);
    setCarrinho(novoCarrinho);
    localStorage.setItem('carrinho', JSON.stringify(novoCarrinho));
    if (produtoRemovido) {
      toast.info(`O produto ${produtoRemovido.nome} foi removido do carrinho por quantidade zero.`);
    }
  };

  const removerProduto = (id) => {
    const produtoRemovido = carrinho.find(produto => produto.id === id);
    const novoCarrinho = carrinho.filter(produto => produto.id !== id);
    setCarrinho(novoCarrinho);
    localStorage.setItem('carrinho', JSON.stringify(novoCarrinho));
    if (produtoRemovido) {
      toast.info(`O produto ${produtoRemovido.nome} foi removido do carrinho.`);
    }
  };

  const verificarProdutosAtivosEEstoque = async () => {
    try {
      const promises = carrinho.map(produto =>
        axios.get(`${process.env.REACT_APP_API_URL}/produtos/${produto.id}`)
      );
      const responses = await Promise.all(promises);

      const produtosAtivosEEstoque = responses.every(response => {
        const produto = response.data;
        if (produto.statusId !== 1) {
          removerProduto(produto.id);
          toast.warn(`O produto ${produto.nome} está inativo e foi removido do carrinho.`);
          return false;
        }
        if (produto.estoque < carrinho.find(p => p.id === produto.id).quantidade) {
          toast.warn(`O produto ${produto.nome} tem estoque insuficiente.`);
          return false;
        }
        return true;
      });

      return produtosAtivosEEstoque;
    } catch (error) {
      console.error('Erro ao verificar status dos produtos:', error);
      return false;
    }
  };

  const finalizarCompra = async () => {
    if (!localStorage.getItem('token')) {
      toast.error('Você precisa estar logado para finalizar a compra.');
      return;
    }

    if (carrinho.length === 0) {
      toast.warn('O carrinho está vazio. Adicione produtos antes de finalizar a compra.');
      return;
    }

    const produtosAtivosEEstoque = await verificarProdutosAtivosEEstoque();
    if (!produtosAtivosEEstoque) {
      return;
    }

    axios.post(`${process.env.REACT_APP_API_URL}/pedidos/finalizar-compra`, carrinho, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
      .then(response => {
        toast.success(response.data);
        localStorage.removeItem('carrinho');
        setCarrinho([]);
      })
      .catch(error => {
        console.error('Erro ao finalizar compra:', error);
        toast.error('Erro ao finalizar compra.');
      });
  };

  const calcularTotal = () => {
    return carrinho.reduce((total, produto) => total + produto.preco * produto.quantidade, 0).toFixed(2);
  };

  return (
    <>
      <Header searchHidden={true} navbarHidden={true} />
      <div className="carrinho-container carrinho-page">
        <h2 className="carrinho-title">Carrinho</h2>
        <div className="carrinho-content">
          <ProdutosList
            produtos={carrinho}
            aumentarQuantidade={aumentarQuantidade}
            diminuirQuantidade={diminuirQuantidade}
            removerProduto={removerProduto}
          />
          <ResumoCompra produtos={carrinho} total={calcularTotal()} finalizarCompra={finalizarCompra} />
        </div>
      </div>
    </>
  );
};

export default Carrinho;