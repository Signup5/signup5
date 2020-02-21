import React from 'react'
import LoginForm from './Components/LoginForm'
import './App.css'
import { ApolloProvider } from "react-apollo"
import { useQuery } from '@apollo/react-hooks'
import ApolloClient from 'apollo-boost'
import AllPersons from './Components/AllPersons'

const client = new ApolloClient({
  uri: "https://signup5-dev.herokuapp.com/graphql"
});


function App() {

  return (    
    <ApolloProvider client={client}>
    <div className="App">

        <LoginForm />
        <AllPersons />
       
    </div>
    </ApolloProvider>
  );
}

export default App;
