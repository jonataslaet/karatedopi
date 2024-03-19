export class RouteItem {
    constructor(
      public id: number,
      public path: string,
      public text: string,
      public authorities: string[],
      public isMenu: boolean
    ) {}
  }
  