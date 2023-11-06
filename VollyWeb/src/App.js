import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './Home';
import FiliereList from './FiliereList';
import FiliereUpdate from './FiliereUpdate';
import FiliereForm from './FiliereForm';
import RoleForm from './RoleForm';
import RoleList from './RoleList';
import RoleUpdate from './RoleUpdate';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/filiere" element={<FiliereList />} />
          <Route path="/updateFiliere/:id" element={<FiliereUpdate />} />
          <Route path="/createFiliere" element={<FiliereForm />} />

          
          <Route path="/role" element={<RoleList />} />
          <Route path="/createRole" element={<RoleForm />} />
          <Route path="/updateRole/:id" element={<RoleUpdate />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
