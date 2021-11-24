import {UserRegistrationPayload} from "./user-registration-payload";

export class OrganisationRegistrationPayload {
  name: String;
  email: String;
  memberApprovalRequired: Boolean;
  creator: UserRegistrationPayload
}
