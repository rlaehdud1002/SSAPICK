export interface IMessage {
  id: number;
  senderId: number;
  senderName: string;
  receiverName: string;
  senderGender: string;
  receiverGender: string;
  createdAt: string;
  content: string;
  questionContent: string;
  senderProfileImage: string;
  receiverProfileImage: string;
  senderCampus: string;
  receiverCampus: string;
  senderSection: string;
  receiverSection: string;
}

export interface ISendMessage {
  pickId: number;
  content: string;
}
