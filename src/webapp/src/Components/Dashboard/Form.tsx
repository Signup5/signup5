import DateFnsUtils from "@date-io/date-fns";
import { Button, TextField } from "@material-ui/core";
import ScheduleIcon from "@material-ui/icons/Schedule";
import {
  KeyboardDatePicker,
  KeyboardTimePicker,
  MuiPickersUtilsProvider
} from "@material-ui/pickers";
import React, { FC, FormEvent, useEffect, useState } from "react";
import RoomOutlinedIcon from "@material-ui/icons/RoomOutlined";

interface Props {}

export const Form: FC<Props> = () => {
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [date_of_event, setDate_of_event] = useState<Date | null>();
  const [time_of_event, setTime_of_event] = useState<Date | null>();

  const onDateChange = (date: Date | null) => {
    setDate_of_event(date);
  };

  const onTimeChange = (time: Date | null) => {
    setTime_of_event(time);
  };

  const onDesciptionChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDescription(e.target.value);
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
  };

  useEffect(() => {});

  return (
    <>
      <form onSubmit={handleSubmit} noValidate style={{ width: "100%" }}>
        <h2>Create new event</h2>

        <TextField id="title" label="Title" style={{ width: "50%" }} />
        <br />
        <br />

        <TextField
          id="description"
          label="Description"
          multiline
          rows="2"
          rowsMax="5"
          value={description}
          onChange={onDesciptionChange}
          variant="outlined"
          style={{ width: "50%" }}
        />
        <br />

        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <KeyboardDatePicker
            margin="normal"
            id="date-picker-dialog"
            label="Date of event"
            format="yyyy-MM-dd"
            value={date_of_event}
            onChange={onDateChange}
            KeyboardButtonProps={{
              "aria-label": "change date"
            }}
            style={{ marginRight: 20 }}
          />
          <KeyboardTimePicker
            margin="normal"
            id="time-picker"
            label="Time of event"
            value={time_of_event}
            onChange={onTimeChange}
            ampm={false}
            keyboardIcon={<ScheduleIcon />}
            KeyboardButtonProps={{
              "aria-label": "change time"
            }}
          />
        </MuiPickersUtilsProvider>
        <br />

        <TextField
          id="location"
          label="Location"
          style={{ width: "50%" }}
          InputProps={{
            endAdornment: (
              <RoomOutlinedIcon style={{ marginRight: 10, opacity: 0.65 }} />
            )
          }}
        />
        <br />
        <br />

        <Button
          color="primary"
          variant="contained"
          type="submit"
          onClick={() => handleSubmit}
        >
          create event
        </Button>
      </form>
    </>
  );
};
