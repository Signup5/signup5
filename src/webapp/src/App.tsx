import React, { FC } from "react";
import Classes from "./App.module.css";
import SignupLogo from "./Components/Icons/SignupLogo";
import LoginForm from "./Components/LoginForm";
import { BrowserRouter, Route } from "react-router-dom";
import { useSelector } from "react-redux";
import { Person } from "./Types";
import { InitialState } from "./Store/Reducers/rootReducer";
import { Form } from "./Components/Dashboard";

interface StateProps {
  person: Person;
}

const App: FC = () => {
  const { person } = useSelector<InitialState, StateProps>(
    (state: InitialState) => {
      return {
        person: state.person
      };
    }
  );

  return (
    <div className={Classes.App}>
      <div className={Classes.AppHeader}>
        <SignupLogo />
      </div>
      <div className={Classes.AppSidebar}></div>
      <div className={Classes.AppMainContent}>
        <BrowserRouter>
          <Route exact path="/">
            <LoginForm />
          </Route>
          <Route path="/dashboard">
            <Form />
          </Route>
        </BrowserRouter>
      </div>

      <div className={Classes.AppFooter}></div>
    </div>
  );
};

export default App;
