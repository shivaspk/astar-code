const { json } = require('express');
const mysql = require('mysql')
var express = require('express')
const bodyParser = require('body-parser');
var jsonParser = bodyParser.json()
var router = express.Router()

var amqp = require('amqplib/callback_api');
const DB_HOST = process.env.DB_HOST || 'db';
const DB_SCHEMA = process.env.DB_SCHEMA || 'cartsdb';
const DB_USER = process.env.DB_USER || 'mycartuser';
const DB_PWD = process.env.DB_PWD || 'rootroot';

const db_config = {
    host: DB_HOST,
    user: DB_USER,
    password: DB_PWD,
    database: DB_SCHEMA
};

function handleDisconnect() {
    connection = mysql.createConnection(db_config); // Recreate the connection, since
    // the old one cannot be reused.

    connection.connect(function (err) {              // The server is either down
        if (err) {                                     // or restarting (takes a while sometimes).
            console.log('error when connecting to db:', err);
            setTimeout(handleDisconnect, 2000); // We introduce a delay before attempting to reconnect,
        }                                     // to avoid a hot loop, and to allow our node script to
    });                                     // process asynchronous requests in the meantime.
    // If you're also serving http, display a 503 error.
    connection.on('error', function (err) {
        console.log('db error', err);
        if (err.code === 'PROTOCOL_CONNECTION_LOST') { // Connection to the MySQL server is usually
            handleDisconnect();                         // lost due to either server restart, or a
        } else {                                      // connnection idle timeout (the wait_timeout
            throw err;                                  // server variable configures this)
        }
    });
}

handleDisconnect();
//return cart
router.get('/', function (req, res) {
    connection.query("SELECT * FROM `cartsdb`.`cart_prd`;", function (err, rows, fields) {
        if (err) throw err
        res.json(rows);
    })

})

// define the cart by ID
router.get('/:id', function (req, res) {
    connection.query("Select * from `cartsdb`.`cart_prd` where id  = ?", [req.params.id], function (err, rows, fields) {
        if (err) throw err

        if (rows.length === 1)
            res.json(rows[0]);
        else {
            res.status(404);
            res.json({ error: "Cart not found!" });
        }
    });
})

router.delete('/:id', function (req, res) {
    connection.query("DELETE FROM `cartsdb`.`cart_prd` WHERE id = ?", [req.params.id], function (err, rows, fields) {
        if (err) throw err

        if (rows.affectedRows === 1) {
            res.status(204);
        }
        else {
            res.status(404);
            res.json({ error: "Cart not found!" });
        }
    });
})

router.post('/', jsonParser, function (req, res) {

    var query = connection.query('INSERT INTO cartsdb.cart_prd SET ?', req.body, function (error, results, fields) {
        if (error) throw error;

        // amqp.connect('amqp://rabbitmq', function (error0, connection) {
        //     if (error0) {
        //         throw error0;
        //     }
        //     connection.createChannel(function (error1, channel) {
        //         if (error1) {
        //             throw error1;
        //         }

        //         var queue = 'carts';
        //         var msg = req.body;

        //         channel.assertQueue(queue, {
        //             durable: true
        //         });
        //         channel.sendToQueue(queue, Buffer.from(JSON.stringify(msg)));

        //         console.log(" [x] Sent %s", msg);
        //     });
        //     setTimeout(function () {
        //         connection.close();
        //         process.exit(0);
        //     }, 500);
        // });

        amqp.connect('amqp://rabbitmq', function (err,
            conn) {
            //console.log("Enter in callback", conn);
            if (err) {
                console.error("[AMQP]", err.message);
                return err;
            }
            conn.on("error", function (err) {
                if (err.message !== "Connection closing") {
                    console.error("[AMQP] conn error", err.message);
                }
            });
            conn.on("close", function () {
                console.error("[AMQP] reconnecting");
                return;
            });

            console.log("[AMQP] connected");
            amqpConn = conn;
            console.log("Success");
            var queue = 'carts';
            var msg = req.body;
            conn.createChannel(function (error1, channel) {
                if (error1) {
                    throw error1;
                }

                channel.assertQueue(queue, {
                    durable: true
                });
                channel.sendToQueue(queue, Buffer.from(JSON.stringify(msg)));

                console.log(" [x] Sent %s", msg);
            });
        });
            res.status(201);
            res.json({ message: "Cart created!" });
        });
    })

    module.exports = router


