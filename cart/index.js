
// CREATE TABLE `cartsdb`.`cart_prd` (
//     `id` INT NOT NULL,
//     `prd_des` VARCHAR(255) NULL,
//     `prd_icon` VARCHAR(255) NULL,
//     `prd_id` VARCHAR(45) NOT NULL,
//     `prd_name` VARCHAR(255) NULL,
//     `prd_price` DOUBLE ZEROFILL NULL,
//     `prd_stock` INT ZEROFILL NULL,
//     PRIMARY KEY (`id`));


const express = require('express');
const app = express();
const cart = require('./cart')
app.use('/cart', cart)
console.log("Server Started!");
app.listen(3000);