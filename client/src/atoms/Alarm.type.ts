export interface IAlarm {
  messageAlarm: boolean;
  nearbyAlarm: boolean;
  pickAlarm: boolean;
  addQuestionAlarm: boolean;
}

export interface IAlarmAll {
  onOff: boolean;
}

export interface IPickAlarm {
  id: number;
  sender: object;
  receiver: object;
  question: object;
  alarm: boolean;
  createdAt: string;
  messageSend: boolean;
  openedHint: Array<string>;
}