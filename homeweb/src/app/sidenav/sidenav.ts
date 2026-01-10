import {Component, output} from '@angular/core';
import {MatNavList} from '@angular/material/list';

@Component({
  selector: 'app-sidenav',
  imports: [
    MatNavList,
  ],
  templateUrl: './sidenav.html',
  styleUrl: './sidenav.css'
})
export class Sidenav {

  closing = output<void>();

  closeSidenav() {
    this.closing.emit();
  }

}
