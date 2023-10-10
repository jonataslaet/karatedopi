import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProfileInput } from 'src/app/common/profile-input';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {

  profileForm: ProfileInput = {
    id: null,
    fullname: '',
    father: '',
    mother: '',
    address: null,
    bloodType: '',
    phone: '',
    birthday: null,
    cpf: '',
    rg: ''
  };

  constructor(private profileService: ProfileService, 
    private router: Router, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((param) => {
      var id = Number(param.get('id'));
      this.getProfileById(id);
    });
  }
  
  myFilter = (d: Date | null): boolean => {
    const day = (d || new Date()).getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  };

  getProfileById(id: number) {
    this.profileService.getProfileById(id).subscribe((data) => {
      this.profileForm = data;
      // map(a => {
      //   this.profileForm = data
      // })
    });
  }

  updateProfile() {
    this.profileService.updateProfile(this.profileForm).subscribe(() => {
      this.router.navigate(['']);
    });
  }

  cancel(): void{
    this.router.navigate(['/products']);
  }
}
