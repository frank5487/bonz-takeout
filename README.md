# bonz-takeout (mainly focus on backend)

## Abstract
Built a web application with Springboot, SpringMVC, Mybatis (SSM Framework)

Saved a lot of time to develop a web application compare to using a tradition MVC developing way

In addition, I've learned the concept of IOC and AOP in this project, 
which decomposes each layer more precisely and maintains in a simpler way

Deployed in the Azure

## controller
Utilized RESTful interface to receive the data from frontend
and Sent an appropriate data structure (regulated by fronted) to the frontend

## service
Implemented a service login that frontend asked.

In this layer, we may use another service to complete more complex service
and to generate an appropriate data structure that frontend demands

## mapper
with the help of mybatis, we don't need to write dynamic sql by ourselves
we can implement the query by setting xml or using lambdaQuery function.

## common
Implemented lots of AOP operations including:
* handle exception
* convert data structure

Set up global attributes that every layer could use

## pojo(entity)
It's corresponded with database table

Used lombok to quickly generate getter and setter methods

## dto
Conveniently add additional attributes to original pojo object (extends from pojo)

## config
If we want to use pagination function in mybatis, we need to add pagination plugin config in it
so that Spring can manage this pagination plugin

If we want to load some static web resources in the frontend, we also need to set up a property 
to let Spring manage static web resources

## filter
If user or employee have not logged in yet, 
then they will be filtered

## Project display

### employee list (website background)
![employee list](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/employee.jpeg "employee list")

### add employee (website background)
![add employee](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/add+employee.jpeg "add employee")

### login page (website background)
![login page](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/backend+login.jpeg "login page")

### category list (website background)
![category list](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/category.jpeg "category list")

### order (website background)
![order](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/order.jpeg "order")

### combo list (website background)
![combo list](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/combo.jpeg "combo list")

### dish list (website background)
![dish list](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/food.jpeg "dish list")

### login page (web application)
![login page](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/frontend+login.jpeg "login page")

### dish list (web application)
![dish list](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/frontend+food.jpeg "dish list")
![dish list](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/frontend+food+2.jpeg "dish list")

### food list (web application)
![food list](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/order+food.jpeg "food list")

### select flavor (web application)
![select flavor](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/select+food.jpeg "select flavor")

### shopping cart (web application)
![shopping cart](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/shopping+cart.jpeg "shopping cart")

### user address (web application)
![user address](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/user+address.jpeg "user address")

### user history order (web application)
![user history order](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/user+history+order.jpeg "user history order")

### user profile (web application)
![](https://github-zen-project.s3.amazonaws.com/bonz-takeout-images/user+profile.jpeg)