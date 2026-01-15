export interface User {
  userId: number;
  name: string;
  email: string;
  contactNumber: string;
  address: string;
  designation?: string;
  expertDomains?: string[] | null;
  officeLocation?: string | null;
  performanceMetrics?: {} | null;
  permissionLevel: number;
  responsibility?: string | null;
  sessionHoursDelivered?: number | null;
  yearsOfExperience?: number | null;
}
