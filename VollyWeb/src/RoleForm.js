import React, { useState } from 'react';
import axios from 'axios';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css'; // Importez le fichier CSS Bootstrap

const RoleForm = () => {
  const [formData, setFormData] = useState({ nom: '', description: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8090/api/v1/roles', formData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 200) {
        // Réinitialiser le formulaire après l'envoi des données
        setFormData({ nom: '', description: '' });
        console.log('Rôle ajouté avec succès !');
      } else {
        console.error('Erreur lors de l\'ajout du rôle');
      }
    } catch (error) {
      console.error('Erreur lors de l\'ajout du rôle :', error);
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
            <h2>Ajouter un rôle</h2>
          </div>
          <Form onSubmit={handleSubmit} className="p-4 border rounded">
            <Form.Group controlId="name">
              <Form.Label>Nom</Form.Label>
              <Form.Control
                type="text"
                placeholder="Entrez le nom du rôle"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
              />
            </Form.Group>
            <Form.Group controlId="description">
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                placeholder="Entrez la description du rôle"
                name="description"
                value={formData.description}
                onChange={handleInputChange}
              />
            </Form.Group>
            <br />
            <Button variant="primary" type="submit">
              Ajouter le rôle
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default RoleForm;
