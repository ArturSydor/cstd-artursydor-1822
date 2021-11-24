import {RolePayload} from "../model/role-payload";
import {SimpleOrganisationPayload} from "../model/simple-organisation-payload";

export class UserPayload {
  id: Number;
  firstName: String;
  lastName: String;
  email: String;
  role: RolePayload;
  organisation: SimpleOrganisationPayload
}
