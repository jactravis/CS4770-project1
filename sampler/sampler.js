let readingCount = 0;
const SAMPLE_RATE = 2;

function sample(data) {
    if(!data.sensor.id || data.value === undefined || !data.timestamp){
        return { stat: "rejected", reason: "Missing required feilds"};
    }


    readingCount++;

    if(readingCount % SAMPLE_RATE !== 0){
        return {status: "skipped", reason: "Filtered by sample rate"};
    }

    return {
        status: "accepted",
        samplerValue: data.value,
        sensorId: data.sensorId,
        timestamp: data.timestamp
    };
}
module.exports = { sample };