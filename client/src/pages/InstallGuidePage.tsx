import LocationGuide from 'components/InstallGuidePage/LocationGuide';
import InstallIOS from 'components/InstallGuidePage/InstallIOS';
import InstallAndroid from 'components/InstallGuidePage/InstallAndroid';

const InstallGuidePage = () => {
  return (
    <div className="flex flex-col splash absolute w-screen">
      <InstallIOS />
      <InstallAndroid />
      <LocationGuide />
    </div>
  );
};

export default InstallGuidePage;
