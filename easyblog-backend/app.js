const { spawn } = require('child_process');
const path = require('path');

const backendPath = path.join(__dirname, 'target', 'easyblog-backend-0.0.1-SNAPSHOT.jar');

const backendProcess = spawn('java', ['-jar', backendPath]);

backendProcess.stdout.on('data', (data) => {
    console.log(`Backend: ${data}`);
});

backendProcess.stderr.on('data', (data) => {
    console.error(`Backend Error: ${data}`);
});

backendProcess.on('close', (code) => {
    console.log(`Backend process exited with code ${code}`);
});
