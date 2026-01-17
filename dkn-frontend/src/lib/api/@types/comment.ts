import { User } from "./user";

export interface Comment {
  id: number;
  comment: string;
  commentedBy: User;
  timestamp: string;
}
