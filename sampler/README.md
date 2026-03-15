##Overview
The Sampler is a Node.js REST API that receives voltage readings from a sensor and filters them using a configurable sampling rate. It acts as a middleware layer in the pipeline between the raw sensor and downstream processing. The API accepts JSON over HTTP and returns a status indicating whether each reading was accepted, skipped, or rejected. A Circuit Breaker pattern is also built in to handle sensor faults and prevent system crashes.

##Endpoint
POST /sample
Host: http://localhost:3000
Content-Type: application/json


##Input JSON
sensorId string
value number
timestamp string

##Example
{
  "sensorId": "s1",
  "value": 3.847,
  "timestamp": "2026-03-14T10:00:00Z"
}

##Output JSON
###Accepted State
{
  "status": "accepted",
  "sampledValue": 3.847,
  "sensorId": "s1",
  "timestamp": "2026-03-14T10:00:00Z",
  "samplingMode": "normal"
}
###Skipped State
{
  "status": "skipped",
  "reason": "Filtered by sample rate"
}
###Rejected State
{
  "status": "rejected",
  "reason": "Missing required fields"
}
###Circuit Open -- sensor is unavailable
{
  "error": "Circuit OPEN — sensor unavailable, retrying in 3 seconds"
}

##How to Run
npm install

node server.js

##How to call the APi (curl example)
curl -X POST http://localhost:3000/sample \
  -H "Content-Type: application/json" \
  -d '{"sensorId": "s1", "value": 3.847, "timestamp": "2026-03-14T10:00:00Z"}'
```
## Design Notes
The Sampler uses a configurable sampling rate (default: every 2nd reading) to reduce the volume of data passed through the pipeline, supporting energy efficiency on constrained hardware. A Circuit Breaker is implemented to detect sensor faults — after 3 consecutive failures the circuit opens and blocks requests for 3 seconds before attempting recovery. Input validation ensures only well-formed readings enter the pipeline, preventing crashes from bad data. The sampling logic (`sampler.js`) is intentionally separated from the HTTP layer (`server.js`) to keep the system modular and easy to test.
