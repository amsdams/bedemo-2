import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PensioenRegelingDTO } from '../models/pensioen-regeling-dto';
import { environment } from '../../environments/environment';
@Injectable({
	providedIn: 'root'
})
export class PensioenRegelingService {


	private pensioenRegelingsUrl: string;

	constructor(private http: HttpClient) {
		this.pensioenRegelingsUrl = environment.baseUrl + '/pensioen-regelings';
	}

	public getAll(): Observable<PensioenRegelingDTO[]> {
		return this.http.get<PensioenRegelingDTO[]>(this.pensioenRegelingsUrl);
	}

	public getOne(id: string): Observable<PensioenRegelingDTO> {
		return this.http.get<PensioenRegelingDTO>(this.pensioenRegelingsUrl + '/' + id);
	}

	public update(pensioenRegeling: PensioenRegelingDTO) {
		return this.http.put<PensioenRegelingDTO>(this.pensioenRegelingsUrl, pensioenRegeling);
	}
}
