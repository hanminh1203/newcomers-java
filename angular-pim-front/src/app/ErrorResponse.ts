export interface ErrorResponse extends Error {
    message: string;
    detail: string
}