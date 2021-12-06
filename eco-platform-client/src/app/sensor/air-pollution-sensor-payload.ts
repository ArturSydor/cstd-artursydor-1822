import {SimpleOrganisationPayload} from "../model/simple-organisation-payload";
import {UserPayload} from "../user/user-payload";

export class AirPollutionSensorPayload {
  id: BigInteger;
  externalIdentifier: String;
  latitude: String;
  longitude: String;
  organisation: SimpleOrganisationPayload;
  creator: UserPayload;
}
