# Deeplinking-Android

This project contains deeplinking using intent filters in Android which works with custom Url which when hits redirects to the application again with Url specific path variables displayed on an Alert Dialog inside UI.

This also contains a simple network call architecture (MVVM) which uses retrofit and moshi and calls a public api endpoint and shows the json inside an alert dialog. There is also a copy feature to copy the json.

All of the code is written in Jetpack Compose.

<h1> Steps to run </h1>

<ul>
    <li>Clone the github repo and run the application on an Android Emulator or Device</li>
    <li>You can call the API using the button on the UI</li>
    <li>The custom url which redirects to the application should be formatted like <strong>sc-assignment://home/redirect?status={x}&code={y}&data={z}</strong> 
        Here,
        <ul>
<li>a. x can be any String</li>
<li>b. y can be any Integer</li>
<li>c. z can be any String that is json decodable</li>
        </ul>
    </li>
    <li>Suppose x = "abcd123" ,y = 100 and z is a json string encoded as "%7B%22name%22%3A%22xyz%22%2C%22age%22%3A20%7D"  so the url becomes sc-assignment://home/redirect?status=abcd123&code=100&data=%7B%22name%22%3A%22xyz%22%2C%22age%22%3A20%7D</li>
    <li>Click the open in app browser button and paste this url into the url textfield. Now the app reopens with the expected values of x, y and z (with decoded json)</li>
</ul>
Attaching screenshot for reference :
https://drive.google.com/file/d/1HZ-IWBMKkxiZo1tCHbh-TnXsors8YtXs/view?usp=share_link
