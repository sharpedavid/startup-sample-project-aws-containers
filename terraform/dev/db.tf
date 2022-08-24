resource "aws_dynamodb_table" "startup_sample_table2" {
  name           = "ProductInfo"
  hash_key       = "pid"
  read_capacity  = 1
  write_capacity = 1

  attribute {
    name = "id"
    type = "S"
  }

}