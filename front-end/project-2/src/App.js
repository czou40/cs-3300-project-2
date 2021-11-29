import React, { useState } from 'react';
import SockJsClient from 'react-stomp';
import Dashboard from './pages/Dashboard';
import BillSplitter from './pages/BillSplitter';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Signup from './pages/Signup';

function App() {

  return (
    <div>
      <Router>
        <Routes>
          <Route exact path='/' element={<Navigate to={'/login'}/>}/>
          <Route exact path='/login' element={<Login/>}/>
          <Route exact path='/signup' element={<Signup/>}/>
          <Route exact path='/dashboard' element={<Navigate to={'/splitter'}/>}/>
          <Route exact path='/splitter' element={<BillSplitter />}/> 
        </Routes>
      </Router>
    </div>
  );
}

export default App;
