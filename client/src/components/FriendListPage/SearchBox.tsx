import { Input } from "components/ui/input";
import { useEffect } from "react";
import { useForm } from "react-hook-form";

interface FriendSearchForm {
  search: string;
}

const Search = () => {
  
  const { register, handleSubmit, reset, formState: { isSubmitSuccessful } } = useForm<FriendSearchForm>();

  useEffect(() => {
    if (isSubmitSuccessful) {
      reset()
    }
  }, [isSubmitSuccessful, reset]);

  const onSubmit = (data: FriendSearchForm) => {
    console.log(data)

  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="flex relative items-center space-x-2 m-auto">
        <div className="w-4/5 flex flex-row m-auto">
          <Input className="w-full bg-white h-10" type="text" placeholder="친구 검색" register={register("search")} />
        </div>
      </div>
    </form>
  )
}

export default Search;