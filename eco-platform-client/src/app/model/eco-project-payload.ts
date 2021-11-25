import {UserPayload} from "../user/user-payload";
import {SimpleOrganisationPayload} from "./simple-organisation-payload";

export class EcoProjectPayload {
  id: BigInteger;
  name: String;
  description: String;
  points: Number;
  maxAllowedPointsPerUser:  Number;
  published: boolean;
  closed: boolean;
  creator: UserPayload;
  organisation: SimpleOrganisationPayload
}
