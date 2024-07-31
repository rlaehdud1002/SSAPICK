import CommonRoute from 'components/Routes/CommonRoute';
import LoginRoute from 'components/Routes/LoginRoute';
import ProfileRoute from 'components/Routes/ProfileRoute';
import { useLocation } from 'react-router-dom';
import Footer from './components/common/Footer';
import Header from './components/common/Header';
import { RecoilRoot } from 'recoil';

function App() {
  const location = useLocation().pathname.split('/')[1];
  console.log(location);

  return (
   <RecoilRoot>
    <div className="flex flex-col relative">
      <div className="flex flex-col max-h-screen">
        {(location !== 'login' && location !== 'splash') && <Header />}
        <div className="flex-grow">
          <CommonRoute />
          <LoginRoute />
          <ProfileRoute />
          <div className="flex flex-col max-h-screen">
            {(location !== 'login' && location !== 'splash') && <Footer />}
          </div>
        </div>
      </div>
    </div>
    </RecoilRoot>

  );
}

export default App;
