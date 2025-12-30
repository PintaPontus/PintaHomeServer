import {Component, signal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatSidenavContent, MatSidenav, MatSidenavContainer],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  isSidenavOpen = signal(true);
}
