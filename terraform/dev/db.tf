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

resource "aws_iam_role_policy" "sample_app_dynamodb2" {
  name = "sample_app_dynamodb"
  role = aws_iam_role.sample_app_container_role.id

  policy = <<-EOF
    {
    "Version": "2012-10-17",
    "Statement": [
      {
          "Effect": "Allow",
          "Action": [
              "dynamodb:BatchGet*",
              "dynamodb:DescribeStream",
              "dynamodb:DescribeTable",
              "dynamodb:Get*",
              "dynamodb:Query",
              "dynamodb:Scan",
              "dynamodb:BatchWrite*",
              "dynamodb:CreateTable",
              "dynamodb:Delete*",
              "dynamodb:Update*",
              "dynamodb:PutItem"
          ],
          "Resource": "${aws_dynamodb_table.startup_sample_table2.arn}"
        }
    ]
  }
  EOF
}
