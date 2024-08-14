import { getToken } from 'firebase/messaging';
import { getRecoil, setRecoil } from 'recoil-nexus';
import { accessTokenState, firebaseTokenState } from 'atoms/UserAtoms';
import { registerToken } from 'api/notificationApi';

export function requestPermission(messaging: any) {
  void Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {
      getToken(messaging, { vapidKey: process.env.REACT_APP_FIREBASE_VAPID_KEY })
        .then((token: string) => {
          registerToken(token).then(() => {
            setRecoil(firebaseTokenState, token);
          }).catch((error) => {
          })
        })
        .catch((err) => {
        })
    } else if (permission === 'denied') {
    }
  })
}