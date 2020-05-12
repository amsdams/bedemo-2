import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DeelnemerService } from 'src/app/services/deelnemer.service';
import { DeelnemerDTO } from 'src/app/models/deelnemer-dto';

@Component({
	selector: 'app-deelnemer-details',
	templateUrl: './deelnemer-details.component.html',
	styleUrls: ['./deelnemer-details.component.css']
})
export class DeelnemerDetailsComponent implements OnInit {

	deelnemer: DeelnemerDTO;
	constructor(private deelnemerService: DeelnemerService,
		private route: ActivatedRoute,
		private router: Router,
	) { this.deelnemer = new DeelnemerDTO }

	ngOnInit() {
		const id: string = this.route.snapshot.paramMap.get('id');
		console.log(id);

		this.deelnemerService.getOne(id).subscribe(data => {
			this.deelnemer = data;
		}, error =>{
			window.alert('Ophalen mislukt');
		});

	}

	onSubmit() {
		this.deelnemerService.update(this.deelnemer).subscribe(data => {
			this.deelnemer = data;
			window.alert('Aangepast');
		}, error =>{
			window.alert('Aanpassen mislukt');
		});

	}
}
