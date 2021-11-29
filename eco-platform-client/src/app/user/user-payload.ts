import {RolePayload} from "../model/role-payload";
import {SimpleOrganisationPayload} from "../model/simple-organisation-payload";

export class UserPayload {
  id: Number;
  firstName: String;
  lastName: String;
  email: String;
  active: Boolean;
  joined: String;
  role: RolePayload;
  organisation: SimpleOrganisationPayload
}
