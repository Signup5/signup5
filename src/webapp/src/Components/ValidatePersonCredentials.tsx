import { useQuery } from "@apollo/react-hooks";
import { gql } from "apollo-boost";
import React, { FC } from "react";
import { useDispatch } from "react-redux";
import { RootDispatcher } from "../Store/Reducers/rootReducer";
import { Person } from "../Types";

interface Props {
  Email: string;
}

const ValidatePersonCredentials: FC<Props> = props => {
  const dispatch = useDispatch();
  const rootDispatcher = new RootDispatcher(dispatch);

  const PERSONS_QUERY = gql`
    {
        getPersonByEmail(email: "${props.Email}") {
        id
        first_name
        last_name
        email
        }
    }
    `;

  const { loading, error, data } = useQuery(PERSONS_QUERY);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Email and/or password did not match!</p>;

  const person: Person = data.getPersonByEmail;

  rootDispatcher.updatePerson(person);

  return <div>{person.email}</div>;
};
export default ValidatePersonCredentials;
