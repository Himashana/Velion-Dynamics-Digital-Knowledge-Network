export interface Content {
  contentId: number;
  filePath: string;
  tags?: string[] | null;
  flagged?: boolean | null;
}
