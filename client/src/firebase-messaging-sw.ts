import { getToken } from 'firebase/messaging';
import { initializeApp } from "firebase/app";
import { getMessaging, onMessage } from 'firebase/messaging';
import { getRecoil, setRecoil } from 'recoil-nexus';
import { accessTokenState, firebaseTokenState } from 'atoms/UserAtoms';
import { onBackgroundMessage } from "firebase/messaging/sw";
import { registerToken } from 'api/notificationApi';


export function requestPermission(messaging: any) {
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