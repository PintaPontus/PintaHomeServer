import {Component, model} from '@angular/core';
import {RouterLink} from '@angular/router';
import {MatToolbar} from '@angular/material/toolbar';

@Component({
  selector: 'app-toolbar',
  imports: [
    RouterLink,
    MatToolbar,
  ],
  templateUrl: './toolbar.html',
  styleUrl: './toolbar.css'
})
export class Toolbar {

  isSidenavOpen = model<boolean>(false);

  openSidenav() {
    this.isSidenavOpen.set(true);
  }

}
