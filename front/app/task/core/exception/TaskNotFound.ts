export class TaskNotFound extends Error {
  constructor(message: string) {
    super(message);
    this.name = "TaskNotFound";
  }
}
