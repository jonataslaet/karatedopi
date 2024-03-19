import { Injectable } from '@angular/core';
import { GraduationDTO } from '../common/graduation.dto';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private requestService: RequestService) { }

  changeGraduation(id: number, graduationDto: GraduationDTO) {
      const endpoint = `/profiles/${id}/graduations`;
      return this.requestService.request('PATCH', endpoint, graduationDto);
  }

}