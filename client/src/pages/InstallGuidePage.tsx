import LocationGuide from 'components/InstallGuidePage/LocationGuide';
import Install from 'components/InstallGuidePage/Install';

const InstallGuidePage = () => {
  return (
    <div className="flex flex-col splash absolute w-screen">
      <Install />
      <LocationGuide />
    </div>
  );
};

export default InstallGuidePage;
