import {UserRegistrationPayload} from "./UserRegistrationPayload";

export class OrganisationRegistrationPayload {
  name: String;
  email: String;
  memberApprovalRequired: Boolean;
  creator: UserRegistrationPayload
}
