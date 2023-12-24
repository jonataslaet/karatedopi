export class MenuItem {
  constructor(
    public id: number,
    public path: string,
    public text: string,
    public authorities: string[]
  ) {}
}
