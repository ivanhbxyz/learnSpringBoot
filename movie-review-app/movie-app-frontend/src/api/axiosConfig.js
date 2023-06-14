// Axios is used to send/receive Http request to the API

import axios from 'axios';


// What is CORS: Cross Origin Request
export default axios.create({
    baseURL:'https://9c96-103-106-239-104.ap.ngrok.io',
    headers: {"ngrok-skip-browser-warning": "true"}
});