namespace php task9


exception InvalidValueException {
  1: i32 error_code,
  2: string error_msg
}

service TaskService {

  string load(1:string url) throws (1: InvalidValueException e),
}
