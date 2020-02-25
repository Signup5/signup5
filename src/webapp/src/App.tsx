import React from "react";
import Classes from "./App.module.css";
import SignupLogo from "./Components/Icons/SignupLogo";
import LoginForm from "./Components/LoginForm";

function App() {
  return (
    <div className={Classes.App}>
      <div className={Classes.AppHeader}>
        <SignupLogo />
      </div>
      <div className={Classes.AppSidebar}>
        <p>this is a sidebar</p>
      </div>
      <div className={Classes.AppMainContent}>
        <LoginForm />
      </div>
      <div className={Classes.AppFooter}>
        <p>this is a footer</p>
      </div>
    </div>
  );
}

export default App;
