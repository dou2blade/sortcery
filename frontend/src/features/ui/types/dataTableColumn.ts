export interface DataTableColumn<T> {
  name: string;
  selector: (row: T) => string;
  flex?: number;
}
