import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { PensioenRegelingDTO } from 'src/app/models/pensioen-regeling-dto';
import {PensioenRegelingService} from 'src/app/services/pensioen-regeling.service'
import { HttpErrorResponse } from '@angular/common/http';
@Component({
  selector: 'app-pensioen-regelings-details',
  templateUrl: './pensioen-regelings-details.component.html',
  styleUrls: ['./pensioen-regelings-details.component.css']
})
export class PensioenRegelingsDetailsComponent implements OnInit {

  pensioenRegeling: PensioenRegelingDTO;
	constructor(private pensioenRegelingService: PensioenRegelingService,
		private route: ActivatedRoute,
		private router: Router,
	) { this.pensioenRegeling = new PensioenRegelingDTO }

	ngOnInit() {
		const id: string = this.route.snapshot.paramMap.get('id');
		console.log(id);

		this.pensioenRegelingService.getOne(id).subscribe(data => {
			
			this.pensioenRegeling = data;
			
		}, (error:HttpErrorResponse) =>{
			window.alert('Ophalen mislukt');
			
		});

	}

	onSubmit() {
		this.pensioenRegelingService.update(this.pensioenRegeling).subscribe(data => {
			this.pensioenRegeling = data;
			window.alert('Aangepast');
		}, (error:HttpErrorResponse) =>{
			window.alert('Aanpassing mislukt');
			
		});

	}

}
