import React from 'react';
import LoginForm from './Components/LoginForm'
import './App.css';
import ApolloClient from "apollo-boost";
import gql from "graphql-tag";


function App() {
  return (    
    <div className="App">
        <LoginForm />
    </div>
  );
}

export default App;
