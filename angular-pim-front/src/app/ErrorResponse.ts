export class ErrorResponse{
    status: number;
    message: string;
    detail: string;
    constructor(status: number, message: string, detail: string){
        this.status = status;
        this.message = message;
        this.detail = detail;
    };
    

}