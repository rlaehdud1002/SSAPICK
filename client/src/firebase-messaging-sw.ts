import { getToken, onMessage } from 'firebase/messaging';
import { initializeApp } from "firebase/app";
import { getMessaging } from 'firebase/messaging';
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
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID,
};

const firebaseApp = initializeApp(firebaseConfig);
const messaging = getMessaging(firebaseApp);

export { messaging };