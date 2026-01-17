import { mergeQueryKeys } from "@lukemorales/query-key-factory";
import { users } from "./users";
import { workspaces } from "./workspace";
import { contents } from "./content";
import { comments } from "./comment";

export const queries = mergeQueryKeys(users, workspaces, contents, comments);
