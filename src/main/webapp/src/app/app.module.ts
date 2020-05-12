import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DeelnemersListComponent } from './components/deelnemers-list/deelnemers-list.component';
import { DeelnemerDetailsComponent } from './components/deelnemer-details/deelnemer-details.component';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PensioenRegelingsListComponent } from './components/pensioen-regelings-list/pensioen-regelings-list.component';
import { PensioenRegelingsDetailsComponent } from './components/pensioen-regelings-details/pensioen-regelings-details.component';

@NgModule({
  declarations: [
    AppComponent,
    DeelnemersListComponent,
    DeelnemerDetailsComponent,
    PensioenRegelingsListComponent,
    PensioenRegelingsDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
	FormsModule,
	HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
