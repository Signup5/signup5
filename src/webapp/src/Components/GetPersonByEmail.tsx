import { useQuery } from "@apollo/react-hooks";
import { gql } from "apollo-boost";
import React, { FC } from "react";

interface Props {
  Email: string;
}

type Person = {
  id?: number;
  first_name?: string;
  last_name?: string;
  email?: string;
};

const GetPersonByEmail: FC<Props> = props => {

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

  const person:Person = data.getPersonByEmail;

  return (
    <div key={person.id}>
      <p>
        {person.first_name} {person.last_name} - {person.email}
      </p>
    </div>
  );
};
export default GetPersonByEmail;
