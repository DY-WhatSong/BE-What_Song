variable "aws_region" {
  default = "ap-northeast-2"
}

variable "az_count" {
  default = "2"
}

variable "app_port" {
  default = 8082
}

variable "domain" {
  default = "whatsong.me"
}

variable "frontend_ip" {
  sensitive = true
}

variable "ec2_key_name" {
  sensitive = true
}

variable "health_check_path" {
  default = "/health"
}

variable "project_name" {
  default = "whatsong"
}

variable "project_repository" {
  default = "https://github.com/DY-WhatSong/BE-What_Song"
}

variable "db_username" {
  sensitive = true
}

variable "db_password" {
  sensitive = true
}

variable "db_name" {
  default = "WhatSong"
}

variable "db_port" {
  default   = "5432"
  sensitive = true
}

variable "db_instance_class" {
  default = "db.t3.micro"
}

variable "db_allocated_storage" {
  default = 8
}

variable "db_storage_type" {
  default = "gp2"
}

variable "db_engine" {
  default = "postgres"
}

variable "db_engine_version" {
  default = "15.4"
}