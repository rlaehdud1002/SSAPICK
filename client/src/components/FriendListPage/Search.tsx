import { Button } from "components/ui/button";
import { Input } from "components/ui/input";
import { useForm } from "react-hook-form";

interface FriendSearchForm {
  search: string;
}

const Search = () => {

  const { register, handleSubmit, setValue } = useForm<FriendSearchForm>();

  const onSubmit = (data: FriendSearchForm) => {
    console.log(data)
  }

  const onInvalid = (errors: any) => {
    console.log(errors)
  }

  return (
    <form onSubmit={handleSubmit(onSubmit, onInvalid)}>
      <div className="flex w-80 items-center space-x-2 ml-12">
        <Input className="bg-white/50 h-10" type="text" placeholder="친구 검색" register={register("search", {})} />
        {/* <Button className="background-color-5F86E9" type="submit">검색</Button> */}
      </div>
    </form>
  )
}

export default Search;