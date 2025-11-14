// Example using fetch API for a GET request
fetch('http://localhost:8080/regler', {
    method: 'GET', // Change POST to GET
    headers: {
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7'
        // Other headers as needed
    }
})
.then(response => {
    if (!response.ok) {
        // If the response is not OK, it might still contain useful error info.
        // Try to parse it as text if it's not successful.
        return response.text().then(text => {
            throw new Error(`HTTP error! status: ${response.status}, message: ${text}`);
        });
    }
    return response.json(); // Only parse as JSON if response is OK
})
.then(data => {
    console.log('Success:', data);
})
.catch(error => {
    console.error('Error during fetch:', error);
});