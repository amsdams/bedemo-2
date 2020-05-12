import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DeelnemerDTO } from '../models/deelnemer-dto';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
	providedIn: 'root'
})
export class DeelnemerService {

	private deelnemersUrl: string;

	constructor(private http: HttpClient) {
		this.deelnemersUrl = 'http://localhost:8080/api/deelnemers';
	}

	public getAll(): Observable<DeelnemerDTO[]> {
		return this.http.get<DeelnemerDTO[]>(this.deelnemersUrl);
	}

	public getOne(id: string): Observable<DeelnemerDTO> {
		return this.http.get<DeelnemerDTO>(this.deelnemersUrl + '/' + id);
	}

	public update(deelnemer: DeelnemerDTO) {
		return this.http.put<DeelnemerDTO>(this.deelnemersUrl, deelnemer);
	}


}
