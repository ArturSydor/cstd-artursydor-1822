import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MappingHelperService {

  constructor() { }

  mapBooleanToYesNoString(value: boolean): string {
    return value ? 'YES' : 'NO';
  }

}
