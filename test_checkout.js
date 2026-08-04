async function run() {
    const baseUrl = 'http://localhost:8080/api';
    const num = Math.floor(Math.random() * 1000000000);
    const username = `testuser${num}`;
    const phone = `9${String(num).padStart(9, '0')}`;

    let res = await fetch(`${baseUrl}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email: `${username}@test.com`, password: 'password', confirmPassword: 'password', fullName: 'Test User', mobileNumber: phone })
    });

    res = await fetch(`${baseUrl}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ identifier: username, password: 'password' })
    });
    const loginText = await res.text();
    const loginData = JSON.parse(loginText);
    const token = loginData.data.token;

    // Add many products to make the amount > 100,000
    res = await fetch(`${baseUrl}/cart`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
        body: JSON.stringify({ productId: 1, quantity: 2 }) // 2 * 64900 = 129800
    });
    
    res = await fetch(`${baseUrl}/addresses`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
        body: JSON.stringify({
            fullName: "Test",
            mobileNumber: phone,
            streetAddress: "123 Test St",
            city: "Test",
            state: "TS",
            postalCode: "12345",
            country: "US",
            isDefault: true
        })
    });
    const addressData = await res.json();
    const addressId = addressData.data.addressId;

    console.log("Placing Order with CARD (High Amount)...");
    res = await fetch(`${baseUrl}/orders`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
        body: JSON.stringify({ addressId, paymentMethod: 'CARD' })
    });
    console.log("Order Status:", res.status);
    console.log("Order Res:", await res.text());
}

run().catch(console.error);
