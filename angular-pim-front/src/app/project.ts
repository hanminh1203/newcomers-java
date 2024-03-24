import { Employee } from "./employee";

export class Project{
    id?: number;
    projectNumber: any;
    name: any;
    customer: any;
    groupId: any;
    members: Employee[]|null;
    startDate: any;
    status?: any;
    endDate?: any;
    
    constructor(projectNumber: any, name: any, customer: any, groupId: any, 
        members: Employee[]|null,  startDate: any, status?: any, endDate?: any){
            this.projectNumber = projectNumber;
            this.name = name;
            this.customer = customer;
            this.groupId = groupId;
            this.members = members;
            this.startDate = startDate;
            this.status = status;
            this.endDate = endDate;
    }
}