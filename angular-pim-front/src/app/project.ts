export class Project{
    projectNumber: any;
    name: any;
    customer: any;
    groupId: any;
    membersVisa: any;
    startDate: any;
    status?: any;
    endDate?: any;
    
    constructor(projectNumber: any, name: any, customer: any, groupId: any, 
        membersVisa: any,  startDate: any, status?: any, endDate?: any){
            this.projectNumber = projectNumber;
            this.name = name;
            this.customer = customer;
            this.groupId = groupId;
            this.membersVisa = membersVisa;
            this.startDate = startDate;
            this.status = status;
            this.endDate = endDate;
    }
}