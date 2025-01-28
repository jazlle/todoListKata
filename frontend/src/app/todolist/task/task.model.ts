export interface Task {
  id: number;
  label?: string;
  completed?: boolean;
}

export interface TaskInDto {
  label?: string;
  completed?: boolean;
}
