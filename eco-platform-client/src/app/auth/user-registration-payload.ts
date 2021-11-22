import {RolePayload} from "../model/role-payload";
import {SimpleOrganisationPayload} from "../model/simple-organisation-payload";

export class UserRegistrationPayload {
  firstName: String;
  lastName: String;
  email: String;
  password: String;
  confirmPassword: String;
  role: RolePayload;
  organisation: SimpleOrganisationPayload
}
