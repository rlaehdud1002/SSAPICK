import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
} from "components/ui/card";


const Pick = () => {
  return (
    <div>
      <Card>
        <CardHeader>
          <CardDescription>Card Description</CardDescription>
        </CardHeader>
        <CardContent>
          <p>Card Content</p>
        </CardContent>
        <CardFooter>
          <p>Card Footer</p>
        </CardFooter>
      </Card>



    </div>
  );
};

export default Pick;
