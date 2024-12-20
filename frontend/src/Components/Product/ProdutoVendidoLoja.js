import React from 'react';
import PropTypes from 'prop-types';
import './ProdutoLoja.css';

const ProdutoVendidoLoja = ({ item, onEdit, pageStyle }) => {
    const formatarPreco = (preco) => {
        return new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(preco);
    };

    const obterStatus = (status) => {
        return status === 'Vendido' ? 'Vendido' : 'Indisponível';
    };

    return (
        <div className={`produto-item vendido ${pageStyle}`}>
            <h3>{item.nomeItem}</h3> {/* Alterado de item.nome para item.nomeItem */}
            <p className="preco">Preço: {formatarPreco(item.valor)}</p> {/* Alterado de item.preco para item.valor */}
            <p className="quantidade">Quantidade: {item.quantidade}</p>
            <p className="valor-total">Valor Total: {formatarPreco(item.valorTotal)}</p>
            <div className="produto-info">
                <p className="status">Status: {obterStatus(item.status)}</p> {/* Alterado de item.statusId para item.status */}
            </div>
            <div className="produto-acoes">
                <button type="button" onClick={() => onEdit(item)}>
                    Editar
                </button>
            </div>
        </div>
    );
};

ProdutoVendidoLoja.propTypes = {
    item: PropTypes.object.isRequired, // Alterado de produto para item
    onEdit: PropTypes.func.isRequired,
    pageStyle: PropTypes.string
};

export default ProdutoVendidoLoja;