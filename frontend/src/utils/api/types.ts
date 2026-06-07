export interface ApiResponse<T> {
  status?: number,
  message?: string,
  data?: T, 
  timestamp: string, 
  meta?: {
    page: number,
    size: number,
    totalElements: number,
    totalPages: number,
    last: boolean
  }
}
