import { Component, OnInit } from '@angular/core';
import { DeelnemerDTO } from 'src/app/models/deelnemer-dto';
import { DeelnemerService } from 'src/app/services/deelnemer.service';

@Component({
	selector: 'app-deelnemers-list',
	templateUrl: './deelnemers-list.component.html',
	styleUrls: ['./deelnemers-list.component.css']
})
export class DeelnemersListComponent implements OnInit {

	deelnemers: DeelnemerDTO[];
	constructor(private deelnemerService: DeelnemerService) { }

	ngOnInit() {
		this.deelnemerService.getAll().subscribe(data => {
			this.deelnemers = data;
		}, error =>{
			window.alert('Ophalen mislukt');
		});
	}

}
