import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DeelnemersListComponent } from './components/deelnemers-list/deelnemers-list.component';
import { DeelnemerDetailsComponent } from './components/deelnemer-details/deelnemer-details.component';
import { PensioenRegelingsListComponent } from './components/pensioen-regelings-list/pensioen-regelings-list.component';
import { PensioenRegelingsDetailsComponent } from './components/pensioen-regelings-details/pensioen-regelings-details.component';


const routes: Routes = [
	{ path: '', redirectTo: 'deelnemers', pathMatch: 'full' },
	{ path: 'deelnemers', component: DeelnemersListComponent },
	{ path: 'deelnemers/:id', component: DeelnemerDetailsComponent },
	{ path: 'pensioenregelingen', component: PensioenRegelingsListComponent },
	{ path: 'pensioenregelingen/:id', component: PensioenRegelingsDetailsComponent }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
