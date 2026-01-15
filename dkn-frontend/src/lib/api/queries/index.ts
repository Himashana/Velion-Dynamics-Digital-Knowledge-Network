import { mergeQueryKeys } from "@lukemorales/query-key-factory";
import { users } from "./users";
import { workspaces } from "./workspace";

export const queries = mergeQueryKeys(users, workspaces);
