import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Footer } from "./footer/footer";
import { Navbar } from './navbar/navbar';

@Component({
  selector: 'app-root',
  host: { class: 'flex min-h-screen flex-col' },
  imports: [RouterOutlet, Navbar, Footer],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {

}
