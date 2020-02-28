import React, { FC } from "react";
import Classes from "./App.module.css";
import SignupLogo from "./Components/Icons/SignupLogo";
import LoginForm from "./Components/LoginForm";

const App: FC = () => {
  return (
    <div className={Classes.App}>
      <div className={Classes.AppHeader}>
        <SignupLogo />
      </div>
      <div className={Classes.AppSidebar}></div>
      <div className={Classes.AppMainContent}>
        <LoginForm />
      </div>
      <div className={Classes.AppFooter}></div>
    </div>
  );
};

export default App;
