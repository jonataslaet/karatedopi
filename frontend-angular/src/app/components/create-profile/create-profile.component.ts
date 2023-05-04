import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Profile } from 'src/app/common/profile';
import { ProfileService } from 'src/app/services/profile.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-profile',
  templateUrl: './create-profile.component.html',
  styleUrls: ['./create-profile.component.css']
})
export class CreateProfileComponent implements OnInit {

  profileForm: Profile = {
    id: null,
    email: '',
    password: '',
    firstname: '',
    lastname: '',
    father: '',
    mother: '',
    hometown: '',
    birthday: null,
    cpf: '',
    rg: ''
  };

  constructor(private userService: UserService, 
    private profileService: ProfileService, 
    private router: Router, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {}

  createProfile() {
    this.userService.createProfile(this.profileForm).subscribe(() => {
      this.router.navigate(['/']);
    });
  }

  cancel(): void{
    this.router.navigate(['/']);
  }

}
