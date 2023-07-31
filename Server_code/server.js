/* Node / Express */

const express = require('express')
const { SerialPort } = require('serialport');
const { ReadlineParser } = require('@serialport/parser-readline');
const bodyParser = require('body-parser'); // import body-parser

const expressApp = express()
const expressPort = 3001

const port = new SerialPort({ path: '/dev/cu.usbmodem11101', baudRate: 9600 });
//const port = new SerialPort('/dev/cu.usbmodem11101', { baudRate: 9600 });
const parser = port.pipe(new ReadlineParser({ delimiter: '\n' }));

port.on("open", () => {
  console.log('serial port open');
});

parser.on('data', data => {
  console.log('got word from arduino:', data);
});

let c = 0;

// use body-parser middleware
expressApp.use(bodyParser.urlencoded({ extended: false }));

// New POST endpoint to handle grid data
// New POST endpoint to handle grid data
expressApp.post('/post', (req, res) => {
    const payload = req.body.payload;
    console.log('Received payload:', payload);
  
    // Send the payload to your Arduino
    port.write(`${payload}\n`, (err) => {
      if (err) {
        console.log('Error on write: ', err.message);
      } else {
        console.log('Payload sent to Arduino');
      }
    });
  
    res.sendStatus(200);
});
  

expressApp.get('/', (req, res) => {
  port.write('' + c++, (err) => {
    if (err) {
      return console.log('Error on write: ', err.message);
    }
    // console.log('Msg: ' + c);
  });
})

expressApp.get('/lightup', (req, res) => {
    port.write('lightup\n', (err) => {
      if (err) {
        return console.log('Error on write: ', err.message);
      }
      console.log('lightup command sent to Arduino');
    });
  });

expressApp.get('/clear', (req, res) => {
    port.write('clear\n', (err) => {
      if (err) {
        return console.log('Error on write: ', err.message);
      }
      console.log('clear command sent to Arduino');
    });
  });
  expressApp.get('/drawA', (req, res) => {
    port.write('drawA\n', (err) => {
      if (err) {
        return console.log('Error on write: ', err.message);
      }
      console.log('drawA command sent to Arduino');
    });
  });
  
expressApp.listen(expressPort, () => {
  console.log(`Arduino app listening on port ${expressPort}`)
})