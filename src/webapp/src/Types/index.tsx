export type Person = {
  id: number;
  first_name: string;
  last_name: string;
  email: string;
};

export enum Attendance {
  ATTENDING,
  NOT_ATTENDING,
  MAYBE,
  NO_RESPONSE
}

export type Invitation = {
  id: number;
  guest: Person;
  event_id: number;
  attendance: Attendance;
};

export type Event = {
  id: number;
  title: string;
  description: string;
  date_of_event: string;
  time_of_event: string;
  location: string;
};
