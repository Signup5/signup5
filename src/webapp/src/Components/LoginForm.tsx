import { Button, Grid, Paper, TextField } from "@material-ui/core";
import AccountCircle from "@material-ui/icons/AccountCircle";
import LockIcon from "@material-ui/icons/Lock";
import React, { ChangeEvent, FC, FormEvent, useEffect, useState } from "react";
import Classes from "../App.module.css";
import GetPersonByEmail from "./GetPersonByEmail";

const LoginForm: FC = () => {
  const [email, setEmail] = useState<string>("");
  const [validEmail, setValidEmail] = useState<boolean>(true);
  const [submitted, setSubmitted] = useState<boolean>(false);
  const [password, setPassword] = useState<string>("");
  const [renderPerson, setRenderPerson] = useState<boolean>(false);
  
  const regEx = new RegExp(
    `^[a-zA-Z0-9_#$%&'*+/=?^.-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-_]+\\.)+[a-zA-Z]{2,13}$`
  );

  const onPasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const onEmailChange = (e: ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    setSubmitted(true);

    setRenderPerson(validEmail && submitted)
    
  };

  const validateEmail = () => {
    setValidEmail(email.match(regEx)? true: false);
  };

  useEffect(() => {
    if (submitted) {
      validateEmail();
    }
  })


  return (
    <div>
      <form onSubmit={handleSubmit} noValidate className={Classes.LoginForm}>
        <h2>Sign in</h2>
        <Grid container spacing={1} alignItems="flex-end">
          <Grid item xs={1}>
            <AccountCircle />
          </Grid>
          <Grid item xs={11}>
            <TextField
              required
              className={Classes.InputField}
              label="Email"
              onChange={onEmailChange}
              name="email"
              type="email"
              error={!validEmail}
              helperText={validEmail? "" : "Not a valid email!"}
              value={email}
            />
          </Grid>
        </Grid>

        <br />
        <Grid container spacing={1} alignItems="flex-end">
          <Grid item xs={1}>
            <LockIcon />
          </Grid>
          <Grid item xs={11}>
            <TextField
              required
              className={Classes.InputField}
              label="Password"
              onChange={onPasswordChange}
              name="password"
              type="password"
              error={(password.length===0) && submitted}
              helperText={((password.length===0) && submitted)? "Password required!" : ""}
              value={password}
            />
          </Grid>
        </Grid>
        <br />
        <Button
          className={Classes.Button}
          color="primary"
          variant="contained"
          type="submit"
          onClick={() => handleSubmit}
        >
          Sign in
        </Button>
      </form>
      <Paper> {renderPerson? <GetPersonByEmail Email={email}/>: ""}</Paper>
    </div>
  );
};

export default LoginForm;