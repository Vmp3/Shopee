import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import Home from './Pages/HTML/Home';
import Register from './Pages/HTML/Register';
import Login from './Pages/HTML/Login';
import CreateLoja from './Pages/HTML/CreateLoja';
import MinhaLoja from './Pages/HTML/MinhaLoja';
import CriarProduto from './Pages/HTML/CriarProduto';
import Carrinho from './Pages/HTML/Carrinho';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/CreateLoja" element={<CreateLoja />} />
          <Route path="/MinhaLoja" element={<MinhaLoja />} />
          <Route path="/criar-produto" element={<CriarProduto />} />
          <Route path="/carrinho" element={<Carrinho />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
