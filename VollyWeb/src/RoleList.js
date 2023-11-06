import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function RoleList() {
  const [roles, setRoles] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8090/api/v1/roles')
      .then(response => {
        setRoles(response.data);
      })
      .catch(error => {
        console.error('Error fetching data: ', error);
      });
  }, []);

  // Fonction pour supprimer un rôle
  const handleDelete = (id) => {
    axios.delete(`http://localhost:8090/api/v1/roles/${id}`)
      .then(response => {
        // Mettez à jour la liste des rôles après la suppression
        setRoles(roles.filter(role => role.id !== id));
      })
      .catch(error => {
        console.error('Error deleting role: ', error);
      });
  };

  return (
    <div className="list-container">
      <h2>Liste des rôles :</h2>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Nom</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {roles.map(role => (
            <tr key={role.id}>
              <td>{role.name}</td>
              <td>
                <Link to={`/updateRole/${role.id}`} style={{ textDecoration: 'none' }}>
                  <button className="btn btn-primary">Modifier</button>
                </Link>
                <button onClick={() => handleDelete(role.id)} className="btn btn-danger">Supprimer</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="button-container">
        <Link to="/createRole" style={{ textDecoration: 'none' }}>
          <button className="btn btn-success">Créer un rôle</button>
        </Link>
      </div>
    </div>
  );
}

export default RoleList;
