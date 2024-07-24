import Footer from './components/common/Footer';
import Header from './components/common/Header';
import CommonRoute from 'pages/Routes/CommonRoute';
import LoginRoute from 'pages/Routes/LoginRoute';
import ProfileRoute from 'pages/Routes/ProfileRoute';

function App() {
  return (
    <div className="flex flex-col relative">
        <div className="flex flex-col max-h-screen">
          <Header />
          <div className="flex-grow">
            <CommonRoute />
            <LoginRoute />
            <ProfileRoute />
            <div className="flex flex-col max-h-screen">
              <Footer />
            </div>
          </div>
        </div>
    </div>

  );
}

export default App;
