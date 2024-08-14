// importScripts('https://www.gstatic.com/firebasejs/10.11.0/firebase-app-compat.js');
// importScripts('https://www.gstatic.com/firebasejs/10.11.0/firebase-messaging-compat.js');


// self.addEventListener("install", function (e) {
//   self.skipWaiting();
// });

// self.addEventListener("activate", function (e) {
// });

// self.addEventListener("push", function (e) {
//   if (!e.data.json()) return;

//   const resultData = e.data.json().notification;
//   const notificationTitle = resultData.title;
//   const notificationOptions = {
//     body: resultData.body,
//     icon: '/images/icons/ssapick-128x128.png',
//     tag: resultData.tag,
//     ...resultData,
//   };
//   self.registration.showNotification(notificationTitle, notificationOptions);
// });

// self.addEventListener("notificationclick", function (event) {
//   const url = "/";
//   event.notification.close();
//   event.waitUntil(clients.openWindow(url));
// });

// public/firebase-messaging-sw.js
importScripts('https://www.gstatic.com/firebasejs/9.0.0/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.0.0/firebase-messaging-compat.js');

firebase.initializeApp({
  "apiKey":"AIzaSyCrfqljIoiXVP6VteqRMtpdBWmYrJ40iKc","authDomain":"ssapick-40aee.firebaseapp.com","projectId":"ssapick-40aee","storageBucket":"ssapick-40aee.appspot.com","messagingSenderId":"995065367267","appId":"1:995065367267:web:460378a858f316784c13d6","measurementId":"G-1GVCVHX5F4"});

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
    icon: '/firebase-logo.png'
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});