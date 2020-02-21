import React from "react";

import { createStyles, Theme, makeStyles } from "@material-ui/core/styles";
import IconButton from "@material-ui/core/IconButton";
import InputAdornment from "@material-ui/core/InputAdornment";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import AccountCircle from "@material-ui/icons/AccountCircle";
import LockIcon from "@material-ui/icons/Lock";
import Visibility from "@material-ui/icons/Visibility";
import VisibilityOff from "@material-ui/icons/VisibilityOff";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: "flex",
      flexWrap: "wrap"
    },
    margin: {
      margin: theme.spacing(1)
    },
    withoutLabel: {
      marginTop: theme.spacing(3)
    },
    textField: {
      width: 400
    }
  })
);

interface State {
  password: string;
  showPassword: boolean;
}

export default function InputAdornments() {
  const classes = useStyles();

  const [values, setValues] = React.useState<State>({
    password: "",
    showPassword: false
  });

  const handleChange = (prop: keyof State) => (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  const handleClickShowPassword = () => {
    setValues({ ...values, showPassword: !values.showPassword });
  };

  const handleMouseDownPassword = (
    event: React.MouseEvent<HTMLButtonElement>
  ) => {
    event.preventDefault();
  };

  return (
    <Grid container spacing={2}>
      
      <Grid item xs={12}>
        <Grid container justify="center" spacing={1} alignItems="flex-end">
          <Grid item>
            <AccountCircle />
          </Grid>

          <Grid item>
            <TextField id="input-email" label="Email" />
          </Grid>
        </Grid>
      </Grid>

      <Grid item xs={12}>
        <Grid container justify="center" spacing={1} alignItems="flex-end">
          <Grid item>
            <LockIcon />
          </Grid>

          <Grid item>
            <TextField
              id="input-password"
              label="Password"
              type={values.showPassword ? "text" : "password"}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      aria-label="toggle password visibility"
                      onClick={handleClickShowPassword}
                      onMouseDown={handleMouseDownPassword}
                    >
                      {values.showPassword ? <Visibility /> : <VisibilityOff />}
                    </IconButton>
                  </InputAdornment>
                )
              }}
            />
          </Grid>
        </Grid>
      </Grid>


     <Grid item xs={12}>
         <Grid container justify="center">
         <Button variant="contained" color="primary">
          Login
        </Button>
         </Grid>
     </Grid>

    </Grid>
  );
}
