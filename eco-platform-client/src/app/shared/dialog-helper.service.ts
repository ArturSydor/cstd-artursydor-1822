import {Injectable} from '@angular/core';
import {DialogInterface} from "../interfaces/dialog.interface";
import {DialogComponent} from "../components/dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Injectable({
  providedIn: 'root'
})
export class DialogHelperService {

  constructor(public dialog: MatDialog) {
  }

  showError(message: string) {
    const dialogInterface: DialogInterface = {
      dialogHeader: 'Error',
      dialogContent: message,
      cancelButtonLabel: 'Ok',
      confirmButtonLabel: null,
      callbackMethod: null
    };
    this.dialog.open(DialogComponent, {
      width: '500px',
      data: dialogInterface,
    });
  }
}
