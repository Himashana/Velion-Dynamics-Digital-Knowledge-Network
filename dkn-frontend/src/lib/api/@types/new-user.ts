export interface NewUser {
  name: string;
  email: string;
  password: string;
  contactNumber: string;
  address: string;
  designation?: string;
  expertDomains?: string[] | null;
  officeLocation?: string | null;
  performanceMetrics?: {} | null;
  permissionLevel: number;
  responsibility?: string | null;
}
