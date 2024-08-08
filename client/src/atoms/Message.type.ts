export interface IMessage {
  id: number;
  senderName: string;
  receiverName: string;
  senderGender: string;
  receiverGender: string;
  createdAt: string;
  content: string;
  questionContent: string;
}

export interface ISendMessage {
  pickId: number;
  content: string;
}
