import {Component, Inject, OnInit} from '@angular/core';
import {DialogInterface} from "../../interfaces/dialog.interface";
import {StateService} from "../../shared/state.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public dialogData: DialogInterface,
    public stateService: StateService
  ) {
  }

  ngOnInit(): void {
  }

  handleDialogSubmit() {
    this.stateService.isAsyncOperationRunning$.next(true);
    setTimeout(() => {
      this.dialogData.callbackMethod();
      this.stateService.isAsyncOperationRunning$.next(false);
      this.closeDialog();
    }, 500);
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

}
