import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './styles.css';

function FiliereList() {
  const [filieres, setFilieres] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8090/api/v1/filieres')
      .then(response => {
        setFilieres(response.data);
      })
      .catch(error => {
        console.error('Error fetching data: ', error);
      });
  }, []);

  // Fonction pour supprimer une filière
  const handleDelete = (id) => {
    axios.delete(`http://localhost:8090/api/v1/filieres/${id}`)
      .then(response => {
        // Mettez à jour la liste des filières après la suppression
        setFilieres(filieres.filter(filiere => filiere.id !== id));
      })
      .catch(error => {
        console.error('Error deleting filiere: ', error);
      });
  };

  return (
    <div className="list-container">
      <h2>Liste des filières :</h2>
      <table className="filiere-table">
        <thead>
          <tr>
            <th>Code</th>
            <th>Libellé</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filieres.map(filiere => (
            <tr key={filiere.id}>
              <td>{filiere.code}</td>
              <td>{filiere.libelle}</td>
              <td>
                <Link to={`/updateFiliere/${filiere.id}`} style={{ textDecoration: 'none' }}>
                  <button className="modify-button">Modifier</button>
                </Link>
                <button onClick={() => handleDelete(filiere.id)} className="delete-button">Supprimer</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="button-container">
        <Link to="/createFiliere" style={{ textDecoration: 'none' }}>
          <button className="create-button">Créer une filière</button>
        </Link>
      </div>
    </div>
  );
}

export default FiliereList;
