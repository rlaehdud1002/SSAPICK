import { getToken } from 'firebase/messaging';
import { initializeApp } from "firebase/app";
import { getMessaging, onMessage } from 'firebase/messaging';
import { getRecoil, setRecoil } from 'recoil-nexus';
import { accessTokenState, firebaseTokenState } from 'atoms/UserAtoms';
import { onBackgroundMessage } from "firebase/messaging/sw";
import { registerToken } from 'api/notificationApi';

const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_FIREBASE_APP_ID,
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID
};

const firebaseApp = initializeApp(firebaseConfig);
const messaging = getMessaging(firebaseApp);

export function requestPermission() {
  const accessToken = getRecoil(accessTokenState);
  const firebaseToken = getRecoil(firebaseTokenState);
  if (!accessToken && firebaseToken) return;

  void Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {
      getToken(messaging, { vapidKey: process.env.REACT_APP_FIREBASE_VAPID_KEY })
        .then((token: string) => {
          registerToken(token);
          setRecoil(firebaseTokenState, token);
        })
        .catch((err) => {
        })
    } else if (permission === 'denied') {
    }
  })
}

onMessage(messaging, (payload) => {
  console.log('Message received. ', payload);
});

// onBackgroundMessage(messaging, (payload) => {
//   console.log('[firebase-messaging-sw.js] Received background message ', payload);
//   // Customize notification here
//   const notificationTitle = 'Background Message Title';
//   const notificationOptions = {
//     body: 'Background Message body.',
//     icon: '/firebase-logo.png'
//   };

//   self.registration.showNotification(notificationTitle,
//     notificationOptions);
// });