import InstallAndroid from 'components/InstallGuidePage/InstallAndroid';
import InstallIOS from 'components/InstallGuidePage/InstallIOS';
import LocationGuide from 'components/InstallGuidePage/LocationGuide';

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
