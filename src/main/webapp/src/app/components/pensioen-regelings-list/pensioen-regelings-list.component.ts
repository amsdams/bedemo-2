import { Component, OnInit } from '@angular/core';
import { PensioenRegelingDTO } from 'src/app/models/pensioen-regeling-dto';
import { PensioenRegelingService } from 'src/app/services/pensioen-regeling.service';

@Component({
	selector: 'app-pensioen-regelings-list',
	templateUrl: './pensioen-regelings-list.component.html',
	styleUrls: ['./pensioen-regelings-list.component.css']
})
export class PensioenRegelingsListComponent implements OnInit {

	pensioenRegelings: PensioenRegelingDTO[];
	constructor(private pensioenRegelingService: PensioenRegelingService) { }

	ngOnInit() {
		this.pensioenRegelingService.getAll().subscribe(data => {
			this.pensioenRegelings = data;
		}, error =>{
			window.alert('Ophalen mislukt');
		});
	}

}
