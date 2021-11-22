import {RolePayload} from "../model/RolePayload";
import {SimpleOrganisationPayload} from "../model/SimpleOrganisationPayload";

export class UserRegistrationPayload {
  firstName: String;
  lastName: String;
  email: String;
  password: String;
  confirmPassword: String;
  role: RolePayload;
  organisation: SimpleOrganisationPayload
}
