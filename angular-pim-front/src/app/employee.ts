export class Employee{
    id: number;
    visa: string;
    employeeName: string;

    constructor(id: number, visa: string, name: string){
        this.id = id;
        this.visa = visa;
        this.employeeName = name;
    }
}