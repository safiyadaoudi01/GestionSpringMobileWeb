import React, { useState } from 'react';
import axios from 'axios';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css'; // Importez le fichier CSS Bootstrap



const FiliereForm = () => {
  const [formData, setFormData] = useState({ code: '', libelle: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8090/api/v1/filieres', formData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 200) {
        // Réinitialiser le formulaire après l'envoi des données
        setFormData({ code: '', libelle: '' });
        console.log('Filiere ajoutée avec succès !');
      } else {
        console.error('Erreur lors de l\'ajout de la filiere');
      }
    } catch (error) {
      console.error('Erreur lors de l\'ajout de la filiere :', error);
    }
  };

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <Container>
      <Row className="justify-content-md-center mt-5">
        <Col md={6}>
          <div className="text-center mb-4">
            <h2>Ajouter une filière</h2>
          </div>
          <Form onSubmit={handleSubmit} className="p-4 border rounded">
            <Form.Group controlId="code">
              <Form.Label>Code</Form.Label>
              <Form.Control
                type="text"
                placeholder="Entrez le code de la filière"
                name="code"
                value={formData.code}
                onChange={handleInputChange}
              />
            </Form.Group>
            <Form.Group controlId="libelle">
              <Form.Label>Libellé</Form.Label>
              <Form.Control
                type="text"
                placeholder="Entrez le libellé de la filière"
                name="libelle"
                value={formData.libelle}
                onChange={handleInputChange}
              />
            </Form.Group>
            <br></br>
            <Button variant="primary" type="submit">
              Ajouter la filière
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default FiliereForm;
